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
	private final int ROOMS_PER_SEGMENT = 4;
	private final int SEGMENTS_PER_DUNGEON = 3;
	
	ArrayList<DungeonSegment> segments = new ArrayList<DungeonSegment>();
	
	public RandomDungeon()
	{
		super();
		
		this.firstRoom = new Room(this, 2, 2);
		this.firstRoom.setTileSet(this.getTileSet(0));
		this.firstRoom.setRoomLayout(this.getSpecialRoomLayout("first room"));
		
		Room previousRoom = this.firstRoom;
		for(int i = 0; i < SEGMENTS_PER_DUNGEON; i++)
		{
			DungeonSegment segment = new DungeonSegment();
			segment.setTileSet(this.getTileSet(i));
			
			for(int j = 0; j < ROOMS_PER_SEGMENT; j++)
			{
				Direction direction = previousRoom.getRandomDirectionForAnotherRoom();
				
				if(direction != Direction.NONE)
				{
					//previousRoom.addArrowTile(direction);
					previousRoom.setMajorDirection(direction);
					Room nextRoom = previousRoom.makeRoom(direction);
					nextRoom.setTileSet(segment.getTileSet());
					nextRoom.setRoomLayout(this.getRandomRoomLayout());
					previousRoom.makeDoor(direction);
					
					segment.addMajorRoom(nextRoom);
					previousRoom = nextRoom;
				}
				else
				{
					throw new DungeonException(); //is a dead end.
				}
			}
			
			this.segments.add(segment);
		}
		
		{
			Direction direction = previousRoom.getRandomDirectionForAnotherRoom();
			
			if(direction != Direction.NONE)
			{
				//previousRoom.addArrowTile(direction);
				previousRoom.setMajorDirection(direction);
				this.lastRoom = previousRoom.makeRoom(direction);
				this.lastRoom.setTileSet(this.getTileSet(2));
				this.lastRoom.setRoomLayout(this.getSpecialRoomLayout("last room"));
				previousRoom.makeDoor(direction);
			}
			else
			{
				throw new DungeonException(); //is a dead end.
			}
		}
		
		for(DungeonSegment segment : this.segments)
		{
			for(Room room : segment.getAllMajorRooms())
			{
				Direction direction = room.getRandomDirectionForAnotherRoom();
				
				if(direction != Direction.NONE)
				{
					Room nextRoom = room.makeRoom(direction);
					nextRoom.setTileSet(segment.getTileSet());
					nextRoom.setRoomLayout(this.getRandomRoomLayout());
					room.makeDoor(direction);
					segment.addMinorRoom(nextRoom);
				}
			}
			
			//segment.getLastMajorRoom().addDoor(Direction.NORTH);
			//segment.getRandomMinorRoom().addKey();
		}
	}
}