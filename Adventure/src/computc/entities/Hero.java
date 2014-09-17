package computc.entities;

import java.util.ArrayList;
import java.util.LinkedList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import computc.Camera;
import computc.Direction;
import computc.worlds.Dungeon;
import computc.worlds.Room;
import computc.worlds.Tile;

public class Hero extends Entity
{
	private boolean dead = false;
	
	private int projectileCooldown = 0;
	
	public int arrowCount;
	private int maxArrows;
	
	private int arrowDamage;
	public ArrayList<Arrow> arrows;
	
	protected boolean firing;
	protected boolean swinging;
	private int meleeDamage;
	private int meleeRange;
	
	private Image swingingImage;
	
	// actions 
	private Animation sprite, firingArrow, meleeSwing, idle;
	
	public Hero(Dungeon dungeon, Room room, int tx, int ty) throws SlickException
	{
		super(dungeon, room.getRoomyX(), room.getRoomyY(), tx, ty);
		
		this.dungeon = dungeon;
		this.acceleration = 0.06f;
		this.deacceleration = 0.02f;
		this.maximumVelocity = 3f;
		
		facingRight = true; 
		facingDown = true;
		
		this.arrowCount = this.maxArrows = 300;
		arrowDamage = 2;
		arrows = new ArrayList<Arrow>();
		
		this.currentHealth = this.maximumHealth = 3;
		
		meleeDamage = 3;
		meleeRange = 64;
		
		sprite = idle;
		
		this.image = new Image("res/hero.png"); 
		this.swingingImage = new Image("res/heroMelee.png");
		
		this.meleeSwing = new Animation(new SpriteSheet(swingingImage, 48, 48), 500);
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		super.render(graphics, camera);
		// draw arrows
		for(int i = 0; i < arrows.size(); i++)
		{
			arrows.get(i).render(graphics, camera);
		}
		
		if(blinking) 
		{
			if(blinkCooldown % 4 == 0) 
			{
				return;
			}
		}
		
		if(swinging)
		{
			meleeSwing.draw(this.getX() - this.getHalfWidth() - camera.getX(), this.getY() - this.getHalfHeight() - camera.getY());
		}
		
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
		
		// Check if the attack has stopped
		if(swinging) 
		{
			meleeSwing.setLooping(false);
			if(meleeSwing.isStopped())
			{
				swinging = false;
				meleeSwing.restart();
			}
		}
		

		if (blinkCooldown > 0)
		{
			blinkCooldown --;
		}
		
		if(blinkCooldown == 0)
		{
			blinking = false;
		}
		
		if(arrowCount > maxArrows)
		{
			arrowCount = maxArrows;
		}
		
		// update arrows
		for (int i = 0; i < arrows.size(); i++) 
			{
				arrows.get(i).update();
				if(arrows.get(i).shouldRemove()) 
				{
					arrows.remove(i);
					i--;
				}
			}
		
		if(projectileCooldown > 0){
			projectileCooldown -= delta;
		}
		
		// set Animation
		if(swinging) 
		{
			if(sprite != meleeSwing) 
			{
				sprite = meleeSwing;
			}
		}
		else if(firing) 
		{
			if(sprite != firingArrow) 
			{
				sprite = firingArrow;
			}
		}
		
		// set direction
		if(sprite != meleeSwing && sprite != firingArrow) 
		{
			if(this.direction == Direction.EAST) facingRight = true;
			if(this.direction == Direction.WEST) facingRight = false;
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
				facingDown = false;
//				this.y -= step;
				}
			else if(input.isKeyDown(Input.KEY_DOWN))
				{
				this.direction = Direction.SOUTH;
				facingDown = true;
//				this.y += step;
				}
		
			if(input.isKeyDown(Input.KEY_LEFT))
				{
				this.direction = Direction.WEST;
				facingRight = false;
//				this.x -= step;
				}
			else if(input.isKeyDown(Input.KEY_RIGHT))
				{
				this.direction = Direction.EAST;
				facingRight = true;
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
			
			if(swinging) 
			{
				if(facingRight) 
				{
					if(e.getX() > x && e.getX() < x + meleeRange && e.getY() > y - getHalfHeight() && e.getY() < y + getHalfHeight()) 
					{
						e.hit(meleeDamage);
					}
				}
				else 
				{
					if( e.getX() > x && e.getX() < x + meleeRange && e.getY() > y - getHalfHeight() && e.getY() < y + getHalfHeight()) 
					{
						e.hit(meleeDamage);
					}
				}
			}
			
			if(intersects(e))
			{
				hit(e.getDamage());
				e.maximumVelocity = .3f;
			}
			
			for(int j = 0; j < arrows.size(); j++) 
			{
				if(arrows.get(j).intersects(e)) {
					e.hit(arrowDamage);
					arrows.get(j).setHit();
					break;
				}
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
	
	public void setFiring()
	{
		firing = true;
	}
	public void setSwinging()
	{
		swinging = true;
	}

	
	private float speed = 0.25f;
}