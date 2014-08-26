package computc;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public abstract class Entity {
	
	// world
	protected World world;
	
	// position
	protected float x;
	protected float y;
	
	// movement
	protected Vector2f movement;
	protected Direction direction;
	
	// rendering
	protected Image image;
	
	// hitbox
	protected int hitboxWidth;
	protected int hitboxHeight;
	
	// some attributes?
	protected double maxSpeed;
	protected double moveSpeed;
	protected double stopSpeed;
	protected double attackSpeed;
	
	// more collision stuff?
	protected int currentRow;
	protected int currentCol;
	protected int destinationX;
	protected int destinationY;
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	
	public Entity(World world, int tx, int ty)
	{
		this.world = world;
				
		this.x = (tx + 0.5f) * 64; //this.world.getTileWidth();
		this.y = (ty + 0.5f) * 64; //this.world.getTileWidth();
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
		
	public void setMovement(Vector2f movement)
	{
		this.movement = movement;
	}
	
	public Vector2f getMovement()
	{
		return this.movement;
	}
}