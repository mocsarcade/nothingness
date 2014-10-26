package computc.states;

import org.jbox2d.common.Vec2;
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

import computc.Game;
import computc.GameData;
import computc.Menu;
import computc.cameras.RoomFollowingCamera;
import computc.entities.Arrow;
import computc.worlds.rooms.Room;

public class TutorialState extends BasicGameState
{
	public GameData gamedata;
	
	public boolean swordAttack = false;
	public boolean arrowAttack = false;
	public boolean arrowPowerAttack = false;
	public boolean peeker = false;
	
	public Menu menu;
	public RoomFollowingCamera camera;
	
	private Animation textBox;
	
	private String greeting = "Tutorial(esc to quit): Swing your sword with B!";
	private String greeting2 = "Press N to show off your archery skills!";
	private String greeting3 = "Nice shot! Now hold N for a power shot!";
	private String greeting4 = "One more trick: Try leaning into the wall. ";
	private String greeting5 = "You can peek into the next room while staying safe!";
	
	private float counter, counter2, counter3, counter4, counter5;

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
		
		if(input.isKeyDown(Input.KEY_ENTER) && swordAttack && arrowAttack && arrowPowerAttack && peeker)
		{
			MainGameState maingame = (MainGameState) game.getState(MainGameState.ID);
			maingame.camera.setToTargetX();
			maingame.camera.setToTargetY();
			
			game.enterState(2, new FadeOutTransition(Color.black, 100), new FadeInTransition(Color.black, 1000));
		}
		
		if(this.gamedata.hero.getRoom() == this.gamedata.dungeon.firstRoom && this.gamedata.hero.getRoomPositionY() >= Room.HEIGHT/2)
				{
					if((int)(counter) < greeting.length())
					{
						counter += delta * 0.025;
					}
					else if(swordAttack)
					{
						counter2 += delta * 0.025;
					}
					
					if(arrowAttack && !arrowPowerAttack)
					{
						counter3 += delta * 0.025;
					}
					else if(arrowPowerAttack)
					{
						counter4 += delta * 0.025;
					}
					
					if(counter4 > 125)
					{
						counter5 += delta * 0.025;
					}
				}
		
		if(this.gamedata.hero.getPeekTimer() > 850)
		{
			this.camera.setPeeking(this.gamedata.hero.getDirection());
		}
		
		if(this.gamedata.hero.getPeekTimer() > 800)
		{
			if(swordAttack && arrowAttack && arrowPowerAttack && !peeker)
			{
			this.gamedata.dungeon.firstRoom.addKey();
			peeker = true;
			}
		}
		
		if(this.gamedata.hero.collidesWith(this.gamedata.dungeon.ladder))
		{
			this.gamedata.level++;
			System.out.println(this.gamedata.level);
			
			if(this.gamedata.level < 4)
			{
				Game.assets.fadeMusicOut();
				Game.assets.playSoundEffectWithoutRepeat("levelComplete");
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
		graphics.setColor(Color.white);
		
		this.gamedata.dungeon.render(graphics, this.camera);
		this.menu.render(graphics, camera);
		this.gamedata.hero.render(graphics, this.camera);

		if(this.gamedata.hero.getRoomyX() == this.gamedata.dungeon.firstRoom.getRoomyX()
				&& this.gamedata.hero.getRoomyY() == this.gamedata.dungeon.firstRoom.getRoomyY())
				{
					textBox.draw(Room.WIDTH/11, Room.HEIGHT/11);
					textBox.setLooping(false);
					
					int xCoord = (int) (Room.WIDTH/11 + 12);
					int yCoord = (int) (Room.HEIGHT/11 + 12);
					int xCoord2 = (int) (Room.WIDTH/11 + 12);
					int yCoord2 = (int) (Room.HEIGHT/11 + 32);
					
					String greeting2temp = greeting2;
					graphics.setColor(Color.white);
					if(!arrowAttack)
					{
					graphics.drawString(greeting.substring(0, (int)(Math.min(counter, greeting.length()))), xCoord, yCoord);
					graphics.drawString(greeting2temp.substring(0, (int)(Math.min(counter2, greeting2temp.length()))), xCoord2, yCoord2);
					}
					
					String greeting4temp = greeting4;
					if(arrowAttack)
					{ 
						graphics.drawString(greeting3.substring(0, (int)(Math.min(counter3, greeting3.length()))), xCoord, yCoord);
						graphics.drawString(greeting4temp.substring(0, (int)(Math.min(counter4, greeting4temp.length()))), xCoord2, yCoord2);
						graphics.drawString(greeting5.substring(0, (int)(Math.min(counter5, greeting5.length()))), xCoord2, yCoord2 + 20);
					}
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
			}
		}
	}
	
	public void keyPressed(int k, char c)
	{
		if(k == Input.KEY_B)
		{
			swordAttack = true;
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
					arrowAttack = true;
					
					if(this.gamedata.hero.getArrowPowerUp() > 2000)
					{
						arrow.setPowerCharge();
						arrowPowerAttack = true;
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
		return TutorialState.ID;
	}
	
	public static final int ID = 1;
}
