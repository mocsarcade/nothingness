package computc;

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
	
	public Tile getTile(float x, float y)
	{
		int rx = (int)(Math.floor(x / Room.WIDTH));
		int ry = (int)(Math.floor(y / Room.HEIGHT));
		
		int tx = (int)(Math.floor((x - (rx * Room.WIDTH)) / Tile.SIZE));
		int ty = (int)(Math.floor((y - (ry * Room.HEIGHT)) / Tile.SIZE));
		
		return this.rooms.get(rx, ry).getTile(tx, ty);
	}
}