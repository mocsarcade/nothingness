package computc.worlds.tiles;

import computc.Direction;
import computc.Game;
import computc.worlds.rooms.Room;

public class ArrowFloorTile extends FloorTile
{
	public ArrowFloorTile(Room room, int tx, int ty, Direction direction)
	{
		super(room, tx, ty);
		
		TileSubSet tilesubset = this.getRoom().getTileSet().getTileSubSet("arrow");
		
		if(direction == Direction.NORTH)
		{
			this.image = tilesubset.getImage(0);
			this.color = tilesubset.getColor();
		}
		else if(direction == Direction.SOUTH)
		{
			this.image = tilesubset.getImage(1);
			this.color = tilesubset.getColor();
		}
		else if(direction == Direction.EAST)
		{
			this.image = tilesubset.getImage(2);
			this.color = tilesubset.getColor();
		}
		else if(direction == Direction.WEST)
		{
			this.image = tilesubset.getImage(3);
			this.color = tilesubset.getColor();
		}
	}
}