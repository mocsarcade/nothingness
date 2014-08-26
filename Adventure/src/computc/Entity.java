package computc;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Vector2f;

public abstract class Entity {
	
	// tile stuff
	protected TiledWorld tiledWorld;
	protected int tileSize;
	protected float mapX;
	protected float mapY;
	
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
		tiledWorld = world;
		tileSize = world.getTileWidth();
		
		this.x = (tx + 0.5f) * world.getTileWidth();
		this.y = (ty + 0.5f) * world.getTileWidth();
	}
	
	public boolean intersects (Entity o) 
	{
		Rectangle r1 = getRectangle();
		
		Rectangle r2 = o.getRectangle();
		
		return r1.intersects(r2);
	}
	
	public Rectangle getRectangle() {
		return new Rectangle ((int)x - collisionWidth, (int)y - collisionHeight, collisionWidth, collisionHeight);
	}
	
	
	public int getX() 
	{
		return (int)x;
	}
		
	public int getY() 
	{
		return (int)y;
	}
		
	public int getWidth() 
	{
		return width;
	}
		
	public int getHeight()
	{
		return height;
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
	
	public void setMapPosition() 
	{
		mapX = tiledWorld.getX();
		mapY = tiledWorld.getY();
	}
		
		
	public void forceLeft(boolean b) 
	{
	facingLeft = b;
	}
	
	public void forceRight (boolean b) 
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
	

	
	
	

}
