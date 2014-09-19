package computc.entities;

import org.newdawn.slick.Image;

import computc.worlds.Dungeon;
import computc.worlds.Room;

public class Coin extends Entity
{
	public Coin(Dungeon dungeon, Room room, float x, float y)
	{
		super(dungeon, room, x, y);
		this.image = Coin.IMAGE;
	}
	
	public static Image IMAGE;
}