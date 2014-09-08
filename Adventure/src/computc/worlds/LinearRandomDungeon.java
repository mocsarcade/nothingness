package computc.worlds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import org.newdawn.slick.SlickException;

import computc.Direction;

public class LinearRandomDungeon extends Dungeon
{
	public LinearRandomDungeon() throws SlickException
	{
		Room room = new Room(this, 2, 2, "empty");
		
		for(int i = 0; i < 10; i++)
		{
			ArrayList<Direction> directions = room.getExpandableDirections();
			
			if(directions.size() > 0)
			{
				Collections.shuffle(directions);
				Direction direction = directions.get(0);
				
				room = room.addRoom(direction, "empty");
			}
			else
			{
				System.out.println("dead end");
			}
		}
	}
}