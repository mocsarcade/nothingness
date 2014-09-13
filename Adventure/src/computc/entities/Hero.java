package computc.entities;

import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

import computc.Camera;
import computc.Direction;
import computc.worlds.Dungeon;
import computc.worlds.Room;

public class Hero extends Entity
{
	private boolean dead = false;
	
	private int projectileCooldown = 0;
	
	public Hero(Dungeon dungeon, Room room, int tx, int ty) throws SlickException
	{
		super(dungeon, room.getRoomyX(), room.getRoomyY(), tx, ty);
		
		this.dungeon = dungeon;
		this.acceleration = 0.06f;
		this.deacceleration = 0.02f;
		this.maximumVelocity = 3f;
		
		this.currentHealth = this.maximumHealth = 3;
		
		this.image = new Image("res/hero.png");
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
	
	public void update(Input input, int delta) throws SlickException
	{
		getNextPosition(input, delta);
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(input.isKeyDown(Input.KEY_Z))
		{
			maximumVelocity = 6f;
		}
		else
		{
			maximumVelocity = 3f;
		}
		

		if (blinkCooldown > 0)
		{
			blinkCooldown --;
		}
		
		if(blinkCooldown == 0)
		{
			blinking = false;
		}
		
		if (input.isKeyDown(Input.KEY_SPACE)) {
			
			if (projectileCooldown <= 0) 
			{
			dungeon.pc.addArrow(new Arrow(this.getX(), this.getY(), this.direction, true));
			System.out.println("shooting");
			projectileCooldown = 300;
			}
			
		}
		
		if(projectileCooldown > 0){
			projectileCooldown -= delta;
		}
	
		this.dungeon.getRoom(this.getRoomyX(), this.getRoomyY()).visited = true;
		
		super.update(delta);
	}
	
	// movement method
	private void getNextPosition(Input input, int delta)
	{
		if(input.isKeyDown(Input.KEY_UP)) 
		{
			this.direction = Direction.NORTH;
			
			dy -= acceleration * delta;
			if(dy < -maximumVelocity)
			{
				dy = -maximumVelocity;
			}
		}
		else if(input.isKeyDown(Input.KEY_DOWN))
		{
			this.direction = Direction.SOUTH;
			
			dy += acceleration * delta;
			
			if(dy > maximumVelocity)
			{
				dy = maximumVelocity;
			}
		}
		
		else //if neither KEY_UP nor KEY_DOWN
		{
			if (dy > 0) 
			{
				dy -= deacceleration * delta;
				if(dy < 0)
				{
					dy = 0;
				}
			}
			else if (dy < 0)
			{
				dy += deacceleration * delta;
				if(dy > 0) 
				{
					dy = 0;
				}
			}
		}

		 if(input.isKeyDown(Input.KEY_RIGHT))
		{
			 this.direction = Direction.EAST;
			 
			dx += acceleration * delta;
			if(dx > maximumVelocity) 
			{
				dx = maximumVelocity;
			}
		}
		 else if(input.isKeyDown(Input.KEY_LEFT)) 
		{
			 this.direction = Direction.WEST;
			 
			dx -= acceleration * delta;
			if(dx < -maximumVelocity)
			{
				dx = -maximumVelocity;
			}
		}
		else //if neither KEY_RIGHT nor KEY_LEFT
		{
			if (dx > 0) 
			{
				dx -= deacceleration * delta;
				if(dx < 0)
				{
					dx = 0;
				}
			}
			else if (dx < 0)
			{
				dx += deacceleration * delta;
				if(dx > 0) 
				{
					dx = 0;
				}
			}
		}
		
//		float step = this.moveSpeed * delta;
		
			if(input.isKeyDown(Input.KEY_UP))
				{
					this.direction = Direction.NORTH;
//					this.y -= step;
				}
			else if(input.isKeyDown(Input.KEY_DOWN))
				{
				this.direction = Direction.SOUTH;
//				this.y += step;
				}
		
			if(input.isKeyDown(Input.KEY_LEFT))
				{
				this.direction = Direction.WEST;
//				this.x -= step;
				}
			else if(input.isKeyDown(Input.KEY_RIGHT))
			{
				this.direction = Direction.EAST;
//				this.x += step;
			}
	}
	
	private void hit(int damage)
	{
		if(blinking)
		{
			return;
		}
		
		currentHealth -= damage;
		
		if(currentHealth < 0)
		{
			currentHealth = 0;
		}
		
		if(currentHealth == 0)
		{
			dead = true;
		}
		
		blinking = true;
		blinkCooldown = 100;
	}
	
	public void checkAttack(LinkedList<Enemy> enemies)
	{
		for(int i = 0; i < enemies.size(); i++)
		{
			Enemy e = enemies.get(i);
			if(intersects(e))
			{
				hit(e.getDamage());
				e.maximumVelocity = .3f;
			}
		}
	}
	
	public int getHealth()
	{
		return currentHealth;
	}
	
	public boolean isDead()
	{
		return dead;
	}
	
	public void setAlive()
	{
		dead = false;
	}
	
	// projectile Manager
	public static class ProjectileController {
		
		private LinkedList<Arrow> quiver = new LinkedList<Arrow>();
			
			 static Arrow TempArrow;
			
			Dungeon dungeon;
			
			public void update(int delta) {
				 for(int i = 0; i < quiver.size(); i++) {
					 TempArrow = quiver.get(i);
					 
					 if(TempArrow.getY() < 0)
						 removeArrow(TempArrow);
					 
					 TempArrow.update(delta);
					 System.out.println("arrows coord: " + TempArrow.getX() + " , " + TempArrow.getY());
				 }
			}
			
			public void render (Graphics g, Camera camera) {
				for (int i = 0; i < quiver.size(); i++) {
					quiver.get(i).render(g, camera);
				}
			}
			
			public void addArrow(Arrow block) {
				quiver.add(block);
			}
			
			public void removeArrow(Arrow block) {
				quiver.remove(block);
			}
			
			public int getQuiverSize(){
				return quiver.size();
			}

		}

	
	private float speed = 0.25f;
}