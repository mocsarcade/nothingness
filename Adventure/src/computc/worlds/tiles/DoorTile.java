package computc.worlds.tiles;

import org.newdawn.slick.Color;

import computc.Game;
import computc.worlds.rooms.Room;

public class DoorTile extends Tile
{
	public DoorTile(Room room, int tx, int ty)
	{
		super(room, tx, ty);

		this.image = Game.assets.getTileGroup("./res/tiles/door.tile.png").getRandomImage();
		this.color = Color.yellow;
		
		this.collideable = true;
	}
	
	public void unlock()
	{
		this.room.setTile(this.tx, this.ty, new FloorTile(this.room, this.tx, this.ty));
	}
}