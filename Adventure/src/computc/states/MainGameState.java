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
import computc.GameData;
import computc.Menu;
import computc.RoomFollowingCamera;
import computc.entities.Hero;
import computc.entities.OldMan;
import computc.worlds.Dungeon;
import computc.worlds.PredesignedDungeon;
import computc.worlds.LinearRandomDungeon;
import computc.worlds.Tile;

public class MainGameState extends BasicGameState
{
	public GameData gamedata;
	public Camera camera;
	public Menu menu;
	
	public MainGameState(GameData gamedata)
	{
		this.gamedata = gamedata;
	}
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException
	{
		this.gamedata.instantiate();
		
		this.menu = new Menu(this.gamedata.dungeon, this.gamedata.hero);
		this.camera = new RoomFollowingCamera(this.gamedata.hero);
		
		Tile.WALL_IMAGE = new Image("./res/wall.png");
		Tile.FLOOR_IMAGE = new Image("./res/floor.png");
		Tile.STAIR_IMAGE = new Image("./res/stairs.png");
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
	{
		Input input = container.getInput();
		
		this.gamedata.hero.update(input, delta);
		this.menu.update(input, game);
		this.camera.update(input, delta);
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException
	{
		this.gamedata.dungeon.render(graphics, this.camera);
		this.gamedata.hero.render(graphics, this.camera);
		this.menu.render(graphics, this.camera);
	}
	
	public int getID()
	{
		return MainGameState.ID;
	}
	
	public static final int ID = 0;
}