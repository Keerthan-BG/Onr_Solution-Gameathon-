package items;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Masterkey extends Entity {
	GamePanel gp;
	
	public OBJ_Masterkey(GamePanel gp) {
		
		super(gp);
		name="MasterKey";
		down1=setup("/items/MasterKeyBlooded",gp.tileSize,gp.tileSize);
		
	}
}
