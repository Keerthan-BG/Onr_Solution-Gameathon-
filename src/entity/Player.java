package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.Rectangle;
import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity {
	KeyHandler keyH;
	public final int screenX;
	public final int screenY;
	public int hasKey=0;
	public int hasMasterKey=0;
	public int hasGateKey=0;
	public int hasTreasure=0;
	public int hasSword=0;
	private BufferedImage attackUp1;
	public Player(GamePanel gp,KeyHandler keyH) {
		super(gp);
		this.keyH=keyH;
		screenX=gp.screenWidth/2-(gp.tileSize/2);
		screenY=gp.screenHeight/2-(gp.tileSize/2);
		
		solidArea=new Rectangle();
		solidArea.x=8;
		solidArea.y=16;
		solidAreaDefaultX=solidArea.x;
		solidAreaDefaultY=solidArea.y;
		solidArea.width=32;
		solidArea.height=32;
		attackArea.width=36;
		attackArea.height=36;
		setDefaultValues();
		getPlayerImage();
		getPlayerAttackImage();
	}
		
		public void setDefaultValues() {
			worldX=gp.tileSize*53;
			worldY=gp.tileSize*12;
			speed=4;
			direction="down";
			
			maxLife=8;
			life=maxLife;
			up1=setup("/player/boyback1",gp.tileSize,gp.tileSize);
			up2=setup("/player/boyback2",gp.tileSize,gp.tileSize);
			down1=setup("/player/boyfront1",gp.tileSize,gp.tileSize);
			down2=setup("/player/boyfront2",gp.tileSize,gp.tileSize);
			right1=setup("/player/boyright1",gp.tileSize,gp.tileSize);
			right2=setup("/player/boyright2",gp.tileSize,gp.tileSize);
			left1=setup("/player/boyleft1",gp.tileSize,gp.tileSize);
			left2=setup("/player/boyleft2",gp.tileSize,gp.tileSize);
		}
		public void setDefaultPositions()
		{
			worldX=gp.tileSize*32;
			worldY=gp.tileSize*34;
		}
		public void getPlayerImage() {
		
			up1=setup("/player/boyback11",gp.tileSize,gp.tileSize);
			up2=setup("/player/boyback12",gp.tileSize,gp.tileSize);
			down1=setup("/player/boydown11",gp.tileSize,gp.tileSize);
			down2=setup("/player/boydown22",gp.tileSize,gp.tileSize);
			right1=setup("/player/boyright11",gp.tileSize,gp.tileSize);
			right2=setup("/player/boyright22",gp.tileSize,gp.tileSize);
			left1=setup("/player/boyleft11",gp.tileSize,gp.tileSize);
			left2=setup("/player/boyleft22",gp.tileSize,gp.tileSize);
		}
		public void getPlayerAttackImage()
		{
			attackUp1=setup("/player/attackup11",gp.tileSize,gp.tileSize*2);
			attackUp2=setup("/player/attackup21",gp.tileSize,gp.tileSize*2);
			attackDown1=setup("/player/attackdown11",gp.tileSize,gp.tileSize*2);
			attackDown2=setup("/player/attackdown12",gp.tileSize,gp.tileSize*2);
			attackLeft1=setup("/player/attackleft11",gp.tileSize*2,gp.tileSize);
			attackLeft2=setup("/player/attackleft21",gp.tileSize*2,gp.tileSize);
			attackRight1=setup("/player/attackright11",gp.tileSize*2,gp.tileSize);
			attackRight2=setup("/player/attackright21",gp.tileSize*2,gp.tileSize);
		}

		public void update() {
			
			if(attacking==true)
			{
				attacking();
			}
			
			else if(keyH.downPressed==true||keyH.upPressed==true||keyH.leftPressed==true||keyH.rightPressed==true||keyH.enterPressed==true) {
			 if(keyH.downPressed==true) {
				 	direction="down";
					
				}
			else if(keyH.upPressed==true) {
					direction="up";
				
				}
				
				else if(keyH.leftPressed==true) {
					direction="left";
				
				}
				else if(keyH.rightPressed==true) {
					direction="right";
				
				}
			 
			 collisionOn=false;
			 gp.cChecker.checkTile(this);
			int objIndex= gp.cChecker.checkObject(this, true);
			pickUpObject(objIndex);
			
			int npcIndex=gp.cChecker.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			int monsterIndex=gp.cChecker.checkEntity(this,gp.monster);
			contactMonster(monsterIndex);
			
			gp.eHandler.checkEvent();
        
			 if(collisionOn==false && keyH.enterPressed==false)
			 {
				 switch(direction)
				 {
				 case "up":
					 worldY-=speed;
					 break;
				 case "down":
						worldY+=speed;
					 break;
				 case "left":
						worldX-=speed;
					 break;
				 case "right":
						worldX+=speed;
					 break;
				 }
			 }
			    gp.keyH.enterPressed=false;
			 
			 spriteCounter++;
			 if(spriteCounter>10) {
				 if(spriteNum==1) {
					 spriteNum=2;
				 }
				 else if(spriteNum==2) {
					 spriteNum=1;
				 }
				 spriteCounter=0;
			 }
			}
			
			if(invincible==true)
			{
				invincibleCounter++;
				if(invincibleCounter>60)
				{
					invincible=false;
					invincibleCounter=0;
				}
				if(life<=0)
				{
					gp.gameState=gp.gameOverState;
				}
			}
			
		}
		public void attacking()
		{
			spriteCounter++;
			if(spriteCounter<=5)
			{
				spriteNum=1;
			}
			if(spriteCounter>5 && spriteCounter<=25)
			{
				spriteNum=2;
				int currentWorldX=worldX;
				int currentWorldY=worldY;
				int solidAreaWidth=solidArea.width;
				int solidAreaHeight=solidArea.height;
				
				switch(direction)
				{
				case "up":worldY-=attackArea.height;break;
				case "down":worldY+=attackArea.height;break;
				case "left":worldX-=attackArea.width;break;
				case "right":worldX+=attackArea.width;break;
				}
				solidArea.width=attackArea.width;
				solidArea.height=attackArea.height;
				
				int monsterIndex=gp.cChecker.checkEntity(this,gp.monster);
				damageMonster(monsterIndex);
				
				worldX=currentWorldX;
				worldY=currentWorldY;
				solidArea.width=solidAreaWidth;
				solidArea.height=solidAreaHeight;
			}
			if(spriteCounter>25)
			{
				spriteNum=1;
				spriteCounter=0;
				attacking=false;
			}
		}
		public void pickUpObject(int i)
		{
			if(i!=999)
			{
				String objectName=gp.obj[i].name;
				switch(objectName)
				{
				case "Key":
					gp.playSE(1);
					hasKey++;
					gp.obj[i]=null;
					gp.ui.showMessage("You picked up a key");
					break;
				case "Door":
					if(hasKey>0)
					{
						gp.playSE(3);
						gp.obj[i]=null;
						hasKey--;
						gp.ui.showMessage("You  opened the door");
					}
					else
					{
						gp.ui.showMessage("You need a key to unlock this door");
					}
					System.out.println("Key:"+hasKey);
					break;
				case "BloodSword":
					hasTreasure=1;
					gp.ui.showMessage("You picked up Blood Sword\n Attack =+3");
					speed-=1;
					gp.ui.showMessage("\n Speed--");
					hasSword++;
					if(hasSword>0)
					{
						gp.playSE(3);
						gp.obj[i]=null;
					}
					break;
				case "Boots":
					gp.ui.showMessage("Your speed has been increased");
					speed+=2;
					gp.playSE(2);
					try {
						up1=ImageIO.read(getClass().getResourceAsStream("/playerwithboots/boywithboots.png"));
						up2=ImageIO.read(getClass().getResourceAsStream("/playerwithboots/boywithbootss.png"));
						down1=ImageIO.read(getClass().getResourceAsStream("/playerwithboots/boywithbootsfront1.png"));
						down2=ImageIO.read(getClass().getResourceAsStream("/playerwithboots/boyfrontwithboots2.png"));
						right1=ImageIO.read(getClass().getResourceAsStream("/playerwithboots/boyrightwithboots1.png"));
						right2=ImageIO.read(getClass().getResourceAsStream("/playerwithboots/boyrightwithboots2.png"));
						left1=ImageIO.read(getClass().getResourceAsStream("/playerwithboots/boyleftwithboots1.png"));
						left2=ImageIO.read(getClass().getResourceAsStream("/playerwithboots/boylefttwithboots2.png"));
						
					}catch(IOException e) {
						e.printStackTrace();
					}

					gp.obj[i]=null;
					break;
				case "Chest":
					if(hasMasterKey==1) 
					{
						hasTreasure=1;
						gp.ui.showMessage("You acquired Treasure");
					}
					else
					{
						gp.ui.showMessage("Locked you need a master key");
					}
					break;
				case "boat":
				{
					if(hasTreasure==1)
					{
						gp.ui.gameFinished=true;
						gp.stopMusic();
						gp.playSE(i);
					}
					else
					{
						gp.ui.showMessage("Cannot go back without the Treasure");
					}
				}
				break;
				case "MasterKey":
					gp.playSE(1);
					hasMasterKey++;
					gp.obj[i]=null;
					gp.ui.showMessage("You picked up Master key");
					break;
				
				case "gateKey":
				{
					gp.playSE(1);
					hasGateKey++;
					gp.obj[i]=null;
					gp.ui.showMessage("You picked the Gate Key");
					break;
				}
				case "Gate":
				{
					if(hasGateKey==1)
					{
						gp.obj[i]=null;
						hasGateKey--;
						gp.ui.showMessage("You opened the Gate");
					}
					else
					{
						gp.ui.showMessage("I need a Gate Key");
					}
				}
				
				}
			}
			
		}
		public void interactNPC(int i)
		{
			if(gp.keyH.enterPressed==true)
			{
			if(i!=999)
			{
					gp.gameState=gp.dialogueState;
					gp.npc[i].speak();
			}
				
				else
				{
					gp.playSE(5);	
					attacking=true;
				}
			}
			
		}
		public void contactMonster(int i)
		{
			if(i!=999)
			{
				if(invincible==false)
				{
					gp.playSE(6);	
					life-=1;
					invincible =true;
				}
			}
		}
		public void damageMonster(int i)
		{
			if(i!=999)
			{
				if(gp.monster[i].invincible==false)
				{
					gp.playSE(7);
					gp.monster[i].life-=1;
					gp.monster[i].invincible=true;
					gp.monster[i].damageReaction();
					if(gp.monster[i].life<=0)
					{
						gp.monster[i].dying=true;
					}
				}
			}
		
		}
		
		public void draw(Graphics2D g2) {

		    BufferedImage image=null;
		    int tempScreenX=screenX;
		    int tempScreenY=screenY;
		    int width=gp.tileSize;
		    int height=gp.tileSize;

		    switch(direction) {

		        case "up":
		            if(!attacking) {
		                image=(spriteNum==1)?up1:up2;
		            } else {
		                image=(spriteNum==1)?attackUp1:attackUp2;
		                height=gp.tileSize*2;   
		                tempScreenY=screenY-gp.tileSize;
		            }
		            break;

		        case "down":
		            if(!attacking) {
		                image=(spriteNum==1)?down1:down2;
		            } else {
		                image=(spriteNum==1)?attackDown1:attackDown2;
		                height=gp.tileSize*2;       
		            }
		            break;

		        case "left":
		            if(!attacking) {
		                image=(spriteNum==1)?left1:left2;
		            } else {
		                image=(spriteNum==1)?attackLeft1:attackLeft2;
		                width=gp.tileSize*2;
		                tempScreenX=screenX-gp.tileSize;
		            }
		            break;

		        case "right":
		            if(!attacking) {
		                image=(spriteNum==1)?right1:right2;
		            } else {
		                image=(spriteNum==1)?attackRight1:attackRight2;
		                width=gp.tileSize*2;           
		            }
		            break;
		    }

		    if(invincible) {
		        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.7f));
		    }

		    g2.drawImage(image,tempScreenX,tempScreenY,width,height,null);

		    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,1f));
		}

}
