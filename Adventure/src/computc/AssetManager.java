package computc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.util.ResourceLoader;

import computc.worlds.rooms.RoomLayout;
import computc.worlds.tiles.Tile;
import computc.worlds.tiles.TileSet;
import computc.worlds.tiles.TileSubSet;

public class AssetManager
{
	private HashMap<String, Image> loadedImages = new HashMap<String, Image>();
	private HashMap<String, TileSet> loadedTileSets = new HashMap<String, TileSet>();
	private HashMap<String, RoomLayout> loadedRoomLayouts = new HashMap<String, RoomLayout>();
	private HashMap<String, Audio> loadedSounds = new HashMap<String, Audio>();
	private Music backgroundMusic;
	public float volume = 1.0f;
	
	public AssetManager()
	{
		initAudio();
	}
	public Image getImage(String source)
	{
		try
		{
			if(this.loadedImages.get(source) == null)
			{
				this.loadedImages.put(source, new Image(source));
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		
		return this.loadedImages.get(source);
	}

	public RoomLayout getRoomLayout(String source)
	{
		try
		{
			if(this.loadedRoomLayouts.get(source) == null)
			{
				this.loadedRoomLayouts.put(source, new RoomLayout(source));
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		
		return this.loadedRoomLayouts.get(source);
	}

	public TileSet getTileSet(String source)
	{
		try
		{
			if(this.loadedTileSets.get(source) == null)
			{
				this.loadedTileSets.put(source, new TileSet(source));
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		
		return this.loadedTileSets.get(source);
	}
	/**
	 * Eventually, this should read from an XML file with filenames of all 
	 * sounds and their game names. Until then, this will be ugly.
	 */
	public void initAudio()
	{
		HashMap<String, String> sounds = new HashMap<String, String>();
		//sounds.put("res/audio/wack.wav", "backgroundMusic");
		sounds.put("res/audio/chirps/arrowNotched.wav", "arrowNotched");
		sounds.put("res/audio/chirps/arrowFire.wav", "arrowFire");
		sounds.put("res/audio/chirps/arrowInEnemy.wav", "arrowInEnemy");
		sounds.put("res/audio/chirps/arrowInWall.wav", "arrowInWall");
		sounds.put("res/audio/chirps/arrowPickup.wav", "arrowPickup");
		sounds.put("res/audio/chirps/wallsShaking.wav", "wallsShaking");
		sounds.put("res/audio/chirps/footstep.wav","footstep");
		sounds.put("res/audio/chirps/openMap.wav","openMap");
		sounds.put("res/audio/chirps/closeMap.wav","closeMap");
		sounds.put("res/audio/chirps/swordStrikesStone.wav","swordStrikesStone");
		sounds.put("res/audio/chirps/swordStrikesAir.wav","swordStrikesAir");
		sounds.put("res/audio/chirps/swordStrikesEnemy.wav","swordStrikesEnemy");
		sounds.put("res/audio/chirps/swordStrikesMetal.wav","swordStrikesMetal");
		sounds.put("res/audio/chirps/keyDrop.wav","keyDrop");
		sounds.put("res/audio/chimes/enemyDying.wav","enemyDying");
		sounds.put("res/audio/chimes/levelComplete.wav","levelComplete");
		//sounds.put("res/audio/chimes/coinDrop.wav","coinDrop");
		//sounds.put("res/audio/chimes/arrowDrop.wav","arrowDrop");

		
		try
		{			
			for(Entry<String, String> sound:sounds.entrySet())
			{
				Audio audio = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream(sound.getKey()));
				loadedSounds.put(sound.getValue(), audio);
			}
			backgroundMusic = new Music("res/audio/wack.wav");
		}
		catch(IOException | SlickException e)
		{
			e.printStackTrace();
		}
            //backgroundMusic.play(1.0f, volume);
	}
	
	public void playMusicWithRepeat(String id)
	{
		loadedSounds.get(id).playAsMusic(1.0f, 1.0f, true);
	}
	public void playSoundEffectWithRepeat(String id)
	{
		loadedSounds.get(id).playAsSoundEffect(1.0f, 1.0f, true);
	}
	public void playSoundEffectWithoutRepeat(String id)
	{
		loadedSounds.get(id).playAsSoundEffect(1.0f, 1.0f, false);
	}
	
	// Fades music to prevent jarring stop
	public void fadeMusicOut()
	{
		for(int i = 0; i < 100; i++)
		{
			backgroundMusic.setVolume((float) (backgroundMusic.getVolume()-.01));
		}
	}
	// Fades music to prevent jarring stop
	public void fadeMusicIn()
	{
		for(int i = 0; i < 1000; i++)
		{
			backgroundMusic.setVolume((float) (backgroundMusic.getVolume()+.01));
		}
	}
}