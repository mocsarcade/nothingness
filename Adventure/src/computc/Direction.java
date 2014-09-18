package computc;

import java.util.Random;

public enum Direction
{
	NORTH, SOUTH, EAST, WEST, NONE;
	
	public static Direction getRandom()
	{
		Direction[] directions = Direction.values();
		return directions[Game.randomness.nextInt(directions.length)];
	}
}