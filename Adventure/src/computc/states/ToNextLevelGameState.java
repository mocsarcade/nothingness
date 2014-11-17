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
import computc.entities.Murk;

public class ToNextLevelGameState extends BasicGameState
{
	private GameData gamedata;
	
	public Menu menu;
	public RoomFollowingCamera camera;
	
	public Animation textBox;
	public Murk murk;
	
	public boolean scoreScreen = true;
	
	public static boolean transitionRoom;

	public ToNextLevelGameState(GameData gamedata)
	{
		this.gamedata = gamedata;
	}
	public Image screen = Game.assets.getImage("./res/textScreens/Floor-Cleared.png");
	public Image blackScreen = Game.assets.getImage("./res/textScreens/Main-Menu-1.png");
	
	private int cursor_time = 0;
	private int screenTimer = 0;
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException
	{
		this.textBox = new Animation(new SpriteSheet(new Image("res/largeTextBox.png"), 585, 100), 100);
		
		this.gamedata.instantiate();
		
		this.camera = new RoomFollowingCamera(this.gamedata);
		this.menu = new Menu(gamedata);
		this.murk = new Murk(this.gamedata.dungeon, 5, 3);
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
	{
		Input input = container.getInput();
		
		if(input.isKeyDown(Input.KEY_ENTER) || input.isKeyDown(Input.KEY_SPACE))
		{
			int coinage = this.gamedata.hero.coinage;
			int currentHealth = this.gamedata.hero.currentHealth;
			int arrowCount = this.gamedata.hero.arrowCount;
			int monsters_killed = this.gamedata.hero.monsters_killed;
			
			this.gamedata.instantiate();
			
			Game.assets.lowHealth.stop();
			
			this.gamedata.hero.coinage = coinage;
			this.gamedata.hero.currentHealth = currentHealth;
			this.gamedata.hero.arrowCount = arrowCount;
			this.gamedata.hero.monsters_killed = monsters_killed;
			
			//will someone just think of the children?
			
			scoreScreen = false;
			transitionRoom = true;
			
//			game.enterState(MainGameState.ID, new FadeOutTransition(Color.black, 100), new FadeInTransition(Color.black, 1000));
		}
		else if(input.isKeyDown(Input.KEY_ESCAPE))
		{
			game.enterState(TitleScreen.ID, new FadeOutTransition(Color.black, 100), new FadeInTransition(Color.black, 100));
		}
		
		cursor_time += delta;
		
		this.gamedata.hero.update(input, delta);
		this.camera.update(input, delta);
		
		this.gamedata.dungeon.update(delta, input);
		this.murk.update(delta);
		
		if(this.gamedata.hero.collidesWith(this.gamedata.dungeon.ladder))
		{
			transitionRoom = false;
			
			System.out.println(this.gamedata.level);
			
				game.enterState(MainGameState.ID, new FadeOutTransition(Color.black, 500), new FadeInTransition(Color.black, 1000));
				screenTimer = 1000;
				scoreScreen = true;
				
				int coinage = this.gamedata.hero.coinage;
				int currentHealth = this.gamedata.hero.currentHealth;
				int arrowCount = this.gamedata.hero.arrowCount;
				int monsters_killed = this.gamedata.hero.monsters_killed;
				
				this.gamedata.instantiate();
				
				Game.assets.lowHealth.stop();
				
				this.gamedata.hero.coinage = coinage;
				this.gamedata.hero.currentHealth = currentHealth;
				this.gamedata.hero.arrowCount = arrowCount;
				this.gamedata.hero.monsters_killed = monsters_killed;
		}
		
		if(screenTimer > 0)
		{
			screenTimer -= delta;
		}

	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException
	{
		if(scoreScreen)
		{
			this.screen.draw(0, 0);
			

			graphics.setColor(Color.white);
			graphics.drawString(this.gamedata.hero.monsters_killed + "", 280, 200);
			graphics.drawString(this.gamedata.hero.coinage + "", 280, 250);
		
			if(cursor_time % 1000 < 750)
			{
				graphics.fillOval(175, 500, 10, 10);
			}
			else
			{
				graphics.drawOval(175, 500, 10, 10);
			}
			
			if(screenTimer > 0)
			{
				this.blackScreen.draw(0, 0);
			}
			
		}
		else
		{
			this.gamedata.dungeon.render(graphics, this.camera);
			this.gamedata.hero.render(graphics, this.camera);
			this.murk.render(graphics, this.camera);
			this.menu.render(graphics, camera);
		}
		
	}
	
	public int getID()
	{
		return ToNextLevelGameState.ID;
	}
	
	public static final int ID = 4;
}