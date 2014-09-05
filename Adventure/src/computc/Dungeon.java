package computc;

import java.awt.Point;
import java.util.HashMap;
import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.tiled.TiledMap;

public class Dungeon
{
	private HashMap<String, Room> rooms = new HashMap<String, Room>();
	public LinkedList<Enemy> thugs; 
	
	public Dungeon() throws SlickException
	{
		TiledMap tiled = new TiledMap("./res/dungeons/prototype.dungeon.tmx");
		
		int ROOMY_WIDTH = 9;
		int ROOMY_HEIGHT = 5;
		
		for(int rx = 0; rx < ROOMY_WIDTH; rx++)
		{
			for(int ry = 0; ry < ROOMY_HEIGHT; ry++)
			{
				Room room = new Room(this, rx, ry);
				
				for(int tx = 0; tx < Room.TILEY_WIDTH; tx++)
				{
					for(int ty = 0; ty < Room.TILEY_HEIGHT; ty++)
					{
						Tile tile = new Tile(room, tx, ty);

						int rxtx = (rx * Room.TILEY_WIDTH) + tx;
						int ryty = (ry * Room.TILEY_HEIGHT) + ty;
						int tid = tiled.getTileId(rxtx, ryty, 0);
						
						tile.isBlocked = (tid == 1);
						
						room.setTile(tx, ty, tile);
					}
				}
				
				this.addRoom(room);
			}
		}
		
		for(int rx = 0; rx < ROOMY_WIDTH; rx++)
		{
			for(int ry = 0; ry < ROOMY_HEIGHT; ry++)
			{
				Room room = this.getRoom(rx, ry);
				
				if(!room.getTile(Room.TILEY_WIDTH / 2, 0).isBlocked)
				{
					room.connectNorthernRoom(this.getRoom(rx, ry - 1));
				}
				if(!room.getTile(Room.TILEY_WIDTH / 2, Room.TILEY_HEIGHT - 1).isBlocked)
				{
					room.connectSouthernRoom(this.getRoom(rx, ry + 1));
				}
				if(!room.getTile(Room.TILEY_WIDTH - 1, Room.TILEY_HEIGHT / 2).isBlocked)
				{
					room.connectEasternRoom(this.getRoom(rx + 1, ry));
				}
				if(!room.getTile(0, Room.TILEY_HEIGHT / 2).isBlocked)
				{
					room.connectWesternRoom(this.getRoom(rx - 1, ry));
				}
			}
		}
		
		this.thugs = new LinkedList<Enemy>();
		
		Point[] thug_positions_in_tiley_coordinates = new Point[] {
				new Point(34, 5),
				new Point(42, 2),
				new Point(39, 5),
				new Point(46, 2),
				new Point(52, 6),
				new Point(47, 13),
				new Point(50, 15)
			};
		
		for(Point point : thug_positions_in_tiley_coordinates)
		{
			thugs.add(new Thug(this, point.x, point.y));
		}
	}
	
	public void update(int delta)
	{
		for(int i = 0; i < thugs.size(); i++)
		{
			Enemy e = thugs.get(i);
			e.update(delta);
				if(e.isDead())
				{
					thugs.remove(i);
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
		
		for(Enemy thug : this.thugs)
		{
			thug.render(graphics, camera);
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
	
	public Tile getTile(int rx, int ry, int tx, int ty)
	{
		return this.getRoom(rx, ry).getTile(tx, ty);
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