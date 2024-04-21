/**
 * Program Name: Pandemic_Simulator.java
 * Purpose: Demostrate a pandemic simulation
 * Author: Robert Ren
 * Date: August 07, 2023
 */
 
 // Constants
public class Const
{
    public static final int WIDTH = 700, HEIGHT = 600;    // Size of JPanel
	public static final int LAG_TIME = 50;               // 200 time in milliseconds between re-paints of screen
	public static final int IMG_DIM = 10;                  // 10 Size of ball to be drawn

    // Vaccination immunity level
    public static final int UNVACCINATED = 1;
	public static final int ONE_SHOT = 2;
	public static final int TWO_SHOT = 3;
	public static final int THREE_SHOT = 4;
	public static final int NATURAL_IMMUNITY = 5;

	// Health status
	public static final int NEVER_INFECTED = 0;
	public static final int INFECTED = 1;
	public static final int RECOVERED = 2;
	public static final int DEAD = 3;

	// Running days
	public static final double DAY = 21.4285714286;
	public static final int DAY_PERIOD = 21;
}
