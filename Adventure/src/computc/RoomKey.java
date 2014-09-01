package computc;

public class RoomKey
{
	private final int x;
	private final int y;
	
	public RoomKey(int x, int y)
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
		
		if(!(object instanceof RoomKey))
		{
			return false;
		}
		
		RoomKey key = (RoomKey)(object);
		return x == key.x && y == key.y;
	}
	
	public int hashCode()
	{
		int result = x;
		result = 31 * result + y;
		return result;
	}
}