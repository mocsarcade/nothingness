package computc.worlds.dungeons;

import org.newdawn.slick.SlickException;

import computc.Level;
import computc.worlds.rooms.Room;

public class OneRoomDungeon extends Dungeon
{
	public OneRoomDungeon(Level level)
	{
		super(level);
		
		this.firstRoom = new Room(this, 2, 2, "./res/rooms/example.room.tmx");
	}
}