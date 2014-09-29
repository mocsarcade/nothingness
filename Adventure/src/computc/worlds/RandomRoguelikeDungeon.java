package computc.worlds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import org.newdawn.slick.SlickException;

import computc.Direction;
import computc.GameData;

public class RandomRoguelikeDungeon extends Dungeon
{
	private final int SCALE = 5;
	
	public RandomRoguelikeDungeon(GameData gamedata) throws SlickException
	{
		super(gamedata);
		this.firstRoom = new Room(this, 2, 2, "empty");
		
		for(int i = 0; i < SCALE; i++)
		{
			LinkedList<Room> rooms = this.getAllRooms();
			
			while(rooms.size() > 0)
			{
				Room room = rooms.pop();
				Direction direction = room.getRandomPotentialDirection();
				room.instantiateRoom(direction);
			}
		}
	}
}