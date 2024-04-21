/**
 * Program Name: Pandemic_Simulator.java
 * Purpose: Demostrate a pandemic simulation
 * Author: Robert Ren
 * Date: August 07, 2023
 */

import java.awt.*;

public class Person
{
    // Fields
    private int immunityLevel;
    private int currentStatus;
    private int cycleCounter;
    private Color color;

    private int timesInfected;
    private int timesRecovered;

    private int xCoord;
    private int yCoord;
    private int xIncrement;
	private int yIncrement;

    // Constructor
    public Person(int immunityLevel, int currentStatus)
    {
        this.timesInfected = 0;
        this.timesRecovered = 0;
        this.cycleCounter = 0;
        this.immunityLevel = immunityLevel;
        this.currentStatus = currentStatus;

        // Set color
        if(currentStatus == Const.NEVER_INFECTED)
        {
            if(immunityLevel == Const.UNVACCINATED)
                color = Color.MAGENTA;
            
            else if(immunityLevel == Const.ONE_SHOT)
                color = Color.YELLOW;
            
            else if(immunityLevel == Const.TWO_SHOT)
                color = Color.CYAN;

            else if(immunityLevel == Const.THREE_SHOT)
                color = Color.BLUE;

            else if(immunityLevel == Const.NATURAL_IMMUNITY)
                color = Color.GRAY;
        }
        else if(currentStatus == Const.INFECTED)
            color = Color.RED;

        else if (currentStatus == Const.RECOVERED)
            color = Color.GREEN;

        else if(currentStatus == Const.DEAD)
            color = Color.BLACK;

        // Generate random coords for X and Y
        xCoord = 0;
        while(xCoord == 0 || xCoord > Const.WIDTH - Const.IMG_DIM)
            xCoord = (int)(Math.random() * Const.WIDTH);
        
        yCoord = 0;
        while(yCoord == 0 || yCoord > Const.HEIGHT - Const.IMG_DIM)
            yCoord = (int)(Math.random() * Const.HEIGHT);

        setNewIncrements();
    }

    // Methods
    private boolean hasDied() 
	{
		boolean isDead = false;

		switch(immunityLevel)
		{
		case Const.UNVACCINATED:
			isDead = (int)(Math.random()*100) + 1 <= 10;
			break;

		case Const.ONE_SHOT:
			isDead = (int)(Math.random()*100) + 1 <= 7;
			break;

		case Const.TWO_SHOT:
			isDead = (int)(Math.random()*100) + 1 <= 3;
			break;

		case Const.THREE_SHOT:
			isDead = (int)(Math.random()*100) + 1 <= 1;
			break;

		case Const.NATURAL_IMMUNITY:
			isDead = (int)(Math.random()*100) + 1 <= 3;
			break;
		}

		return isDead;
	}

    public void setNewIncrements()
    {
        // Calculate movement
        xIncrement = 0;
        yIncrement = 0;
        while(xIncrement == 0 || yIncrement == 0)
        {
            xIncrement = (int)(Math.random() * 6);
			yIncrement = (int)(Math.random() * 6);

            if((int)(Math.random() * 100) > 50)
                xIncrement *= -1;

            if((int)(Math.random() * 100) > 50)
                yIncrement *= -1;
        }
    }

    public void move()
    {
        if(currentStatus == Const.DEAD)
            return;

        // Check X limits
        if(xCoord - Const.IMG_DIM <= 0 || xCoord >=  Const.WIDTH - Const.IMG_DIM)
            xIncrement = xIncrement * -1;

        // Check Y limits
        if(yCoord - Const.IMG_DIM <= 0 || yCoord >=  Const.HEIGHT - Const.IMG_DIM)
            yIncrement = yIncrement * -1;

        // Set position
        xCoord += xIncrement;
        yCoord += yIncrement;

        // Life cycles
        if(currentStatus == Const.INFECTED)
        {
            cycleCounter++;
            if(cycleCounter >= 150)
            {
                if(hasDied())
                {
                    currentStatus = Const.DEAD;
                    color = Color.BLACK;
                }
                else
                {
                    cycleCounter = 0;
                    timesRecovered++;
                    currentStatus = Const.RECOVERED;
                    color = Color.GREEN;

                    // SERVICE PACK
                    if(this.immunityLevel == Const.UNVACCINATED || this.immunityLevel == Const.ONE_SHOT)
                        this.immunityLevel = Const.NATURAL_IMMUNITY;
                }
            }
        }
    }

    public boolean isDead()
    {
        if(currentStatus == Const.DEAD)
            return true;
        
        return false;
    }

    public void checkCollision(Person anotherPerson)
    {
        // Check if persons are alive
        if(this.isDead() || anotherPerson.isDead())
            return;

        int deltaX = this.xCoord - anotherPerson.getxCoord();
        int deltaY = this.yCoord - anotherPerson.getyCoord();
        if(Math.sqrt(deltaX * deltaX + deltaY * deltaY) <= Const.IMG_DIM)
        {
            // People have touched!
            if(this.currentStatus == Const.INFECTED && anotherPerson.getCurrentStatus() == Const.INFECTED)
            {
                // Both of them are infected, nothig to do
            }
            else if(anotherPerson.getCurrentStatus() == Const.INFECTED)
                this.tryToInfect();
            
            else if(this.currentStatus == Const.INFECTED)
                anotherPerson.tryToInfect();

            // Modify increments
            this.setNewIncrements();
            anotherPerson.setNewIncrements();
        }
    }

    public void tryToInfect()
    {
        boolean isInfected = false;
        switch(immunityLevel)
		{
		case Const.UNVACCINATED:
			isInfected = (int)(Math.random()*100) + 1 <= 80;
			break;

		case Const.ONE_SHOT:
			isInfected = (int)(Math.random()*100) + 1 <= 60;
			break;

		case Const.TWO_SHOT:
			isInfected = (int)(Math.random()*100) + 1 <= 30;
			break;

		case Const.THREE_SHOT:
			isInfected = (int)(Math.random()*100) + 1 <= 10;
			break;

		case Const.NATURAL_IMMUNITY:
			isInfected = (int)(Math.random()*100) + 1 <= 40;
			break;
		}

        if(isInfected == true)
        {
            timesInfected++;
            currentStatus = Const.INFECTED;
            color = Color.RED;
        }
    }

    public void setInfection()
    {
        this.currentStatus = Const.INFECTED;
        this.color = Color.RED;
        timesInfected++;
    }

    // Getters
    public int getxCoord() { return xCoord; }
    public int getyCoord() { return yCoord; }
    public int getCurrentStatus() { return currentStatus; }
    public int getImmunityLevel() { return immunityLevel; }
    public int getTimesInfected() { return timesInfected; }
    public int getTimesRecovered() { return timesRecovered; }
    public Color getColor() { return color; }
}
