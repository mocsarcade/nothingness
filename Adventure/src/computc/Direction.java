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

	public static Direction getOpposite(Direction direction)
	{
		if(direction == NORTH)
		{
			return SOUTH;
		}
		else if(direction == SOUTH)
		{
			return NORTH;
		}
		else if(direction == EAST)
		{
			return WEST;
		}
		else if(direction == WEST)
		{
			return EAST;
		}
		else
		{
			return NONE;
		}
	}
}