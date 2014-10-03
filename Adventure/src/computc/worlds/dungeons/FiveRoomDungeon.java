package computc.worlds.dungeons;

import computc.Direction;
import computc.GameData;
import computc.worlds.Door;
import computc.worlds.rooms.Room;
import computc.worlds.tiles.TileSet;

public class FiveRoomDungeon extends Dungeon
{
	public FiveRoomDungeon(GameData gamedata)
	{
		super(gamedata);
		
		this.firstRoom = new Room(this, 2, 2);

		new Room(this, 2, 3);
		new Room(this, 2, 1);
		new Room(this, 3, 2);
		new Room(this, 1, 2);

		this.firstRoom.makeDoor(Direction.NORTH);
		this.firstRoom.makeDoor(Direction.SOUTH);
		this.firstRoom.makeDoor(Direction.EAST);
		this.firstRoom.makeDoor(Direction.WEST);
	}
}