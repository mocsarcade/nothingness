package computc.worlds;

import org.newdawn.slick.SlickException;

public class SingleRoomDungeon extends Dungeon
{
	public SingleRoomDungeon() throws SlickException
	{
		this.firstRoom = new Room(this, 0, 0, "empty");
		this.addRoom(this.firstRoom);
	}
}