package computc.worlds.tiles;

import computc.Direction;
import computc.Game;
import computc.worlds.rooms.Room;

public class ArrowFloorTile extends FloorTile
{
	public ArrowFloorTile(Room room, int tx, int ty, Direction direction)
	{
		super(room, tx, ty);
		
		if(direction == Direction.NORTH)
		{
			this.image = Game.assets.getTileGroup("./res/tiles/northern.arrow.tile.png").getRandomImage();
		}
		else if(direction == Direction.SOUTH)
		{
			this.image = Game.assets.getTileGroup("./res/tiles/southern.arrow.tile.png").getRandomImage();
		}
		else if(direction == Direction.EAST)
		{
			this.image = Game.assets.getTileGroup("./res/tiles/eastern.arrow.tile.png").getRandomImage();
		}
		else if(direction == Direction.WEST)
		{
			this.image = Game.assets.getTileGroup("./res/tiles/western.arrow.tile.png").getRandomImage();
		}
	}
}