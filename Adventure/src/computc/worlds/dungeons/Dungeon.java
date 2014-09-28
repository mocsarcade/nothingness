package computc.worlds.dungeons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.newdawn.slick.Graphics;

import computc.Game;
import computc.cameras.Camera;
import computc.entities.Coin;
import computc.entities.Enemy;
import computc.entities.Key;
import computc.entities.OldMan;
import computc.worlds.rooms.Room;
import computc.worlds.rooms.RoomTemplate;
import computc.worlds.tiles.Tile;
import computc.worlds.tiles.TileGroup;
import computc.worlds.tiles.TileTemplate;

public abstract class Dungeon
{
	protected HashMap<String, Room> rooms = new HashMap<String, Room>();
	protected LinkedList<Enemy> enemies = new LinkedList<Enemy>();
	public LinkedList<Key> keys = new LinkedList<Key>();
	public LinkedList<Coin> coins = new LinkedList<Coin>();
	protected Room firstRoom;
	public Room lastRoom;
	public OldMan oldman;
	
	private ArrayList<RoomTemplate> roomTemplates = new ArrayList<RoomTemplate>();
	public HashMap<String, TileGroup> tileGroups = new HashMap<String, TileGroup>();
	
	public Dungeon(String filepath)
	{
		try
		{
			Document document = new SAXBuilder().build(filepath);
			Element dungeonElement = document.getRootElement();
			
			for(Element roomElement : dungeonElement.getChild("rooms").getChildren())
			{
				String source = roomElement.getAttributeValue("source");
				this.roomTemplates.add(new RoomTemplate(source));
			}
			
			for(Element tileElement : dungeonElement.getChild("tiles").getChildren())
			{
				String tileElementType = tileElement.getAttributeValue("type");
				Element imageElement = tileElement.getChild("image");
				String imageElementSource = imageElement.getAttributeValue("source");
				
				this.tileGroups.put(tileElementType, Game.assets.getTileGroup(imageElementSource));
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}
	
	public RoomTemplate getRandomRoomTemplate()
	{
		return this.roomTemplates.get(Game.randomness.nextInt(this.roomTemplates.size()));
	}
	
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
		
		for(Key key : this.keys)
		{
			key.update(delta);
		}
	}

	public void render(Graphics graphics, Camera camera)
	{
		for(Room room : this.getAllRooms())
		{
			room.render(graphics, camera);
		}

		for(Coin coin : this.coins)
		{
			coin.render(graphics, camera);
		}

		for(Enemy enemy: this.getAllEnemies())
		{
			enemy.render(graphics, camera);
		}
		
		if(this.oldman != null)
		{
			this.oldman.render(graphics, camera);
		}
	}
	
	public void renderKeys(Graphics graphics, Camera camera)
	{
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