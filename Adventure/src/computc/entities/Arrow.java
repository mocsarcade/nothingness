package computc.entities;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

import computc.cameras.Camera;
import computc.Direction;
import computc.Game;
import computc.worlds.dungeons.Dungeon;
import computc.worlds.rooms.Room;

public class Arrow extends Entity
{

	private boolean hit;
	private boolean remove;
	protected boolean left;
    protected boolean right;
    protected boolean up;
    protected boolean down;
    protected boolean arrowNotched;
    protected boolean tempArrow;
    
    protected boolean stuck;
    protected boolean inert;
    private int stickCooldown;
    
    private boolean filterSwitch;
    private boolean powerCharge;
    
    public int arrowDamage;
    
    Image arrows = Game.assets.getImage("res/arrowSpriteSheet.png");
	
	public Arrow(Dungeon dungeon, Room room, int tx, int ty, Direction direction) 
	{
		super(dungeon, room, tx, ty);
		
		this.myFilter = new Color(redFilter, greenFilter, blueFilter, 1f);
		
		this.direction = direction;
		
		this.dungeon = dungeon;
		
		this.acceleration = 7f;
		
		this.arrowDamage = 2;
		
		if(direction == Direction.NORTH)
		{
			dy = -acceleration;
			this.image = arrows.getSubImage(1, 1, 30, 30);
		}
		else if(direction == Direction.SOUTH)
		{
			dy = acceleration;
			this.image = arrows.getSubImage(129, 1, 30, 30);
		}
		else if(direction == Direction.EAST)
		{
			dx = acceleration;
			this.image = arrows.getSubImage(97, 1, 30, 30);
		}
		else if(direction == Direction.WEST)
		{
			dx = -acceleration;
			this.image = arrows.getSubImage(161, 1, 30, 30);
		}
		
	}
	
	public void update(int delta)
	{
		
		checkTileMapCollision();
		setPosition(xtemp, ytemp);
		
		if(dx == 0 && !hit && (this.direction == Direction.EAST || this.direction == Direction.WEST))
		{
			setHit();
		}
		
		if(dy == 0 && !hit && (this.direction == Direction.NORTH || this.direction == Direction.SOUTH))
		{
			setHit();
		}
		
		if(hit)
		{
			stuck = true;
		}
		
		if(stuck && stickCooldown == 0)
		{
			stickCooldown = 5000;
		}
		
		if(stickCooldown < 100 && stickCooldown > 0)
		{
			remove = true;
		}
		
		if(!(this.getMostRecentCollision() == null) && this.getMostRecentCollision().isDead())
		{
			remove = true;
		}
		
		if(stickCooldown > 0)
		{
			stickCooldown -= delta;
		}
		
		if(stickCooldown < 0)
		{
			stickCooldown = 0;
		}
		
		if(stuck && this.getMostRecentCollision() == null)
		{
			inert = true;
		}
		
		if(stuck && this.getMostRecentCollision() instanceof Enemy)
		{
			this.x = this.getMostRecentCollision().getX();
			this.y = this.getMostRecentCollision().getY();
			this.arrowDamage = 0;
			
			if(stickCooldown > 2500)
			{
				stickCooldown = 2500;
			}
			
			inert = true;
		}
		
		myFilter = new Color(redFilter, greenFilter, blueFilter, this.filterAlpha);
		
		if(this.powerCharge)
		{
			
			if(this.direction == Direction.SOUTH)
			{
				this.dy += 1f;
			}
			if(this.direction == Direction.NORTH)
			{
				this.dy -= 1f;
			}
			if(this.direction == Direction.EAST)
			{
				this.dx += 1f;
			}
			if(this.direction == Direction.WEST)
			{
				this.dx -= 1f;
			}
		}

	}
	
	public void render(Graphics graphics, Camera camera)
	{
		if(this.powerCharge)
		{
			this.setPowerUp();
		}
		
		myFilter = new Color(redFilter, greenFilter, blueFilter, this.filterAlpha);
		
		int x = (int)this.getX() - this.getHalfWidth() - camera.getX();
		int y = (int)this.getY() - this.getHalfHeight() - camera.getY();
		
		this.image.draw(x, y, myFilter);
		
		if(this.tempArrow)
		{
			if(this.direction == Direction.WEST)
			{
				this.image.draw(this.dungeon.gamedata.hero.getX() - this.dungeon.gamedata.hero.getHalfWidth() - camera.getX() + 5, this.dungeon.gamedata.hero.getY() - this.dungeon.gamedata.hero.getHalfHeight() - camera.getY() + 5, myFilter);
			}
			else if(this.direction == Direction.EAST)
			{
				this.image.draw(this.dungeon.gamedata.hero.getX() - this.dungeon.gamedata.hero.getHalfWidth() - camera.getX() + 8, this.dungeon.gamedata.hero.getY() - this.dungeon.gamedata.hero.getHalfHeight() - camera.getY() + 5, myFilter);

			}	
			else if(this.direction == Direction.SOUTH)
			{
				this.image.draw(this.dungeon.gamedata.hero.getX() - this.dungeon.gamedata.hero.getHalfWidth() - camera.getX() + 12, this.dungeon.gamedata.hero.getY() - this.dungeon.gamedata.hero.getHalfHeight() - camera.getY() + 20, myFilter);
			}
		}
	}
	
	public void setHit() 
	{
		if(hit)
			return;
		
		hit = true;
		dx = 0; dy = 0;
		
	}
	
	public boolean intersects(Entity that)
	{
		Rectangle r1 = this.getHitbox();
		Rectangle r2 = that.getHitbox();
		
		if(that instanceof Enemy && r1.intersects(r2) == true)
		{
			this.mostRecentCollision = that;
		}
		
		if(inert)
		{
			return false;
		}
		
		return r1.intersects(r2);
	}
	
	public boolean shouldRemove()
	{
		return remove;
	}
	
	public float getX() 
	{
		return x;
	}
	
	public float getY() 
	{
		return y;
	}
	
	public Image getImage()
	{
		return this.image;
	}
	
	public int getArrowCooldown()
	{
		return stickCooldown;
	}
	
	public void setRemove()
	{
		remove = true;
	}
	
	public void setArrowNotched()
	{
		arrowNotched = true;
	}
	
	public void setPowerCharge()
	{
		powerCharge = true;
	}
	
	public void setPowerUp()
	{
		if(!filterSwitch)
		{
			this.greenFilter -= .1f;
			this.blueFilter -= .1f;
			
			if(greenFilter < .2f || blueFilter < .2f)
			{
				filterSwitch = true;
			}
		}
		
		if(filterSwitch)
		{
			this.greenFilter += .1f;
			this.blueFilter += .1f;
			
			if(this.greenFilter > .9f || this.blueFilter > .9f)
			{
				filterSwitch = false;
			}
		}
	}
	
	public void setTempArrow()
	{
		tempArrow = true;
	}
	
}
