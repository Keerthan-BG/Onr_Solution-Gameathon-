package items;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class OBJ_Heart extends Entity {
	GamePanel gp;
	public OBJ_Heart(GamePanel gp) {
		super(gp);
		name="heart";
		image=setup("/items/Full Heart",gp.tileSize,gp.tileSize);
		image2=setup("/items/Half Heart",gp.tileSize,gp.tileSize);
		image3=setup("/items/Empty Heart",gp.tileSize,gp.tileSize);

	}

}
