package computc;

public class MultifacetedHashMapKey
{
	private final int i;
	private final int j;
	
	public MultifacetedHashMapKey(int i, int j)
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
		
		if(object instanceof MultifacetedHashMapKey)
		{
			MultifacetedHashMapKey that = (MultifacetedHashMapKey)(object);
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