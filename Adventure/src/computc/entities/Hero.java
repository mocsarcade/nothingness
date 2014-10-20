
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
import org.newdawn.slick.geom.Vector2f;

import computc.cameras.Camera;
import computc.Direction;
import computc.Game;
import computc.worlds.dungeons.Dungeon;
import computc.worlds.rooms.Room;
import computc.worlds.tiles.Tile;

public class Hero extends Entity
{
	private boolean dead = false;
	private Graphics graphics;
	
	protected boolean chainAttack;
	protected int chainAttackCooldown;
	protected int bounceCooldown;
	
	Image ironBall = new Image("res/ironball.png");
	
	
	// box2d "world"
	 
	private final Vec2 gravity = new Vec2(0, .5f);
	private Vec2 box2dPlayerPosition;
	 
	 
	public Chain chain;
	private ChainEnd ball;
	private int ballDamage;
	
	private int arrowCooldown = 0;
	
	public int arrowCount;
	private int maxArrows;
	
	
	public ArrayList<Arrow> arrows;
	
	protected boolean firing;
	protected boolean swinging;
	private int meleeDamage;
	private int meleeRange;
	
	private double actualEnemySeparation;
	
	private int peekTimer;
	
	//Not sure if this is supposed to be int -- it was missing but referenced?
	
	private Image swingRight, swingLeft, swingUp, swingDown;
	private Image spriteSheet = Game.assets.getImage("res/heroSpriteSheet.png");
	private Image walkRight = spriteSheet.getSubImage(1, 1, 156, 62);
	private Image walkLeft = spriteSheet.getSubImage(1, 63, 156, 62);
	private Image walkDown = spriteSheet.getSubImage(1, 125, 156, 62);
	private Image walkUp = spriteSheet.getSubImage(1, 186, 156, 62);
	private Image heroIdleDown, heroIdleRight, heroIdleLeft, heroIdleUp;
	
	// actions 
	private Animation sprite, firingArrow, meleeSwing, meleeRight, meleeLeft, meleeUp, meleeDown, idle, idleDown, idleUp, idleRight, idleLeft, walkingLeft, walkingDown, walkingUp, walkingRight;

	public int coinage;

	private boolean hasKey;
	
	public Vector2f distanceToEnemy;
	public Enemy closestEnemy;
	
	public Hero(Dungeon dungeon, int tx, int ty) throws SlickException
	{
		super(dungeon, dungeon.getFirstRoom(), tx, ty);
		
		this.dungeon = dungeon;
		this.acceleration = 0.06f;
		this.deacceleration = 0.02f;
		this.maximumVelocity = 1f;
		
		this.heroIdleDown = walkDown.getSubImage(1, 1, 62, 62);
		this.heroIdleRight = walkRight.getSubImage(1, 1, 62, 62);
		this.heroIdleLeft = walkLeft.getSubImage(39, 1, 62, 62);
		this.heroIdleUp = walkUp.getSubImage(1, 1, 62, 62);

		facingRight = true; 
		facingDown = true;
		
		this.arrowCount = this.maxArrows = 30;
		arrows = new ArrayList<Arrow>();
		
		this.ballDamage = 2;
		
		this.currentHealth = this.maximumHealth = 15;
		
		meleeDamage = 3;
		meleeRange = 96;
		
		this.image = walkRight.getSubImage(1, 1, 39, 55); 
		this.swingRight = new Image("res/heroMeleeRight.png");
		this.swingLeft = new Image("res/heroMeleeLeft.png");
		this.swingUp = new Image("res/heroMeleeUp.png");
		this.swingDown = new Image("res/heroMeleeDown.png");
		
		this.walkingRight = new Animation(new SpriteSheet(walkRight, 39, 62), 200);
		this.walkingLeft = new Animation(new SpriteSheet(walkLeft, 39, 62), 200);
		this.walkingUp = new Animation(new SpriteSheet(walkUp, 39, 62), 200);
		this.walkingDown = new Animation(new SpriteSheet(walkDown, 39, 62), 200);
		
		this.meleeRight = new Animation(new SpriteSheet(swingRight, 96, 48), 300);
		this.meleeLeft = new Animation(new SpriteSheet(swingLeft, 96, 48), 300);
		this.meleeDown = new Animation(new SpriteSheet(swingDown, 48, 96), 300);
		this.meleeUp = new Animation(new SpriteSheet(swingUp, 48, 96), 300);
		
		this.idleDown = new Animation(new SpriteSheet(heroIdleDown, 39, 62), 10000);
		this.idleUp = new Animation(new SpriteSheet(heroIdleUp, 39, 62), 10000);
		this.idleRight = new Animation(new SpriteSheet(heroIdleRight, 39, 62), 10000);
		this.idleLeft = new Animation(new SpriteSheet(heroIdleLeft, 39, 62), 10000);
		
		this.direction = Direction.SOUTH;
		idle = idleDown;
		
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
		this.graphics = graphics;
		
		if(blinking) 
		{
			if(blinkCooldown % 4 == 0) 
			{
				return;
			}
		}
		
//		super.render(graphics, camera);
		
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
				
		
		// Setting sprite animation
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
		
		// Drawing animations
		if(sprite == walkingUp)
		{
			walkingUp.draw(this.getX() - this.getHalfWidth() - camera.getX(), this.getY() - this.getHalfHeight() - camera.getY());
		}
		else if(sprite == walkingDown)
		{
			walkingDown.draw(this.getX() - this.getHalfWidth() - camera.getX() , this.getY() - this.getHalfHeight() - camera.getY());
		}
		else if(sprite == walkingRight)
		{
			walkingRight.draw(this.getX() - this.getHalfWidth() - camera.getX() , this.getY() - this.getHalfHeight() - camera.getY());
		}
		else if(sprite == walkingLeft)
		{
			walkingLeft.draw(this.getX() - this.getHalfWidth() - camera.getX() , this.getY() - this.getHalfHeight() - camera.getY());
		}
		else if(sprite == idle && (this.direction == Direction.SOUTH || this.direction == Direction.NONE))
		{
			idleDown.draw(this.getX() - this.getHalfWidth() - camera.getX() , this.getY() - this.getHalfHeight() - camera.getY());
		}
		else if(sprite == idle && this.direction == Direction.NORTH)
		{
			idleUp.draw(this.getX() - this.getHalfWidth() - camera.getX() , this.getY() - this.getHalfHeight() - camera.getY());
		}
		else if(sprite == idle && this.direction == Direction.EAST)
		{
			idleRight.draw(this.getX() - this.getHalfWidth() - camera.getX() , this.getY() - this.getHalfHeight() - camera.getY());
		}
		else if(sprite == idle && this.direction == Direction.WEST)
		{
			idleLeft.draw(this.getX() - this.getHalfWidth() - camera.getX() , this.getY() - this.getHalfHeight() - camera.getY());
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
			maximumVelocity = 4f;
		}
		else
		{
			maximumVelocity = 2f;
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
				
			this.ball.update(delta);
				
			this.chain.update(input, delta);		
		}
		
		if(arrowCount > maxArrows)
		{
			arrowCount = maxArrows;
		}
		
		// update arrows
		for (int i = 0; i < arrows.size(); i++) 
			{
				arrows.get(i).update(delta);
				
				if(this.intersects(arrows.get(i)) && arrows.get(i).getArrowCooldown() > 0)
				{
					arrows.get(i).setRemove();
					this.arrowCount += 1;
				}
				
				if(arrows.get(i).shouldRemove()) 
				{
					arrows.remove(i);
					i--;
				}
			}
		
		if(arrowCooldown > 0)
		{
			arrowCooldown -= delta;
		}
		
		if(bounceCooldown > 0)
		{
			bounceCooldown -= delta;
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
		
	
		this.dungeon.getRoom(this.getRoomyX(), this.getRoomyY()).hasVisited = true;
		
		for(Key key : this.dungeon.keys)
		{
			if(this.intersects(key))
			{
				key.setX(this.x);
				key.setY(this.y);
				this.hasKey = true;
			}
		}
		
		super.update(delta);
		
		// the update method for the box2d world
		world.step(1/60f, 8, 3);
	}
	
	// movement method
	private void getNextPosition(Input input, int delta)
	{
	
		if(input.isKeyDown(Input.KEY_UP)) 
		{
			dy -= acceleration * delta;
			if(dy < -maximumVelocity)
			{
				dy = -maximumVelocity;
			}
		}
		else if(input.isKeyDown(Input.KEY_DOWN))
		{
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
			dx += acceleration * delta;
			if(dx > maximumVelocity) 
			{
				dx = maximumVelocity;
			}
		}
		 else if(input.isKeyDown(Input.KEY_LEFT)) 
		{
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
				sprite = walkingUp;
				this.direction = Direction.NORTH;
				facingDown = false;
				
					if(!(this.dy == -maximumVelocity))
					{
						if(this.getRoomPositionY() < Tile.SIZE * 2)
						{
							peekTimer += delta;
						}
					}
					else peekTimer = 0;
//				this.y -= step;
				}
			else if(input.isKeyDown(Input.KEY_DOWN))
				{
				sprite = walkingDown;
				this.direction = Direction.SOUTH;
				facingDown = true;
//				this.y += step;
				
					if(!(this.dy == maximumVelocity))
					{
						if(this.getRoomPositionY() > Room.HEIGHT - Tile.SIZE * 2)
						{
							peekTimer += delta;
						}
					}
					else peekTimer = 0;
				}
			
		
			if(input.isKeyDown(Input.KEY_LEFT))
				{
				sprite = walkingLeft;
				this.direction = Direction.WEST;
				facingRight = false;
//				this.x -= step;
				
					if(!(this.dx == -maximumVelocity))
					{
						if(this.getRoomPositionX()  < Tile.SIZE * 2)
						{
						peekTimer += delta;
						}
					}
					else peekTimer = 0;
				}
			else if(input.isKeyDown(Input.KEY_RIGHT))
				{
				sprite = walkingRight;
				this.direction = Direction.EAST;
				facingRight = true;
//				this.x += step;
				
					if(!(this.dx == maximumVelocity))
					{
						if(this.getRoomPositionX() > Room.WIDTH - Tile.SIZE * 2)
						{
						peekTimer += delta;
						}
					}
					else peekTimer = 0;
				}
			
			if(!(input.isKeyDown(Input.KEY_UP)) && !(input.isKeyDown(Input.KEY_DOWN))  && !(input.isKeyDown(Input.KEY_RIGHT)) && !(input.isKeyDown(Input.KEY_LEFT)))
			{
				sprite = idle;
			}
			
			
			if(input.isKeyDown(Input.KEY_D))
			{
				this.dungeon.toggleDebugDraw(this.graphics);
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
	
	public void checkAttack(LinkedList<Enemy> enemies, int delta)
	{
		for(int i = 0; i < enemies.size(); i++)
		{
			Enemy e = enemies.get(i);
			
			this.distanceToEnemy = new Vector2f(e.getX() - this.getX(), e.getY() - this.getY());
			actualEnemySeparation = Math.sqrt(distanceToEnemy.x * distanceToEnemy.x + distanceToEnemy.y * distanceToEnemy.y);
			e.distanceToHero = actualEnemySeparation;
		
				if(this.closestEnemy == null)
				{
					this.closestEnemy = e;
				}
				else if(e.getDistanceToHero() < closestEnemy.getDistanceToHero())
				{
					this.closestEnemy = e;
				}		
			if(swinging) 
			{
				if(facingRight) 
				{
					if(e.getX() > x && e.getX() < x + meleeRange && e.getY() > y - getHalfHeight() && e.getY() < y + getHalfHeight()) 
					{
						e.hit(meleeDamage);
						e.dx +=  delta * .01f;	
					}
				}
				else 
				{
					if( e.getX() < x && e.getX() > x - meleeRange && e.getY() > y - getHalfHeight() && e.getY() < y + getHalfHeight()) 
					{
						e.hit(meleeDamage);
						e.dx -=  delta * .01f;
					}
				}
				
				if(facingDown) 
				{
					if(e.getY() > y && e.getY() < y + meleeRange && e.getX() > x - getHalfHeight() && e.getX() < x + getHalfHeight()) 
					{
						e.hit(meleeDamage);
						e.dy +=  delta * .01f;
					}
				}
				
				else 
				{
					if( e.getY() < y && e.getY() > y - meleeRange && e.getX() > x - getHalfHeight() && e.getX() < x + getHalfHeight()) 
					{
						e.hit(meleeDamage);
						this.dy -=  delta * .01f;
					}
				}
			}
			
			if(intersects(e))
			{
				hit(e.getDamage());
				e.maximumVelocity = .3f;
				e.mood = 2;
				
				if(!(e instanceof Thug) && distanceToEnemy.x > e.getHalfWidth() && bounceCooldown <= 0)
				{
					this.dx -=  delta * .3f;
					bounceCooldown = 200;
				}
				
				else if(!(e instanceof Thug) && distanceToEnemy.x < - e.getHalfWidth() && bounceCooldown <= 0)
				{
					this.dx +=  delta * .3f;
					bounceCooldown = 200;

				}
				
				else if(!(e instanceof Thug) && distanceToEnemy.y > e.getHalfHeight() && bounceCooldown <= 0)
				{
					this.dy -=  delta * .3f;
					bounceCooldown = 200;
				}
				
				else if(!(e instanceof Thug) && distanceToEnemy.y < - e.getHalfHeight() && bounceCooldown <= 0)
				{
					this.dy +=  delta * .3f;
					bounceCooldown = 200;
				}
			}
			
			for(int j = 0; j < arrows.size(); j++) 
			{
				if(arrows.get(j).intersects(e)) 
				{
					e.hit(arrows.get(j).arrowDamage);
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
	
	public int getArrowCooldown()
	{
		return this.arrowCooldown;
	}
	
	public void startArrowCooldown()
	{
		this.arrowCooldown = 800;
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
	
	public int getPeekTimer()
	{
		return this.peekTimer;
	}
	
	public void resetPeekTimer()
	{
		peekTimer = 0;
	}
	
	public void checkPickup(LinkedList<Commodity> commodities)
	{
		/*for(Key key : keys)
		{
			if(this.intersects(key) && key.pickedup == false)
			{
				key.target = this;
				this.keys.add(key);
				key.pickedup = true;
			}
		}*/
		
		for(Commodity commodity: commodities)
		{
			if(this.intersects(commodity) && commodity.getType() == 2)
			{
				this.arrowCount += 5;
				commodities.remove(commodity);
			}
		}
	}
	
	public void checkGetCoin()
	{
		/*for(Coin coin : this.dungeon.coins)
		{
			if(this.intersects(coin) && coin.pickedup == false)
			{
				coin.pickedup = true;
				this.coinage++;
				System.out.println(coinage);
			}
		}*/
	}
	
	public boolean collidesWith(Entity entity)
	{
		return this.intersects(entity);
	}
	
	public Enemy getClosestEnemy()
	{
		return this.closestEnemy;
	}
	
	private float speed = 0.25f;
}