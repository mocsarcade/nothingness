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
		
		//int rx = this.hero.getRoomyX();
		//int ry = this.hero.getRoomyY();

		for(int rx = 0; rx < 9; rx++)
		{
			for(int ry = 0; ry < 5; ry++)
			{
				int SIZE = 22, MARGIN = 3;
				int x = MARGIN + (rx * (SIZE + MARGIN));
				int y = (Room.HEIGHT + MARGIN) + (ry * (SIZE + MARGIN));
				
				graphics.setColor(Color.darkGray);
				
				if(this.dungeon.rooms.get(rx, ry) != null)
				{
					graphics.setColor(Color.lightGray);
				}
				else
				{
					graphics.setColor(Color.darkGray);
				}
				
				graphics.fillRect(x, y, SIZE, SIZE);
			}
		}
	}
	
	public static final int HEIGHT = 128;
}