package computc;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Dungeon
{
	public Room currentRoom;
	
	public Room firstRoom, secondRoom, thirdRoom;
	
	public Dungeon() throws SlickException
	{
		this.firstRoom = new Room(0, 0);
		this.secondRoom = new Room(0, 1);
		this.thirdRoom = new Room(0, 2);
		
		this.firstRoom.connectSouthernRoom(this.secondRoom);
		this.secondRoom.connectSouthernRoom(this.thirdRoom);
		
		this.currentRoom = this.firstRoom;
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		this.firstRoom.render(graphics, camera);
		this.secondRoom.render(graphics, camera);
		this.thirdRoom.render(graphics, camera);
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
	
	public Room getCurrentRoom()
	{
		return this.currentRoom;
	}
}