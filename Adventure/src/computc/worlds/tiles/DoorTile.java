package computc.worlds.tiles;

import computc.worlds.rooms.Room;

public class DoorTile extends Tile
{
	public DoorTile(Room room, int tx, int ty)
	{
		super(room, tx, ty);
		
		this.image = Tile.images.get("door tile");
		this.color = Tile.colors.get("door tile");
	}
	
	public boolean canMoveHere()
	{
		return false;
	}
	
	/*public void lock()
	{
		this.image = Tile.images.get("door");
		this.isBlocked = true;
		this.locked = true;
	}
	
	public void unlock()
	{
		this.image = Tile.images.get("floor");
		this.isBlocked = false;
		this.locked = false;
	}*/
}