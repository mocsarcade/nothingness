
package computc.entities;

import java.util.ArrayList;
import java.util.LinkedList;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import computc.cameras.Camera;
import computc.Direction;
import computc.worlds.Dungeon;
import computc.worlds.Room;
import computc.worlds.Tile;

public class Hero extends Entity
{
	private boolean dead = false;
	
	protected boolean chainAttack;
	protected int chainAttackCooldown;
	
	Image ironBall = new Image("res/ironball.png");
	
	
	// box2d "world"
	 
	private final Vec2 gravity = new Vec2(0, .5f);
	private Vec2 box2dPlayerPosition;
	 
	 
	public Chain chain;
	private ChainEnd ball;
	private int ballDamage;
	
	private int projectileCooldown = 0;
	
	public int arrowCount;
	private int maxArrows;
	
	private int arrowDamage;
	public ArrayList<Arrow> arrows;
	
	protected boolean firing;
	protected boolean swinging;
	private int meleeDamage;
	private int meleeRange;
	
	private Image swingRight, swingLeft, swingUp, swingDown;
	
	// actions 
	private Animation sprite, firingArrow, meleeSwing, meleeRight, meleeLeft, meleeUp, meleeDown, idle;

	public int coinage;
	
	public Hero(Dungeon dungeon, int tx, int ty) throws SlickException
	{
		super(dungeon, dungeon.getFirstRoom(), tx, ty);
		
		this.dungeon = dungeon;
		this.acceleration = 0.06f;
		this.deacceleration = 0.02f;
		this.maximumVelocity = 3f;
		
		facingRight = true; 
		facingDown = true;
		
		this.arrowCount = this.maxArrows = 300;
		arrowDamage = 2;
		arrows = new ArrayList<Arrow>();
		
		this.ballDamage = 2;
		
		this.currentHealth = this.maximumHealth = 3;
		
		meleeDamage = 3;
		meleeRange = 96;
		
		sprite = idle;
		
		this.image = new Image("res/hero.png"); 
		this.swingRight = new Image("res/heroMeleeRight.png");
		this.swingLeft = new Image("res/heroMeleeLeft.png");
		this.swingUp = new Image("res/heroMeleeUp.png");
		this.swingDown = new Image("res/heroMeleeDown.png");
		
		this.meleeRight = new Animation(new SpriteSheet(swingRight, 96, 48), 300);
		this.meleeLeft = new Animation(new SpriteSheet(swingLeft, 96, 48), 300);
		this.meleeDown = new Animation(new SpriteSheet(swingDown, 48, 96), 300);
		this.meleeUp = new Animation(new SpriteSheet(swingUp, 48, 96), 300);
		
		this.direction = Direction.SOUTH;
		
		// box2d stuff (chain)
		this.world = new World(gravity);
				
		// converts box2d position to hero's position on screen
		box2dPlayerPosition = new Vec2(this.getRoomPositionX()/30, this.getRoomPositionY()/30);
				
		if(this.dungeon.chainEnabled)
		{
		this.chain = new Chain(this.world, this);
		this.chain.playerBody.setTransform(box2dPlayerPosition, 0);
		this.ball = new ChainEnd(this.dungeon, this.getTileyX(), this.getTileyY(), this.direction, this.chain, this);
		}
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
		
		// draw chain
		if(this.dungeon.chainEnabled)
		{
		this.chain.render(graphics, camera);
		ironBall.draw(this.chain.lastLinkBody.getPosition().x * 30, (this.chain.lastLinkBody.getPosition().y * 30));
		}
		
		// draw arrows
		for(int i = 0; i < arrows.size(); i++)
		{
			arrows.get(i).render(graphics, camera);
		}
				
		
		if(this.direction == Direction.NORTH)
		{
			meleeSwing = meleeUp;
		}
		if(this.direction == Direction.SOUTH)
		{
			meleeSwing = meleeDown;
		}
		if(this.direction == Direction.EAST)
		{
			meleeSwing = meleeRight;
		}
		if(this.direction == Direction.WEST)
		{
			meleeSwing = meleeLeft;
		}
		
		
		if(swinging)
		{
			if(meleeSwing == meleeLeft)
			{
				meleeSwing.draw(this.getX() - this.getHalfWidth() - camera.getX() - 48, this.getY() - this.getHalfHeight() - camera.getY());	
			}
			else if(meleeSwing == meleeUp)
			{
				meleeSwing.draw((this.getX() - this.getHalfWidth() - camera.getX()), (this.getY() - this.getHalfHeight() - camera.getY()) - 48);	
			}
			else
			{
			meleeSwing.draw(this.getX() - this.getHalfWidth() - camera.getX(), this.getY() - this.getHalfHeight() - camera.getY());
			}
		}
		// converts box2d position to hero's position on screen
		box2dPlayerPosition = new Vec2(this.getLocalX(camera)/30, this.getLocalY(camera)/30);
	}
	
	public void renderOnMap(Graphics graphics, Camera camera)
	{
		int x = (int)((this.getX() - this.getHalfWidth()) / 8) - camera.getX();
		int y = (int)((this.getY()  - this.getHalfHeight()) / 8) - camera.getY();
		int w = this.getWidth() / 8;
		int h = this.getHeight() / 8;
		
		graphics.setColor(Color.white);
		graphics.fillRoundRect(x, y, w, h, 2);
	}
	
	public void update(Input input, int delta) throws SlickException
	{
//		System.out.println("the ball at the end of the chain's x & y are: " + this.ball.x + " , " + this.ball.y);
		
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
		
		// chain stuff
		if(this.dungeon.chainEnabled)
		{	
			this.chain.playerBody.setTransform(box2dPlayerPosition, 0);
				
			if(chainAttackCooldown > 0)
			{
				chainAttackCooldown -= delta;
			}
				
			if(chainAttackCooldown <= 0)
			{
				chainAttack = false;
				chainAttackCooldown = 0;
			}
				
			this.ball.update();
				
			this.chain.update(input, delta);		
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
		
		// the update method for the box2d world
		world.step(1/60f, 8, 3);
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
			
			if(input.isKeyDown(Input.KEY_D))
			{
				this.dungeon.toggleDebugDraw();
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
					if( e.getX() < x && e.getX() > x - meleeRange && e.getY() > y - getHalfHeight() && e.getY() < y + getHalfHeight()) 
					{
						e.hit(meleeDamage);
					}
				}
				
				if(facingDown) 
				{
					if(e.getY() > y && e.getY() < y + meleeRange && e.getX() > x - getHalfHeight() && e.getX() < x + getHalfHeight()) 
					{
						e.hit(meleeDamage);
					}
				}
				else 
				{
					if( e.getY() < y && e.getY() > y - meleeRange && e.getX() > x - getHalfHeight() && e.getX() < x + getHalfHeight()) 
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
			
			if(chainAttack)
			{
				if(this.ball.intersects(e))
				{
					e.hit(ballDamage);
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
	
	public void setChainAttack()
	{
		chainAttack = true;
		chainAttackCooldown = 200;
	}
	
	public boolean getChainAttack()
	{
		return chainAttack;
	}
	
	public void checkPickup(LinkedList<Key> keys)
	{
		for(Key key : keys)
		{
			if(this.intersects(key) && key.pickedup == false)
			{
				key.target = this;
				this.keys.add(key);
				key.pickedup = true;
			}
		}
	}
	
	public void checkGetCoin()
	{
		for(Coin coin : this.dungeon.coins)
		{
			if(this.intersects(coin) && coin.pickedup == false)
			{
				coin.pickedup = true;
				this.coinage++;
				System.out.println(coinage);
			}
		}
	}
	
	private float speed = 0.25f;
}