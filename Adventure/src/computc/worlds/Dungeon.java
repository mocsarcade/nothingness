
package computc.worlds;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glRectd;
import static org.lwjgl.opengl.GL11.glRotated;
import static org.lwjgl.opengl.GL11.glTranslatef;

import org.jbox2d.collision.shapes.CircleShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;

import computc.cameras.Camera;
import computc.entities.Coin;
import computc.entities.Enemy;
import computc.entities.Entity;
import computc.entities.Key;
import computc.entities.OldMan;
import computc.entities.Thug;

public abstract class Dungeon
{
	protected HashMap<String, Room> rooms = new HashMap<String, Room>();
	protected LinkedList<Enemy> enemies = new LinkedList<Enemy>();
	public LinkedList<Key> keys = new LinkedList<Key>();
	public LinkedList<Coin> coins = new LinkedList<Coin>();
	protected Room firstRoom;
	public Room lastRoom;
	public OldMan oldman;
	
	public LinkedList<Animation> explosions  = new LinkedList<Animation>();
	
	public Vec2 gravity = new Vec2(0, .5f);
	
	public boolean debug = false;
	
	public boolean chainEnabled = true;
	
	private Image explosion;
	private float explodeX;
	private float explodeY;
	private float enemyHalfWidth = 24;
	
	public Dungeon() throws SlickException
	{
		this.explosion = new Image("res/explosion.png");
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
					this.explodeX = e.getX();
					this.explodeY = e.getY();
					explosions.add(new Animation(new SpriteSheet(explosion, 30, 30), 200));
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
		
		public boolean toggleDebugDraw()
		{
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