package computc.worlds.dungeons;

import computc.Direction;
import computc.worlds.rooms.Room;
import computc.worlds.tiles.TileSet;

public class FiveRoomDungeon extends Dungeon
{
	public FiveRoomDungeon()
	{
		super();
		
		this.firstRoom = new Room(this, 2, 2);

		new Room(this, 2, 3);
		new Room(this, 2, 1);
		new Room(this, 3, 2);
		new Room(this, 1, 2);

		this.firstRoom.connectWithRoomToTheNorth();
	}
}