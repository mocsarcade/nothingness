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
		
		private Image image;
		
		protected boolean blinking;
		protected long blinkTimer;
		
		
		public Enemy(TiledWorld world, int tx, int ty) throws SlickException 
		{
			super(world,  tx, ty);
			
			this.image = new Image("res/thug.png");
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
		
		public void render(Graphics g) {
			
			image.draw(x, y);
			
		}
	
}
