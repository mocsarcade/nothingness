package computc.worlds.dungeons;

import org.newdawn.slick.SlickException;

import computc.worlds.rooms.Room;

public class FiveRoomDungeon extends Dungeon
{
	public FiveRoomDungeon(String filepath)
	{
		super(filepath);
		
		this.firstRoom = new Room(this, 2, 2, this.getRandomRoomLayout());
		
		this.firstRoom.instantiateNorthernRoom(this.getRandomRoomLayout());
		this.firstRoom.instantiateSouthernRoom(this.getRandomRoomLayout());
		this.firstRoom.instantiateEasternRoom(this.getRandomRoomLayout());
		this.firstRoom.instantiateWesternRoom(this.getRandomRoomLayout());
	}
}