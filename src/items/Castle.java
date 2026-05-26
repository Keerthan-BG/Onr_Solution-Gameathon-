package items;

import entity.Entity;
import main.GamePanel;

public class Castle extends Entity {
	
GamePanel gp;
	
	public Castle(GamePanel gp) {
		super(gp);
		name="Castle";
		down1=setup("/items/Castle (1)",gp.tileSize*5,gp.tileSize*6);
		collision=true;
		
		solidArea.x=0;
		solidArea.y=16;
		solidArea.width=48;
		solidArea.height=40;
		solidAreaDefaultX=solidArea.x;
		solidAreaDefaultY=solidArea.y;
	}

}
