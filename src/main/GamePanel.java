package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import items.UI;
import monster.MON_GreenSlime;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	final int originalTileSize=16;
	final int scale=3;
	public final int tileSize=originalTileSize*scale;
	public final int maxScreenCol=20;
	public final int maxScreenRow=12;
	public final int screenWidth=tileSize*maxScreenCol;
	public final int screenHeight=tileSize*maxScreenRow;
	
	public final int maxWorldCol=64;
	public final int maxWorldRow=79;
	
	int screenWidth2=screenWidth;
	int screenHeight2=screenHeight;
	BufferedImage tempScreen;
	Graphics2D g2;
	public boolean fullScreenOn=false;
	
	int FPS=60;
	
	TileManager tileM=new TileManager(this);
	public KeyHandler keyH=new KeyHandler(this);
	public Sound music=new Sound();
	public Sound se=new Sound();
	public CollisionChecker cChecker=new CollisionChecker(this);
	public AssetSetter aSetter=new AssetSetter(this);
	public UI ui=new UI(this);
	public EventHandler eHandler=new EventHandler(this);
	public Thread gameThread;
	public Player player=new Player(this,keyH);
	
	public Entity obj[]=new Entity[20];
	public Entity npc[]=new Entity[10];
	public Entity monster[]=new Entity[20];
	ArrayList<Entity> entityList=new ArrayList<>();
	
	public int gameState;
	public final int titleState=0;
	public final int gameOverState=6;
	public final int playState=1;
	public final int pauseState=2;
	public final int dialogueState=3;
	public final int optionsState=5;
	
	public int wave = 0;
	public int enemiesPerWave = 5;
	public int enemiesRemaining = 0;
	
	public int monstersKilled = 0;
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		this.setBackground(Color.CYAN);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
	}
	
	public void setupGame()
	{
		aSetter.setNPC();
		aSetter.setObject();
		aSetter.setMonster();
		playMusic(0);
		gameState=titleState;
		
		tempScreen=new BufferedImage(screenWidth,screenHeight,BufferedImage.TYPE_INT_ARGB);
		g2=(Graphics2D)tempScreen.getGraphics();
		
		setFullScreen();
	}
	public void restart()
	{
		player.setDefaultPositions();
		player.setDefaultValues();
		aSetter.setNPC();
		aSetter.setObject();
		aSetter.setMonster();
		player.hasGateKey=0;
		player.hasKey=0;
		player.hasMasterKey=0;
	}
	public void setFullScreen()
	{
		GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice gd=ge.getDefaultScreenDevice();
		gd.setFullScreenWindow(Main.window);
		
		screenWidth2=Main.window.getWidth();
		screenHeight2=Main.window.getHeight();
	}
	
	public void startGameThread() {
		gameThread=new Thread(this);
		
		gameThread.start();
	}


	public void run() {
		double drawInterval=1000000000/FPS;
		double delta=0;
		long lastTime=System.nanoTime();
		long currentTime;
		long timer=0;
		int drawCount=0;
		while(gameThread!=null) {
			currentTime=System.nanoTime();
			delta+=(currentTime-lastTime)/drawInterval;
			timer+=(currentTime-lastTime);
			lastTime=currentTime;
			if(delta>=1) {
			update();
			drawToTempScreen();
			drawToScreen();
			delta--;
			drawCount++;
		}
			if(timer>=1000000000) {
				System.out.println("FPS:"+drawCount);
				drawCount=0;
				timer=0;
			}
		
		}
	}
	
	

		public void startWave() {

		    enemiesPerWave = wave * 5;
		    enemiesRemaining = enemiesPerWave;

		    spawnWave();

		    ui.showMessage("WAVE " + wave);
		}

		private void spawnWave() {

		    Random rand = new Random();

		    for (int i = 0; i < monster.length; i++) {
		        monster[i] = null;
		    }

		    int spawned = 0;

		    int minCol = 2;
		    int maxCol = 62; // 64 - 2

		    int minRow = 2;
		    int maxRow = 47; // 49 - 2

		    while (spawned < enemiesPerWave) {

		        int col = minCol + rand.nextInt(maxCol - minCol);
		        int row = minRow + rand.nextInt(maxRow - minRow);

		        MON_GreenSlime m = new MON_GreenSlime(this);

		        m.worldX = tileSize * col;
		        m.worldY = tileSize * row;

		        monster[spawned] = m;
		        spawned++;
		    }
		}

		
	

	public void update() {

	    if (gameState == playState) {

	        // PLAYER
	        player.update();

	        // NPCs
	        for (int i = 0; i < npc.length; i++) {
	            if (npc[i] != null) {
	                npc[i].update();
	            }
	        }

	        // MONSTERS
	        for (int i = 0; i < monster.length; i++) {

	            if (monster[i] != null) {

	                if (monster[i].alive && !monster[i].dying) {
	                    monster[i].update();
	                }

	                // WHEN MONSTER DIES
	                if (!monster[i].alive) {
	                    monster[i] = null;

	                    // IMPORTANT: decrease wave counter
	                    monstersKilled++;   
	                    enemiesRemaining--;
	                }
	            }
	        }

	        // ================= WAVE SYSTEM =================
	        if (gameState == playState && enemiesRemaining <= 0) {
	            wave++;
	            startWave();
	        }
	    }

	    else if (gameState == pauseState) {
	        // game paused → nothing updates
	    }
	}
	public void drawToTempScreen()
	{
		long drawStart=0;
		if(keyH.checkDrawTime==true)
		{
			drawStart=System.nanoTime();
		}
		
		if(gameState==titleState)
		{
			ui.draw(g2);
		}
		else {

		    tileM.draw(g2);

		    entityList.clear(); 

		    entityList.add(player);

		    for (int i=0;i<npc.length; i++) {
		        if (npc[i]!= null) {
		            entityList.add(npc[i]);
		        }
		    }

		    for (int i=0;i<obj.length;i++) {
		        if (obj[i]!=null) {
		            entityList.add(obj[i]);
		        }
		    }
		    
		    for (int i=0;i<monster.length;i++) {
		        if (monster[i]!=null) {
		            entityList.add(monster[i]);
		        }
		    }


		    entityList.sort((e1, e2)->
		        Integer.compare(e1.worldY, e2.worldY)
		    );


		    for (Entity e : entityList) {
		        e.draw(g2);
		    }

		    ui.draw(g2);
		}

		
		if(keyH.checkDrawTime==true) {
		long drawEnd=System.nanoTime();
		long passed=drawEnd-drawStart;
		g2.setColor(Color.white);
		g2.drawString("Draw Time:"+passed, 10, 400);
		System.out.println("Draw Time:"+passed);
		}
	}
	public void drawToScreen()
	{
		Graphics g=getGraphics();
		g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2,null);
		g.dispose();
	}
	public void playMusic(int i)
	{
		music.setFile(i);
		music.play();
		music.loop();
	}
	public void stopMusic()
	{
		music.stop();
	}
	public void playSE(int i)
	{
		se.setFile(i);
		se.play();
	}
	
}    

