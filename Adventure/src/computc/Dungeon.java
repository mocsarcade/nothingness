package computc;

import java.util.HashMap;
import java.util.Map.Entry;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Dungeon
{
	public Room firstRoom, secondRoom, thirdRoom, fourthRoom, fifthRoom;
	public MultifacetedHashMap<Room> rooms = new MultifacetedHashMap<Room>();
	
	public Dungeon() throws SlickException
	{
		this.firstRoom = new Room(this, 0, 0);
		this.secondRoom = new Room(this, 0, 1);
		this.thirdRoom = new Room(this, 0, 2);
		this.fourthRoom = new Room(this, 1, 1);
		this.fifthRoom = new Room(this, -1, 1);
		
		//this.secondRoom.connectNorthernRoom(this.firstRoom);
		//this.secondRoom.connectSouthernRoom(this.thirdRoom);
		//this.secondRoom.connectEasternRoom(this.fourthRoom);
		//this.secondRoom.connectWesternRoom(this.fifthRoom);
	}
	
	public void update(int delta)
	{
		//code goes here.
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		for(Room room : this.rooms.getAll())
		{
			room.render(graphics, camera);
		}
	}
}