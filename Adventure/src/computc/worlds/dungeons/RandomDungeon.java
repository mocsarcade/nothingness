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

public class RandomDungeon extends Dungeon
{
	private final int AMOUNT_OF_ROOMS_IN_SEGMENT = 3;
	private final int AMOUNT_OF_SEGMENTS_IN_DUNGEON = 2;
	
	ArrayList<DungeonSegment> segments = new ArrayList<DungeonSegment>();
	
	public RandomDungeon()
	{
		super();
		
		this.firstRoom = new Room(this, 2, 2);
		//this.firstRoom.setTileSet(this.getTileSet(0));
		this.firstRoom.setRoomLayout(this.getSpecialRoomLayout("first room"));
		
		Room currentRoom = this.firstRoom;
		
		for(int i = 0; i < AMOUNT_OF_SEGMENTS_IN_DUNGEON; i++)
		{
			DungeonSegment segment = new DungeonSegment();
			//segment.setTileSet(this.getTileSet(i));
			
			for(int j = 0; j < AMOUNT_OF_ROOMS_IN_SEGMENT; j++)
			{
				Direction direction = currentRoom.getRandomPotentialDirection();
				
				if(direction != Direction.NONE)
				{
					currentRoom.addArrowTile(direction);
					currentRoom.critpathDirection = direction;
					Room instantiatedRoom = currentRoom.instantiateRoom(direction);
					
					segment.addMajorRoom(instantiatedRoom);
					currentRoom = instantiatedRoom;
				}
				else
				{
					throw new DungeonException(); //is a dead end.
				}
			}
			
			this.segments.add(segment);
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
		
		for(DungeonSegment segment : this.segments)
		{
			for(Room room : segment.getAllMajorRooms())
			{
				Direction direction = room.getRandomPotentialDirection();
				
				if(direction != Direction.NONE)
				{
					Room instantiatedRoom = room.instantiateRoom(direction);
					segment.addMinorRoom(instantiatedRoom);
				}
			}

			//segment.getLastMajorRoom().addDoor(Direction.NORTH);
			//segment.getRandomMinorRoom().addKey();
		}
		
		//this.oldman = new OldMan(this, this.lastRoom, 200, 200);
	}
}