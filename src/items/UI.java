package items;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.DecimalFormat;
import entity.Entity;
import javax.imageio.ImageIO;

import main.GamePanel;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	BufferedImage heart_full,heart_half,heart_blank;
	Font arial_40,courier_80B,timemessage;
	public boolean messageOn=false;
	public String message="";
	int messageCounter=0;
	public boolean gameFinished=false;
	public String currentDialogue="";
	public int commandNum=0;
	double playTime;
	BufferedImage keyImage;
	DecimalFormat dFormat=new DecimalFormat("#0.00");
	public int subState=0;
	
	public UI(GamePanel gp) {
		this.gp=gp;
		arial_40=new Font("Arial",Font.BOLD,30);
		courier_80B=new Font("Courier New",Font.ITALIC,80);
		timemessage=new Font("monospaced",Font.BOLD,20);
		
		OBJ_key key=new OBJ_key(gp); 
		keyImage=key.down1;
		
		Entity heart=new OBJ_Heart(gp);
		heart_full=heart.image;
		heart_half=heart.image2;
		heart_blank=heart.image3;
	}
	public void showMessage(String text)
	{
		message=text;
		messageOn=true;
	}
	
	public void draw(Graphics2D g2)
	{
		if(gameFinished==true)
		{
			g2.setFont(arial_40);
			g2.setColor(Color.RED);
			
			String text;
			int textLenght;
			int x;
			int y;
			
			text="You found the treasure";
			textLenght=(int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		    x=gp.screenWidth/2-textLenght/2;
			y=gp.screenHeight/2-(gp.tileSize*3);
			g2.drawString(text, x, y);
			
			g2.setFont(new Font("TimesNewRoman",Font.BOLD,20));
			g2.setColor(Color.CYAN);
			text="Your Total Game Time is:"+dFormat.format(playTime);
			textLenght=(int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		    x=gp.screenWidth/2-textLenght/2;
			y=gp.screenHeight/2+(gp.tileSize*3);
			g2.drawString(text, x, y);
			
			g2.setFont(courier_80B);
			g2.setColor(Color.ORANGE);
			text="Congragulations";
			textLenght=(int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		    x=gp.screenWidth/2-textLenght/2;
			y=gp.screenHeight/2+(gp.tileSize*2);
			g2.drawString(text, x, y);
			gp.gameThread=null;
			
		}
		else
		{
		
		g2.setFont(arial_40);
		g2.setColor(Color.MAGENTA);
		g2.drawImage(keyImage, 10,80, gp.tileSize, gp.tileSize, null);
		g2.drawString("x"+gp.player.hasKey,50,120);
		
		// ================= WAVE INFO =================
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Arial", Font.BOLD, 20));

		g2.drawString("Wave: " + gp.wave, 10, 200);
		g2.drawString("Enemies Left: " + gp.enemiesRemaining, 10, 230);
		g2.drawString("Kills: " + gp.monstersKilled, 10, 260);
	
		g2.setFont(timemessage);
		g2.setColor(Color.WHITE);
		playTime+=(double)1/60;
		g2.drawString("Total Game Time"+dFormat.format(playTime),700,20);
		
		if(messageOn==true)
		{
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("TimesNewRoman",Font.BOLD,20));
			g2.drawString(message, 10, 160);
			messageCounter++;
			if(messageCounter>150)
			{
				messageCounter=0;
				messageOn=false;
			}
		}
	}
		this.g2=g2;
		g2.setFont(courier_80B);
		g2.setColor(Color.BLACK);
		
		if(gp.gameState==gp.titleState)
		{
			drawTitleScreen();
		}
		
		if(gp.gameState==gp.playState)
		{
			drawPlayerLife();
		}
		if(gp.gameState==gp.pauseState)
		{
			drawPlayerLife();
			drawPauseScreen();
		}
		if(gp.gameState==gp.dialogueState)
		{
			drawPlayerLife();
			drawDialogueScreen();
		}
		if(gp.gameState==gp.gameOverState)
		{
			drawGameOverScreen();
		}
		if(gp.gameState==gp.optionsState)
		{
			drawOptionsScreen();
		}
	}
	public void drawPlayerLife()
	{
		int x=gp.tileSize/2;
		int y=gp.tileSize/2;
		int i=0;
		
		while(i<gp.player.maxLife/2)
		{
			g2.drawImage(heart_blank, x, y, null);
			i++;
			x+=gp.tileSize;
		}
		
		x=gp.tileSize/2;
		y=gp.tileSize/2;
		i=0;
		while(i<gp.player.life)
		{
			g2.drawImage(heart_half, x, y,null);
			i++;
			if(i<gp.player.life)
			{
				g2.drawImage(heart_full, x, y,null);
			}
			i++;
			x+=gp.tileSize;
		}
	}
	public void drawTitleScreen()
	{
		g2.setColor(new Color(38, 71, 104));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
	    g2.setFont(new Font("Felix titling", Font.BOLD, 40));
		String text="Treasure  Island  Adventure";
		int x=getXforCenteredText(text);
		int y=gp.tileSize*2;
		
		g2.setColor(Color.BLACK);
		g2.drawString(text, x+7, y+7);
		
		g2.setColor(Color.WHITE);
		g2.drawString(text, x, y);
		
		x=gp.screenWidth/2-(gp.tileSize*2)/2;
		y+=gp.tileSize*1;
		g2.drawImage(gp.player.down1, x, y, gp.tileSize*2, gp.tileSize*2,null);
		
	    g2.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
	    g2.setColor(Color.WHITE);
	    text="NEW GAME";
	    x=getXforCenteredText(text);
	    y+=gp.tileSize*3;
	    g2.drawString(text, x, y);
	    if(commandNum==0)
	    {
	    	g2.drawString("->", x-gp.tileSize, y);
	    }
	    
	    g2.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
	    g2.setColor(Color.WHITE);
	    text="LOAD GAME";
	    x=getXforCenteredText(text);
	    y+=gp.tileSize*1.5;
	    g2.drawString(text, x, y);
	    if(commandNum==1)
	    {
	    	g2.drawString("->", x-gp.tileSize, y);
	    }
	    
	    
	    g2.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
	    g2.setColor(Color.WHITE);
	    text="EXIT";
	    x=getXforCenteredText(text);
	    y+=gp.tileSize*1.5;
	    g2.drawString(text, x, y);
	    if(commandNum==2)
	    {
	    	g2.drawString("->", x-gp.tileSize, y);
	    }
	    
	    g2.setFont(new Font("Felix titling", Font.BOLD, 25));
	    g2.setColor(new Color(0,0,0));
	    text="THIS IS THE GAME DEVELOPED BY KEERTHAN B.G";
	    x=getXforCenteredText(text);
	    y+=gp.tileSize*1.5;
	    g2.drawString(text, x+3, y+3);
	    g2.setColor(new Color(202, 230, 231));
	    g2.drawString(text, x, y);
	  
	    
	    
		
	}
	public void drawGameOverScreen()
	{
		g2.setColor(new Color(0,0,0,150));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		int x;
		int y;
		String text;
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 100f));
		
		text="You Died";
		g2.setColor(Color.black);
		x=getXforCenteredText(text);
		y=gp.tileSize*4;
		g2.drawString(text, x, y);
		
		text="You Died";
		g2.setColor(Color.white);
		g2.drawString(text, x-8, y-8);
		g2.drawString(text, x, y);
		
		g2.setFont(g2.getFont().deriveFont(50f));
		text="Retry";
		x=getXforCenteredText(text);
		y+=gp.tileSize*2;
		g2.drawString(text, x, y);
		if(commandNum==0)
		{
			g2.drawString(">", x-40, y);
		}
		
		text="Back to Title Screen";
		x=getXforCenteredText(text);
		y+=100;
		g2.drawString(text, x, y);
		if(commandNum==1)
		{
			g2.drawString(">", x-40, y);
		}
		
		text="Rage Quit";
		x=getXforCenteredText(text);
		y+=80;
		g2.drawString(text, x, y);
		if(commandNum==2)
		{
			g2.drawString(">", x-40, y);
		}
		
	}
	public void drawPauseScreen()
	{
		String text="GAME PAUSED";
		int x=getXforCenteredText(text);
		int y=gp.screenHeight/2;
		g2.drawString(text, x, y);
	}
	public void drawDialogueScreen()
	{
		
		int x=gp.tileSize*2;
		int y=gp.tileSize/2;
		int width=gp.screenWidth-(gp.tileSize*4);
		int height=gp.tileSize*4;
		drawSubWindow(x,y,width,height);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD,28));
		 g2.setColor(Color.white);
		x+=gp.tileSize;
		y+=gp.tileSize;
		for(String line:currentDialogue.split("\n"))
		{
		g2.drawString(line, x, y);
		y+=40;
		}
	}
	public void drawSubWindow(int x,int y,int width,int height)
	{
		Color c=new Color(0,0,0,180);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		c=new Color(109, 78, 104);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
	}
	public void drawSubWindowMenu(int x,int y,int width,int height)
	{
		Color c=new Color(208, 0, 255);
		g2.setColor(c);
		g2.fillRoundRect(x, y, width, height, 35, 35);
		c=new Color(232, 145, 39);
		g2.setColor(c);
		g2.setStroke(new BasicStroke(5));
		g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);
	}
	public int getXforCenteredText(String text)
	{
		int length=(int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x=gp.screenWidth/2-length/2;
		return x;
	}
	public void drawOptionsScreen()
	{
		g2.setColor(Color.white);
		g2.setFont(g2.getFont().deriveFont(32F));
		
		int frameX=gp.tileSize*4;
		int frameY=gp.tileSize;
		int frameWidth=gp.tileSize*12;
		int frameHeight=gp.tileSize*10;
		drawSubWindowMenu(frameX,frameY,frameWidth,frameHeight);
		
		switch(subState)
		{
		case 0: options_top(frameX,frameY);break;
		case 1: options_fullScreenNotification(frameX,frameY);break;
		case 2: options_control(frameX,frameY);break;
		case 3: options_endGameConfirmation(frameX,frameY);break;
		}
		
		gp.keyH.enterPressed=false;
	
	}
	public void options_top(int frameX,int frameY)
	{
		int textX;
		int textY;
		
		String text="Options";
		g2.setColor(Color.white);
		g2.setFont(new Font("Monospaced", Font.BOLD, 50));
		textX=getXforCenteredText(text);
		textY=frameY+gp.tileSize;
		g2.drawString(text, textX, textY);
		
		textX=frameX+gp.tileSize;
		textY+=gp.tileSize*2;
		g2.setFont(new Font("ComicSams", Font.BOLD, 30));
		g2.drawString("Full Screen", textX, textY);
		if(commandNum==0)
		{
			g2.setColor(Color.black);
			g2.drawString(">", textX-30, textY);
			if(gp.keyH.enterPressed==true)
			{
				if(gp.fullScreenOn==false)
				{
					gp.fullScreenOn=true;
				}
				else if(gp.fullScreenOn==true)
				{
					gp.fullScreenOn=false;
				}
				subState=1;
			}
		}
		
		textY+=gp.tileSize;
		g2.setColor(Color.white);
		g2.drawString("Music", textX, textY);
		if(commandNum==1)
		{
			g2.setColor(Color.black);
			g2.drawString(">", textX-30, textY);
		}
		
		textY+=gp.tileSize;
		g2.setColor(Color.white);
		g2.drawString("SE", textX, textY);
		if(commandNum==2)
		{
			g2.setColor(Color.black);
			g2.drawString(">", textX-30, textY);
		}
		
		textY+=gp.tileSize;
		g2.setColor(Color.white);
		g2.drawString("Control", textX, textY);
		if(commandNum==3)
		{
			g2.setColor(Color.black);
			g2.drawString(">", textX-30, textY);
			if(gp.keyH.enterPressed==true)
			{
				subState=2;
				commandNum=0;
			}
		}
		
		textY+=gp.tileSize;
		g2.setColor(Color.white);
		g2.drawString("Back", textX, textY);
		if(commandNum==4)
		{
			g2.setColor(Color.black);
			g2.drawString(">", textX-30, textY);
			if(gp.keyH.enterPressed==true)
			{
				gp.gameState=gp.playState;
				commandNum=0;
			}
		}
		
		textY+=gp.tileSize*2;
		g2.setColor(Color.RED);
		g2.drawString("Rage Quit", textX, textY);
		if(commandNum==5)
		{
			g2.setColor(Color.black);
			g2.drawString(">", textX-30, textY);
			if(gp.keyH.enterPressed==true)
			{
				subState=3;
				commandNum=0;
			}
		}
		
		textX=frameX+gp.tileSize*5+10;
		textY=frameY+120;
		g2.setColor(Color.BLACK);                  //Full Screen
		g2.setStroke(new BasicStroke(3));
		g2.drawRect(textX, textY, 24, 24);
		if(gp.fullScreenOn==true)
		{
			textX=frameX+gp.tileSize*5+12;
			textY=frameY+122;
			g2.setColor(Color.YELLOW); 
			g2.fillOval(textX, textY, 19, 19);
		}
		
		textX=frameX+gp.tileSize*5+10;
		textY=frameY+120;
		g2.setColor(Color.BLACK);
		textY+=gp.tileSize;
		g2.drawRect(textX, textY, 120, 20);         //Music
		int volumeWidth=24*gp.music.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
		g2.setColor(Color.BLACK);
		textY+=gp.tileSize;
		g2.drawRect(textX, textY, 120, 20);          //SE
		volumeWidth=24*gp.se.volumeScale;
		g2.fillRect(textX, textY, volumeWidth, 24);
		
	}
	public void options_fullScreenNotification(int frameX,int frameY)
	{
		int textX=frameX+gp.tileSize;
		int textY=frameY+gp.tileSize*3;
		
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("ComicSams", Font.BOLD, 30));
		currentDialogue="Full Screen mode\noption settings will\nbe updated soon";
		for(String line:currentDialogue.split("\n"))
		{
			g2.drawString(line, textX, textY);
			textY+=40;
		}
		
		textY=frameY+gp.tileSize*9;
		g2.drawString("Back", textX, textY);
		if(commandNum==0)
		{
			g2.drawString(">", textX-30, textY);
			if(gp.keyH.enterPressed==true)
			{
				subState=0;
			}
		}
	}
	public void options_control(int frameX,int frameY)
	{
		int textX;
		int textY;
		
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Monospaced", Font.BOLD, 45));
		String text="Controls";
		textX=getXforCenteredText(text);
		textY=frameY+gp.tileSize;
		g2.drawString(text, textX, textY);
		
		textX=frameX+gp.tileSize;
		textY+=gp.tileSize;
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("ComicSams", Font.BOLD, 30));
		g2.drawString("Move", textX, textY); textY+=gp.tileSize;
		g2.drawString("Interact/Attack", textX, textY); textY+=gp.tileSize;
		g2.drawString("Pause", textX, textY); textY+=gp.tileSize;
		g2.drawString("Rendering Time Display", textX, textY); textY+=gp.tileSize;
		g2.drawString("Options", textX, textY); textY+=gp.tileSize;
		
		
		g2.setColor(Color.GRAY);
		textX=frameX+gp.tileSize*9;
		textY=frameY+gp.tileSize*2;
		g2.drawString("W A S D", textX, textY);textY+=gp.tileSize;
		g2.drawString("ENTER", textX, textY);textY+=gp.tileSize;
		g2.drawString("P", textX, textY);textY+=gp.tileSize;
		g2.drawString("X", textX, textY);textY+=gp.tileSize;
		g2.drawString("ESC", textX, textY);textY+=gp.tileSize;
		
		textX=frameX+gp.tileSize;
		textY=frameY+gp.tileSize*9;    //Back in controls
		g2.setColor(Color.WHITE);
		g2.drawString("Back", textX, textY);
		if(commandNum==0)
		{
			g2.drawString(">", textX-25, textY);
			if(gp.keyH.enterPressed==true)
			{
				subState=0;
				commandNum=3;
			}
		}
	}
	
	public void options_endGameConfirmation(int frameX,int frameY)
	{
		int textX=frameX+gp.tileSize;
		int textY=frameY+gp.tileSize*3;
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("ComicSams", Font.BOLD, 45));
		currentDialogue="Quit game and return\n to title screen?";
		for(String line:currentDialogue.split("\n"))
		{
			g2.drawString(line, textX, textY);
			textY+=40;
		}
		
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Monospaced", Font.BOLD, 30));
		String text="No";
		textY+=gp.tileSize*3;
		g2.drawString(text, textX, textY);
		if(commandNum==1)                                         //No
		{
			g2.drawString(">", textX-30, textY);
			if(gp.keyH.enterPressed==true)
			{
				subState=0;
				commandNum=5;
			}
		}
		
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Monospaced", Font.BOLD, 20));
		text="Yes";
		textY+=gp.tileSize;                                        //Yes
		g2.drawString(text, textX, textY);
		if(commandNum==0)
		{
			g2.drawString(">", textX-30, textY);
			if(gp.keyH.enterPressed==true)
			{
				subState=0;
				gp.gameState=gp.titleState;
			}
		}
	}
}

