package computc.worlds.dungeons;

import org.newdawn.slick.SlickException;

import computc.worlds.rooms.Room;

public class FiveRoomDungeon extends Dungeon
{
	public FiveRoomDungeon(String filepath)
	{
		super(filepath);
		
		this.firstRoom = new Room(this, 2, 2);
		
		this.firstRoom.instantiateNorthernRoom().addKey();
		this.firstRoom.instantiateSouthernRoom().addKey();
		this.firstRoom.instantiateEasternRoom().addKey();
		this.firstRoom.instantiateWesternRoom().addKey();
	}
}