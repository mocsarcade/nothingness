package computc;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Dungeon
{
	public Room room;
	
	public Dungeon() throws SlickException
	{
		this.room = new Room();
		this.room.connectNorthernRoom(new Room());
		this.room.connectSouthernRoom(new Room());
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		this.room.render(graphics, camera);
	}
}