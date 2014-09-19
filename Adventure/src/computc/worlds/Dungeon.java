
package computc.worlds;

import java.util.HashMap;
import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import computc.cameras.Camera;
import computc.entities.Enemy;
import computc.entities.Entity;
import computc.entities.Key;
import computc.entities.Thug;

public abstract class Dungeon
{
	protected HashMap<String, Room> rooms = new HashMap<String, Room>();
	protected LinkedList<Enemy> enemies = new LinkedList<Enemy>();
	protected LinkedList<Key> keys = new LinkedList<Key>();
	protected Room firstRoom;

	public void update(int delta)
	{
		for(int i = 0; i < enemies.size(); i++)
		{
			Enemy e = enemies.get(i);
			e.update(delta);
				if(e.isDead())
				{
					enemies.remove(i);
					i--;
				}
		}
	}

	public void render(Graphics graphics, Camera camera)
	{
		for(Room room : this.getAllRooms())
		{
			room.render(graphics, camera);
		}

		for(Enemy enemy: this.getAllEnemies())
		{
			enemy.render(graphics, camera);
		}
		
		for(Key key : this.keys)
		{
			key.render(graphics, camera);
		}
	}
	
	public void renderOnMap(Graphics graphics, Camera camera)
	{
		for(Room room : this.getAllRooms())
		{
			room.renderOnMap(graphics, camera);
		}
		
		for(Enemy enemy: this.getAllEnemies())
		{
			enemy.renderOnMap(graphics, camera);
		}
	}
	
	public LinkedList<Enemy> getAllEnemies()
	{
		return this.enemies;
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
	
	public Room getFirstRoom()
	{
		return this.firstRoom;
	}
	
	public void setFirstRoom(Room firstRoom)
	{
		this.firstRoom = firstRoom;
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