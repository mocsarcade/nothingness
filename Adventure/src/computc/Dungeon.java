package computc;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Dungeon
{
	public Room currentRoom;
	
	public Dungeon() throws SlickException
	{
		this.currentRoom = new Room();
		this.currentRoom.connectNorthernRoom(new Room());
		this.currentRoom.connectSouthernRoom(new Room());
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		this.currentRoom.render(graphics, camera);
	}
	
	public Room getCurrentRoom()
	{
		return this.currentRoom;
	}
	
	public void move(Direction direction)
	{
		if(direction == Direction.NORTH)
		{
			this.currentRoom = this.currentRoom.getNorthernRoom();
		}
		else if(direction == Direction.SOUTH)
		{
			this.currentRoom = this.currentRoom.getSouthernRoom();
		}
		else if(direction == Direction.EAST)
		{
			this.currentRoom = this.currentRoom.getEasternRoom();
		}
		else if(direction == Direction.WEST)
		{
			this.currentRoom = this.currentRoom.getWesternRoom();
		}
	}
}