package computc.entities;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import computc.Game;
import computc.cameras.Camera;
import computc.worlds.dungeons.Dungeon;
import computc.worlds.rooms.Room;

public class Loafer extends Enemy
{
	
	public static boolean hit = false;
	
	public boolean angry;
	private int pursuitCooldown;
	private int stuckCoolDown;
	
	public Loafer(Dungeon dungeon, Room room, int x, int y)
	{
		super(dungeon, room, x, y);
		
		this.dungeon = dungeon;
		
		this.image = Game.assets.getImage("res/Loafer.png");
		
		this.damage = 1;
		this.acceleration = 0.03f;
		this.deacceleration = 0.001f;
		this.maximumVelocity = 0.03f;
		
		this.health = this.maximumHealth = 8;
		
		right = true;
		stuckCoolDown = 0;
		mood = 1;
	}
	
	public void update(int delta)
	{
		getNextPosition(delta);
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		Vec2 distanceToPlayer = new Vec2(this.x - dungeon.gamedata.hero.getX(), this.y - dungeon.gamedata.hero.getY());
		
		switch(mood)
		{
		case 1:
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
				up = false;
				down = true;
			}
			else if(down && dy == 0) 
			{
				up = true;
				down = false;
			}
			break;
			
		case 2: // this is when the Loafer is in pursuit
			
			this.acceleration = .06f;
			this.maximumVelocity = .06f;
			if(this.getRoom() == this.dungeon.gamedata.hero.getRoom())
			{
				if(this.x < this.dungeon.gamedata.hero.getX())
				{
					dx = .0001f; dy = .0001f;
					right = true;
					left = false;
				}
				else if (this.x > this.dungeon.gamedata.hero.getX())
				{
					dx = .0001f; dy = .0001f;
					left = true;
					right = false;
				}
		
				if(this.y < this.dungeon.gamedata.hero.getY())
				{
					dx = .0001f; dy = .0001f;
					down = true;
					up = false;
				}
				else if(this.y > this.dungeon.gamedata.hero.getY())
				{
					dx = .0001f; dy = .0001f;
					up = true;
					down = false;
				}
			}
			else
			{
				if(this.dungeon.gamedata.hero.getRoom().getX() == this.getRoom().getX() - Room.WIDTH)
				{
					dx = .0001f; dy = .0001f;
					left = true;
					right = false;
					
					if(this.getRoomPositionY() < Room.HEIGHT/2)
					{
						dx = .0001f; dy = .0001f;
						down = true;
						up = false;
					}
					else if(this.getRoomPositionY() > Room.HEIGHT/2)
					{
						dx = .0001f; dy = .0001f;
						up = true;
						down = false;
					}
				}
				
				if(this.dungeon.gamedata.hero.getRoom().getX() == this.getRoom().getX() + Room.WIDTH)
				{
					dx = .0001f; dy = .0001f;
					right = true;
					left = false;
					
					if(this.getRoomPositionY() < Room.HEIGHT/2)
					{
						dx = .0001f; dy = .0001f;
						down = true;
						up = false;
					}
					else if(this.getRoomPositionY() > Room.HEIGHT/2)
					{
						dx = .0001f; dy = .0001f;
						up = true;
						down = false;
					}
				}
				
				if(this.dungeon.gamedata.hero.getRoom().getY() == this.getRoom().getY() + Room.HEIGHT)
				{
					dx = .0001f; dy = .0001f;
					down = true;
					up = false;
					
					if(this.getRoomPositionX() < Room.WIDTH/2)
					{
						dx = .0001f; dy = .0001f;
						right = true;
						left = false;
					}
					else if(this.getRoomPositionX() > Room.WIDTH/2)
					{
						dx = .0001f; dy = .0001f;
						left = true;
						right = false;
					}
				}
				
				if(this.dungeon.gamedata.hero.getRoom().getY() == this.getRoom().getY() - Room.HEIGHT)
				{
					dx = .0001f; dy = .0001f;
					up = true;
					down = false;
					
					if(this.getRoomPositionX() < Room.WIDTH/2)
					{
						dx = .0001f; dy = .0001f;
						right = true;
						left = false;
					}
					else if(this.getRoomPositionX() > Room.WIDTH/2)
					{
						dx = .0001f; dy = .0001f;
						left = true;
						right = false;
					}
				}
			}
			
			break;
			
		case 3:
			 System.out.println("the loafer is stuck");
			 break;
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
		
		if(blinking)
		{
			mood = 2;
		}
		
	}
	
	private void getNextPosition(int delta) 
	{
		if(left)
		{
			dx -= acceleration;
			if(dx < -maximumVelocity)
			{
				dx = -maximumVelocity;
			}
			dx *= delta;
		}
		
		else if(right) 
		{
			dx += acceleration;
			if(dx > maximumVelocity)
			{
				dx = maximumVelocity;
			}
			
			dx *= delta;
		}
		
		if(up) 
		{
			dy -= acceleration;
			if(dy < -maximumVelocity)
			{
				dy = -maximumVelocity;
			}
			dy *= delta;
		}
		else if(down) 
		{
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
		super.render(graphics, camera);
	}
}


