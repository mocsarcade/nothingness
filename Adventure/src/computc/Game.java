package computc;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

public class Game extends BasicGame
{
	public Hero hero;
	public Dungeon dungeon;
	public Camera camera;
	public Menu menu;
	
	public Game()
	{
		super(Game.TITLE);
	}
	
	public void init(GameContainer container) throws SlickException
	{
		this.dungeon = new Dungeon();
		this.hero = new Hero(dungeon, dungeon.firstRoom, 5, 1);
		this.camera = new Camera(hero);
		this.menu = new Menu(dungeon, hero);
	}
	
	public void update(GameContainer container, int delta) throws SlickException
	{
		Input input = container.getInput();
		
		this.hero.update(input, delta);
		this.dungeon.update(delta);
		this.camera.update(delta);
	}
	
	public void render(GameContainer container, Graphics graphics) throws SlickException
	{
		this.dungeon.render(graphics, camera);
		this.hero.render(graphics, camera);
		this.menu.render(graphics, camera);
	}
	
	public static void main(String[] args)
	{
		try
		{
			AppGameContainer container = new AppGameContainer(new Game());
			container.setDisplayMode(Room.WIDTH, Room.HEIGHT + Menu.HEIGHT, false);
			container.start();
		}
		catch(Exception error)
		{
			System.out.println(error.getMessage());
		}
	}
	
	public static final String TITLE = "We don't need no title.";
}