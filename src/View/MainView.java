package View;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MainView extends JFrame {

    // The panel which will hold the view of the simulation and the simulation control buttons.
    private JPanel simulationPanel;
    // The view of the simulation
    private WorldPanel worldPanel;
    // The panel for the simulation control buttons
    private JPanel simulationControlButtonPanel;

    // The panel which holds all the simulation controls
    private JPanel simulationSettingsPanel;
    // The panel for the agent ratio and density controls
    private JPanel agentCreationControlPanel;

    // Simulation control buttons
    private JButton runNStepsButton;
    private JButton runOneStepButton;
    private JButton populateButton;
    private JButton clearBoardButton;

    // Agent ratio and density control spinners and their respective labels
    private JSpinner agent0RatioSpinner;
    private JSpinner agent1RatioSpinner;
    private JSpinner agentDensitySpinner;
    private JLabel agent0RatioLabel;
    private JLabel agent1RatioLabel;
    private JLabel agentDensityLabel;

    // Environment settings
    private JSpinner runNStepsSpinner;
    private JSpinner minFoodLevelSpinner;
    private JLabel runNStepsLabel;
    private JLabel minFoodLevelLabel;

    JLabel agent0DensityValue = new JLabel();
    JLabel agent1DensityValue = new JLabel();

    public MainView(int size_) {

        // Set the layout to border and to exit on close
        this.getContentPane().setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Step: 0");

        // Define the simulation panel
        this.simulationPanel = new JPanel(new GridBagLayout());
        // Define the world panel and its constrains and add them to the simulation panel
        GridBagConstraints constraintsWorldPanel = new GridBagConstraints();
        constraintsWorldPanel.gridy = 0;
        this.worldPanel = new WorldPanel(size_);
        this.simulationPanel.add(this.worldPanel, constraintsWorldPanel);
        // Define the simulation control buttons and set their text, then add them to the simulation control button panel
        this.runOneStepButton = new JButton("Run 1 Step");
        this.runNStepsButton = new JButton("Run N Steps");
        this.populateButton = new JButton("populate grid");
        this.clearBoardButton = new JButton("clear board");
        this.simulationControlButtonPanel = new JPanel(new GridLayout(2, 2));
        this.simulationControlButtonPanel.setPreferredSize(new Dimension(600, 40));
        GridBagConstraints constraintsSimulationButtonsPanel = new GridBagConstraints();
        constraintsSimulationButtonsPanel.gridy = 1;
        this.simulationControlButtonPanel.add(this.runOneStepButton);
        this.simulationControlButtonPanel.add(this.clearBoardButton);
        this.simulationControlButtonPanel.add(this.runNStepsButton);
        this.simulationControlButtonPanel.add(this.populateButton);
        this.simulationPanel.add(this.simulationControlButtonPanel, constraintsSimulationButtonsPanel);

        // Define the simulation control panel. This is the parent panel of all controls related to the simulation and environment settings.
        this.simulationSettingsPanel = new JPanel(new GridLayout(1, 1, 1, 1));
        // Define the agent creation control panel. This is the parent panel of all controls related to manually creating agents
        this.agentCreationControlPanel = new JPanel(new GridLayout(3, 4));

        // Define the environment control settings
        this.runNStepsSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 1000000, 1));
        this.runNStepsLabel = new JLabel("N:   ", SwingConstants.RIGHT);

        // Define the agent creation controls and add them to the agent creation control panel
        this.agent0RatioSpinner = new JSpinner(new SpinnerNumberModel(40, 0, 100, 1));
        this.agent1RatioSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 100, 1));
        this.agentDensitySpinner = new JSpinner(new SpinnerNumberModel(15, 0, 100, 1));
        this.agent0RatioLabel = new JLabel("Predator Ratio:   ", SwingConstants.RIGHT);
        this.agent1RatioLabel = new JLabel("Prey Ratio:   ", SwingConstants.RIGHT);
        this.agentDensityLabel = new JLabel("Density Percent:   ", SwingConstants.RIGHT);
        this.agentCreationControlPanel.add(this.runNStepsLabel);
        this.agentCreationControlPanel.add(this.runNStepsSpinner);
        this.agentCreationControlPanel.add(this.agent0RatioLabel);
        this.agentCreationControlPanel.add(this.agent0RatioSpinner);
        this.agentCreationControlPanel.add(new JLabel());
        this.agentCreationControlPanel.add(new JLabel());
        this.agentCreationControlPanel.add(this.agent1RatioLabel);
        this.agentCreationControlPanel.add(this.agent1RatioSpinner);

        // TODO - temporary - remove when full diagnostics window works
        JLabel agent1Density = new JLabel("Prey No. ");
        JLabel agent0Density = new JLabel("Predator No. ");
        JPanel miniDiagnosticsPanel0 = new JPanel(new GridLayout(1, 2));
        miniDiagnosticsPanel0.setPreferredSize(new Dimension(30, 20));
        JPanel miniDiagnosticsPanel1 = new JPanel(new GridLayout(1, 2));
        miniDiagnosticsPanel1.setPreferredSize(new Dimension(30, 20));
        miniDiagnosticsPanel0.add(agent0Density);
        miniDiagnosticsPanel0.add(this.agent0DensityValue);
        miniDiagnosticsPanel1.add(agent1Density);
        miniDiagnosticsPanel1.add(this.agent1DensityValue);
        this.agentCreationControlPanel.add(miniDiagnosticsPanel0);
        this.agentCreationControlPanel.add(miniDiagnosticsPanel1);

//        this.agentCreationControlPanel.add(new JPanel());
//        this.agentCreationControlPanel.add(new JPanel());
        this.agentCreationControlPanel.add(this.agentDensityLabel);
        this.agentCreationControlPanel.add(this.agentDensitySpinner);

        // Add the agent creation control panel and the environment control panel to the simulation control panel
        this.simulationSettingsPanel.add(this.agentCreationControlPanel);



        // Place the simulation view in the center of the border layout
        this.getContentPane().add(this.simulationPanel, BorderLayout.CENTER);
        // Place the simulation control panel at the bottom of the border layout, right under the simulation view
        this.getContentPane().add(this.simulationSettingsPanel, BorderLayout.SOUTH);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    public void updateWorldPanel(BufferedImage worldImage, int i) {
        this.worldPanel.updateWorldImage(worldImage);
        this.repaint();
        this.setTitle("Step: " + ((Integer) i).toString());
    }

    public JButton getRunOneStepButton() {
        return this.runOneStepButton;
    }

    public JButton getRunNStepsButton() { return this.runNStepsButton; }

    public JButton getPopulateButton() { return this.populateButton; }

    public JButton getClearBoardButton() { return this.clearBoardButton; }

    public JSpinner getAgent0Ratio() {
        return this.agent0RatioSpinner;
    }

    public JSpinner getAgent1Ratio() {
        return this.agent1RatioSpinner;
    }

    public JSpinner getAgentDensity() { return this.agentDensitySpinner; }

    public void setagent1DensityValue(int value) {
        this.agent1DensityValue.setText(((Integer) value).toString());
    }

    public void setagent0DensityValue(int value) {
        this.agent0DensityValue.setText(((Integer) value).toString());
    }

    public JSpinner getNSteps() {
        return this.runNStepsSpinner;
    }
}
