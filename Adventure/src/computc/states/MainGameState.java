package computc.states;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import computc.Camera;
import computc.Game;
import computc.Menu;
import computc.entities.Hero;
import computc.entities.OldMan;
import computc.worlds.Dungeon;
import computc.worlds.PredesignedDungeon;
import computc.worlds.RandomizedDungeon;
import computc.worlds.Tile;

public class MainGameState extends BasicGameState
{
	public Hero hero;
	public Dungeon dungeon;
	public Camera camera;
	public Menu menu;
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException
	{
		this.dungeon = new RandomizedDungeon();
		this.hero = new Hero(dungeon, dungeon.getRoom(0, 0), 5, 1);
		this.menu = new Menu(dungeon, hero);
		this.camera = new Camera(hero);
		
		Tile.WALL_IMAGE = new Image("./res/wall.png");
		Tile.FLOOR_IMAGE = new Image("./res/floor.png");
		Tile.STAIR_IMAGE = new Image("./res/stairs.png");
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
	{
		Input input = container.getInput();
		
		this.hero.update(input, delta);
		this.camera.update(delta);
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException
	{
		this.dungeon.render(graphics, camera);
		this.hero.render(graphics, camera);
		this.menu.render(graphics, camera);
	}
	
	public int getID()
	{
		return MainGameState.ID;
	}
	
	public static final int ID = 0;
}