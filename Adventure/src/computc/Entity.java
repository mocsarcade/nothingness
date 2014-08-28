package computc;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public abstract class Entity
{
	// world
	protected World world;
	
	// position
	protected float x;
	protected float y;
	
	// movement
	protected Vector2f step;
	protected Direction direction;
	
	// rendering
	protected Image image;
	
	// some attributes?
	protected float maxSpeed;
	protected float moveSpeed;
	protected double stopSpeed;
	protected double attackSpeed;
	
	// blinking collision indicator
	protected boolean blinking;
	protected int blinkTimer;
	
	public Entity(World world, int tx, int ty)
	{
		this.world = world;
		
		this.x = (tx + 0.5f) * this.world.room.getTileWidth();
		this.y = (ty + 0.5f) * this.world.room.getTileWidth();
	}
	
	public void update(Input input, int delta)
	{
		//this is to be overloaded by subclasses.
	}
	
	public void render(Graphics graphics)
	{
		int x = this.getX() - (this.getWidth() / 2);
		int y = this.getY() - (this.getHeight() / 2);
		
		this.image.draw(x, y);
	}
	
	public boolean intersects(Entity that)
	{
		Rectangle r1 = this.getHitbox();
		Rectangle r2 = that.getHitbox();
		
		return r1.intersects(r2);
	}
	
	public Rectangle getHitbox()
	{
		int x = this.getX();
		int y = this.getY();
		
		int width = this.getHitboxWidth();
		int height = this.getHitboxHeight();
		
		return new Rectangle(x - (width / 2), y - (width / 2), width, height);
	}
	
	public int getX()
	{
		return (int)(this.x);
	}
		
	public int getY() 
	{
		return (int)(this.y);
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public void setY(float y)
	{
		this.y = y;
	}
	
	public void setPosition(float x, float y) 
	{
		this.x = x;
		this.y = y;
	}
	
	public int getWidth()
	{
		return this.image.getWidth();
	}
	
	public int getHeight()
	{
		return this.image.getHeight();
	}
	
	public Direction getDirection()
	{
		return this.direction;
	}
		
	public void setDirection(Direction direction) 
	{
		this.direction = direction;
	}
	
	public int getHitboxWidth() 
	{
		return this.getWidth();
	}
		
	public int getHitboxHeight() 
	{
		return this.getHeight();
	}
		
	public void setStep(Vector2f step)
	{
		this.step = step;
	}
	
	public Vector2f getStep()
	{
		return this.step;
	}
}