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
import computc.GameData;
import computc.cameras.Camera;
import computc.entities.Key;
import computc.entities.Ladder;
import computc.entities.OldMan;
import computc.worlds.rooms.Room;
import computc.worlds.tiles.TileSet;

public class RandomDungeon extends Dungeon
{
	private final int ROOMS_PER_SEGMENT = 3;
	private final int SEGMENTS_PER_DUNGEON = 2;
	
	ArrayList<DungeonSegment> segments = new ArrayList<DungeonSegment>();
	
	public RandomDungeon(GameData gamedata)
	{
		super(gamedata);
		
		int tilesetid = 0;
		
		this.firstRoom = new Room(this, 2, 2);
		this.firstRoom.setTileSet(this.getTileSet(tilesetid));
		this.firstRoom.setRoomLayout(this.getSpecialRoomLayout("first room"));
		
		Room previousRoom = this.firstRoom;
		for(int i = 0; i < SEGMENTS_PER_DUNGEON; i++)
		{
			DungeonSegment segment = new DungeonSegment();
			
			for(int j = 0; j < ROOMS_PER_SEGMENT; j++)
			{
				Direction direction = previousRoom.getRandomDirectionForAnotherRoom();
				
				if(direction != Direction.NONE)
				{
					//previousRoom.addArrowTile(direction);
					previousRoom.setMajorDirection(direction);
					Room nextRoom = previousRoom.makeRoom(direction);
					nextRoom.setTileSet(this.getTileSet(tilesetid));
					nextRoom.setRoomLayout(this.getRandomRoomLayout());
					previousRoom.makeDoor(direction, true);
					previousRoom.critdir = direction;
					
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
				this.lastRoom.setTileSet(this.getTileSet(tilesetid));
				this.lastRoom.setRoomLayout(this.getSpecialRoomLayout("last room"));
				previousRoom.makeDoor(direction, true);
				previousRoom.critdir = direction;
				
				this.ladder = new Ladder(this, this.lastRoom, 5, 4);
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
					nextRoom.setTileSet(this.getTileSet(tilesetid));
					nextRoom.setRoomLayout(this.getRandomRoomLayout());
					room.makeDoor(direction, false);
					segment.addMinorRoom(nextRoom);
				}
			}
			
			segment.getLastMajorRoom().getCritDoor();
			segment.getRandomMinorRoom().addKey();
		}
		
		firstRoom.addKey();
		firstRoom.getCritDoor().lock();
	}
}