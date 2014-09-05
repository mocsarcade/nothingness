package computc;

import java.awt.Point;
import java.util.LinkedList;

import org.newdawn.slick.Animation;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Game extends BasicGame
{
	public Hero hero;
	public Dungeon dungeon;
	public Camera camera;
	public Menu menu;
	
	public Ancient ancient;
	public Thug specialThug;
	
	private Image heart;
	private Image menuBox;
	private Image largeTextBox;
	
	private int counter, counter2;
	
	
	Animation textBox;
	
	public Color textColor = Color.white;
	
	public static boolean reset;
	private boolean nextLevel = false;
	
	public Game()
	{
		super(Game.TITLE);
	}
	
	public void init(GameContainer container) throws SlickException
	{
		this.dungeon = new Dungeon();
		this.hero = new Hero(dungeon, dungeon.getRoom(3, 0), 5, 1);
		this.camera = new Camera(hero);
		this.menu = new Menu(dungeon, hero);
		
		this.heart = new Image("res/heart.png");
		this.menuBox = new Image("res/textBox.png");
		this.largeTextBox = new Image("res/largeTextBox.png");
		this.textBox = new Animation(new SpriteSheet(largeTextBox, 585, 100), 100);
		this.ancient = new Ancient(dungeon, 38, 12);

		Tile.WALL_IMAGE = new Image("./res/wall.png");
		Tile.FLOOR_IMAGE = new Image("./res/floor.png");
		Tile.STAIR_IMAGE = new Image("./res/stairs.png");
	}
	
	public void update(GameContainer container, int delta) throws SlickException
	{
		Input input = container.getInput();
		
		if(!hero.isDead() == true)
		{
			this.hero.update(input, delta);
		
			this.dungeon.update(delta);
			this.camera.update(delta);
			this.ancient.update(delta);
		
			hero.checkAttack(dungeon.thugs);
		}
		else
			if(hero.isDead() == true)
			{
				if(input.isKeyDown(Input.KEY_R))
				{
					reset = true;
					this.hero = new Hero(dungeon, dungeon.getRoom(3, 0), 5, 1);
					this.camera = new Camera(hero);
					this.hero.setAlive();
				}
				if(input.isKeyDown(Input.KEY_Q))
					System.exit(0);
			}
		
		if(hero.getRoomyX() == 3 && hero.getRoomyY() == 1 && ancient.y < Tile.SIZE * 14.5)
		{
			ancient.y += .005 * delta;
		}
		
		if(dungeon.getTile(hero.x, hero.y).isStairs)
		{
			nextLevel = true;
		}
	}
	
	public void render(GameContainer container, Graphics graphics) throws SlickException
	{
		this.dungeon.render(graphics, camera);
		
		this.hero.render(graphics, camera);
		this.menu.render(graphics, camera);
		ancient.render(graphics, camera);
		
		for(int i = 0; i < hero.getHealth(); i++)
		{
			heart.draw(500+(10*i), 20);
		}
		
		if(hero.isDead() == true)
		{
			graphics.setColor(textColor);
			menuBox.draw(Room.WIDTH/5, Room.HEIGHT/3);
			graphics.drawString("Restart (R)", Room.WIDTH/3, Room.HEIGHT/3 + 10);
			graphics.drawString("Main Menu (M)", Room.WIDTH/3, Room.HEIGHT/3 + 30);
			graphics.drawString("Quit Game (Q)", Room.WIDTH/3, Room.HEIGHT/3 + 50);
		}
		
		// temporary dialogue box
		graphics.setColor(textColor);
		if(hero.getRoomyX() == 3 && hero.getRoomyY() == 1 && ancient.y > Tile.SIZE * 13 && ancient.y < Tile.SIZE * 14)
		{
		textBox.draw(Room.WIDTH/6, Room.HEIGHT/6);
		textBox.setLooping(false);
		String[] greeting = {"I", "t", "s", " ", "l", "o", "n", "e", "l", "y", " ", "h", "e", "r", "e", "!", " ", "T", "h", "a", "n", "k", "s", " ", "f", "o", "r", " ", "s","t", "o", "p", "p" ,"i", "n", "g", " ", "b", "y", "!", " ", "C", "o", "m", "e", " ", "b", "a", "c", "k", " ", "f", "o", "r", " ", " "};
		String[] greeting2 = {"t", "h", "e", " ", "n", "e", "x", "t", " ", "r", "o", "u", "n", "d", " ", "o", "f", " ", "p", "l", "a", "y", "t", "e", "s", "t", "i", "n", "g", " ", "i", "n", " ", "t", "w", "o", " ", "w", "e", "e", "k", "s", "!"};
		int xCoord = (int) (Room.WIDTH/6 + 10);
		int yCoord = (int) (Room.HEIGHT/6 + 10);
		int xCoord1 = (int) (Room.WIDTH/6 + 10);
		int yCoord1 = (int) (Room.HEIGHT/6 + 30);
		int timerIncrement = 100;
		int letterCounter = 0;
		int timerIncrement2 = 100;
		int letterCounter2 = 0;
			for(int i = 0; i < greeting.length; i++)
			{	
				counter++;	
				if(counter > timerIncrement)
				{	
					graphics.drawString(greeting[letterCounter], xCoord, yCoord);
					xCoord = xCoord + 10;
					timerIncrement += 400;	
					letterCounter ++;
				}
		}
			if(letterCounter >= greeting.length)
			{
				for(int i = 0; i < greeting2.length; i++)
				{	
					counter2++;	
					if(counter2 > timerIncrement2)
					{	
						graphics.drawString(greeting2[letterCounter2], xCoord1, yCoord1);
						xCoord1 = xCoord1 + 10;
						timerIncrement2 += 400;	
						letterCounter2 ++;
					}
				}
			}
		}
		
		if(nextLevel)
		{
		graphics.setColor(textColor);
		menuBox.draw(Room.WIDTH/5, Room.HEIGHT/3);
		graphics.drawString("Congrats! 1st floor complete!", Room.WIDTH/3, Room.HEIGHT/3 + 5);
		graphics.drawString("Restart (R)", Room.WIDTH/3, Room.HEIGHT/3 + 20);
		graphics.drawString("Main Menu (M)", Room.WIDTH/3, Room.HEIGHT/3 + 35);
		graphics.drawString("Quit Game (Q)", Room.WIDTH/3, Room.HEIGHT/3 + 50);
		}
	}
	
	public static void main(String[] args)
	{
		try
		{
			AppGameContainer container = new AppGameContainer(new Game());
			container.setDisplayMode(Room.WIDTH, Room.HEIGHT, false);
			container.start();
		}
		catch(Exception error)
		{
			System.out.println(error.getMessage());
		}
	}
	
	public static final String TITLE = "We don't need no title.";
}