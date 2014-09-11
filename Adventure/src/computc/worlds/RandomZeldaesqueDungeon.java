package computc.worlds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import org.newdawn.slick.SlickException;

import computc.Direction;

public class RandomZeldaesqueDungeon extends Dungeon
{
	final int SCALE = 5;
	
	public RandomZeldaesqueDungeon() throws SlickException
	{
		Room room = new Room(this, 2, 2, "empty");
		
		for(int i = 0; i < SCALE; i++)
		{
			room = room.addRoom(Direction.SOUTH, null);
		}
	}
}