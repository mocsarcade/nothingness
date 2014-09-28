package computc;

import java.util.HashMap;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

import computc.worlds.tiles.Tile;
import computc.worlds.tiles.TileGroup;

public class AssetManager
{
	private HashMap<String, TileGroup> tileGroups = new HashMap<String, TileGroup>();
	
	public TileGroup getTileGroup(String source)
	{
		if(this.tileGroups.get(source) == null)
		{
			this.tileGroups.put(source, new TileGroup(source));
		}
		return this.tileGroups.get(source);
	}
}