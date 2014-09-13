package computc.worlds;

import org.newdawn.slick.SlickException;

public class OneRoomDungeon extends Dungeon
{
	public OneRoomDungeon() throws SlickException
	{
		this.firstRoom = new Room(this, 2, 2, "empty");
		this.addRoom(this.firstRoom);
	}
}