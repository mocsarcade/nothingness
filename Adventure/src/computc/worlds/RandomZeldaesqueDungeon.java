package computc.worlds;

import java.util.ArrayList;
import java.util.Collections;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import computc.Direction;
import computc.cameras.Camera;

public class RandomZeldaesqueDungeon extends Dungeon
{
	private final int SCALE = 5;
	
	public RandomZeldaesqueDungeon() throws SlickException
	{
		Room room = new Room(this, 2, 2, "empty");
		this.addRoom(room);
		
		for(int i = 0; i < SCALE; i++)
		{
			ArrayList<Direction> directions = room.getPotentialDirections();
			
			if(directions.size() > 0)
			{
				Collections.shuffle(directions);
				room = room.instantiateRoom(directions.get(0));
			}
			else
			{
				//is a dead end.
			}
		}
	}
}