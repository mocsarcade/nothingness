package computc.worlds;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import computc.cameras.Camera;

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
	}
}