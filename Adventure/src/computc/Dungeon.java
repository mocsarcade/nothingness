package computc;

import java.util.HashMap;
import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Dungeon
{
	public Room firstRoom, secondRoom, thirdRoom, fourthRoom, fifthRoom;
	private HashMap<String, Room> rooms = new HashMap<String, Room>();
	
	public Dungeon() throws SlickException
	{
		this.firstRoom = new Room(this, 4, 1);
		this.secondRoom = new Room(this, 4, 2);
		this.thirdRoom = new Room(this, 4, 3);
		this.fourthRoom = new Room(this, 5, 2);
		this.fifthRoom = new Room(this, 3, 2);
		
		this.secondRoom.connectNorthernRoom(this.firstRoom);
		this.secondRoom.connectSouthernRoom(this.thirdRoom);
		this.secondRoom.connectEasternRoom(this.fourthRoom);
		this.secondRoom.connectWesternRoom(this.fifthRoom);

		this.addRoom(this.firstRoom);
		this.addRoom(this.secondRoom);
		this.addRoom(this.thirdRoom);
		this.addRoom(this.fourthRoom);
		this.addRoom(this.fifthRoom);
	}
	
	public void update(int delta)
	{
		//code goes here.
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		for(Room room : this.getAllRooms())
		{
			room.render(graphics, camera);
		}
	}
	
	public void addRoom(Room room)
	{
		int rx = room.getRoomyX();
		int ry = room.getRoomyY();
		
		if(this.hasRoom(rx, ry))
		{
			throw new DungeonException();
		}
		else
		{
			this.rooms.put(rx + ":" + ry, room);
		}
	}
	
	public Room getRoom(int rx, int ry)
	{
		return this.rooms.get(rx + ":" + ry);
	}
	
	public boolean hasRoom(int rx, int ry)
	{
		return this.rooms.containsKey(rx + ":" + ry);
	}
	
	public LinkedList<Room> getAllRooms()
	{
		return new LinkedList<Room>(this.rooms.values());
	}
	
	public Tile getTile(float x, float y)
	{
		int rx = (int)(Math.floor(x / Room.WIDTH));
		int ry = (int)(Math.floor(y / Room.HEIGHT));
		
		int tx = (int)(Math.floor((x - (rx * Room.WIDTH)) / Tile.SIZE));
		int ty = (int)(Math.floor((y - (ry * Room.HEIGHT)) / Tile.SIZE));
		
		return this.getRoom(rx, ry).getTile(tx, ty);
	}
}