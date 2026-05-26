package main;

import java.awt.Rectangle;

public class EventHandler {
	
	GamePanel gp;
	EventRect eventRect[][];
	int previousEventX,previousEventY;
	boolean canTouchEvent=true;
	public EventHandler(GamePanel gp)
	{
		this.gp=gp;
		eventRect=new EventRect[gp.maxWorldCol][gp.maxWorldRow];
		int col=0;
		int row=0;
		while(col<gp.maxWorldCol && row<gp.maxWorldRow)
		{
			eventRect[col][row]=new EventRect();
			eventRect[col][row].x=23;
			eventRect[col][row].y=23;
			eventRect[col][row].width=2;
			eventRect[col][row].height=2;
			eventRect[col][row].eventRectDefaultX=eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY=eventRect[col][row].y;
			col++;
			if(col==gp.maxWorldCol)
			{
				col=0;
				row++;
			}
		}
		
		}
	
	
	public void checkEvent()
	{
		int xDistance=Math.abs(gp.player.worldX-previousEventX);
		int yDistance=Math.abs(gp.player.worldY-previousEventY);
		int distance=Math.max(xDistance, yDistance);
		if(distance>gp.tileSize)
		{
			canTouchEvent=true;
		}
		if(canTouchEvent==true)
		{
			
			if(hit(18,52,"any")==true){damagePit(18,52,gp.dialogueState);}
			if(hit(41,54,"any")==true){damagePit(41,54,gp.dialogueState);}
			if(hit(22,28,"any")==true){damagePit(22,28,gp.dialogueState);}
			if(hit(15,23,"any")==true){damagePit(15,23,gp.dialogueState);}
			if(hit(16,25,"any")==true){damagePit(16,25,gp.dialogueState);}
			
			if(hit(53,12,"any")==true){healingPool(53,12,gp.dialogueState);}
			if(hit(38,49,"any")==true){healingPool(59,17,gp.dialogueState);}
			if(hit(12,12,"any")==true){healingPool(12,12,gp.dialogueState);}
			if(hit(21,41,"any")==true){healingPool(21,41,gp.dialogueState);}
		
		}
	}
	public boolean hit(int col,int row,String reqDirection)
	{
		boolean hit=false;
		gp.player.solidArea.x=gp.player.worldX+gp.player.solidArea.x;
		gp.player.solidArea.y=gp.player.worldY+gp.player.solidArea.y;
		eventRect[col][row].x=col*gp.tileSize+eventRect[col][row].x;
		eventRect[col][row].y=row*gp.tileSize+eventRect[col][row].y;
		
		if(gp.player.solidArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone==false) {
			if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any"))
			{
				hit=true;
				
				previousEventX=gp.player.worldX;
				previousEventY=gp.player.worldY;
			}
		}
		gp.player.solidArea.x=gp.player.solidAreaDefaultX;
		gp.player.solidArea.y=gp.player.solidAreaDefaultY;
		eventRect[col][row].x=eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y=eventRect[col][row].eventRectDefaultY;
		
		return hit;
	}
	public void damagePit(int col,int row,int gameState)
	{
		gp.gameState=gameState;
		gp.ui.currentDialogue="You stepped on sharp stones";
		gp.player.life-=1;
		canTouchEvent=false;
	}
	public void healingPool(int col,int row,int gameState)
	{
			if(gp.keyH.enterPressed==true)
			{
			gp.gameState=gameState;
			gp.ui.currentDialogue="You drank elixer\nYour health has been restored";
			gp.player.life=gp.player.maxLife;
			}
	
}
}
