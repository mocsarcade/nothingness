package computc.worlds.dungeons;

import org.newdawn.slick.SlickException;

import computc.Direction;
import computc.GameData;
import computc.entities.Ladder;
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
		this.lastRoom.setTileSet(this.getTileSet(tilesetid));
		this.lastRoom.setRoomLayout(this.getSpecialRoomLayout("last room"));
		
		this.firstRoom.makeDoor(Direction.SOUTH, true);
		
		this.ladder = new Ladder(this, this.lastRoom, 5, 4);
	}
}