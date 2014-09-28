package computc.worlds.tiles;

import java.util.HashMap;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import computc.worlds.rooms.RoomTemplate;

public class TileSet
{
	private HashMap<String, TileSubSet> tilesubsets = new HashMap<String, TileSubSet>();
	
	public TileSet(String tilesetSource)
	{
		try
		{
			Document tilesetDocument = new SAXBuilder().build(tilesetSource);
			Element tilesetElement = tilesetDocument.getRootElement();
			
			for(Element tileElement : tilesetElement.getChildren("tile"))
			{
				String type = tileElement.getAttributeValue("type");
				String source = tileElement.getAttributeValue("source");
				
				this.tilesubsets.put(type, new TileSubSet(source));
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}
	
	public TileSubSet getTileSubSet(String type)
	{
		return this.tilesubsets.get(type);
	}
}