package computc.worlds;

import org.newdawn.slick.SlickException;

public class RandomizedDungeon extends Dungeon
{
	public RandomizedDungeon() throws SlickException
	{
		this.addRoom(new Room(this, 0, 0, "empty"));
	}
}