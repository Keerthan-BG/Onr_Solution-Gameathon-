package items;

import java.io.IOException;
import javax.imageio.ImageIO;
import entity.Entity;
import main.GamePanel;

public class OBJ_BloodSword extends Entity {
	GamePanel gp;
	
	public OBJ_BloodSword(GamePanel gp) {
		super(gp);
		name="BloodSword";
		down1=setup("/items/swords",gp.tileSize,gp.tileSize);

	}
}