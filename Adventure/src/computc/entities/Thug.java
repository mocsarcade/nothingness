
package computc.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Point;

import computc.Direction;
import computc.Game;
import computc.cameras.Camera;
import computc.worlds.dungeons.Dungeon;
import computc.worlds.rooms.Room;
import computc.worlds.tiles.Tile;

public class Thug extends Enemy	
{

	public static boolean hit = false;
	
	private int angryCooldown;
	private int shiftCooldown;
	
	private Image spriteSheet = Game.assets.getImage("res/Droggon.png");
	private Image walkDown = spriteSheet.getSubImage(1, 1, 64, 128);
	private Image walkUp = spriteSheet.getSubImage(65, 1, 64, 128);
	private Image walkLeft = spriteSheet.getSubImage(129, 1, 64, 128);
	private Image walkRight = spriteSheet.getSubImage(192, 1, 64, 128);

	Animation sprite, walkingDown, walkingUp, walkingLeft, walkingRight;
	
	public Thug(Dungeon dungeon, Room room, int tx, int ty)
	{
		super(dungeon, room, tx, ty);
		//System.out.println(room.getTileyX() + "->" + x);
		//System.out.println(this.getTileyX());
		
		this.dungeon = dungeon;
		
		this.image = Game.assets.getImage("res/Droggon.png").getSubImage(1, 1, 64, 64);
		
		walkingDown = new Animation(new SpriteSheet(walkDown, 64, 64), 400);
		walkingUp = new Animation(new SpriteSheet(walkUp, 64, 64), 400);
		walkingLeft = new Animation(new SpriteSheet(walkLeft, 64, 64), 400);
		walkingRight = new Animation(new SpriteSheet(walkRight, 64, 64), 400);
		
		this.damage = 1;
		this.acceleration = 0.03f;
		this.deacceleration = 0.001f;
		this.maximumVelocity = 0.03f;
		
		this.health = this.maximumHealth = 5;
		
		right = true; down = true;
	}
	
	public void update(int delta)
	{
		getNextPosition(delta);
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// if hits wall change direction
		if(right && dx == 0)
		{
			right = false;
			left = true;
		}
		else if(left && dx == 0) 
		{
			right = true;
			left = false;
		}
		
		if(up && dy == 0)
		{
			shiftCooldown = 500;
			up = false;
			down = true;
		}
		else if(down && dy == 0) 
		{
			up = true;
			down = false;
		}
		
		// check blinking
		if (blinkCooldown > 0)
		{
			blinkCooldown --;
		}
		
		if(blinkCooldown == 0)
		{
			blinking = false;
		}
		
		if(angryCooldown > 0 && angryCooldown < 100)
		{
			this.maximumVelocity = .03f;
		}
		
		if(angryCooldown > 0)
		{
			angryCooldown -= delta;
		}
		
		if(shiftCooldown > 0)
		{
			shiftCooldown -= delta;
		}
		
		if(this.maximumVelocity > .03f && angryCooldown <= 0)
		{
			angryCooldown = 5000;
		}
		
		if(angryCooldown > 0 && angryCooldown < 100)
		{
			this.maximumVelocity = .03f;
		}
		
		if(angryCooldown > 0)
		{
			angryCooldown -= delta;
		}
		
		if(this.maximumVelocity > .03f && angryCooldown <= 0)
		{
			angryCooldown = 5000;
		}
	}
	
	private void getNextPosition(int delta) 
	{
		if(left)
		{
			this.direction = Direction.WEST;
			sprite = walkingLeft;
			dx -= acceleration;
			if(dx < -maximumVelocity)
			{
				dx = -maximumVelocity;
			}
			dx *= delta;
		}
		
		else if(right) 
		{
			this.direction = Direction.EAST;
			sprite = walkingRight;
			dx += acceleration;
			if(dx > maximumVelocity)
			{
				dx = maximumVelocity;
			}
			
			dx *= delta;
		}
		
		if(up) 
		{
			this.direction = Direction.NORTH;
			sprite = walkingUp;
			dy -= acceleration;
			if(dy < -maximumVelocity)
			{
				dy = -maximumVelocity;
			}
			dy *= delta;
		}
		else if(down) 
		{
			this.direction = Direction.SOUTH;
			sprite = walkingDown;
			dy += acceleration;
			if(dy > maximumVelocity)
			{
				dy = maximumVelocity;
			}
			dy *= delta;
		}	
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		if(blinking) 
		{
			if(blinkCooldown % 4 == 0) 
			{
				return;
			}
		}
//		super.render(graphics, camera);
		
		if(this.direction == Direction.NORTH && shiftCooldown <= 0)
		{
			walkingUp.draw(this.getX() - this.getHalfWidth() - camera.getX(), this.getY() - this.getHalfHeight() - camera.getY());
		}
		else if (this.direction == Direction.SOUTH || shiftCooldown > 0)
		{
			walkingDown.draw(this.getX() - this.getHalfWidth() - camera.getX(), this.getY() - this.getHalfHeight() - camera.getY());
		}
	}
	
	public void checkTileMapCollision() 
	{	   
		   xdest = x + dx;
		   ydest = y + dy;
		   
		   xtemp = x;
		   ytemp = y;
		   
		   calculateCorners(x, ydest);
		   
		   if(dy < 0) 
		   {
			   if((!topLeft.canMoveHere() || !topRight.canMoveHere()) && (this.getRoomPositionX() < Tile.SIZE * 1.5 || this.getRoomPositionX() > Room.WIDTH - Tile.SIZE * 1.5 || this.getRoomPositionY() < Tile.SIZE * 1.5 || this.getRoomPositionY() > Room.HEIGHT - Tile.SIZE * 1.5)) 
			   {
				   dy = 0;
			   }
			   else {
				   ytemp += dy;
			   }
		   }
			   
			if(dy > 0) 
			{
				if((!bottomLeft.canMoveHere() || !bottomRight.canMoveHere()) && (this.getRoomPositionX() < Tile.SIZE * 1.5 || this.getRoomPositionX() > Room.WIDTH - Tile.SIZE * 1.5 || this.getRoomPositionY() < Tile.SIZE * 1.5 || this.getRoomPositionY() > Room.HEIGHT - Tile.SIZE * 1.5))
				{
					dy = 0;
				}
				else 
				{
					ytemp += dy;
				}
			}
			
			calculateCorners(xdest, y);
			
			if(dx < 0) {
				if((!topLeft.canMoveHere() || !bottomLeft.canMoveHere()) && (this.getRoomPositionX() < Tile.SIZE * 1.5 || this.getRoomPositionX() > Room.WIDTH - Tile.SIZE * 1.5 || this.getRoomPositionY() < Tile.SIZE * 1.5 || this.getRoomPositionY() > Room.HEIGHT - Tile.SIZE * 1.5)) 
				{
					dx = 0;
				}
				else 
				{
					xtemp += dx;
				}
			}
				
			if(dx > 0) {
				if((!topRight.canMoveHere() || !bottomRight.canMoveHere()) && (this.getRoomPositionX() < Tile.SIZE * 1.5 || this.getRoomPositionX() > Room.WIDTH - Tile.SIZE * 1.5 || this.getRoomPositionY() < Tile.SIZE * 1.5 || this.getRoomPositionY() > Room.HEIGHT - Tile.SIZE * 1.5 )) 
				{
					dx = 0;
				}
				else 
				{
					xtemp += dx;
				}
			}
	}
}
