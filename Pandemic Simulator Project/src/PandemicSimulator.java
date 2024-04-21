/**
 * Program Name: Pandemic_Simulator.java
 * Purpose: Demostrate a pandemic simulation
 * Author: Robert Ren
 * Date: August 07, 2023
 */

import javax.swing.*;
import java.awt.*;   
import java.awt.event.*;
import javax.swing.event.*;
import java.util.ArrayList;

public class PandemicSimulator extends JPanel
{
    // Fields
    ArrayList<Person> personArray;

    // Constructor
    public PandemicSimulator()
    {
        personArray = new ArrayList<Person>();
        this.setPreferredSize(new Dimension(Const.WIDTH, Const.HEIGHT));
        this.setBackground(Color.WHITE);
    }

    // allows to control the panel from outside
    public void updaeComponent(ArrayList<Person> newValues)
    {
        personArray = newValues;
        repaint();
    }

    // OVER-RIDE the JPanel's paintComponent() method
    public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.setColor(Color.PINK);

		for(int i = 0; i < personArray.size(); i++)
		{
			// Get color and draw dot
			g.setColor(personArray.get(i).getColor());
			g.fillOval(personArray.get(i).getxCoord(), personArray.get(i).getyCoord(),  Const.IMG_DIM, Const.IMG_DIM);
		}
	}
}
