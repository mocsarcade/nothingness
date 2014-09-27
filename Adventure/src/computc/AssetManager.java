package computc;

import java.util.HashMap;
import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import computc.worlds.tiles.Tile;

public class AssetManager
{
	private HashMap<String, ArrayList<Image>> tileImageLists = new HashMap<String, ArrayList<Image>>();
	
	public ArrayList<Image> getTileImageList(String source)
	{
		try
		{
			// if the images for the tiles have not yet been
			// loaded into the game, load them into the game!!
			
			if(this.tileImageLists.get(source) == null)
			{
				// since the images for tiles are saved in the same
				// file, we parse the image into subimages when loading.
				
				ArrayList<Image> tileImageList = new ArrayList<Image>();
				SpriteSheet tileImageSheet = new SpriteSheet(source, Tile.SIZE, Tile.SIZE);
				
				for(int tx = 0; tx < tileImageSheet.getHorizontalCount(); tx++)
					for(int ty = 0; ty < tileImageSheet.getVerticalCount(); ty++)
						tileImageList.add(tileImageSheet.getSprite(tx, ty));
				
				this.tileImageLists.put(source, tileImageList);
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		
		return this.tileImageLists.get(source);
	}
	
	public Image getTileImage(String source)
	{
		ArrayList<Image> tileImageList = this.getTileImageList(source);
		return tileImageList.get(Game.randomness.nextInt(tileImageList.size()));
	}
	
	public Image getTileImage(String source, int index)
	{
		ArrayList<Image> tileImageList = this.getTileImageList(source);
		return tileImageList.get(index);
	}
}