package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
	GamePanel gp;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		this.gp=gp;
		tile=new Tile[40];
		mapTileNum=new int[gp.maxWorldCol][gp.maxWorldRow];
		getTileImage();
		loadMap("/maps/FullScreenMap.txt");
	}
	public void getTileImage() {
			setup(0,"grasspath",false);
			setup(1,"laketile",true);
			setup(2,"walltile",true);
			setup(3,"tree1",true);
			setup(4,"sandpath",false);
			setup(5,"sandstonetile",false);
			setup(6,"rocktile",false);
			setup(7,"buildingtile",true);
			setup(8,"cornerwaterupleft",true);
			setup(9,"lilypade",true);
			
			
			setup(10,"woodenfloor",false);
			setup(11,"laketile",true);
			setup(12,"walltile",true);
			setup(13,"tree1",true);
			setup(14,"sandpath",false);
			setup(16,"rocktile",false);
			setup(17,"buildingtile",true);
			setup(19,"lilypade",true);
			
			setup(20,"lakesideleft",true);
			setup(21,"lakesideright",true);
			setup(22,"laketop",true);
			setup(23,"lakebottom",true);
			setup(24,"laketopleft",true);
			setup(25,"laketopright",true);
			setup(26,"lakedownleft",true);
			setup(27,"lakedownright",true);
			setup(28,"stonetrap",false);
			setup(29,"ElixerTile",false);
			setup(30,"boat2tile",true);
			
	}
	public void setup(int index,String imageName,boolean collision)
	{
		UtilityTool uTool=new UtilityTool();
		try
		{
			tile[index]=new Tile();
			tile[index].image=ImageIO.read(getClass().getResource("/tiles/"+imageName+".png"));
			tile[index].image=uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision=collision;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
	public void loadMap(String filePath) {
	    try {
	        InputStream is=getClass().getResourceAsStream(filePath);
	        BufferedReader br=new BufferedReader(new InputStreamReader(is));

	        int row=0;

	        while (row<gp.maxWorldRow) {
	            String line=br.readLine();
	            if (line==null) break;

	            String[] numbers = line.trim().split("\\s+");

	            for (int col=0;col<gp.maxWorldCol && col<numbers.length;col++) {
	                int num=Integer.parseInt(numbers[col]);
	                mapTileNum[col][row]=num;
	            }

	            row++;
	        }

	        br.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	public void draw(Graphics2D g2) {
		
		int worldCol=0;
		int worldRow=0;
		
		while(worldCol<gp.maxWorldCol && worldRow<gp.maxWorldRow) {
			int tileNum=mapTileNum[worldCol][worldRow];
			
			int worldX=worldCol*gp.tileSize;
			int worldY=worldRow*gp.tileSize;
			int screenX=worldX-gp.player.worldX+gp.player.screenX;
			int screenY=worldY-gp.player.worldY+gp.player.screenY;
			
			if(worldX+gp.tileSize>gp.player.worldX-gp.player.screenX && worldX-gp.tileSize<gp.player.worldX+gp.player.screenX &&
					worldY+gp.tileSize>gp.player.worldY-gp.player.screenY && worldY-gp.tileSize<gp.player.worldY+gp.player.screenY) {
			g2.drawImage(tile[tileNum].image,screenX,screenY,null);
				
			}
			worldCol++;
			if(worldCol==gp.maxWorldCol) {
				worldCol=0;
				worldRow++;
			}
		}
	}
}
