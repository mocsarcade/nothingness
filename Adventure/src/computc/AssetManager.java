package computc;

import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import computc.worlds.rooms.RoomTemplate;
import computc.worlds.tiles.Tile;
import computc.worlds.tiles.TileSubSet;

public class AssetManager
{
	private HashMap<String, Image> images = new HashMap<String, Image>();
	private HashMap<String, RoomTemplate> roomTemplates = new HashMap<String, RoomTemplate>();
	
	public Image getImage(String source)
	{
		try
		{
			if(this.images.get(source) == null)
			{
				this.images.put(source, new Image(source));
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		
		return this.images.get(source);
	}

	public RoomTemplate getRoomTemplate(String source)
	{
		try
		{
			if(this.roomTemplates.get(source) == null)
			{
				this.roomTemplates.put(source, new RoomTemplate(source));
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		
		return this.roomTemplates.get(source);
	}
	
	/*private HashMap<String, TileSubSet> tileGroups = new HashMap<String, TileSubSet>();
	
	public TileSubSet getTileGroup(String source)
	{
		if(this.tileGroups.get(source) == null)
		{
			this.tileGroups.put(source, new TileSubSet(source));
		}
		return this.tileGroups.get(source);
	}*/
}