package items;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_key extends Entity {
	public OBJ_key(GamePanel gp) {
		super(gp);
		name="Key";
		down1=setup("/items/key",gp.tileSize,gp.tileSize);
	}

}
