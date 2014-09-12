package computc.worlds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import computc.Direction;
import computc.cameras.Camera;
import computc.entities.Enemy;
import computc.entities.Thug;

public class RandomZeldaesqueDungeon extends Dungeon
{
	final int SCALE = 5;
	
	public RandomZeldaesqueDungeon() throws SlickException
	{
		Room room = new Room(this, 2, 2, "empty");
		this.addRoom(room);
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		super.render(graphics, camera);
		
		/*for(Thug thug : this.thugs)
		{
			thug.render(graphics, camera);
		}*/
	}
}