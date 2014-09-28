package computc.worlds.tiles;

import computc.worlds.rooms.Room;

public class FloorTile extends Tile
{
	public FloorTile(Room room, int tx, int ty)
	{
		super(room, tx, ty);
		
		TileSubSet tilesubset = this.getRoom().getTileSet().getTileSubSet("floor");
		
		this.image = tilesubset.getRandomImage();
		this.color = tilesubset.getColor();
	}
}