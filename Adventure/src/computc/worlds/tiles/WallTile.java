package computc.worlds.tiles;

import computc.worlds.rooms.Room;

public class WallTile extends Tile
{
	public WallTile(Room room, int tx, int ty)
	{
		super(room, tx, ty);
		
		this.image = Tile.images.get("wall tile");
		this.color = Tile.colors.get("wall tile");
	}
	
	public boolean canMoveHere()
	{
		return false;
	}
	
	public static final int GID = 1;
}