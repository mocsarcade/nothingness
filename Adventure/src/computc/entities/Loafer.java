package computc.entities;

import org.jbox2d.common.Vec2;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import computc.Direction;
import computc.Game;
import computc.cameras.Camera;
import computc.worlds.dungeons.Dungeon;
import computc.worlds.rooms.Room;
import computc.worlds.tiles.Tile;

public class Loafer extends Enemy
{
	
	public static boolean hit = false;
	
	public boolean angry;
	private int angryCooldown;
	private boolean forceDrawRight, forceDrawLeft;
	
	private int pursuitCooldown;
	private Image spriteSheet = Game.assets.getImage("res/WolfLoafer.png");
	private Image walkDown = spriteSheet.getSubImage(1, 1, 57, 128);
	private Image walkUp = spriteSheet.getSubImage(57, 1, 57, 128);
	private Image walkLeft = spriteSheet.getSubImage(116, 1, 57, 128);
	private Image walkRight = spriteSheet.getSubImage(171, 1, 57, 128);
	
	private Image spriteSheetAngry = Game.assets.getImage("res/WolfLoaferAngry.png");
	private Image walkDownAngry = spriteSheetAngry.getSubImage(1, 1, 57, 128);
	private Image walkUpAngry = spriteSheetAngry.getSubImage(57, 1, 57, 128);
	private Image walkLeftAngry = spriteSheetAngry.getSubImage(116, 1, 57, 128);
	private Image walkRightAngry = spriteSheetAngry.getSubImage(171, 1, 57, 128);
	
	Animation sprite, walkingDown, walkingUp, walkingLeft, walkingRight, angryDown, angryUp, angryLeft, angryRight;
	
	public Loafer(Dungeon dungeon, Room room, int x, int y)
	{
		super(dungeon, room, x, y);
		
		this.dungeon = dungeon;
		
		this.image = Game.assets.getImage("res/WolfLoafer.png").getSubImage(1, 1, 55, 61);
		
		
		walkingDown = new Animation(new SpriteSheet(walkDown, 57, 64), 200);
		walkingUp = new Animation(new SpriteSheet(walkUp, 57, 64), 200);
		walkingLeft = new Animation(new SpriteSheet(walkLeft, 57, 64), 200);
		walkingRight = new Animation(new SpriteSheet(walkRight, 57, 64), 200);
		
		angryDown = new Animation(new SpriteSheet(walkDownAngry, 57, 64), 175);
		angryUp = new Animation(new SpriteSheet(walkUpAngry, 57, 64), 175);
		angryLeft = new Animation(new SpriteSheet(walkLeftAngry, 57, 64), 175);
		angryRight = new Animation(new SpriteSheet(walkRightAngry, 57, 64), 175);
		
		this.damage = 1;
		this.acceleration = 0.03f;
		this.deacceleration = 0.001f;
		this.maximumVelocity = 0.03f;
		
		this.health = this.maximumHealth = 8;
		
		right = true;
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
				
				if(this.y > this.dungeon.gamedata.hero.getY() - Tile.SIZE && this.y < this.dungeon.gamedata.hero.getY() + Tile.SIZE && this.x > this.dungeon.gamedata.hero.getX())
				{
					forceDrawLeft = true;
				}
				else forceDrawLeft = false;
				
				if(this.y > this.dungeon.gamedata.hero.getY() - Tile.SIZE && this.y < this.dungeon.gamedata.hero.getY() + Tile.SIZE && this.x < this.dungeon.gamedata.hero.getX())
				{
					forceDrawRight = true;
				}
				else forceDrawRight = false;
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
		
		if (angryCooldown > 0)
		{
			angryCooldown -= delta;
		}
		
		if(blinking)
		{
			mood = 2;
			angryCooldown = 15000;
		}
		
		if(angryCooldown <= 0)
		{
			up = false; 
			down = false;
			mood = 1;
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
		if(mood == 1)
		{
			if(this.direction == Direction.NORTH)
			{
				walkingUp.draw(this.getX() - this.getHalfWidth() - camera.getX(), this.getY() - this.getHalfHeight() - camera.getY());
			}
			else if (this.direction == Direction.SOUTH)
			{
				walkingDown.draw(this.getX() - this.getHalfWidth() - camera.getX(), this.getY() - this.getHalfHeight() - camera.getY());
			}
			else if (this.direction == Direction.EAST)
			{
				walkingRight.draw(this.getX() - this.getHalfWidth() - camera.getX(), this.getY() - this.getHalfHeight() - camera.getY());
			}
			else if (this.direction == Direction.WEST)
			{
				walkingLeft.draw(this.getX() - this.getHalfWidth() - camera.getX(), this.getY() - this.getHalfHeight() - camera.getY());
			}
		}
		
		else if (mood == 2)
		{
			if(this.direction == Direction.NORTH)
			{
				if(!(forceDrawLeft || forceDrawRight))
				angryUp.draw(this.getX() - this.getHalfWidth() - camera.getX(), this.getY() - this.getHalfHeight() - camera.getY());
			}
			else if (this.direction == Direction.SOUTH && (!forceDrawLeft || !forceDrawRight))
			{
				if(!(forceDrawLeft || forceDrawRight))
				angryDown.draw(this.getX() - this.getHalfWidth() - camera.getX(), this.getY() - this.getHalfHeight() - camera.getY());
			}
			if (this.direction == Direction.EAST)
			{
				angryRight.draw(this.getX() - this.getHalfWidth() - camera.getX(), this.getY() - this.getHalfHeight() - camera.getY());
			}
			else if (this.direction == Direction.WEST)
			{
				angryLeft.draw(this.getX() - this.getHalfWidth() - camera.getX(), this.getY() - this.getHalfHeight() - camera.getY());
			}
			
			if(forceDrawLeft)
			{
				angryLeft.draw(this.getX() - this.getHalfWidth() - camera.getX(), this.getY() - this.getHalfHeight() - camera.getY());
			}
			
			if(forceDrawRight)
			{
				angryRight.draw(this.getX() - this.getHalfWidth() - camera.getX(), this.getY() - this.getHalfHeight() - camera.getY());
			}
		}
	}
}


