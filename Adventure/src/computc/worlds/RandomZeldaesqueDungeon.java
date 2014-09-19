package computc.worlds;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import computc.Direction;
import computc.cameras.Camera;
import computc.entities.Key;

public class RandomZeldaesqueDungeon extends Dungeon
{
	private final int AMOUNT_OF_ROOMS_IN_SEGMENT = 3;
	private final int AMOUNT_OF_SEGMENTS_IN_DUNGEON = 2;
	//private final int AMOUNT_OF_ROOMS_IN_SIDEPATH;
	//private final int AMOUNT_OF_SIDEPATHS_PER_SEGMENT;
	
	public RandomZeldaesqueDungeon() throws SlickException
	{
		ArrayList<LinkedList<Room>> segments = new ArrayList<LinkedList<Room>>();
		
		Room currentRoom = this.firstRoom = new Room(this, 2, 2, "oval");
		
		for(int i = 0; i < AMOUNT_OF_SEGMENTS_IN_DUNGEON; i++)
		{
			LinkedList<Room> segment = new LinkedList<Room>();
			
			for(int j = 0; j < AMOUNT_OF_ROOMS_IN_SEGMENT; j++)
			{
				Direction direction = currentRoom.getRandomPotentialDirection();
				
				if(direction != Direction.NONE)
				{
					currentRoom.addArrow(direction);
					currentRoom.critpathDirection = direction;
					Room instantiatedRoom = currentRoom.instantiateRoom(direction, "oval");
					
					segment.add(instantiatedRoom);
					currentRoom = instantiatedRoom;
				}
				else
				{
					throw new DungeonException(); //is a dead end.
				}
			}
			
			segments.add(segment);
		}
		
		for(LinkedList<Room> segment : segments)
		{
			Room lastRoom = segment.getLast();
			lastRoom.addDoor(lastRoom.critpathDirection);
			
			LinkedList<Room> sidepath = new LinkedList<Room>();
			
			for(Room room : segment)
			{
				Direction direction = room.getRandomPotentialDirection();
				
				if(direction != Direction.NONE)
				{
					Room instantiatedRoom = room.instantiateRoom(direction, "empty");
					sidepath.add(instantiatedRoom);
				}
				else
				{
					throw new DungeonException(); //is a dead end.
				}
			}
			
			Collections.shuffle(sidepath);
			this.keys.add(new Key(this, sidepath.get(0), 5, 4));
		}
		
		//this.firstRoom.addDoor(this.firstRoom.critpathDirection);
		//this.keys.add(new Key(this, this.firstRoom, 5, 4));
	}
}