package computc.worlds;

import org.newdawn.slick.SlickException;

import computc.GameData;

public class FiveRoomDungeon extends Dungeon
{
	public FiveRoomDungeon(GameData gamedata) throws SlickException
	{
		super(gamedata);
		this.firstRoom = new Room(this, 2, 2, "grid");
		
		this.firstRoom.instantiateNorthernRoom("corners");
		this.firstRoom.instantiateSouthernRoom("threelines");
		this.firstRoom.instantiateEasternRoom("fivedots");
		this.firstRoom.instantiateWesternRoom("fourlines");
	}
}