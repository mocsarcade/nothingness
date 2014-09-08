package computc.states;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import computc.entities.Hero;
import computc.worlds.Dungeon;

public class DungeonMapGameState extends BasicGameState
{
	public Hero hero;
	public Dungeon dungeon;
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException
	{
		//?!
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
	{
		Input input = container.getInput();

		if(input.isKeyDown(Input.KEY_ESCAPE))
		{
			game.enterState(0);
		}
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException
	{
		
	}
	
	public int getID()
	{
		return DungeonMapGameState.ID;
	}
	
	public static final int ID = 1;
}