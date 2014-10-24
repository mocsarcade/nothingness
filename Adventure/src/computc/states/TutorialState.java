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
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

import computc.Game;
import computc.GameData;
import computc.Menu;
import computc.cameras.RoomFollowingCamera;

public class TutorialState extends BasicGameState
{
	public GameData gamedata;
	
	public Menu menu;
	public RoomFollowingCamera camera;
	
	private Animation textBox;

	public TutorialState(GameData gamedata)
	{
		this.gamedata = gamedata;		
	}
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException
	{
		this.textBox = new Animation(new SpriteSheet(new Image("res/largeTextBox.png"), 585, 100), 100);
		
		this.gamedata.instantiate();
		
		this.camera = new RoomFollowingCamera(this.gamedata);
		this.menu = new Menu(gamedata);
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
	{
		Input input = container.getInput();
		
		this.gamedata.hero.update(input, delta);
		this.camera.update(input, delta);
		
		this.gamedata.dungeon.update(delta, input);
		
		this.gamedata.hero.checkAttack(this.gamedata.dungeon.getAllEnemies(), delta);
		this.gamedata.hero.checkPickup(this.gamedata.dungeon.commodities, this.gamedata.dungeon.keys);
		
		if(input.isKeyDown(Input.KEY_ENTER))
		{
			MainGameState maingame = (MainGameState) game.getState(MainGameState.ID);
			maingame.camera.setToTargetX();
			maingame.camera.setToTargetY();
			
			game.enterState(2, new FadeOutTransition(Color.black, 100), new FadeInTransition(Color.black, 1000));
		}
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException
	{
		graphics.setColor(Color.white);
		
		this.gamedata.dungeon.render(graphics, this.camera);
		this.menu.render(graphics, camera);
		this.gamedata.hero.render(graphics, this.camera);
		System.out.println("this is happening");
	}
	
	public int getID()
	{
		return TutorialState.ID;
	}
	
	public static final int ID = 1;
}
