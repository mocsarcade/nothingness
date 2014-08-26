package computc;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Thug extends Enemy	
{
	public Thug(World world, int tx, int ty) throws SlickException 
	{
		super(world, tx, ty);
		
		this.image = new Image("res/thug.png");
		
		this.health = this.maxHealth = 5;
		this.damage = 2;
	}
	
	public void update()
	{
		this.setX(this.x);
		this.setY(this.y);
		
		if(this.intersects(this.world.hero))
		{
			System.out.println("touching the hero!"); //this still isn't working. :<
		}
	}
}