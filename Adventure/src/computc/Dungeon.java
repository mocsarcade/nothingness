package computc;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Dungeon
{
	public Room firstRoom, secondRoom, thirdRoom;
	
	public Dungeon() throws SlickException
	{
		this.firstRoom = new Room(0, 0);
		this.secondRoom = new Room(0, 1);
		this.thirdRoom = new Room(0, 2);
		
		this.firstRoom.connectSouthernRoom(this.secondRoom);
		this.secondRoom.connectSouthernRoom(this.thirdRoom);
	}
	
	public void update(int delta)
	{
		this.firstRoom.update(delta);
		this.secondRoom.update(delta);
		this.thirdRoom.update(delta);
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		this.firstRoom.render(graphics, camera);
		this.secondRoom.render(graphics, camera);
		this.thirdRoom.render(graphics, camera);
	}
}