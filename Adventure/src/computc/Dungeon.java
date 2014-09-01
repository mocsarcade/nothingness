package computc;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class Dungeon
{
	public Room firstRoom, secondRoom, thirdRoom, fourthRoom, fifthRoom;
	public RoomGrid rooms = new RoomGrid();
	
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