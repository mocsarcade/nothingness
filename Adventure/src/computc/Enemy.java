package computc;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Enemy extends Entity

	{

		private int health;
		private int maxHealth;
		private boolean dead;
		private int damage;
		
		private Image image;
		
		protected boolean blinking;
		protected long blinkTimer;
		
		
		public Enemy(TiledWorld world, int tx, int ty) throws SlickException 
		{
			super(world,  tx, ty);
			
			
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
		
		public void update() 
		{
			
		}
	
}
