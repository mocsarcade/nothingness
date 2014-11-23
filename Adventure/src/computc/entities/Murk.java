package computc.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import computc.Direction;
import computc.Game;
import computc.cameras.Camera;
import computc.worlds.dungeons.Dungeon;
import computc.worlds.rooms.Room;

public class Murk extends Entity
{

	private Animation  sprite, walkingRight, walkingLeft, idleDown;
	
	protected boolean left;
    protected boolean right;
    
    protected boolean freezePosition;
    
    private Image spriteSheet = Game.assets.getImage("res/Murk.png");
    private Image walkRight = spriteSheet.getSubImage(1, 1, 390, 90);
    private Image walkLeft = spriteSheet.getSubImage(65, 91, 390, 90);
    private Image idle	= spriteSheet.getSubImage(1, 181, 65, 90);
	
	public Murk(Dungeon dungeon, int x, int y)
	{
		super(dungeon, dungeon.getFirstRoom(), x, y);
		
		this.acceleration = 0.03f;
		this.deacceleration = 0.001f;
		this.maximumVelocity = 0.04f;
		
		this.image = idle;

		this.walkingRight =  new Animation(new SpriteSheet(walkRight, 65, 90), 250);
		this.walkingLeft = new Animation(new SpriteSheet(walkLeft, 65, 90), 250);
		this.idleDown = new Animation(new SpriteSheet(idle, 65, 90), 1000);
		
		right = true;
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
		
		if(this.collidesWith(this.dungeon.gamedata.hero))
		{
			freezePosition = true;
		}
		else freezePosition = false;
		
		if(freezePosition)
		{
			this.direction = Direction.SOUTH;
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
			
			if(freezePosition)
			{
				dx = 0;
			}
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
			
			if(freezePosition)
			{
				dx = 0;
			}
		}
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		int x = (int)(this.getX()) - this.getHalfWidth() - camera.getX();
		int y = (int)(this.getY()) - this.getHalfHeight() - camera.getY();
		
		if(this.direction == Direction.EAST)
		{
			walkingRight.draw(x, y);
		}
		else if (this.direction == Direction.WEST)
		{
			walkingLeft.draw(x, y);
		}
		else idle.draw(x, y);
	}
	
	public boolean collidesWith(Entity entity)
	{
		return this.intersects(entity);
	}
	
}
