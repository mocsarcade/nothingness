package computc.worlds;

import org.newdawn.slick.SlickException;

public class HandfulOfRoomsDungeon extends Dungeon
{
	public HandfulOfRoomsDungeon() throws SlickException
	{
		this.firstRoom = new Room(this, 0, 0, "grid");

		this.firstRoom.instantiateNorthernRoom("corners");
		this.firstRoom.instantiateSouthernRoom("threelines");
		this.firstRoom.instantiateEasternRoom("fivedots");
		this.firstRoom.instantiateWesternRoom("fourlines");
	}
}