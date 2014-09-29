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
	private final int ROOMS_PER_SEGMENT = 3;
	private final int SEGMENTS_PER_DUNGEON = 2;
	
	ArrayList<DungeonSegment> segments = new ArrayList<DungeonSegment>();
	
	public RandomDungeon()
	{
		super();
		
		this.firstRoom = new Room(this, 2, 2);
		//this.firstRoom.setTileSet(this.getTileSet(0));
		this.firstRoom.setRoomLayout(this.getSpecialRoomLayout("first room"));
		
		Room currentRoom = this.firstRoom;
		for(int i = 0; i < SEGMENTS_PER_DUNGEON; i++)
		{
			DungeonSegment segment = new DungeonSegment();
			
			for(int j = 0; j < ROOMS_PER_SEGMENT; j++)
			{
				Direction direction = currentRoom.getRandomDirectionForAnotherRoom();
				
				if(direction != Direction.NONE)
				{
					//currentRoom.addArrowTile(direction);
					currentRoom.setMajorDirection(direction);
					//Room instantiatedRoom = currentRoom.instantiateRoom(direction);
					
					//segment.addMajorRoom(instantiatedRoom);
					//currentRoom = instantiatedRoom;
				}
				else
				{
					throw new DungeonException(); //is a dead end.
				}
			}
			
			this.segments.add(segment);
		}
		
		{
			Direction direction = currentRoom.getRandomDirectionForAnotherRoom();
			
			if(direction != Direction.NONE)
			{
				//currentRoom.addArrowTile(direction);
				currentRoom.setMajorDirection(direction);
				//this.lastRoom = currentRoom.instantiateRoom(direction);
			}
			else
			{
				throw new DungeonException(); //is a dead end.
			}
		}
		
		for(DungeonSegment dungeonSegment : this.segments)
		{
			for(Room room : dungeonSegment.getAllMajorRooms())
			{
				Direction direction = room.getRandomDirectionForAnotherRoom();
				
				if(direction != Direction.NONE)
				{
					//Room instantiatedRoom = room.instantiateRoom(direction);
					//dungeonSegment.addMinorRoom(instantiatedRoom);
				}
				else
				{
					throw new DungeonException(); //is a dead end.
				}
			}
			
			//for(Room room : segment.getAllRooms()) {room.setTileSet(this.getTileSet(?));}
			//segment.getLastMajorRoom().addDoor(Direction.NORTH);
			//segment.getRandomMinorRoom().addKey();
		}
	}
}