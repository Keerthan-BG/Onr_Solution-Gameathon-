package main;

import entity.NPC_Oldman;
import items.Castle;
import items.OBJ_BloodSword;
import items.OBJ_Boat;
import items.OBJ_Boots;
import items.OBJ_Chest;
import items.OBJ_Door;
import items.OBJ_Gate;
import items.OBJ_GateKey;
import items.OBJ_Masterkey;
import items.OBJ_key;
import monster.MON_GreenSlime;

public class AssetSetter {

	GamePanel gp;
	public AssetSetter(GamePanel gp) {
		this.gp=gp;
	}
	public void setObject()
	{	
		gp.obj[0]=new OBJ_BloodSword(gp);
		gp.obj[0].worldX=gp.tileSize*37;
		gp.obj[0].worldY=gp.tileSize*23;

	}
	public void setNPC()
	{
		gp.npc[0]=new NPC_Oldman(gp);
		gp.npc[0].worldX=gp.tileSize*51;
		gp.npc[0].worldY=gp.tileSize*26;
			
	}
	public void setMonster()
	{
	
		
	
		
	}

}
