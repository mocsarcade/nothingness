package computc.worlds.rooms;

import java.util.List;
import java.io.IOException;

import org.jdom2.Element;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.DataConversionException;

public class RoomTemplate
{
	private int[][] tiles = new int[Room.TILEY_WIDTH][Room.TILEY_HEIGHT];
	
	public RoomTemplate(String roomSource) throws IOException, JDOMException
	{
		this(new SAXBuilder().build(roomSource).getRootElement());
	}
	
	public RoomTemplate(Element roomElement) throws DataConversionException
	{
		for(Element layerElement : roomElement.getChildren("layer"))
		{
			if(layerElement.getAttributeValue("name").equals("tiles"))
			{
				List<Element> tileElements = layerElement.getChild("data").getChildren();
				
				for(int tx = 0; tx < Room.TILEY_WIDTH; tx++)
				{
					for(int ty = 0; ty < Room.TILEY_HEIGHT; ty++)
					{
						Element tileElement = tileElements.get(ty * Room.TILEY_WIDTH + tx);
						int gid = tileElement.getAttribute("gid").getIntValue();
						this.tiles[tx][ty] = gid;
					}
				}
			}
		}
	}

	public int getTileID(int tx, int ty)
	{
		return this.tiles[tx][ty];
	}
}