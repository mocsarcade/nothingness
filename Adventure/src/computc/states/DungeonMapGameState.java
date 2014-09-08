package computc.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import computc.Camera;
import computc.GameData;
import computc.InputPollingCamera;
import computc.RoomFollowingCamera;
import computc.entities.Hero;
import computc.worlds.Dungeon;

public class DungeonMapGameState extends BasicGameState
{
	public GameData gamedata;
	public Camera camera;
	
	public DungeonMapGameState(GameData gamedata)
	{
		this.gamedata = gamedata;
		this.camera = new InputPollingCamera();
	}
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException
	{
		//code goes here.
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
	{
		Input input = container.getInput();
		
		this.camera.update(input, delta);

		if(input.isKeyDown(Input.KEY_ESCAPE))
		{
			game.enterState(0);
		}
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException
	{
		this.gamedata.dungeon.renderOnMap(graphics, this.camera);
		this.gamedata.hero.renderOnMap(graphics, this.camera);
	}
	
	public int getID()
	{
		return DungeonMapGameState.ID;
	}
	
	public static final int ID = 1;
}