package computc.entities;

import org.newdawn.slick.Image;

import computc.worlds.Dungeon;
import computc.worlds.Room;

public class Door extends Entity
{
	public Door(Dungeon dungeon, Room room, int tx, int ty)
	{
		super(dungeon, room, tx, ty);
		this.image = Door.IMAGE;
	}
	
	public static Image IMAGE;
}
