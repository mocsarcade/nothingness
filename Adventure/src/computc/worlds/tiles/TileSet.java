package computc.worlds.tiles;

import java.util.HashMap;

import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import computc.worlds.rooms.RoomTemplate;

public class TileSet
{
	private HashMap<String, TileGroup> tilegroups = new HashMap<String, TileGroup>();
	
	public TileSet(String tilesetSource)
	{
		try
		{
			Element tilesetElement = new SAXBuilder().build(tilesetSource).getRootElement();
			
			for(Element tileElement : tilesetElement.getChildren("tile"))
			{
				String tileType = tileElement.getAttributeValue("type");
				String tileSource = tileElement.getAttributeValue("source");
				
				this.tilegroups.put(tileType, new TileGroup(tileSource));
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}
	
	public TileGroup getTileGroup(String type)
	{
		return this.tilegroups.get(type);
	}
}