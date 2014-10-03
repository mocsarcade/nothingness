package computc.worlds.dungeons;

import org.newdawn.slick.SlickException;

import computc.worlds.rooms.Room;

public class OneRoomDungeon extends Dungeon
{
	public OneRoomDungeon()
	{
		super();
		
		this.firstRoom = new Room(this, 2, 2);
	}
}