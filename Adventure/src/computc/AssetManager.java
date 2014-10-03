package computc;

import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import computc.worlds.rooms.RoomLayout;
import computc.worlds.tiles.Tile;
import computc.worlds.tiles.TileSet;
import computc.worlds.tiles.TileSubSet;

public class AssetManager
{
	private HashMap<String, Image> loadedImages = new HashMap<String, Image>();
	private HashMap<String, TileSet> loadedTileSets = new HashMap<String, TileSet>();
	private HashMap<String, RoomLayout> loadedRoomLayouts = new HashMap<String, RoomLayout>();
	
	public Image getImage(String source)
	{
		try
		{
			if(this.loadedImages.get(source) == null)
			{
				this.loadedImages.put(source, new Image(source));
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		
		return this.loadedImages.get(source);
	}

	public RoomLayout getRoomLayout(String source)
	{
		try
		{
			if(this.loadedRoomLayouts.get(source) == null)
			{
				this.loadedRoomLayouts.put(source, new RoomLayout(source));
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		
		return this.loadedRoomLayouts.get(source);
	}

	public TileSet getTileSet(String source)
	{
		try
		{
			if(this.loadedTileSets.get(source) == null)
			{
				this.loadedTileSets.put(source, new TileSet(source));
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		
		return this.loadedTileSets.get(source);
	}
}