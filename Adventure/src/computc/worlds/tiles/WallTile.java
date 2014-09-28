package computc.worlds.tiles;

import org.newdawn.slick.Color;

import computc.Game;
import computc.worlds.rooms.Room;

public class WallTile extends Tile
{
	public WallTile(Room room, int tx, int ty)
	{
		super(room, tx, ty);
		
		TileSubSet tilesubset = this.getRoom().getTileSet().getTileSubSet("wall");
		
		this.image = tilesubset.getRandomImage();
		this.color = tilesubset.getColor();
		
		this.collideable = true;
	}
}