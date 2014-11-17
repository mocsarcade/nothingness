package computc.states;

import java.util.LinkedList;

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
import computc.entities.Arrow;
import computc.entities.Murk;
import computc.worlds.rooms.Room;

public class ToNextLevelGameState extends BasicGameState
{
	private GameData gamedata;
	
	public Menu menu;
	public RoomFollowingCamera camera;
	
	public Animation textBox;
	public Murk murk;
	
	public boolean scoreScreen = true;
	
	public static boolean transitionRoom;
	
	public static boolean speedBoost, powerArrow, moreArrow, oneHeart;
	
	public LinkedList<Animation> upgrades = new LinkedList<Animation>();
	
	private Image arrowSheet = Game.assets.getImage("res/arrowAnimationSpriteSheet.png");
	private Image speedBoostIcon = Game.assets.getImage("res/speedBoostSheet.png");
	private Image powerArrowIcon = Game.assets.getImage("res/powerArrowSheet.png");
	public Image heartSheet = Game.assets.getImage("res/collectibleHeartSheet.png");
	private Image groundedHeart = heartSheet.getSubImage(1, 188, 256, 64);
	
	private String greeting = "Gimmee Gimmee, something SHINY";
	private String greeting2 = "I will make it worth your while";
	private String greeting3 = "speed boost discovered! hold z to use it";
	private String greeting4 = "power shot discovered!";
	private String greeting5 = "quiver size upgraded!";
	private String greeting6 = "health increased!";
	
	public Image screen = Game.assets.getImage("./res/textScreens/Floor-Cleared.png");
	public Image blackScreen = Game.assets.getImage("./res/textScreens/Main-Menu-1.png");
	
	private Animation speedy, arrowPower, heartSpin, arrows;
	
	private int cursor = 0;
	private int cursor_time = 0;
	private int screenTimer = 0;
	private int talkTimer = 0;
	
	private float counter, counter2, counter3, counter4, counter5;

	public ToNextLevelGameState(GameData gamedata)
	{
		this.gamedata = gamedata;
	}
	
	public void init(GameContainer container, StateBasedGame game) throws SlickException
	{
		this.textBox = new Animation(new SpriteSheet(new Image("res/largeTextBox.png"), 585, 100), 100);
		
		this.gamedata.instantiate();
		
		this.camera = new RoomFollowingCamera(this.gamedata);
		
		arrows = new Animation(new SpriteSheet(arrowSheet, 64, 64), 100);
		speedy = new Animation(new SpriteSheet(speedBoostIcon, 64, 64), 100);
		arrowPower = new Animation(new SpriteSheet(powerArrowIcon, 64, 64), 100);
		heartSpin = new Animation(new SpriteSheet(groundedHeart, 64 ,64), 100);
		
		this.menu = new Menu(gamedata);
		this.murk = new Murk(this.gamedata.dungeon, 5, 3);
	}
	
	public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException
	{
		Input input = container.getInput();
		
		if((input.isKeyDown(Input.KEY_ENTER) || input.isKeyDown(Input.KEY_SPACE)) && scoreScreen)
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
			
			transitionRoom = true;
		 	scoreScreen = false;
			
			
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
		this.gamedata.hero.checkPickup(this.gamedata.dungeon.commodities, this.gamedata.dungeon.keys);
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
		
		if(this.gamedata.hero.collidesWith(this.murk))
		{
			talkTimer = 3000;
		}
		
		if(talkTimer > 0)
		{
			if((int)(counter) < greeting.length() || talkTimer > 1000)
			{
				counter += delta * 0.025;
			}
			else 
			{
				counter2 += delta * 0.025;
			}
		}
		
		if(screenTimer > 0)
		{
			screenTimer -= delta;
		}
		
		if(talkTimer > 0)
		{
			talkTimer -= delta;
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
		
		if(talkTimer > 0)
		{
			textBox.draw(Room.WIDTH/11, Room.HEIGHT/11);
			textBox.setLooping(false);
			
			int xCoord = (int) (Room.WIDTH/11 + 12);
			int yCoord = (int) (Room.HEIGHT/11 + 12);
			int xCoord2 = (int) (Room.WIDTH/11 + 12);
			int yCoord2 = (int) (Room.HEIGHT/11 + 32);
			
			String greeting2temp = greeting2;
			graphics.setColor(Color.white);
			
			graphics.drawString(greeting.substring(0, (int)(Math.min(counter, greeting.length()))), xCoord, yCoord);
			graphics.drawString(greeting2temp.substring(0, (int)(Math.min(counter2, greeting2temp.length()))), xCoord2, yCoord2);
			
			
//			String greeting4temp = greeting4; 
//				graphics.drawString(greeting3.substring(0, (int)(Math.min(counter3, greeting3.length()))), xCoord, yCoord);
//				graphics.drawString(greeting4temp.substring(0, (int)(Math.min(counter4, greeting4temp.length()))), xCoord2, yCoord2);
//				graphics.drawString(greeting5.substring(0, (int)(Math.min(counter5, greeting5.length()))), xCoord2, yCoord2 + 20);
			
		}
		
		if(this.gamedata.hero.getArrowPowerUp() > 2000 && this.gamedata.hero.arrowCount != 0)
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
		
		if(talkTimer > 0)
		{
			for(int i = 0; i < upgrades.size(); i++)
			{
				upgrades.get(i).draw( 100 +(i * 150), 80);
				
				if(cursor != i)
				{
					upgrades.get(i).stop();
					upgrades.get(i).setCurrentFrame(0);
				}
				
				if(cursor == i && upgrades.get(i).isStopped())
				{
					upgrades.get(i).start();
				}
				
			}
			
			if(cursor_time % 1000 < 750)
			{
				graphics.fillOval(100 + (150*cursor), 120, 10, 10);
			}
			else
			{
				graphics.drawOval(100 + (150*cursor), 120, 10, 10);
			}
		}
		
	}
	
	public void enter(GameContainer container, StateBasedGame game)
	{
		upgrades.clear();
		
		if(!speedBoost)
		{
			upgrades.add(speedy);
		}
		
		if(!powerArrow)
		{
			upgrades.add(arrowPower);
		}
		
		if(!moreArrow)
		{
			upgrades.add(arrows);
		}
		
		upgrades.add(heartSpin);
		

	}
	
	public int getID()
	{
		return ToNextLevelGameState.ID;
	}
	
	public void keyPressed(int k, char c)
	{
		if(k == Input.KEY_B)
		{
			this.gamedata.hero.setSwinging();
		}
		
		if(k == Input.KEY_A || k == Input.KEY_LEFT)
			{
				this.cursor  -= 1;
				if(this.cursor < 0)
					this.cursor = 0;
			}
			
			if(k == Input.KEY_D || k == Input.KEY_RIGHT)
			{
				this.cursor += 1;
				if(this.cursor > 3)
					this.cursor = 3;
			}
		
	}
	
	public void keyReleased(int k, char c)
	{
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
					
					if(this.gamedata.hero.getArrowPowerUp() > 2000)
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
	
	public static final int ID = 4;
}