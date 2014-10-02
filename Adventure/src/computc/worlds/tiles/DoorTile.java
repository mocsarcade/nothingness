package computc.worlds.tiles;

import org.newdawn.slick.Color;

import computc.Direction;
import computc.Game;
import computc.worlds.rooms.Room;

public class DoorTile extends Tile
{
	public DoorTile(Room room, int tx, int ty)
	{
		super(room, tx, ty);
		
		TileSubSet tilesubset = this.getRoom().getTileSet().getTileSubSet("arrow");
		
		if(ty == 0)
		{
			this.image = tilesubset.getImage(0);
			this.color = tilesubset.getColor();
		}
		else if(ty == 8)
		{
			this.image = tilesubset.getImage(1);
			this.color = tilesubset.getColor();
		}
		else if(tx == 10)
		{
			this.image = tilesubset.getImage(2);
			this.color = tilesubset.getColor();
		}
		else if(tx == 0)
		{
			this.image = tilesubset.getImage(3);
			this.color = tilesubset.getColor();
		}
		else
		{
			this.image = tilesubset.getImage(0);
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