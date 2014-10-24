package computc.states;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import computc.Game;
import computc.GameData;
import computc.cameras.Camera;
import computc.cameras.InputPollingCamera;
import computc.cameras.RoomFollowingCamera;
import computc.entities.Hero;
import computc.worlds.dungeons.Dungeon;
import computc.worlds.dungeons.RandomDungeon;
import computc.worlds.rooms.Room;

public class DungeonMapGameState extends BasicGameState
{
	public GameData gamedata;
	public InputPollingCamera camera;
	public StateBasedGame game;
	
	public DungeonMapGameState(GameData gamedata)
	{
		this.gamedata = gamedata;
		this.camera = new InputPollingCamera();
	}
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException
	{
		this.game = game;
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
	{
		Input input = container.getInput();
		
		this.camera.update(input, delta);

		if(Game.devmode)
		{
			if(input.isKeyDown(Input.KEY_R))
			{
				this.gamedata.instantiate();
			}
		}
	}
	
	public void keyPressed(int keycode, char character)
	{
		if(keycode == Input.KEY_M || keycode == Input.KEY_ESCAPE)
		{
			Game.assets.playSoundEffectWithoutRepeat("closeMap");
			game.enterState(MainGameState.ID, new FadeOutTransition(Color.black, 100), new FadeInTransition(Color.black, 100));
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
	
	public static final int ID = 2;
}