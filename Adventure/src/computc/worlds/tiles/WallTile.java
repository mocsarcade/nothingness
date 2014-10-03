package computc.worlds.tiles;

import org.newdawn.slick.Color;

import computc.Game;
import computc.worlds.rooms.Room;

public class WallTile extends Tile
{
	public WallTile(Room room, int tx, int ty)
	{
		super(room, tx, ty);
		
		TileSubSet tilesubset = this.getRoom().getTileSet().getTileSubSet("wall");
		
		if(tx == 0 && ty == 0)
		{
			this.image = tilesubset.getImage(4);
		}
		else if(tx == 0 && ty == 8)
		{
			this.image = tilesubset.getImage(6);
		}
		else if(tx == 10 && ty == 0)
		{
			this.image = tilesubset.getImage(5);
		}
		else if(tx == 10 && ty == 8)
		{
			this.image = tilesubset.getImage(7);
		}
		else if(ty == 0)
		{
			this.image = tilesubset.getImage(0);
		}
		else if(ty == 8)
		{
			this.image = tilesubset.getImage(1);
		}
		else if(tx == 10)
		{
			this.image = tilesubset.getImage(2);
		}
		else if(tx == 0)
		{
			this.image = tilesubset.getImage(3);
		}
		else
		{
			this.image = tilesubset.getImage(8);
		}
		
		this.color = tilesubset.getColor();
		
		this.collideable = true;
	}
	
	public static final int GID = 1;
}