package computc;

import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;

public class DoubleKeyHashMap<O>
{
	public void add(int i, int j, O o)
	{
		if(this.has(i, j))
		{
			throw new DoubleKeyHashMapException();
		}
		else
		{
			this.map.put(new DoubleKey(i, j), o);
		}
	}
	
	public O get(int i, int j)
	{
		return this.map.get(new DoubleKey(i, j));
	}
	
	public boolean has(int i, int j)
	{
		return this.map.containsKey(new DoubleKey(i, j));
	}
	
	public LinkedList<O> getAll()
	{
		return new LinkedList<O>(this.map.values());
	}
	
	private HashMap<DoubleKey, O> map = new HashMap<DoubleKey, O>();
}