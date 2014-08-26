package computc;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Thug extends Enemy	
{
	public Thug(TiledWorld world, int tx, int ty) throws SlickException 
	{
		super(world, tx, ty);
		
		this.image = new Image("res/thug.png");
		
		collisionWidth = image.getWidth();
		collisionHeight = image.getHeight();
		
		this.health = this.maxHealth = 5;
		this.damage = 2;
	}
	
	public void update()
	{
		//this.setPosition(this.x, this.y);
	}
}