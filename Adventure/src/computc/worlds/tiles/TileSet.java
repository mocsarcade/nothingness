package computc.worlds.tiles;

import java.io.IOException;
import java.util.HashMap;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import computc.worlds.rooms.RoomLayout;

public class TileSet
{
	private HashMap<String, TileSubSet> tilesubsets = new HashMap<String, TileSubSet>();

	public TileSet(String tilesetSource) throws IOException, JDOMException
	{
		this(new SAXBuilder().build(tilesetSource).getRootElement());
	}
	
	public TileSet(Element tilesetElement) throws JDOMException
	{
		for(Element tileElement : tilesetElement.getChildren("tile"))
		{
			String type = tileElement.getAttributeValue("type");
			this.tilesubsets.put(type, new TileSubSet(tileElement));
		}
	}
	
	public TileSubSet getTileSubSet(String type)
	{
		return this.tilesubsets.get(type);
	}
}