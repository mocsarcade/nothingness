package computc;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Enemy extends Entity
{
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;
	
	protected boolean blinking;
	protected long blinkTimer;
	
	
	public Enemy(World world, int tx, int ty) throws SlickException 
	{
		super(world, tx, ty);
	}
	
	public boolean isDead() 
	{
		return dead;
	}
	
	public int getDamage() 
	{
		return damage;
	}
	
	public void hit(int damage)
	{
		
		if(dead || blinking)
			return;
		
		health -= damage;
		if(health <= 0)
			dead = true;
		
		blinking = true;
	}
}