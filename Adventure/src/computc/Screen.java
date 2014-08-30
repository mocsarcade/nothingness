package computc;

public class Screen
{
	private final int SIZE_OF_TILE = 64;
	private final int WIDTH_IN_TILES = 11;
	private final int HEIGHT_IN_TILES = 9;
	
	public int getSizeOfTile()
	{
		return SIZE_OF_TILE;
	}
	
	public int getWidthInTiles()
	{
		return WIDTH_IN_TILES;
	}
	
	public int getHeightInTiles()
	{
		return HEIGHT_IN_TILES;
	}
	
	public int getWidth()
	{
		return WIDTH_IN_TILES * SIZE_OF_TILE;
	}
	
	public int getHeight()
	{
		return HEIGHT_IN_TILES * SIZE_OF_TILE;
	}
}