package computc.states;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import computc.Game;
import computc.GameData;

public class TitleScreen extends BasicGameState
{
	private GameData gamedata;
	
	Animation titleScreen;
	int[] titleScreenDuration = {100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 100, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 200, 10000};

	public TitleScreen(GameData gamedata)
	{
		this.gamedata = gamedata;
		
		Image[] menu = { Game.assets.getImage("res/textScreens/Main-Menu-3.png"), Game.assets.getImage("res/textScreens/Main-Menu-4.png"), Game.assets.getImage("res/textScreens/Main-Menu-5.png"), Game.assets.getImage("res/textScreens/Main-Menu-6.png"), Game.assets.getImage("res/textScreens/Main-Menu-7.png"), Game.assets.getImage("res/textScreens/Main-Menu-8.png"), Game.assets.getImage("res/textScreens/Main-Menu-9.png"), Game.assets.getImage("res/textScreens/Main-Menu-10.png"), Game.assets.getImage("res/textScreens/Main-Menu-11.png"), Game.assets.getImage("res/textScreens/Main-Menu-12.png"), Game.assets.getImage("res/textScreens/Main-Menu-13.png"), Game.assets.getImage("res/textScreens/Main-Menu-14.png"), Game.assets.getImage("res/textScreens/Main-Menu-15.png"), Game.assets.getImage("res/textScreens/Main-Menu-16.png"), Game.assets.getImage("res/textScreens/Main-Menu-17.png"), Game.assets.getImage("res/textScreens/Main-Menu-18.png"), Game.assets.getImage("res/textScreens/Main-Menu-19.png"), Game.assets.getImage("res/textScreens/Main-Menu-20.png"), Game.assets.getImage("res/textScreens/Main-Menu-21.png"), Game.assets.getImage("res/textScreens/Main-Menu-22.png"), Game.assets.getImage("res/textScreens/Main-Menu-23.png"), Game.assets.getImage("res/textScreens/Main-Menu-24.png"), Game.assets.getImage("res/textScreens/Main-Menu-25.png"), Game.assets.getImage("res/textScreens/Main-Menu-26.png"), Game.assets.getImage("res/textScreens/Main-Menu-27.png"), Game.assets.getImage("res/textScreens/Main-Menu-28.png")};
		
		titleScreen = new Animation(menu, titleScreenDuration, true);
		titleScreen.setLooping(false);
	}
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException
	{
		this.gamedata.instantiate();
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
	{
		Input input = container.getInput();
	
		if(input.isKeyDown(Input.KEY_ENTER))
		{
			MainGameState maingame = (MainGameState) game.getState(MainGameState.ID);
			maingame.camera.setToTargetX();
			maingame.camera.setToTargetY();
			
			game.enterState(TutorialState.ID, new FadeOutTransition(Color.black, 100), new FadeInTransition(Color.black, 1000));
		}
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException
	{
		graphics.setColor(Color.white);
		
		titleScreen.draw(0, 0);
	}
	
	public int getID()
	{
		return ToNextLevelGameState.ID;
	}
	
	public static final int ID = 0;
}