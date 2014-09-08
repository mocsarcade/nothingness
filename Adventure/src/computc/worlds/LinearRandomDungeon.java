package computc.worlds;

import java.util.Random;

import org.newdawn.slick.SlickException;

import computc.Direction;

public class LinearRandomDungeon extends Dungeon
{
	public LinearRandomDungeon() throws SlickException
	{
		Room room = new Room(this, 2, 2, "empty");
		room.addNorthernRoom("empty");
		room = room.addWesternRoom("empty");
		
		for(int i = 0; i < 10; i++)
		{
			room = room.addSouthernRoom("empty");
		}
	}
}