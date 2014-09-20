package computc.worlds.tiles;

import computc.Direction;
import computc.worlds.rooms.Room;

public class ArrowFloorTile extends FloorTile
{
	public ArrowFloorTile(Room room, int tx, int ty, Direction direction)
	{
		super(room, tx, ty);
		
		if(direction == Direction.NORTH)
		{
			this.image = Tile.images.get("northern arrow floor tile");
		}
		else if(direction == Direction.SOUTH)
		{
			this.image = Tile.images.get("southern arrow floor tile");
		}
		else if(direction == Direction.EAST)
		{
			this.image = Tile.images.get("eastern arrow floor tile");
		}
		else if(direction == Direction.WEST)
		{
			this.image = Tile.images.get("western arrow floor tile");
		}
		else
		{
			this.image = Tile.images.get("floor tile");
		}
	}
}