package computc.worlds.tiles;

import org.newdawn.slick.Color;

import computc.Game;
import computc.worlds.rooms.Room;

public class FloorTile extends Tile
{
	public FloorTile(Room room, int tx, int ty)
	{
		super(room, tx, ty);
		
		TileGroup tilegroup = this.room.getTileGroup("floor");
		this.image = tilegroup.getRandomImage();
		this.color = Color.gray; //tilegroup.getColor();
	}
}