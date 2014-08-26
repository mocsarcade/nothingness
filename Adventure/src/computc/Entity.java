package computc;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public abstract class Entity {
	
	// tile stuff
	protected TiledWorld world;
	
	// position, movement vector, dimensions
	protected float x;
	protected float y;
	protected Vector2f movement;
	protected int width;
	protected int height;
	
	
	// collision box
	protected int collisionWidth;
	protected int collisionHeight;
	
	// more collision stuff
	protected int currentRow;
	protected int currentCol;
	protected int destinationX;
	protected int destinationY;
	
	protected boolean topLeft;
	protected boolean topRight;
	protected boolean bottomLeft;
	protected boolean bottomRight;
	
	// animation
	protected Image image;
	protected Animation animation;
	
	// movement
	protected boolean facingLeft;
	protected boolean facingRight;
	protected boolean facingUp; 
	protected boolean facingDown;
	protected boolean attacking;
	
	// attributes
	protected double moveSpeed;
	protected double maxSpeed;
	protected double stopSpeed;
	protected double attackSpeed;
	
	
	public Entity(TiledWorld world, int tx, int ty)
	{
		this.world = world;
				
		this.x = (tx + 0.5f) * this.world.getTileWidth();
		this.y = (ty + 0.5f) * this.world.getTileWidth();
	}
	
	public boolean intersects(Entity that) 
	{
		Rectangle r1 = this.getRectangle();
		Rectangle r2 = that.getRectangle();
		
		return r1.intersects(r2);
	}
	
	public Rectangle getRectangle()
	{
		return new Rectangle ((int)x - collisionWidth, (int)y - collisionHeight, collisionWidth, collisionHeight);
	}
	
	public int getX() 
	{
		return (int)(this.x);
	}
		
	public int getY() 
	{
		return (int)(this.y);
	}
		
	public int getWidth() 
	{
		return this.width;
	}
		
	public int getHeight()
	{
		return this.height;
	}
		
	public int getCollisionWidth() 
	{
		return collisionWidth;
	}
		
	public int getCollisionHeight() 
	{
		return collisionHeight;
	}
		
	public void setPosition(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
		
	public void setVector(Vector2f movement) 
	{
		this.movement = movement;
	}
		
	public void forceLeft(boolean b) 
	{
		facingLeft = b;
	}
	
	public void forceRight(boolean b) 
	{
		facingRight = b;
	}
	
	public void setUp(boolean b) 
	{
		facingUp = b;
	}
		
	public void setDown(boolean b) 
	{
		facingDown = b;
	}
	
	public void render(Graphics graphics)
	{
		int x = this.getX() - (this.getWidth() / 2);
		int y = this.getY() - (this.getHeight() / 2);
		
		this.image.draw(x, y);
	}
}
