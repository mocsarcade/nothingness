package computc.entities;

import org.newdawn.slick.Image;

import computc.worlds.Dungeon;
import computc.worlds.Room;

public class Key extends Entity
{
	public Key(Dungeon dungeon, Room room, int tx, int ty)
	{
		super(dungeon, room, tx, ty);
		this.image = Key.IMAGE;
	}
	
	public static Image IMAGE;
}