package mirrg.todo;

public class HMath
{

	public static int trim(int value, int min, int max)
	{
		return Math.min(Math.max(value, min), max);
	}

	public static long trim(long value, long min, long max)
	{
		return Math.min(Math.max(value, min), max);
	}

	public static double trim(double value, double min, double max)
	{
		return Math.min(Math.max(value, min), max);
	}

	public static float trim(float value, float min, float max)
	{
		return Math.min(Math.max(value, min), max);
	}

}
