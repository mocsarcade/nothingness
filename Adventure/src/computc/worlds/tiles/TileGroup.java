package computc.worlds.tiles;

import java.util.ArrayList;

import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import computc.Game;

public class TileGroup
{
	private ArrayList<Image> images = new ArrayList<Image>();
	
	public TileGroup(String tilegroupSource)
	{
		try
		{
			Image image = new Image(tilegroupSource);
			
			for(int tx = 0; tx < image.getWidth() / Tile.SIZE; tx++)
			{
				for(int ty = 0; ty < image.getHeight() / Tile.SIZE; ty++)
				{
					int x = tx * Tile.SIZE, y = ty * Tile.SIZE;
					
					this.images.add(image.getSubImage(x, y, Tile.SIZE, Tile.SIZE));
				}
			}
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