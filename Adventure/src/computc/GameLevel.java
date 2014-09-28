package computc;

import java.util.ArrayList;
import java.util.HashMap;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import computc.worlds.rooms.RoomTemplate;
import computc.worlds.tiles.TileSubSet;

public class GameLevel
{
	private HashMap<String, RoomTemplate> roomTemplates = new HashMap<String, RoomTemplate>();
	//private HashMap<String, ...> tileSets = new HashMap<String, ...>();
	
	public GameLevel(String gamelevelSource)
	{
		try
		{
			Element gamelevelElement = new SAXBuilder().build(gamelevelSource).getRootElement();
			
			for(Element roomElement : gamelevelElement.getChildren("room"))
			{
				String roomSource = roomElement.getAttributeValue("source");
				this.roomTemplates.put(roomSource, new RoomTemplate(roomSource));
			}
			
			for(Element tilesetElement : gamelevelElement.getChildren("tileset"))
			{
				String tilesetSource = tilesetElement.getAttributeValue("source");
				//this.tileSets.put(tilesetSource, new ...());
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}

	public RoomTemplate getRandomRoomTemplate()
	{
		return new RoomTemplate("./res/rooms/fourdots.room.tmx");
	}
}