package computc.worlds.tiles;

import org.newdawn.slick.Color;

import computc.Direction;
import computc.Game;
import computc.worlds.rooms.Room;

public class DoorTile extends Tile
{
	private Direction critdir;

	public DoorTile(Room room, int tx, int ty, Direction critdir)
	{
		super(room, tx, ty);
		this.critdir = critdir;
		
		TileSubSet tilesubset = this.getRoom().getTileSet().getTileSubSet("arrow");
		
		if(critdir == Direction.NORTH)
		{
			this.image = tilesubset.getImage(0);
			this.color = tilesubset.getColor();
		}
		else if(critdir == Direction.SOUTH)
		{
			this.image = tilesubset.getImage(1);
			this.color = tilesubset.getColor();
		}
		else if(critdir == Direction.EAST)
		{
			this.image = tilesubset.getImage(2);
			this.color = tilesubset.getColor();
		}
		else if(critdir == Direction.WEST)
		{
			this.image = tilesubset.getImage(3);
			this.color = tilesubset.getColor();
		}
		else
		{
			tilesubset = this.getRoom().getTileSet().getTileSubSet("floor");
			this.image = tilesubset.getRandomImage();
			this.color = tilesubset.getColor();
		}
	}
	
	public void lock()
	{
		TileSubSet tilesubset = this.getRoom().getTileSet().getTileSubSet("door");
		this.image = tilesubset.getRandomImage();
		this.color = tilesubset.getColor();
		this.collideable = true;
		super.lock();
	}
	
	public void unlock()
	{
		if(critdir == Direction.NORTH)
		{
			TileSubSet tilesubset = this.getRoom().getTileSet().getTileSubSet("arrow");
			this.image = tilesubset.getImage(0);
			this.color = tilesubset.getColor();
		}
		else if(critdir == Direction.SOUTH)
		{
			TileSubSet tilesubset = this.getRoom().getTileSet().getTileSubSet("arrow");
			this.image = tilesubset.getImage(1);
			this.color = tilesubset.getColor();
		}
		else if(critdir == Direction.EAST)
		{
			TileSubSet tilesubset = this.getRoom().getTileSet().getTileSubSet("arrow");
			this.image = tilesubset.getImage(2);
			this.color = tilesubset.getColor();
		}
		else if(critdir == Direction.WEST)
		{
			TileSubSet tilesubset = this.getRoom().getTileSet().getTileSubSet("arrow");
			this.image = tilesubset.getImage(3);
			this.color = tilesubset.getColor();
		}
		else
		{
			TileSubSet tilesubset = this.getRoom().getTileSet().getTileSubSet("floor");
			this.image = tilesubset.getRandomImage();
			this.color = tilesubset.getColor();
		}
		
		this.collideable = false;
		super.unlock();
	}
}