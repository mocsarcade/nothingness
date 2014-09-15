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
		Room currentRoom = this.firstRoom = new Room(this, 2, 2, "empty");
		
		for(int i = 0; i < SCALE; i++)
		{
			Direction direction = currentRoom.getRandomPotentialDirection();
			
			if(direction != Direction.NONE)
			{
				currentRoom = currentRoom.instantiateRoom(direction);
			}
			else
			{
				throw new DungeonException(); //is a dead end.
			}
		}
	}
}