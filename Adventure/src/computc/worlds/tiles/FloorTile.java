package computc.worlds.tiles;

import org.newdawn.slick.Color;

import computc.Game;
import computc.worlds.rooms.Room;

public class FloorTile extends Tile
{
	public FloorTile(Room room, int tx, int ty)
	{
		super(room, tx, ty);
		
		TileGroup tilesubset = this.getRoom().getTileSet().getTileGroup("floor");
		
		this.image = tilesubset.getRandomImage();
		this.color = tilesubset.getColor();
	}
}