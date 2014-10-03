package computc.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import computc.Direction;
import computc.cameras.Camera;
import computc.cameras.RoomFollowingCamera;
import computc.worlds.dungeons.Dungeon;
import computc.worlds.rooms.Room;

public class Maniac extends Enemy
{
	
	public static boolean hit = false;
	
	private float bullRushVelocity;
	private float maxBullRushVelocity;
	private int bullRushCoolDown;
	public Direction direction;
	
	
	private boolean bullRush;
	
	public Maniac(Dungeon dungeon, Room room, int x, int  y) throws SlickException 
	{
		super(dungeon, room, x, y);
		
		this.dungeon = dungeon;
		
		this.image = new Image("res/Maniac.png");
		
		this.damage = 1;
		this.acceleration = 0.03f;
		this.deacceleration = 0.001f;
		this.maximumVelocity = 0.03f;
		this.bullRushVelocity = 0.5f;
		this.maxBullRushVelocity = 1.1f;
		
		this.health = this.maximumHealth = 5;
		
		double a = Math.random();
		
		if(a > 0.5)
			{
				right = true; 
				this.direction = Direction.EAST;
			}
			else
			{
				down = true; 
				this.direction = Direction.SOUTH;
			}
		
		bullRush = false;
		alreadySmashed = false;
		bullRushCoolDown = 0;
	}
	
	public void update(int delta)
	{
		
		getNextPosition(delta);
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		// if hits wall change direction
		if(!bullRush && bullRushCoolDown == 0)
		{
			this.maximumVelocity = 0.03f;
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
				up = false;
				down = true;
			}
			else if(down && dy == 0) 
			{
				up = true;
				down = false;
			}
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
		
		// check bullRush
		if(bullRushCoolDown > 0)
		{
			bullRushCoolDown -= delta;
		}
		
		if(bullRushCoolDown <= 0)
		{
			bullRushCoolDown = 0;
			bullRush = false;
			alreadySmashed = false;
		}
		
		
		
		if(bullRush && bullRushCoolDown > 0 && bullRushCoolDown < 2000 && dx == 0 || bullRush && bullRushCoolDown > 0 && bullRushCoolDown < 2000 && dy == 0)
		{
			smash = true;
		}
		else smash = false;
		
		
		if(this.getRoom() == this.dungeon.gamedata.hero.getRoom())
			{
				if(up && this.dungeon.gamedata.hero.getX() > this.x - this.getHalfWidth() && this.dungeon.gamedata.hero.getX() < this.x + this.getHalfWidth() && bullRushCoolDown == 0)
				{
					if(this.dungeon.gamedata.hero.getY() < this.y - this.getHalfHeight())
					{
						bullRush = true;
						bullRushCoolDown = 2800;
					}
				}
				else if(down && this.dungeon.gamedata.hero.getX() > this.x - this.getHalfWidth() && this.dungeon.gamedata.hero.getX() < this.x + this.getHalfWidth() && bullRushCoolDown == 0)
				{
					if(this.dungeon.gamedata.hero.getY() > this.y + this.getHalfHeight())
					{
						bullRush = true;
						bullRushCoolDown = 2800;
					}
				}
				
				if(left && this.dungeon.gamedata.hero.getY() > this.y - this.getHalfHeight() && this.dungeon.gamedata.hero.getY() < this.y + this.getHalfHeight() && bullRushCoolDown == 0)
				{
					if(this.dungeon.gamedata.hero.getX() < this.x - this.getHalfWidth())
					{
						bullRush = true;
						bullRushCoolDown = 2800;
					}
				}
				else if(right && this.dungeon.gamedata.hero.getY() > this.y - this.getHalfHeight() && this.dungeon.gamedata.hero.getY() < this.y + this.getHalfHeight() && bullRushCoolDown == 0)
				{
					if(this.dungeon.gamedata.hero.getX() > this.x + this.getHalfWidth())
					{
						bullRush = true;
						bullRushCoolDown = 2800;
					}
				}
			}
		
//		System.out.println("the bullRushCooldown is: " + bullRushCoolDown);
//		System.out.println("the bullrush is" + bullRush);
//		System.out.println("right is: " + right);
//		System.out.println("left is: " + left);
//		System.out.println("up is: " + up);
//		System.out.println("down is: " + down);
//		System.out.println("the smash is: " + smash);
	
	}
	
	private void getNextPosition(int delta) 
	{
		if(!bullRush)
		{
			if(left)
			{
				this.direction = Direction.WEST;
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
				dy += acceleration;
			if(dy > maximumVelocity)
				{
				dy = maximumVelocity;
				}
			dy *= delta;
			}	
		}
		else if(bullRush && bullRushCoolDown < 2000 && bullRushCoolDown > 0)
			{
				bullRush(delta);
			}
		else if(bullRush && bullRushCoolDown > 2000)
		{
			dx = .00001f;
			dy = .00001f;
		}
	}
	
	public void bullRush(int delta)
	{
		if(left)
		{
			this.direction = Direction.WEST;
			dx -= bullRushVelocity;
			if(dx < -maxBullRushVelocity)
			{
				dx = -maxBullRushVelocity;
			}
			dx *= delta;
		}
		else if(right) 
		{
			this.direction = Direction.EAST;
			dx += bullRushVelocity;
			if(dx > maxBullRushVelocity)
			{
				dx = maxBullRushVelocity;
			}
		
			dx *= delta;
		}
	
		if(up) 
		{
			this.direction = Direction.NORTH;
			dy -= bullRushVelocity;
			if(dy < -maxBullRushVelocity)
			{
				dy = -maxBullRushVelocity;
			}
			dy *= delta;
		}
		else if(down) 
		{
			this.direction = Direction.SOUTH;
			dy += bullRushVelocity;
		if(dy > maxBullRushVelocity)
			{
			dy = maxBullRushVelocity;
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
		super.render(graphics, camera);
		
		if(smash && !alreadySmashed)
		{
			camera.setEarthQuake(this.direction);
			alreadySmashed = true;
		}
	}
}


