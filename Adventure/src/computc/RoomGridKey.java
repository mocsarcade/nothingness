package computc;

public class RoomGridKey
{
	private final int rx;
	private final int ry;
	
	public RoomGridKey(int rx, int ry)
	{
		this.rx = rx;
		this.ry = ry;
	}
	
	public boolean equals(Object object)
	{
		if(this == object)
		{
			return true;
		}
		
		if(object instanceof RoomGridKey)
		{
			RoomGridKey that = (RoomGridKey)(object);
			return this.rx == that.rx && this.ry == that.ry;
		}
		
		return false;
	}
	
	public int hashCode()
	{
		int code = this.rx;
		code = 31 * code + this.ry;
		return code;
	}
}