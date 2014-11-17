package computc.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SpriteSheet;

import computc.Direction;
import computc.Game;
import computc.cameras.Camera;
import computc.worlds.dungeons.Dungeon;
import computc.worlds.rooms.Room;

public class Murk extends Entity
{

	private Animation animation, sprite, walkingRight, walkingLeft;
	
	protected boolean left;
    protected boolean right;
	
	public Murk(Dungeon dungeon, int x, int y)
	{
		super(dungeon, dungeon.getFirstRoom(), x, y);
		
		this.acceleration = 0.03f;
		this.deacceleration = 0.001f;
		this.maximumVelocity = 0.03f;
		
		this.image = Game.assets.getImage("res/ancient.png").getSubImage(1, 1, 240, 104);
		this.animation =  new Animation(new SpriteSheet(this.image, 60, 104), 300);
		
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
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		int x = (int)(this.getX()) - camera.getX();
		int y = (int)(this.getY()) - camera.getY();
		
		this.animation.draw(x, y);
	}
	
}
