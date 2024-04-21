/**
 * Program Name: Pandemic_Simulator.java
 * Purpose: Demostrate a pandemic simulation
 * Author: Robert Ren
 * Date: August 07, 2023
 */
 
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JOptionPane;
import javax.swing.event.*;

import java.util.ArrayList;

public class Menu extends JFrame implements ActionListener, ChangeListener
{
    // Entry point
    public static void main(String[] args)
    {
        new Menu();
    }

    // Fields for inputs
    JTextField txtTotalPopulation;
    JLabel lblUnvaccinated;
    JLabel lblOneShot;
    JLabel lblTwoShot;
    JLabel lblThreeShot;
    JLabel lblNaturalImmunity;
    JSlider sldUnvaccinated;
    JSlider sldOneShot;
    JSlider sldTwoShot;
    JSlider sldThreeShot;
    JSlider sldNaturalImmunity;
    int totalPopulation;
    int unvaccinated;
    int oneShot;
    int twoShot;
    int threeShot;
    int naturalImmunity;

    // Action buttons
    JButton btnClear;
    JButton btnStart;
    JButton btnPause;
    JButton btnResume;
    JButton btnCancel;

    // Fields for simulatios
    ArrayList<Person> personArray;
    PandemicSimulator screen;
    Timer time;
    int currentDay;
    int rePaints;

    // Fields for real-time statistics
    JLabel lblCurrentDay;
    JLabel lblInfectedPeople;
    JLabel lblInfectedNever;
    JLabel lblInfectedUnvaccinated;
    JLabel lblInfectedOneShot;
    JLabel lblInfectedTwoShot;
    JLabel lblInfectedThreeShot;
    JLabel lblInfectedNaturalImmunity;
    JLabel lblReInfectedNaturalImmunity;
    JLabel lblInfectedRecovered;
    JLabel lblInfectedDied;

    // Constructor
    public Menu()
    {
        super("Team Sagacity Pandemic Simulation App");
        // this.setSize(505, 300);
        // this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Build menu bar
        this.setJMenuBar(this.buildMenu());

        // Set application options
        this.add(buildApplicationOptions(), BorderLayout.WEST);

        // Set simulator
        this.add(buildMainScreen(), BorderLayout.CENTER);

        // Set real-time statistics
        this.add(buildRealTimeStatistics(), BorderLayout.EAST);

        // Display option buttons
        this.displayControlButtons(true);

        // Display application
        this.pack();
        this.setVisible(true);
    }

    // Builds menu
    private JMenuBar buildMenu()
    {
        // Help
        JMenu mnuHelp = new JMenu("Help");
        JMenuItem mnuHelp_About = new JMenuItem("About");
        mnuHelp_About.setActionCommand("MNU_About");
        mnuHelp_About.addActionListener(this);
        mnuHelp.add(mnuHelp_About);

        // Add elements to the menu to return it
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(mnuHelp);
        return menuBar;
    }

    // Build application options
    private JPanel buildApplicationOptions()
    {
        // Labels
        JLabel lblTotalPopulation = new JLabel("Total population");

        // Inputs
        txtTotalPopulation = new JTextField();

        sldUnvaccinated = new JSlider(JSlider.HORIZONTAL,0,100,0);
		sldUnvaccinated.setMajorTickSpacing(10);
		sldUnvaccinated.setMinorTickSpacing(10);
		sldUnvaccinated.setPaintTicks(true);
		sldUnvaccinated.setPaintLabels(true);
        sldUnvaccinated.addChangeListener(this);

        sldOneShot = new JSlider(JSlider.HORIZONTAL,0,100,0);
		sldOneShot.setMajorTickSpacing(10);
		sldOneShot.setMinorTickSpacing(10);
		sldOneShot.setPaintTicks(true);
		sldOneShot.setPaintLabels(true);
        sldOneShot.addChangeListener(this);

        sldTwoShot = new JSlider(JSlider.HORIZONTAL,0,100,0);
		sldTwoShot.setMajorTickSpacing(10);
		sldTwoShot.setMinorTickSpacing(10);
		sldTwoShot.setPaintTicks(true);
		sldTwoShot.setPaintLabels(true);
        sldTwoShot.addChangeListener(this);

        sldThreeShot = new JSlider(JSlider.HORIZONTAL,0,100,0);
		sldThreeShot.setMajorTickSpacing(10);
		sldThreeShot.setMinorTickSpacing(10);
		sldThreeShot.setPaintTicks(true);
		sldThreeShot.setPaintLabels(true);
        sldThreeShot.addChangeListener(this);

        sldNaturalImmunity = new JSlider(JSlider.HORIZONTAL,0,100,0);
		sldNaturalImmunity.setMajorTickSpacing(10);
		sldNaturalImmunity.setMinorTickSpacing(10);
		sldNaturalImmunity.setPaintTicks(true);
		sldNaturalImmunity.setPaintLabels(true);
        sldNaturalImmunity.addChangeListener(this);

        // Action buttons
        btnClear = new JButton("Clear");
        btnClear.setActionCommand("BTN_Clear");
        btnClear.addActionListener(this);

        btnStart = new JButton("Start");
        btnStart.setActionCommand("BTN_Start");
        btnStart.addActionListener(this);

        // Reset all values
        this.clearStartingValues();

        // Set population in a single panel
        JPanel pnlPopulation = new JPanel();
        pnlPopulation.setLayout(new GridLayout(1,2));
        pnlPopulation.add(lblTotalPopulation);
        pnlPopulation.add(txtTotalPopulation);

        // Set slider options in a single panel
        JPanel pnlOptions = new JPanel();
        pnlOptions.setLayout(new GridLayout(10,1));
        pnlOptions.add(lblUnvaccinated);
        pnlOptions.add(sldUnvaccinated);
        pnlOptions.add(lblOneShot);
        pnlOptions.add(sldOneShot);
        pnlOptions.add(lblTwoShot);
        pnlOptions.add(sldTwoShot);
        pnlOptions.add(lblThreeShot);
        pnlOptions.add(sldThreeShot);
        pnlOptions.add(lblNaturalImmunity);
        pnlOptions.add(sldNaturalImmunity);

        // Set buttons in a single panel
        JPanel pnlButtons = new JPanel();
        pnlButtons.setLayout(new GridLayout(1,2));
        pnlButtons.add(btnClear);
        pnlButtons.add(btnStart);

        // Put all items toghether
        JPanel subContainer = new JPanel();
        subContainer.setLayout(new BorderLayout());
        subContainer.add(pnlPopulation, BorderLayout.NORTH);
        subContainer.add(pnlOptions, BorderLayout.CENTER);
        subContainer.add(pnlButtons, BorderLayout.SOUTH);

        JPanel result = new JPanel();
        result.add(subContainer);
        return result;
    }

    // Build main screen for sumulations
    private JPanel buildMainScreen()
    {
        JPanel result = new JPanel();
        result.setPreferredSize(new Dimension(Const.WIDTH, Const.HEIGHT));
        screen = new PandemicSimulator();
        result.add(screen);
        return result;
    }

    // Build real time statistics
    private JPanel buildRealTimeStatistics()
    {
        // Labels
        lblCurrentDay = new JLabel("Day [Hasn't started yet]");
        lblInfectedNever = new JLabel("Never infected: 0");
        lblInfectedPeople = new JLabel("Infected people: 0");
        lblInfectedUnvaccinated = new JLabel("Non-vaccinated: 0");
        lblInfectedOneShot = new JLabel("One-shot-vaccinated: 0");
        lblInfectedTwoShot = new JLabel("Two-shot-vaccinated: 0");
        lblInfectedThreeShot = new JLabel("Three-shot-vaccinated: 0");
        lblInfectedNaturalImmunity = new JLabel("Naturally immunity: 0");
        lblReInfectedNaturalImmunity = new JLabel("Naturally immunity re-infected: 0");
        lblInfectedRecovered = new JLabel("People who have recovered: 0");
        lblInfectedDied = new JLabel("People who have died: 0");

        // Action buttons
        btnPause = new JButton("Pause");
        btnPause.setActionCommand("BTN_Pause");
        btnPause.addActionListener(this);

        btnResume = new JButton("Resume");
        btnResume.setActionCommand("BTN_Resume");
        btnResume.addActionListener(this);

        btnCancel = new JButton("Cancel simulation");
        btnCancel.setActionCommand("BTN_Cancel");
        btnCancel.addActionListener(this);

        // Set buttons in a single panel
        JPanel pnlSubButtons = new JPanel();
        pnlSubButtons.setLayout(new GridLayout(1,2));
        pnlSubButtons.add(btnPause);
        pnlSubButtons.add(btnResume);

        JPanel pnlButtons = new JPanel();
        pnlButtons.setLayout(new GridLayout(2,1));
        pnlButtons.add(pnlSubButtons);
        pnlButtons.add(btnCancel);


        // Set labels in a single panel
        JPanel pnlStatistics = new JPanel();
        pnlStatistics.setLayout(new GridLayout(12,1));
        pnlStatistics.add(lblCurrentDay);
        pnlStatistics.add(lblInfectedNever);
        pnlStatistics.add(lblInfectedPeople);
        pnlStatistics.add(lblInfectedUnvaccinated);
        pnlStatistics.add(lblInfectedOneShot);
        pnlStatistics.add(lblInfectedTwoShot);
        pnlStatistics.add(lblInfectedThreeShot);
        pnlStatistics.add(lblInfectedNaturalImmunity);
        pnlStatistics.add(lblReInfectedNaturalImmunity);
        pnlStatistics.add(lblInfectedRecovered);
        pnlStatistics.add(lblInfectedDied);

        // Put all items toghether
        JPanel subContainer = new JPanel();
        subContainer.setLayout(new BorderLayout());
        subContainer.add(pnlStatistics, BorderLayout.CENTER);
        subContainer.add(pnlButtons, BorderLayout.SOUTH);

        JPanel result = new JPanel();
        result.add(subContainer);
        return result;
    }

    // Clear starting values
    private void clearStartingValues()
    {

        this.txtTotalPopulation.setText("");
        this.sldUnvaccinated.setValue(0);
        this.sldOneShot.setValue(0);
        this.sldTwoShot.setValue(0);
        this.sldThreeShot.setValue(0);
        this.sldNaturalImmunity.setValue(0);

        lblUnvaccinated = new JLabel("Unvaccinated: " + sldUnvaccinated.getValue() + " %");
        lblOneShot = new JLabel("One shot vaccinated: " + sldOneShot.getValue() + " %");
        lblTwoShot = new JLabel("Two shot vaccinated: " + sldTwoShot.getValue() + " %");
        lblThreeShot = new JLabel("Three shot vaccinated: " + sldThreeShot.getValue() + " %");
        lblNaturalImmunity = new JLabel("Natural immunity: " + sldNaturalImmunity.getValue() + " %");
    }

    // Hide and show control buttons
    private void displayControlButtons(boolean value)
    {
        // Hide and show buttons
        btnClear.setVisible(value);
        btnStart.setVisible(value);

        btnPause.setVisible(!value);
        btnResume.setVisible(!value);
        btnCancel.setVisible(!value);
    }

    // Execute simulation method
    private void executeSimulation()
    {
        rePaints = 0;
        currentDay = 0;
        // Validate population entered and set stating values
        totalPopulation = -1;
        try
        {
            totalPopulation = Integer.parseInt(txtTotalPopulation.getText().trim());
            if(totalPopulation < 0)
                JOptionPane.showMessageDialog(null, "Enter a population greater than zero.\nCurrent value: \""
                    + txtTotalPopulation.getText() + "\"", "Invalid population value", JOptionPane.WARNING_MESSAGE);
        }
        catch (NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(null, "Total population must be a valid integer number\nCurrent value: \""
                + txtTotalPopulation.getText() + "\"", "Invalid population value", JOptionPane.WARNING_MESSAGE);
        }
        if (totalPopulation < 0)
        {
            txtTotalPopulation.setText("");
            return;
        }

        // Validate percentages
        int percentage = sldUnvaccinated.getValue() + sldOneShot.getValue() + sldTwoShot.getValue() + sldThreeShot.getValue() + sldNaturalImmunity.getValue();
        if(percentage != 100)
        {
            JOptionPane.showMessageDialog(null, "Percentajes sum must match 100%\nCurrent value: "
                + percentage + " %", "Invalid percentaje value", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Calculate number of persons for each percentage
        unvaccinated = (int)((double)sldUnvaccinated.getValue() / 100.0 * totalPopulation);
        oneShot = (int)((double)sldOneShot.getValue() / 100.0 * totalPopulation);
        twoShot = (int)((double)sldTwoShot.getValue() / 100.0 * totalPopulation);
        threeShot = (int)((double)sldThreeShot.getValue() / 100.0 * totalPopulation);
        naturalImmunity = (int)((double)sldNaturalImmunity.getValue() / 100.0 * totalPopulation);
        int totalCalculated = unvaccinated + oneShot + twoShot + threeShot + naturalImmunity;

        // Validate missing value due to fractions
        if(totalCalculated < totalPopulation)
        {
            // Cause of decimal fractions, assign missing value to the first percentage found
            if(unvaccinated != 0)
                unvaccinated += totalPopulation - totalCalculated;
            else if(oneShot != 0)
                oneShot += totalPopulation - totalCalculated;
            else if(twoShot != 0)
                twoShot += totalPopulation - totalCalculated;
            else if(threeShot != 0)
                threeShot += totalPopulation - totalCalculated;
            else if(naturalImmunity != 0)
                naturalImmunity += totalPopulation - totalCalculated;
        }
        else if(totalCalculated > totalPopulation)
        {
            // Cause of decimal fractions, subtract extra value to the first percentage found
            if(unvaccinated != 0)
                unvaccinated -= totalCalculated - totalPopulation;
            else if(oneShot != 0)
                oneShot -= totalCalculated - totalPopulation;
            else if(twoShot != 0)
                twoShot -= totalCalculated - totalPopulation;
            else if(threeShot != 0)
                threeShot -= totalCalculated - totalPopulation;
            else if(naturalImmunity != 0)
                naturalImmunity -= totalCalculated - totalPopulation;
        }

        // Populate person's array and add one patient zero
        personArray = new ArrayList<Person>();
        for(int i = 0; i < unvaccinated; i++)
            personArray.add(new Person(Const.UNVACCINATED, Const.NEVER_INFECTED));
        
        for(int i = 0; i < oneShot; i++)
            personArray.add(new Person(Const.ONE_SHOT, Const.NEVER_INFECTED));

        for(int i = 0; i < twoShot; i++)
            personArray.add(new Person(Const.TWO_SHOT, Const.NEVER_INFECTED));
        
        for(int i = 0; i < threeShot; i++)
            personArray.add(new Person(Const.THREE_SHOT, Const.NEVER_INFECTED));
        
        for(int i = 0; i < naturalImmunity; i++)
            personArray.add(new Person(Const.NATURAL_IMMUNITY, Const.NEVER_INFECTED));

        // Infect the first person (It's not personal, just bad luck buddy)
        personArray.get(0).setInfection();

        // Hide and show buttons
        this.displayControlButtons(false);
        
        time = new Timer(Const.LAG_TIME, this);
        time.setActionCommand("TMR_Tick");
        time.start();
    }

    // Move people method
    private void timerTick()
    {
        // Calculate current day
        rePaints++;
        currentDay = (int)((double)rePaints / Const.DAY) + 1;
        if(currentDay > Const.DAY_PERIOD)
        {
            this.finishSimulation();
            return;
        }
        lblCurrentDay.setText("Day: " + currentDay);

        // Statistics
        int infectedPeople = 0;
        int infectedUnvaccinated = 0;
        int infectedOneShot = 0;
        int infectedTwoShot = 0;
        int infectedThreeShot = 0;
        int infectedNaturalImmunity = 0;
        int infectedRecovered = 0;
        int infectedDied = 0;
        int neverInfected = 0;
        int reInfectedNaturalImmunity = 0;

        // Move people and check for collisions
        for(int i = 0; i < personArray.size(); i++)
		{
            personArray.get(i).move();

            // Check collisions
			for(int j= i + 1;j < personArray.size(); j++) 
				personArray.get(i).checkCollision(personArray.get(j));

            // Get statistics for real time
            if(personArray.get(i).getCurrentStatus() == Const.NEVER_INFECTED)
            {
                neverInfected++;
            }
            else if(personArray.get(i).getCurrentStatus() == Const.INFECTED)
            {
                infectedPeople++;

                if(personArray.get(i).getImmunityLevel() == Const.UNVACCINATED)
                    infectedUnvaccinated++;

                else if(personArray.get(i).getImmunityLevel() == Const.ONE_SHOT)
                    infectedOneShot++;

                else if(personArray.get(i).getImmunityLevel() == Const.TWO_SHOT)
                    infectedTwoShot++;
                
                else if(personArray.get(i).getImmunityLevel() == Const.THREE_SHOT)
                    infectedThreeShot++;

                else if(personArray.get(i).getImmunityLevel() == Const.NATURAL_IMMUNITY)
                {
                    infectedNaturalImmunity++;
                    if(personArray.get(i).getTimesInfected() > 1)
                        reInfectedNaturalImmunity++;
                }
            }
            else if(personArray.get(i).getCurrentStatus() == Const.RECOVERED)
            {
                infectedRecovered++;
            }
            else if(personArray.get(i).getCurrentStatus() == Const.DEAD)
            {
                infectedDied++;
            }
		}

        // Print statistics
        lblInfectedPeople.setText("Infected people: " + infectedPeople);
        lblInfectedNever.setText("Never infected: " + neverInfected);
        lblInfectedUnvaccinated.setText("Non-vaccinated: " + infectedUnvaccinated);
        lblInfectedOneShot.setText("One-shot-vaccinated: " + infectedOneShot);
        lblInfectedTwoShot.setText("Two-shot-vaccinated: " + infectedTwoShot);
        lblInfectedThreeShot.setText("Three-shot-vaccinated: " + infectedThreeShot);
        lblInfectedNaturalImmunity.setText("Naturally immunity: " + infectedNaturalImmunity);
        lblReInfectedNaturalImmunity.setText("Naturally immunity re-infected: " + reInfectedNaturalImmunity);
        lblInfectedRecovered.setText("People who have recovered: " + infectedRecovered);
        lblInfectedDied.setText("People who have died: " + infectedDied);

        // Upadte screen view
        screen.updaeComponent(personArray);
    }

    // Finish simulation
    private void finishSimulation()
    {
        this.time.stop();
        this.displayControlButtons(true);

        // Variables for statistics
        int neverInfected = 0;
        int contractedDesease = 0;
        int infectedUnvaccinated = 0;
        int infectedOneShot = 0;
        int infectedTwoShot = 0;
        int infectedThreeShot = 0;
        int reInfectedNaturalImmunity = 0;
        int infectedRecovered = 0;

        int totalDead = 0;
        int deadUnvaccinated = 0;
        int deadOneShot = 0;
        int deadTwoShot = 0;
        int deadThreeShot = 0;
        int deadNaturalImmunity  = 0;

        // Get final statistics
        for(Person p : personArray)
        {
            if(p.getCurrentStatus() == Const.NEVER_INFECTED)
            {
                neverInfected++;
            }

            if(p.getCurrentStatus() != Const.NEVER_INFECTED)
            {
                contractedDesease++;
            }
            
            if(p.getImmunityLevel() == Const.UNVACCINATED && p.getTimesInfected() > 0)
            {
                infectedUnvaccinated++;
            }
            else if(p.getImmunityLevel() == Const.ONE_SHOT && p.getTimesInfected() > 0)
            {
                infectedOneShot++;
            }
            else if(p.getImmunityLevel() == Const.TWO_SHOT && p.getTimesInfected() > 0)
            {
                infectedTwoShot++;
            }
            else if(p.getImmunityLevel() == Const.THREE_SHOT && p.getTimesInfected() > 0)
            {
                infectedThreeShot++;
            }
            else if(p.getImmunityLevel() == Const.NATURAL_IMMUNITY && p.getTimesInfected() > 1)
            {
                reInfectedNaturalImmunity++;
            }

            if(p.getTimesRecovered() > 0)
            {
                infectedRecovered++;
            }

            // Broken down statistics for dead people
            if(p.getCurrentStatus() == Const.DEAD)
            {
                totalDead++;

                if(p.getImmunityLevel() == Const.UNVACCINATED)
                {
                    deadUnvaccinated++;
                }
                else if(p.getImmunityLevel() == Const.ONE_SHOT)
                {
                    deadOneShot++;
                }
                else if(p.getImmunityLevel() == Const.TWO_SHOT)
                {
                    deadTwoShot++;
                }
                else if(p.getImmunityLevel() == Const.THREE_SHOT)
                {
                    deadThreeShot++;
                }
                else if(p.getImmunityLevel() == Const.NATURAL_IMMUNITY)
                {
                    deadNaturalImmunity++;
                }
            }
        }

        // Do the maths to get finl percentages

        // 0) Percentage od people never infected
        double statistic0 = totalPopulation > 0 ? (double)neverInfected / (double)totalPopulation : 0.0;

        // 1) Percentage of the total population that contracted the disease.
        double statistic1 = totalPopulation > 0 ? (double)contractedDesease / (double)totalPopulation : 0.0;

        // 2) Percentage of unvaccinated persons who contracted the disease.
        double statistic2 = unvaccinated > 0 ? (double)infectedUnvaccinated / (double)unvaccinated : 0.0;

        // 3) Percentage of one-shot-vaccinated persons who contracted the disease.
        double statistic3 = oneShot > 0 ? (double)infectedOneShot / (double)oneShot : 0.0;

        // 4) Percentage of two-shot-vaccinated persons who contracted the disease.
        double statistic4 = twoShot > 0 ? (double)infectedTwoShot / (double)twoShot : 0.0;

        // 5) Percentage of three-shot-vaccinated persons who contracted the disease.
        double statistic5 = threeShot > 0 ? (double)infectedThreeShot / (double)threeShot : 0.0;

        // 6) Percentage of those naturally immune persons who got re-infected.
        double statistic6 = naturalImmunity > 0? (double)reInfectedNaturalImmunity / (double)naturalImmunity : 0.0;

        // 7) Percentage of all those who contracted the disease that recovered.
        double statistic7 = totalPopulation > 0 ? (double)infectedRecovered / (double)totalPopulation : 0.0;

        // 8a) Dead by unvaccinated
        double statistic8a = 0.0;
        double statistic8b = 0.0;
        double statistic8c = 0.0;
        double statistic8d = 0.0;
        double statistic8e = 0.0;
        if(totalDead > 0)
        {
            statistic8a = (double)deadUnvaccinated / (double)totalDead;
            statistic8b = (double)deadOneShot / (double)totalDead;
            statistic8c = (double)deadTwoShot / (double)totalDead;
            statistic8d = (double)deadThreeShot / (double)totalDead;
            statistic8e = (double)deadNaturalImmunity / (double)totalDead;
        }

        String finalReport = "Statistics for simulation:\n\n"

            + "Percentage of people never infected:\n"
            + (statistic0 * 100) + "%  - [" + neverInfected + " out of " + totalPopulation + "]\n\n"

            + "Percentage of the total population that contracted the disease:\n"
            + (statistic1 * 100) + "%  - [" + contractedDesease + " out of " + totalPopulation + "]\n\n"

            + "Percentage of unvaccinated persons who contracted the disease:\n"
            + (statistic2 * 100) + "%  - [" + infectedUnvaccinated + " out of " + unvaccinated + "]\n\n"

            + "Percentage of one-shot-vaccinated persons who contracted the disease:\n"
            + (statistic3 * 100) + "%  - [" + infectedOneShot + " out of " + oneShot + "]\n\n"

            + "Percentage of two-shot-vaccinated persons who contracted the disease:\n"
            + (statistic4 * 100) + "%  - [" + infectedTwoShot + " out of " + twoShot + "]\n\n"

            + "Percentage of three-shot-vaccinated persons who contracted the disease:\n"
            + (statistic5 * 100) + "%  - [" + infectedThreeShot + " out of " + threeShot + "]\n\n"

            + "Percentage of those naturally immune persons who got re-infected:\n"
            + (statistic6 * 100) + "%  - [" + reInfectedNaturalImmunity + " out of " + naturalImmunity + "]\n\n"

            + "Percentage of all those who contracted the disease that recovered:\n"
            + (statistic7 * 100) + "%  - [" + infectedRecovered + " out of " + totalPopulation + "]\n\n"

            + "Death Rate Percentage of all those who contracted the disease that died\n"
            + "Broken down by their immunity status:\n"
            + "Total dead: " + totalDead + "\n"
            + "Unvaccinated: " + (statistic8a * 100) + "%  - [" + deadUnvaccinated + "]\n"
            + "One-shot-vaccinated: " + (statistic8b * 100) + "%  - [" + deadOneShot + "]\n"
            + "Two-shot-vaccinated: " + (statistic8c * 100) + "%  - [" + deadTwoShot + "]\n"
            + "Three-shot-vaccinated: " + (statistic8d * 100) + "%  - [" + deadThreeShot + "]\n"
            + "Naturally immune: " + (statistic8e * 100) + "%  - [" + deadNaturalImmunity + "]"

            ;

        JOptionPane.showMessageDialog(null, finalReport, "Summary", JOptionPane.INFORMATION_MESSAGE);
    }

    // Cancel simulation
    private void cancelSimulation()
    {
        this.time.stop();
        this.displayControlButtons(true);
        JOptionPane.showMessageDialog(null, "Simulation process was cancelled", "Simulation", JOptionPane.WARNING_MESSAGE);
    }

    // Listener method for button events
    public void actionPerformed(ActionEvent e)
    {
        // Validate action command for menu
        if(e.getActionCommand().equals("MNU_About"))
            JOptionPane.showMessageDialog(null, "Aaron Gee\nRobert Ren\nUlises Carvajal", "Project group", JOptionPane.INFORMATION_MESSAGE);

        // Validate action command for clear options button
        else if(e.getActionCommand().equals("BTN_Clear"))
            this.clearStartingValues();

        // Validate action command for start button
        else if(e.getActionCommand().equals("BTN_Start"))
            this.executeSimulation();

        // Validate action command for timer
        else if(e.getActionCommand().equals("TMR_Tick"))
            this.timerTick();

        // Validate pause, resume and cancel buttons
        else if(e.getActionCommand().equals("BTN_Pause"))
            this.time.stop();

        else if(e.getActionCommand().equals("BTN_Resume"))
            this.time.start();

        else if(e.getActionCommand().equals("BTN_Cancel"))
            this.cancelSimulation();
    }

    // Listener method for slider changes
    public void stateChanged(ChangeEvent e)
	{
        if(e.getSource().equals(sldUnvaccinated))
            lblUnvaccinated.setText("Unvaccinated: " + sldUnvaccinated.getValue() + " %");

        else if(e.getSource().equals(sldOneShot))
            lblOneShot.setText("One shot vaccinated: " + sldOneShot.getValue() + " %");

        else if(e.getSource().equals(sldTwoShot))
            lblTwoShot.setText("Two shot vaccinated: " + sldTwoShot.getValue() + " %");

        else if(e.getSource().equals(sldThreeShot))
            lblThreeShot.setText("Three shot vaccinated: " + sldThreeShot.getValue() + " %");

        else if(e.getSource().equals(sldNaturalImmunity))
            lblNaturalImmunity.setText("Natural immunity: " + sldNaturalImmunity.getValue() + " %");
	}
}
