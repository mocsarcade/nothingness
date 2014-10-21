package computc.worlds.tiles;

import org.newdawn.slick.Color;

import computc.Direction;
import computc.Game;
import computc.worlds.rooms.Room;

public class DoorTile extends Tile
{
	public DoorTile(Room room, int tx, int ty, Direction critdir)
	{
		super(room, tx, ty);
		
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
		this.collideable = true;
	}
	
	public void unlock()
	{
		TileSubSet tilesubset = this.getRoom().getTileSet().getTileSubSet("arrow");
		
		if(ty == 0)
		{
			this.image = tilesubset.getImage(0);
			this.color = tilesubset.getColor();
		}
		else if(ty == room.getHeight() - 1)
		{
			this.image = tilesubset.getImage(1);
			this.color = tilesubset.getColor();
		}
		else if(tx == room.getWidth() - 1)
		{
			this.image = tilesubset.getImage(2);
			this.color = tilesubset.getColor();
		}
		else if(tx == 0)
		{
			this.image = tilesubset.getImage(3);
			this.color = tilesubset.getColor();
		}
		
		this.collideable = false;
	}
}