package computc.worlds;

import java.util.Random;

import org.newdawn.slick.SlickException;

import computc.Direction;

public class RandomizedDungeon extends Dungeon
{
	public RandomizedDungeon() throws SlickException
	{
		Room room = new Room(this, 0, 0, "empty");
		
		this.addRoom(room);
	}
}