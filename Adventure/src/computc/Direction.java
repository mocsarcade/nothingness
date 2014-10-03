package computc;

import java.util.Random;

public enum Direction
{
	NORTH, SOUTH, EAST, WEST, NONE;
	
	public static Direction getRandomDirection()
	{
		Direction[] directions = Direction.values();
		return directions[Game.random.nextInt(directions.length)];
	}

	public static Direction getOppositeDirection(Direction direction)
	{
		if(direction == NORTH)
		{
			return SOUTH;
		}
		if(direction == SOUTH)
		{
			return NORTH;
		}
		if(direction == EAST)
		{
			return WEST;
		}
		if(direction == WEST)
		{
			return EAST;
		}
		
		return NONE;
	}
}