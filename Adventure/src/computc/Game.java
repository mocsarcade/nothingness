package computc;

import java.util.Random;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.SlickException;

import computc.worlds.rooms.Room;
import computc.states.MainGameState;
import computc.states.DungeonMapGameState;
import computc.states.ToNextLevelGameState;

public class Game extends StateBasedGame
{
	public static boolean reset;
	
	public Game()
	{
		super(Game.TITLE + " " + Game.VERSION);
	}
	
	public void initStatesList(GameContainer container) throws SlickException
	{
		GameData gamedata = new GameData();
		
        this.addState(new MainGameState(gamedata));
        this.addState(new DungeonMapGameState(gamedata));
        this.addState(new ToNextLevelGameState(gamedata));
	}
	
	public static void main(String[] args) throws SlickException
	{
		AppGameContainer container = new AppGameContainer(new Game());
		container.setDisplayMode(Game.WIDTH, Game.HEIGHT, false);
		container.setTargetFrameRate(60);
		container.start();
	}
	
	public static final String TITLE = "Game";
	public static final String VERSION = "v0.3.0";
	
	public static final int WIDTH = Room.WIDTH;
	public static final int HEIGHT = Room.HEIGHT;
	
	public static Random random = new Random();
	public static AssetManager assets = new AssetManager();
}