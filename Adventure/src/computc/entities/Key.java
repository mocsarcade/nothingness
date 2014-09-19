package computc.entities;

import org.newdawn.slick.Image;

import computc.worlds.Dungeon;
import computc.worlds.Room;

public class Key extends Entity
{
	public Hero target;
	private float speed = 1f;
	public boolean pickedup = false;

	public Key(Dungeon dungeon, Room room, int tx, int ty)
	{
		super(dungeon, room, tx, ty);
		this.image = Key.IMAGE;
	}
	
	public void update(int delta)
	{
		if(this.target != null)
		{
			if(this.x < this.target.getX())
			{
				this.x += delta * this.speed;
				
				if(this.x > this.target.getX())
				{
					this.x = this.target.getX();
				}
			}
			else if(this.x > this.target.getX())
			{
				this.x -= delta * this.speed;
				
				if(this.x < this.target.getX())
				{
					this.x = this.target.getX();
				}
			}

			if(this.y < this.target.getY())
			{
				this.y += delta * this.speed;
				
				if(this.y > this.target.getY())
				{
					this.y = this.target.getY();
				}
			}
			else if(this.y > this.target.getY())
			{
				this.y -= delta * this.speed;
				
				if(this.y < this.target.getY())
				{
					this.y = this.target.getY();
				}
			}
		}
	}
	
	public static Image IMAGE;
}