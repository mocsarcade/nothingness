package computc.worlds;

import org.newdawn.slick.SlickException;

import computc.GameData;

public class OneRoomDungeon extends Dungeon
{
	public OneRoomDungeon(GameData gamedata) throws SlickException
	{
		super(gamedata);
		this.firstRoom = new Room(this, 2, 2, "empty");
	}
}