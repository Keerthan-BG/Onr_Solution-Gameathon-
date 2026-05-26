package items;

import entity.Entity;
import main.GamePanel;

public class OBJ_Boat extends Entity {
	
GamePanel gp;
	
	public OBJ_Boat(GamePanel gp) {
		super(gp);
		name="boat";
		down1=setup("/items/boat1tile",gp.tileSize,gp.tileSize);
		collision=true;
		
		solidArea.x=0;
		solidArea.y=16;
		solidArea.width=48;
		solidArea.height=40;
		solidAreaDefaultX=solidArea.x;
		solidAreaDefaultY=solidArea.y;
	}

}
