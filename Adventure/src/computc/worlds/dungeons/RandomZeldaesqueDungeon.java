package computc.worlds.dungeons;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import computc.Direction;
import computc.cameras.Camera;
import computc.entities.Key;
import computc.entities.OldMan;
import computc.worlds.rooms.Room;
import computc.worlds.tiles.TileSet;

public class RandomZeldaesqueDungeon extends Dungeon
{
	private final int AMOUNT_OF_ROOMS_IN_SEGMENT = 3;
	private final int AMOUNT_OF_SEGMENTS_IN_DUNGEON = 2;
	
	public RandomZeldaesqueDungeon()
	{
		super();
		
		//MAINPATH
		ArrayList<LinkedList<Room>> segments = new ArrayList<LinkedList<Room>>();
		Room currentRoom = this.firstRoom = new Room(this, 2, 2); //this.getSpecialRoomLayout("first room")
		
		for(int i = 0; i < AMOUNT_OF_SEGMENTS_IN_DUNGEON; i++)
		{
			LinkedList<Room> segment = new LinkedList<Room>();
			
			for(int j = 0; j < AMOUNT_OF_ROOMS_IN_SEGMENT; j++)
			{
				Direction direction = currentRoom.getRandomPotentialDirection();
				
				if(direction != Direction.NONE)
				{
					currentRoom.addArrowTile(direction);
					currentRoom.critpathDirection = direction;
					Room instantiatedRoom = currentRoom.instantiateRoom(direction);
					
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
		
		Direction finaldirection = currentRoom.getRandomPotentialDirection();
		
		if(finaldirection != Direction.NONE)
		{
			currentRoom.addArrowTile(finaldirection);
			currentRoom.critpathDirection = finaldirection;
			this.lastRoom = currentRoom.instantiateRoom(finaldirection);
		}
		else
		{
			throw new DungeonException(); //is a dead end.
		}
		
		//SIDEPATH
		
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
					Room instantiatedRoom = room.instantiateRoom(direction);
					sidepath.add(instantiatedRoom);
				}
				else
				{
					throw new DungeonException(); //is a dead end.
				}
			}
			
			Collections.shuffle(sidepath);
			sidepath.get(0).addKey();
		}
		
		//this.oldman = new OldMan(this, this.lastRoom, 200, 200);
	}
}