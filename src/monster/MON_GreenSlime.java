package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_GreenSlime extends Entity {
	
	GamePanel gp;

	public MON_GreenSlime(GamePanel gp) {
		super(gp);
		this.gp=gp;
		type=2;
		name="Parasite";
		speed=2;
		maxLife=5;
		life=maxLife;
		
		solidArea.x=3;
		solidArea.y=18;
		solidArea.width=48;
		solidArea.height=48;
		solidAreaDefaultX=solidArea.x;
		solidAreaDefaultY=solidArea.y;
		getImage();
			
	}
	public void getImage()
	{
		up1=setup("/monster/zombieup1",gp.tileSize,gp.tileSize);
		up2=setup("/monster/zombieup2",gp.tileSize,gp.tileSize);
		down1=setup("/monster/zombiedown1",gp.tileSize,gp.tileSize);
		down2=setup("/monster/zombiedown2",gp.tileSize,gp.tileSize);
		left1=setup("/monster/zombieleft1",gp.tileSize,gp.tileSize);
		left2=setup("/monster/zombieleft2",gp.tileSize,gp.tileSize);
		right1=setup("/monster/zombieright1",gp.tileSize,gp.tileSize);
		right2=setup("/monster/zombieright2",gp.tileSize,gp.tileSize);
	}

		public void setAction() {

		    int dx = gp.player.worldX - worldX;
		    int dy = gp.player.worldY - worldY;

		    if (Math.abs(dx) > Math.abs(dy)) {
		        direction = (dx > 0) ? "right" : "left";
		    } else {
		        direction = (dy > 0) ? "down" : "up";
		    }
		}


	public void damageReaction()
	{
	    actionLockCounter = 0;

	    int dx=gp.player.worldX-worldX;
	    int dy=gp.player.worldY-worldY;

	    if (Math.abs(dx)>Math.abs(dy)) {
	        if (dx>0) {
	            direction="right";
	        } else {
	            direction="left";
	        }
	    } else {
	        if (dy>0) {
	            direction="down";
	        } else {
	            direction="up";
	        }
	    }
	}

	
}
