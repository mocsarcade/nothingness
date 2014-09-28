package computc.worlds.tiles;

import java.util.ArrayList;

import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SpriteSheet;

import computc.Game;

public class TileGroup
{
	private ArrayList<Image> images = new ArrayList<Image>();
	private Color color = new Color(255, 211, 248);
	
	public TileGroup(String tilegroupSource)
	{
		try
		{
			Element tilegroupElement = new SAXBuilder().build(tilegroupSource).getRootElement();
			
			Element tilegroupImageElement = tilegroupElement.getChild("image");
			String tilegroupImageSource = tilegroupImageElement.getAttributeValue("source");
			
			Image tilegroupImage = new Image(tilegroupImageSource);
			for(int tx = 0; tx < tilegroupImage.getWidth() / Tile.SIZE; tx++)
			{
				for(int ty = 0; ty < tilegroupImage.getHeight() / Tile.SIZE; ty++)
				{
					int z = Tile.SIZE, x = tx * Tile.SIZE, y = ty * Tile.SIZE;
					this.images.add(tilegroupImage.getSubImage(x, y, z, z));
				}
			}
			
			Element tilegroupColorElement = tilegroupElement.getChild("color");
			int tileColorRed = tilegroupColorElement.getAttribute("red").getIntValue();
			int tileColorBlue = tilegroupColorElement.getAttribute("blue").getIntValue();
			int tileColorGreen = tilegroupColorElement.getAttribute("green").getIntValue();
			this.color = new Color(tileColorRed, tileColorBlue, tileColorGreen);
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
	
	public Color getColor()
	{
		return this.color;
	}
}