package computc;

public class RoomHashMapKey
{
	private final int x;
	private final int y;
	
	public RoomHashMapKey(Room room)
	{
		this.x = room.getRoomX();
		this.y = room.getRoomY();
	}
	
	public RoomHashMapKey(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public boolean equals(Object object)
	{
		if(this == object)
		{
			return true;
		}
		
		if(!(object instanceof RoomHashMapKey))
		{
			return false;
		}
		
		RoomHashMapKey key = (RoomHashMapKey)(object);
		return x == key.x && y == key.y;
	}
	
	public int hashCode()
	{
		int result = x;
		result = 31 * result + y;
		return result;
	}
}