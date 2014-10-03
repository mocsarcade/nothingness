package computc.worlds.dungeons;

import org.newdawn.slick.SlickException;

import computc.GameData;
import computc.worlds.rooms.Room;

public class OneRoomDungeon extends Dungeon
{
	public OneRoomDungeon(GameData gamedata)
	{
		super(gamedata);
		
		this.firstRoom = new Room(this, 2, 2);
	}
}