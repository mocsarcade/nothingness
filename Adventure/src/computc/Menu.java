package computc;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class Menu
{
	private Hero hero;
	private Dungeon dungeon;
	
	public Menu(Dungeon dungeon, Hero hero)
	{
		this.hero = hero;
		this.dungeon = dungeon;
	}
	
	public void render(Graphics graphics, Camera camera)
	{
		graphics.setColor(Color.black);
		graphics.fillRect(0, Room.HEIGHT, Room.WIDTH, Menu.HEIGHT);
		
		int hrx = this.hero.getRoomyX();
		int hry = this.hero.getRoomyY();
		
		int MAP_WIDTH = 5;
		int MAP_HEIGHT = 5;
		
		for(int i = 0; i < MAP_WIDTH; i++)
		{
			for(int j = 0; j < MAP_HEIGHT; j++)
			{
				int rx = i + 4 - (MAP_WIDTH / 2);
				int ry = j + 1 - (MAP_HEIGHT / 2);
				
				int UNIT = 11, MARGIN = 2, OFFSET = 29;
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
	}
	
	public static final int HEIGHT = 128;
}