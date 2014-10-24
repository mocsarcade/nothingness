package computc.worlds.dungeons;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRectd;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glTranslatef;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glOrtho;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.glMatrixMode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import computc.GameData;
import computc.Game;
import computc.cameras.Camera;
import computc.entities.Coin;
import computc.entities.Commodity;
import computc.entities.Enemy;
import computc.entities.Entity;
import computc.entities.Key;
import computc.entities.Ladder;
import computc.entities.OldMan;
import computc.worlds.rooms.Room;
import computc.worlds.rooms.RoomLayout;
import computc.worlds.tiles.Tile;
import computc.worlds.tiles.TileSubSet;
import computc.worlds.tiles.TileSet;

public abstract class Dungeon
{
	protected HashMap<String, Room> rooms = new HashMap<String, Room>();
	public LinkedList<Key> keys = new LinkedList<Key>();
	public LinkedList<Enemy> enemies = new LinkedList<Enemy>();
	public LinkedList<Commodity> commodities = new LinkedList<Commodity>();
	public Room firstRoom;
	public Room lastRoom;
	public Ladder ladder;
	public GameData gamedata;
	
	ArrayList<TileSet> tilesets = new ArrayList<TileSet>();
	LinkedList<RoomLayout> randomRoomLayouts = new LinkedList<RoomLayout>();
	HashMap<String, RoomLayout> specialRoomLayouts = new HashMap<String, RoomLayout>();
	private boolean debug;
	public boolean chainEnabled = false;
	public LinkedList<Animation> explosions = new LinkedList<Animation>();
	
	private Image explosion;
	private float explodeX;
	private float explodeY;
	private float enemyHalfWidth = 24;
	private int keyDropCooldown;
	private Random random;


	public Dungeon(GameData gamedata, String tileset)
	{
		this.explosion = Game.assets.getImage("res/explosion.png");
		this.gamedata = gamedata;
		
		this.tilesets.add(Game.assets.getTileSet(tileset));
		
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/arena.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/blob.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/brackets.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/corners.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/crumbs.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/diagonals.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/dot.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/fivedots.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/fourdots.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/grid.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/loop.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/niches.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/oval.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/spiral.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/square.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/threedots.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/threelines.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/twodots.room.tmx"));
		this.randomRoomLayouts.add(Game.assets.getRoomLayout("./res/rooms/twolines.room.tmx"));
		this.specialRoomLayouts.put("first room", Game.assets.getRoomLayout("./res/rooms/empty.room.tmx"));
		this.specialRoomLayouts.put("last room", Game.assets.getRoomLayout("./res/rooms/clamp.room.tmx"));
		
		Collections.shuffle(this.randomRoomLayouts);
		
		this.random = new Random();
	}
	
	public void initiate()
	{
		for(Room room : this.getAllRooms())
		{
			try 
			{
				room.initiate();
			} 
			catch (SlickException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public void update(int delta, Input input)
	{
		for(int i = 0; i < enemies.size(); i++)
		{
			Enemy e = enemies.get(i);
			e.update(delta);
				if(e.isDead())
				{
					enemies.remove(i);
					this.explodeX = e.getX();
					this.explodeY = e.getY();
					explosions.add(new Animation(new SpriteSheet(explosion, 30, 30), 200));
					
					double a = Math.random();					
					if(a > 0.2)
					{
						if(this.gamedata.hero.getHealth() == this.gamedata.hero.getMaxHealth())
						{
							commodities.add(new Commodity(this, e.getX(), e.getY(), random.nextInt(3)));
						}
						else commodities.add(new Commodity(this, e.getX(), e.getY(), random.nextInt(4)));
					}
					
					i--;
					
					this.gamedata.hero.monsters_killed++;
				}
		}
		
		for(int i=0; i < commodities.size(); i++)
		{
			Commodity c = commodities.get(i);
			c.update(delta);
			if(commodities.get(i).getType() == 0 && !(keyDropCooldown == 0))	
			{
				commodities.get(i).getRoom().addKey();
				keyDropCooldown = 200;
				commodities.remove(i);
			}
		}
		
		if(keyDropCooldown > 0)
		{
			keyDropCooldown -= delta;
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
		
		for(Commodity commodity: this.commodities)
		{
			commodity.render(graphics, camera);
		}
		
		if(this.ladder != null)
		{
			this.ladder.render(graphics, camera);
		}
		
		for(int i = 0; i < explosions.size(); i++)
		{
			Animation explode = explosions.get(i);
			
			explode.draw(explodeX - enemyHalfWidth - camera.getX(), explodeY - enemyHalfWidth - camera.getY());
			
			explode.setLooping(false);
			
				if(explode.isStopped())
				{
				explosions.remove(i);
				}
		}
	}
	
	public void renderOnMap(Graphics graphics, Camera camera)
	{
		for(Room room : this.getAllRooms())
		{
			room.renderOnMap(graphics, camera);
		}
		
		if(Game.devmode)
		{
			for(Key key : this.keys)
			{
				key.renderOnMap(graphics, camera);
			}
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
	
	public TileSet getTileSet(int index)
	{
		return this.tilesets.get(index);
	}
	
	public TileSet getRandomTileSet()
	{
		return this.tilesets.get(Game.random.nextInt(tilesets.size()));
	}
	
	public RoomLayout getRandomRoomLayout()
	{
		RoomLayout randomRoomLayout = this.randomRoomLayouts.pop();
		this.randomRoomLayouts.add(randomRoomLayout);
		return randomRoomLayout;
	}
	
	public RoomLayout getSpecialRoomLayout(String type)
	{
		return this.specialRoomLayouts.get(type);
	}
	
	// debug mode for viewing box2d physics
		public void rigidBodyDebugDraw(Set<Body> bodies, Set<Body> staticBodies) 
		{
			glClear(GL_COLOR_BUFFER_BIT);
			
			for(Body body: bodies)
			{
					glPushMatrix();
					Vec2 bodyPosition = body.getPosition().mul(30);
					glTranslatef(bodyPosition.x, bodyPosition.y, 0);
					glRotated(Math.toDegrees(body.getAngle()), 0, 0, 1);
					glRectd(-0.5f * 30, -0.060f * 30, 0.5f * 30, 0.060f * 30);
		//			System.out.println("the actual box 2d position of the body is: "  + body.getPosition().x + " , " + body.getPosition().y);
					glPopMatrix();
			}
			
			for(Body body: staticBodies)
			{
					glPushMatrix();
					Vec2 staticBodyPosition = body.getPosition().mul(30);
					glTranslatef(staticBodyPosition.x, staticBodyPosition.y, 0);
					glRectd(-1f * 30, -1f * 30, 1f * 30, 1f * 30);
					glPopMatrix();
			}
		}
		
		public boolean toggleDebugDraw(Graphics graphics)
		{
			if(debug)
			{
				graphics.setDrawMode(1);
			}
			
			if(!debug)
			{
				graphics.setDrawMode(4);
			}
			
			return debug = !debug;
		}
		
		public boolean getDebugDraw()
		{
			return debug;
		}
		
		// if you want turn chain off
		public void disableChain()
		{
			chainEnabled = false;
		}
		
		public void enableChain()
		{
			chainEnabled = true;
		}
}