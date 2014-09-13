package computc.worlds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import org.newdawn.slick.SlickException;

import computc.Direction;

public class RandomRoguelikeDungeon extends Dungeon
{
	public RandomRoguelikeDungeon() throws SlickException
	{
		LinkedList<Room> rooms = new LinkedList<Room>();
		rooms.add(new Room(this, 2, 2, "empty"));
		
		final int SIZE = 5;
		
		for(int i = 0; i < SIZE; i++)
		{
			int size = rooms.size();
			for(int index = 0; index < size; index++)
			{
				Room room = rooms.get(index);
				ArrayList<Direction> directions = room.getExpandableDirections();
				
				if(directions.size() > 0)
				{
					Collections.shuffle(directions);
					rooms.add(room.instantiateRoom(directions.get(0), null));
				}
				else
				{
					rooms.remove(room); //is a dead end.
				}
			}
		}
	}
}