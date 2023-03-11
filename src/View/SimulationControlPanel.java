package View;

import javax.swing.*;
import java.awt.*;

public class SimulationControlPanel extends JPanel {

    // The buttons which control the simulation
    private JButton runStepButton;
    private JButton stopStartButton;
    private JButton runNStepsButton;
    private JButton populateButton;
    private JButton clearButton;
    private JButton replenishEnvironmentEnergyButton;

    // The spinners which control the value of the settings
    private JSpinner runNStepsSpinner;
    private JSpinner populationDensitySpinner;

    // The panel where we group the run n steps button with its spinner
    private JPanel runNStepsPanel;

    // The panel where we group the populate button with the density spinner
    private JPanel populatePanel;

    public SimulationControlPanel() {
        super();
        setLayout(new GridLayout(1, 6));
        //setPreferredSize(new Dimension(600, 60));
        //setBorder(BorderFactory.createLineBorder(Color.darkGray));

        // First all elements are defined and their attributes set
        runStepButton = new JButton("Run Step");
        //runStepButton.setBackground(new Color(100, 150, 255));
        runStepButton.setFont(new Font("Dialog", Font.PLAIN, 16));

        stopStartButton = new JButton("Start");
        stopStartButton.setBackground(new Color(100, 220, 100));
        stopStartButton.setFont(new Font("Dialog", Font.PLAIN, 16));

        clearButton = new JButton("Clear");
        //clearButton.setBackground(new Color(255, 130, 130));
        clearButton.setFont(new Font("Dialog", Font.PLAIN, 16));

        replenishEnvironmentEnergyButton = new JButton("<html>Replenish<br>Energy</html>");
        //replenishEnvironmentEnergyButton.setBackground(new Color(255, 255, 150));
        replenishEnvironmentEnergyButton.setFont(new Font("Dialog", Font.PLAIN, 14));

        // Here we build the run n steps panel
        runNStepsButton = new JButton("Run For: ");
        //runNStepsButton.setBackground(new Color(200, 200, 200));
        runNStepsButton.setFont(new Font("Dialog", Font.BOLD, 12));

        runNStepsSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 1000000, 1));
        //runNStepsSpinner.setBackground(new Color(200, 200, 200));

        runNStepsPanel = new JPanel(new GridLayout(2, 0));

        runNStepsPanel.add(runNStepsButton);
        runNStepsPanel.add(runNStepsSpinner);

        // Here we build the population panel
        populateButton = new JButton("Populate");
        //populateButton.setBackground(new Color(200, 200, 200));
        populateButton.setFont(new Font("Dialog", Font.BOLD, 12));

        populationDensitySpinner = new JSpinner(new SpinnerNumberModel(1.0, 0, 100.0, 1));
        //populationDensitySpinner.setBackground(new Color(200, 200, 200));

        populatePanel = new JPanel(new GridLayout(2, 1));

        populatePanel.add(populateButton);
        populatePanel.add(populationDensitySpinner);

        add(runStepButton);
        add(stopStartButton);
        add(populatePanel);
        add(runNStepsPanel);
        add(replenishEnvironmentEnergyButton);
        add(clearButton);
    }

    public JButton getRunStepButton() {
        return this.runStepButton;
    }

    public JButton getRunNStepsButton() { return this.runNStepsButton; }

    public JSpinner getRunNStepsSpinner() { return this.runNStepsSpinner; }

    public JButton getPopulateButton() { return this.populateButton; }

    public JButton getClearButton() { return this.clearButton; }

    public JButton getReplenishEnvironmentEnergyButton() {
        return replenishEnvironmentEnergyButton;
    }

    public JSpinner getPopulationDensitySpinner() {
        return populationDensitySpinner;
    }

    public JButton getStopStartButton() {
        return stopStartButton;
    }

}
