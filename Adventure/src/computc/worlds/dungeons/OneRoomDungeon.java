package computc.worlds.dungeons;

import org.newdawn.slick.SlickException;

import computc.GameData;
import computc.worlds.rooms.Room;

public class OneRoomDungeon extends Dungeon
{
	public OneRoomDungeon(GameData gamedata)
	{
		super(gamedata);
		
		int tilesetid = 0;
		
		this.firstRoom = new Room(this, 2, 2);
		this.firstRoom.setTileSet(this.getTileSet(tilesetid));
		this.firstRoom.setRoomLayout(this.getSpecialRoomLayout("first room"));
		
		this.lastRoom = new Room(this, 2, 3);
	}
}