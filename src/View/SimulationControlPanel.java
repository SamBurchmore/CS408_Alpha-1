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

    // The spinners which control the value of the settings
    private JSpinner runNStepsSpinner;

    // The panel where we group the run n steps button with its spinner
    private JPanel runNStepsPanel;


    public SimulationControlPanel() {
        super();
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(600, 100));

        // First all elements are defined and their attributes set
        runStepButton = new JButton("Run Step");
        runStepButton.setPreferredSize(new Dimension(600, 30));

        stopStartButton = new JButton("Stop / Start");
        stopStartButton.setPreferredSize(new Dimension(300, 30));

        populateButton = new JButton("Populate");
        populateButton.setPreferredSize(new Dimension(300, 30));

        clearButton = new JButton("Clear");
        clearButton.setPreferredSize(new Dimension(300, 30));

        // Here we build the run n steps panel
        runNStepsButton = new JButton("Run N Steps: ");
        runNStepsButton.setPreferredSize(new Dimension(150, 30));

        runNStepsSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 1000000, 1));
        runNStepsSpinner.setPreferredSize(new Dimension(150, 30));

        runNStepsPanel = new JPanel(new GridLayout(1, 2));
        runNStepsPanel.setPreferredSize(new Dimension(300, 30));

        runNStepsPanel.add(runNStepsButton);
        runNStepsPanel.add(runNStepsSpinner);

        // The GridBag constraints we'll be using to build this panel
        GridBagConstraints c = new GridBagConstraints();
        // This forces components to be fill their container horizontally, we want this for every component
        c.fill = GridBagConstraints.HORIZONTAL;

        // Now all elements are added to the panel
        // First we add the top row components
        c.gridwidth = 2;
        c.gridx = 0;
        this.add(runStepButton, c);

        // Now we add the second row components, these will be 1 per cell so we reset gridwidth to 1
        c.gridy = 1;
        c.gridwidth = 1;

        this.add(stopStartButton, c);

        c.gridx = 1;
        this.add(runNStepsPanel, c);

        // Now we add the third row components
        c.gridy = 2;

        c.gridx = 0;
        this.add(populateButton, c);

        c.gridx = 1;
        this.add(clearButton, c);
    }

    public JButton getRunStepButton() {
        return this.runStepButton;
    }

    public JButton getRunNStepsButton() { return this.runNStepsButton; }

    public JSpinner getRunNStepsSpinner() { return this.runNStepsSpinner; }

    public JButton getPopulateButton() { return this.populateButton; }

    public JButton getClearButton() { return this.clearButton; }

}
