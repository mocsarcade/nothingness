package computc.worlds.tiles;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import computc.Game;

public class TileGroup
{
	private ArrayList<Image> images = new ArrayList<Image>();
	
	public TileGroup(String source)
	{
		try
		{
			SpriteSheet imagesheet = new SpriteSheet(source, Tile.SIZE, Tile.SIZE);
			for(int tx = 0; tx < imagesheet.getHorizontalCount(); tx++)
				for(int ty = 0; ty < imagesheet.getVerticalCount(); ty++)
					this.images.add(imagesheet.getSprite(tx, ty));
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}
	
	public Image getImage(int index)
	{
		return this.images.get(index);
	}
	
	public Image getRandomImage()
	{
		return this.images.get(Game.randomness.nextInt(this.images.size()));
	}
}