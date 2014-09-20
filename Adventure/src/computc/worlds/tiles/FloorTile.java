package computc.worlds.tiles;

import computc.worlds.rooms.Room;

public class FloorTile extends Tile
{
	public FloorTile(Room room, int tx, int ty)
	{
		super(room, tx, ty);
		
		this.image = Tile.images.get("floor tile");
		this.color = Tile.colors.get("floor tile");
	}
	
	public static final int GID = 2;
}