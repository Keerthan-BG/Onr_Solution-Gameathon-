package entity;
import java.util.Random;

import main.GamePanel;

public class NPC_Oldman extends Entity {

	public NPC_Oldman(GamePanel gp) {
		super(gp);
		
		direction="down";
		speed=1;
		getImage();
		setDialogue();
		
	}
	public void getImage() {
		
		up1=setup("/NPC/Duckup",gp.tileSize,gp.tileSize);
		up2=setup("/NPC/Duckup",gp.tileSize,gp.tileSize);
		down1=setup("/NPC/Duckdown",gp.tileSize,gp.tileSize);
		down2=setup("/NPC/Duckdown",gp.tileSize,gp.tileSize);
		right1=setup("/NPC/Duckright",gp.tileSize,gp.tileSize);
		right2=setup("/NPC/Duckright",gp.tileSize,gp.tileSize);
		left1=setup("/NPC/Ducklrft",gp.tileSize,gp.tileSize);
		left2=setup("/NPC/Ducklrft",gp.tileSize,gp.tileSize);
	}
	public void setDialogue()
	{
		dialogues[0]="Hello";
		dialogues[1]="Have you come to find \nthe treasure in this island?";
		dialogues[2]="There are zombies in this Island?";
		dialogues[3]="Lot of people have died in this \nIsland finding the treasure";
		dialogues[4]="Take this sword to fight the monsters";
		dialogues[5]="Press Enter ket to swing the sword";
		dialogues[6]="Bad luck for that";
	}
	public void setAction()
	{
		actionLockCounter++;
		if(actionLockCounter==120)
		{
		Random random=new Random();
		int i=random.nextInt(100);
		if(i<=25)
		{
			direction="up";
		}
		if(i>25 && i<=50)
		{
			direction="down";
		}
		if(i>50 && i<=75)
		{
			direction="right";
		}
		if(i>75 && i<=100)
		{
			direction="left";
		}
		actionLockCounter=0;
	}
		
	}
	public void speak()
	{
		super.speak();
	}
	
}
