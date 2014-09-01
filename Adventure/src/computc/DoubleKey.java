package computc;

public class DoubleKey
{
	private final int i;
	private final int j;
	
	public DoubleKey(int i, int j)
	{
		this.i = i;
		this.j = j;
	}
	
	public boolean equals(Object object)
	{
		if(this == object)
		{
			return true;
		}
		
		if(object instanceof DoubleKey)
		{
			DoubleKey that = (DoubleKey)(object);
			return this.i == that.i && this.j == that.j;
		}
		
		return false;
	}
	
	public int hashCode()
	{
		int code = this.i;
		code = 31 * code + this.j;
		return code;
	}
}