package computc.worlds.rooms;

import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

import computc.worlds.tiles.Tile;

public class RoomTemplate
{
	public int tiles[][] = new int[Room.TILEY_WIDTH][Room.TILEY_HEIGHT];
	
	public RoomTemplate(String filepath)
	{
		try
		{
			Document document = new SAXBuilder().build(filepath);
			
			List<Element> tilelayer = document.getRootElement().getChild("layer").getChild("data").getChildren();
			//List<Element> objectgroup = tmx.getRootElement().getChild("objectgroup").getChildren();
			
			for(int tx = 0; tx < Room.TILEY_WIDTH; tx++)
			{
				for(int ty = 0; ty < Room.TILEY_HEIGHT; ty++)
				{
					Element element = tilelayer.get(ty * Room.TILEY_WIDTH + tx);
					int gid = element.getAttribute("gid").getIntValue();
					tiles[tx][ty] = gid;
				}
			}
			
			/*for(Element element : objectgroup)
			{
				if(element.getAttribute("gid").getIntValue() == 4)
				{
					int x = element.getAttribute("x").getIntValue() + (48 / 2);
					int y = element.getAttribute("y").getIntValue() - (48 / 2);
					
					this.dungeon.enemies.add(new Thug(this.dungeon, this, x, y));
				}
				else if(element.getAttribute("gid").getIntValue() == 5)
				{
					this.keyX = element.getAttribute("x").getIntValue() + (64 / 2);
					this.keyY = element.getAttribute("y").getIntValue() - (32 / 2);
				}
				else if(element.getAttribute("gid").getIntValue() == 6)
				{
					int x = element.getAttribute("x").getIntValue() + (16 / 2);
					int y = element.getAttribute("y").getIntValue() - (16 / 2);
					
					this.dungeon.coins.add(new Coin(this.dungeon, this, x, y));
				}
			}*/
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}
}