package items;

import entity.Entity;
import main.GamePanel;

public class OBJ_GateKey extends Entity {
	
	public OBJ_GateKey(GamePanel gp) {
		super(gp);
		name="gateKey";
		down1=setup("/items/GateKeyBlooded",gp.tileSize,gp.tileSize);
	}
}
