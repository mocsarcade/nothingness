package computc;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import computc.entities.Hero;
import computc.underworlds.Dungeon;
import computc.underworlds.Room;

public class Menu
{
	private Hero hero;
	private Dungeon dungeon;
	
	private Image heart;
	
	public Menu(Dungeon dungeon, Hero hero) throws SlickException
	{
		this.hero = hero;
		this.dungeon = dungeon;
		
		this.heart = new Image("res/heart.png");
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		graphics.setColor(Color.black);
		graphics.fillRect(0, Room.HEIGHT, Room.WIDTH, Menu.HEIGHT);
		
		int MAP_WIDTH = 5, MAP_HEIGHT = 5;
		int UNIT = 16, MARGIN = 3, OFFSET = 29;
		
		for(int i = 0; i < MAP_WIDTH; i++)
		{
			for(int j = 0; j < MAP_HEIGHT; j++)
			{
				int rx = i + 4 - (MAP_WIDTH / 2);
				int ry = j + 1 - (MAP_HEIGHT / 2);
				
				int x = OFFSET + MARGIN + (i * (UNIT + MARGIN));
				int y = OFFSET + MARGIN + (j * (UNIT + MARGIN));
				
				Room room = this.dungeon.getRoom(rx, ry);
				
				if(room != null	&& room.visited)
				{
					graphics.setColor(Color.lightGray);
					graphics.fillRoundRect(x, y, UNIT, UNIT, 3);

					if(room.hasNorthernRoom()) {graphics.fillRect(x + (UNIT / 2) - 1, y - MARGIN, MARGIN, MARGIN);}
					if(room.hasSouthernRoom()) {graphics.fillRect(x + (UNIT / 2) - 1, y + UNIT, MARGIN, MARGIN);}
					if(room.hasEasternRoom()) {graphics.fillRect(x + UNIT, y + (UNIT / 2) - 1, MARGIN, MARGIN);}
					if(room.hasWesternRoom()) {graphics.fillRect(x - MARGIN, y + (UNIT / 2) - 1, MARGIN, MARGIN);}
					
					if(rx == this.hero.getRoomyX()
					&& ry == this.hero.getRoomyY())
					{
						graphics.setColor(Color.white);
						graphics.fillOval(x + 3, y + 3, 5, 5);
					}
				}
				else
				{
					graphics.setColor(Color.darkGray);
					graphics.fillRoundRect(x, y, UNIT, UNIT, 3);
				}
			}
		}
		
		for(int h = 0; h < hero.getHealth(); h++)
		{
			heart.draw(540 + (40 * h), 30);
		}
	}
	
	public static final int HEIGHT = 128;
}