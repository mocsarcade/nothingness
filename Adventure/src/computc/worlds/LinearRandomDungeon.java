package computc.worlds;

import java.util.Random;

import org.newdawn.slick.SlickException;

import computc.Direction;

public class LinearRandomDungeon extends Dungeon
{
	public LinearRandomDungeon() throws SlickException
	{
		Room room = new Room(this, 0, 0, "empty");
		
		for(int i = 0; i < 3; i++)
		{
			room = room.addNorthernRoom(null);
		}
	}
}