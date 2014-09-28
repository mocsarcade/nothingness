package computc.worlds.dungeons;

import computc.Direction;
import computc.worlds.rooms.Room;
import computc.worlds.tiles.TileSet;

public class FiveRoomDungeon extends Dungeon
{
	public FiveRoomDungeon()
	{
		this.firstRoom = new Room(this, 2, 2);

		this.firstRoom.addArrowTile(Direction.NORTH);
		this.firstRoom.addArrowTile(Direction.SOUTH);
		this.firstRoom.addArrowTile(Direction.EAST);
		this.firstRoom.addArrowTile(Direction.WEST);
		
		this.firstRoom.instantiateNorthernRoom().addKey();
		this.firstRoom.instantiateSouthernRoom().addKey();
		this.firstRoom.instantiateEasternRoom().addKey();
		this.firstRoom.instantiateWesternRoom().addKey();
	}
}