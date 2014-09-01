package computc;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class MultifacetedHashMap<O>
{
	public void add(int i, int j, O o)
	{
		if(this.has(i, j))
		{
			throw new MultifacetedHashMapException();
		}
		else
		{
			this.map.put(new MultifacetedHashMapKey(i, j), o);
		}
	}
	
	public O get(int i, int j)
	{
		return this.map.get(new MultifacetedHashMapKey(i, j));
	}
	
	public boolean has(int i, int j)
	{
		return this.map.containsKey(new MultifacetedHashMapKey(i, j));
	}
	
	public LinkedList<O> getAll()
	{
		return new LinkedList<O>(this.map.values());
	}
	
	private HashMap<MultifacetedHashMapKey, O> map = new HashMap<MultifacetedHashMapKey, O>();
}