package computc.entities;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;



import computc.Game;
import computc.cameras.Camera;
import computc.worlds.dungeons.Dungeon;
import computc.worlds.rooms.Room;


public abstract class Enemy extends Entity
{
	public Enemy(Dungeon dungeon, Room room, int x, int y)
	{
		super(dungeon, room, x, y);
	}

	protected int health;
	protected int maxHealth;
	protected boolean dead;
	protected int damage;
	
	public int mood;
	
	protected boolean blinkTimer;
	protected int blinkCooldown;
	
	protected double distanceToHero;
	
	protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;
    
    protected boolean attacking;
    
    protected boolean smash;
	protected boolean alreadySmashed;
	
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
			Game.assets.playSoundEffectWithoutRepeat("enemyDying");
		}
		
		blinking = true;
		blinkCooldown = 50;
		Game.assets.playSoundEffectWithoutRepeat("swordStrikesEnemy");
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public boolean getSmash()
	{
		return smash;
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		if(blinking) 
		{
			if(blinkCooldown % 4 == 0) 
			{
				return;
			}
		}
		super.render(graphics, camera);
		
	}
	
	public double getDistanceToHero()
	{
		return distanceToHero;
	}
}