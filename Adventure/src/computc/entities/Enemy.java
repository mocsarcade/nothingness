package computc.entities;

import org.newdawn.slick.SlickException;

import computc.worlds.Dungeon;

public abstract class Enemy extends Entity
{
	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;
	
	protected boolean blinkTimer;
	protected int blinkCooldown;
	
	protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;
    
    protected boolean attacking;
	
	public Enemy(Dungeon dungeon, int rx, int ry, int tx, int ty) throws SlickException 
	{
		super(dungeon, rx, ry, tx, ty);
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
		{
			return;
		}
		
		health -= damage;
		if(health <= 0)
		{
			dead = true;
		}
		
		blinking = true;
		blinkCooldown = 50;
	}
	
	public int getHealth()
	{
		return health;
	}
}