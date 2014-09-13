package computc.worlds;

import java.util.HashMap;
import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import computc.cameras.Camera;
import computc.entities.Entity;
import computc.entities.Thug;

public abstract class Dungeon
{
	public HashMap<String, Room> rooms = new HashMap<String, Room>();
	public LinkedList<Entity> entities = new LinkedList<Entity>();

	public void render(Graphics graphics, Camera camera)
	{
		for(Room room : this.getAllRooms())
		{
			room.render(graphics, camera);
		}

		for(Entity entity : this.getAllEntities())
		{
			entity.render(graphics, camera);
		}
	}
	
	public void renderOnMap(Graphics graphics, Camera camera)
	{
		for(Room room : this.getAllRooms())
		{
			room.renderOnMap(graphics, camera);
		}
		
		for(Entity entity : this.getAllEntities())
		{
			entity.renderOnMap(graphics, camera);
		}
	}
	
	public LinkedList<Entity> getAllEntities()
	{
		return this.entities;
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