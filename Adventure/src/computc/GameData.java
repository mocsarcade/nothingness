package computc;

import org.newdawn.slick.SlickException;

import computc.entities.Hero;
import computc.worlds.dungeons.Dungeon;
import computc.worlds.dungeons.DungeonException;
import computc.worlds.dungeons.FiveRoomDungeon;
import computc.worlds.dungeons.OneRoomDungeon;
import computc.worlds.dungeons.RandomRoguelikeDungeon;
import computc.worlds.dungeons.RandomZeldaesqueDungeon;

public class GameData
{
	public Hero hero;
	public Level level;
	public Menu menu;
	
	public void instantiate() throws SlickException
	{
		this.level = new Level();
		this.hero = new Hero(this.level.dungeon, 5, 4);
		this.menu = new Menu(this.level.dungeon, this.hero);
	}
}
