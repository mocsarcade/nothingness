package computc;

import java.util.HashMap;
import java.util.ArrayList;

import org.newdawn.slick.Image;

public class AssetManager
{
	private HashMap<String, Image> tileImage = new HashMap<String, Image>();
	
	public Image getTileImage(String filepath)
	{
		try
		{
			//if the image has not yet been loaded
			//into the game, load it into the game!!
			
			if(this.tileImages.get(filepath) == null)
			{
				this.tileImages.put(filepath, new Image(filepath));
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		
		return this.tileImages.get(filepath);
	}
}