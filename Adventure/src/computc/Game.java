package computc;

import java.awt.Point;
import java.util.LinkedList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame
{
	public Hero hero;
	public Dungeon dungeon;
	public Camera camera;
	public Menu menu;
	
	private Image heart;
	private Image menuBox;
	
	public Color textColor = Color.white;
	
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

		Tile.WALL_IMAGE = new Image("./res/wall.png");
		Tile.FLOOR_IMAGE = new Image("./res/floor.png");
	}
	
	public void update(GameContainer container, int delta) throws SlickException
	{
		Input input = container.getInput();
		
		if(!hero.isDead() == true)
		{
			this.hero.update(input, delta);
		
			this.dungeon.update(delta);
			this.camera.update(delta);
		
			hero.checkAttack(dungeon.thugs);
		}
		else
			if(hero.isDead() == true)
			{
				if(input.isKeyDown(Input.KEY_R))
				{
					this.hero = new Hero(dungeon, dungeon.getRoom(3, 0), 5, 1);
					this.camera = new Camera(hero);
					this.hero.setAlive();
				}
				if(input.isKeyDown(Input.KEY_Q))
					System.exit(0);
			}
	}
	
	public void render(GameContainer container, Graphics graphics) throws SlickException
	{
		this.dungeon.render(graphics, camera);
		
		this.hero.render(graphics, camera);
		this.menu.render(graphics, camera);
		
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