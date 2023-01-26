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
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(600, 204));
        setBorder(BorderFactory.createLineBorder(Color.darkGray));

        // First all elements are defined and their attributes set
        runStepButton = new JButton("Run Step");
        runStepButton.setPreferredSize(new Dimension(600, 48));

        stopStartButton = new JButton("Stop / Start");
        stopStartButton.setPreferredSize(new Dimension(300, 48));

        clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(300, 48));

        replenishEnvironmentEnergyButton = new JButton("Replenish Environment Energy");
        replenishEnvironmentEnergyButton.setPreferredSize(new Dimension(600, 48));

        // Here we build the run n steps panel
        runNStepsButton = new JButton("Run N Steps: ");
        runNStepsButton.setPreferredSize(new Dimension(225, 48));

        runNStepsSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 1000000, 1));
        runNStepsSpinner.setPreferredSize(new Dimension(75, 48));

        runNStepsPanel = new JPanel(new GridBagLayout());
        runNStepsPanel.setPreferredSize(new Dimension(300, 48));

        // The GridBag constraints we'll be using to build our panels
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.weighty = 1;
        c.gridwidth = 3;
        runNStepsPanel.add(runNStepsButton);
        c.gridwidth = 1;
        runNStepsPanel.add(runNStepsSpinner);

        // Here we build the population panel
        populationDensitySpinner = new JSpinner(new SpinnerNumberModel(1, 0, 100, 1));
        populationDensitySpinner.setPreferredSize(new Dimension(75, 48));

        populateButton = new JButton("Populate");
        populateButton.setPreferredSize(new Dimension(225, 48));

        populatePanel = new JPanel(new GridBagLayout());
        populatePanel.setPreferredSize(new Dimension(300, 48));

        c.gridwidth = 3;
        populatePanel.add(populateButton);
        c.gridwidth = 1;
        populatePanel.add(populationDensitySpinner);

        // Now all elements are added to the panel
        // First we add the top row components
        c.gridwidth = 2;
        c.gridx = 0;
        add(runStepButton, c);

        // Now we add the second row components, these will be 1 per cell so we reset gridwidth to 1
        c.gridy = 1;
        c.gridwidth = 1;

        add(stopStartButton, c);

        c.gridx = 1;
        add(runNStepsPanel, c);

        // Now we add the third row components
        c.gridy = 2;

        c.gridx = 0;
        add(populatePanel, c);

        c.gridx = 1;
        add(clearButton, c);

        // Now we add the forth row components
        c.gridy = 3;

        c.gridwidth = 4;
        c.gridx = 0;
        add(replenishEnvironmentEnergyButton, c);
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
}
