
package computc.states;

import java.util.HashSet;
import java.util.Set;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.lwjgl.input.Mouse;
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
import org.newdawn.slick.state.transition.RotateTransition;

import computc.Game;
import computc.GameData;
import computc.Menu;
import computc.cameras.Camera;
import computc.cameras.RoomFollowingCamera;
import computc.entities.Arrow;
import computc.entities.Chain;
import computc.entities.Coin;
import computc.entities.Hero;
import computc.entities.Key;
import computc.entities.OldMan;
import computc.worlds.dungeons.Dungeon;
import computc.worlds.rooms.Room;
import computc.worlds.tiles.Tile;

public class MainGameState extends BasicGameState
{
	public GameData gamedata;
	
	public Menu menu;
	public RoomFollowingCamera camera;
	
	private int gravityCoolDown; //chain
	private int deathTimer;
	
	public MainGameState(GameData gamedata)
	{
		this.gamedata = gamedata;
	}
	
	private Animation textBox;
	private Graphics graphics;
	
	public void enter(GameContainer container, StateBasedGame game)
	{
		this.camera.setToTargetX();
		this.camera.setToTargetY();
	}
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException
	{
		Coin.IMAGE = new Image("./res/coin.png");
		
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
		
		if(input.isKeyDown(Input.KEY_M))
		{
			game.enterState(DungeonMapGameState.ID, new FadeOutTransition(Color.black, 100), new FadeInTransition(Color.black, 100));
			Game.assets.playSoundEffectWithoutRepeat("openMap");
		}
		
		if(input.isKeyDown(Input.KEY_ESCAPE))
		{
			game.enterState(TitleScreen.ID, new FadeOutTransition(Color.black, 100), new FadeInTransition(Color.black, 100));
		}
		
		if(this.gamedata.hero.isDead())
		{
			if(deathTimer <= 0)
			{		
			deathTimer = 2000;
			}
			
			this.gamedata.hero.setFlashing();
			
			if(Game.difficulty.equals("HARD"))
			{
				ToNextLevelGameState.speedBoostEnabled = false;
				ToNextLevelGameState.powerArrowEnabled = false;
				ToNextLevelGameState.moreArrow = false;
				this.gamedata.level = 0;
			}
			
			if(deathTimer <= 1000 )
			{
				this.gamedata.dungeon.setDebugDraw(this.graphics);
			}
			
			if(deathTimer <= 100)
			{
			game.enterState(YouDiedGameState.ID, new FadeOutTransition(Color.black, 100), new FadeInTransition(Color.black, 100));
			this.gamedata.dungeon.setNormalDraw(this.graphics);
			}
		}
		
		//  makes the chain movement less floaty
		if(input.isKeyDown(Input.KEY_UP))
		{
			this.gamedata.hero.getWorld().setGravity(new Vec2(0, 1f));
			
		}
		else if(input.isKeyDown(Input.KEY_DOWN))
		{
			this.gamedata.hero.getWorld().setGravity(new Vec2(0, -1f));
		}

		if(input.isKeyDown(Input.KEY_LEFT))
		{
			this.gamedata.hero.getWorld().setGravity(new Vec2(1f, 0));
		}
		else if(input.isKeyDown(Input.KEY_RIGHT))
		{
			this.gamedata.hero.getWorld().setGravity(new Vec2(-1f, 0));
		}
		
		if(this.gamedata.dungeon.chainEnabled)
		{
			for(Body body: this.gamedata.hero.chain.bodies)
			{
				body.setLinearDamping(10);
			}
		}
		
		if(gravityCoolDown != 0)
		{
			gravityCoolDown--;
		}
		
		if(deathTimer > 0)
		{
			deathTimer -= delta;
		}
		
		// sets the camera to peek into adjacent rooms
		if(this.gamedata.hero.getPeekTimer() > 850)
		{
			this.camera.setPeeking(this.gamedata.hero.getDirection());
		}
		
		if(this.gamedata.hero.collidesWith(this.gamedata.dungeon.ladder))
		{
			if(Game.assets.lowHealth.isPlaying())
				Game.assets.lowHealth.stop();
			this.gamedata.level++;
			if(this.gamedata.level <= 3)
			{
				Game.assets.fadeMusicOut();
				Game.assets.playSoundEffectWithoutRepeat("levelComplete");
				ToNextLevelGameState.transitionRoom = true;
				
				int coinage = this.gamedata.hero.coinage;
				int currentHealth = this.gamedata.hero.currentHealth;
				int arrowCount = this.gamedata.hero.arrowCount;
				int monsters_killed = this.gamedata.hero.monsters_killed;
				
				this.gamedata.instantiate();
				
				this.gamedata.hero.coinage = coinage;
				this.gamedata.hero.currentHealth = currentHealth;
				this.gamedata.hero.arrowCount = arrowCount;
				this.gamedata.hero.monsters_killed = monsters_killed;
				
				game.enterState(ToNextLevelGameState.ID, new FadeOutTransition(Color.black, 250), new FadeInTransition(Color.black, 1000));
				
				Game.assets.fadeMusicIn();
			}
			else
			{
				game.enterState(YouWonGameState.ID, new FadeOutTransition(Color.black, 250), new FadeInTransition(Color.black, 250));
			}
		}
	}
	
	public void render(GameContainer container, StateBasedGame game, Graphics graphics) throws SlickException
	{
		Input input = container.getInput();
		this.graphics = graphics;
		
		this.gamedata.dungeon.render(graphics, this.camera);
		this.gamedata.hero.render(graphics, this.camera);
		this.menu.render(graphics, camera);
		
		if(this.gamedata.hero.getArrowPowerUp() > 2000 && this.gamedata.hero.arrowCount != 0 && ToNextLevelGameState.powerArrowEnabled)
		{
			if(this.gamedata.hero.getArrowCooldown() <= 0)
			{
				if(this.gamedata.hero.getArrowPowerUp() < 2100)
				{
				Arrow tempArrow = new Arrow(this.gamedata.dungeon, this.gamedata.hero.getRoom(), this.gamedata.hero.getTileyX(), this.gamedata.hero.getTileyY(), this.gamedata.hero.getDirection());
				this.gamedata.hero.tempArrow = tempArrow;
				}
				this.gamedata.hero.tempArrow.setPowerCharge();
				this.gamedata.hero.tempArrow.setTempArrow();
				this.gamedata.hero.tempArrow.render(graphics, camera);
				
				if(this.gamedata.hero.tempArrow.getDirection() != this.gamedata.hero.getDirection())
				{
					this.gamedata.hero.resetArrowPowerUp();
				}
			}
		}
	}
	
	@Override
	public void keyPressed(int k, char c)
	{
		if(k == Input.KEY_B)
		{
			this.gamedata.hero.setSwinging();
		}
	}
	
	
	@Override
	public void keyReleased(int k, char c)
	{
		
		if(k == Input.KEY_UP)
		{
			this.camera.resetPeeking();
			this.gamedata.hero.resetPeekTimer();
		}
		if(k == Input.KEY_DOWN)
		{
			this.camera.resetPeeking();
			this.gamedata.hero.resetPeekTimer();
		}
		if(k == Input.KEY_LEFT)
		{
			this.camera.resetPeeking();
			this.gamedata.hero.resetPeekTimer();
		}
		if(k == Input.KEY_RIGHT)
		{
			this.camera.resetPeeking();
			this.gamedata.hero.resetPeekTimer();
		}
		
		if(k == Input.KEY_E)
		{
			this.camera.setShaking(this.gamedata.hero.getDirection(), 50);
		}
		
		if(k == Input.KEY_N)
		{
			if(this.gamedata.hero.arrowCount != 0)
			{
				Arrow arrow;
				
				if(this.gamedata.hero.getArrowCooldown() <= 0 && this.gamedata.hero.getFiringArrowFrame() == 2)
				{
					arrow = new Arrow(this.gamedata.dungeon, this.gamedata.hero.getRoom(), this.gamedata.hero.getTileyX(), this.gamedata.hero.getTileyY(), this.gamedata.hero.getDirection());
					arrow.setPosition(this.gamedata.hero.getX(), this.gamedata.hero.getY());
					this.gamedata.hero.arrowCount -= 1;
					Game.assets.playSoundEffectWithoutRepeat("arrowFire");
					this.gamedata.hero.arrows.add(arrow);
					this.gamedata.hero.startArrowCooldown();
					
					if(this.gamedata.hero.getArrowPowerUp() > 2000 && ToNextLevelGameState.powerArrowEnabled)
					{
						arrow.setPowerCharge();
					}
				}
				else
				{
					this.gamedata.hero.restartFiringArrow();
				}
				this.gamedata.hero.resetArrowPowerUp();
			}
		}
		
	}
	
	public int getID()
	{
		return MainGameState.ID;
	}
	
	public static final int ID = 2;

}