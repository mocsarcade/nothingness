package computc.worlds;

import computc.Direction;
import computc.Game;
import computc.worlds.rooms.Room;
import computc.worlds.dungeons.DungeonException;

public class Door
{
	private float tx, ty;
	public Direction critdir = Direction.NONE;
	public Room critroom;
	
	public Door(Room alphaRoom, Room omegaRoom, Direction critdir)
	{
		this.critroom = alphaRoom;
		this.critdir = critdir;
		
		int drx = Math.abs(alphaRoom.getRoomyX() - omegaRoom.getRoomyX());
		int dry = Math.abs(alphaRoom.getRoomyY() - omegaRoom.getRoomyY());
		
		if(drx + dry != 1)
		{
			//the rooms must not be same.
			//the rooms must be orthogonal.
			//the rooms must be adjacent.
			throw new DungeonException();
		}
		
		if(alphaRoom.getRoomyY() > omegaRoom.getRoomyY())
		{
			this.ty = alphaRoom.getTileyY() - 0.5f;
			this.tx = alphaRoom.getTileyX() + (alphaRoom.getTileyWidth() / 2);
		}
		else if(alphaRoom.getRoomyY() < omegaRoom.getRoomyY())
		{
			this.ty = alphaRoom.getTileyY() + alphaRoom.getTileyHeight() - 1 + 0.5f;
			this.tx = alphaRoom.getTileyX() + (alphaRoom.getTileyWidth() / 2);
		}
		else if(alphaRoom.getRoomyX() < omegaRoom.getRoomyX())
		{
			this.tx = alphaRoom.getTileyX() + alphaRoom.getTileyWidth() - 1 + 0.5f;
			this.ty = alphaRoom.getTileyY() + (alphaRoom.getTileyHeight() / 2);
		}
		else if(alphaRoom.getRoomyX() > omegaRoom.getRoomyX())
		{
			this.tx = alphaRoom.getTileyX() - 0.5f;
			this.ty = alphaRoom.getTileyY() + (alphaRoom.getTileyHeight() / 2);
		}
		
		alphaRoom.addDoor(this);
		omegaRoom.addDoor(this);
	}
	
	public float getTileyX()
	{
		return this.tx;
	}
	
	public float getTileyY()
	{
		return this.ty;
	}

	public boolean lock = false;
	
	public void lock()
	{
		this.lock = true;
	}
}