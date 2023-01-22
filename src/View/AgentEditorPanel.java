package View;

import Model.AgentBuilder.AgentSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgentEditorPanel extends JPanel implements ActionListener {

    // The labels which tell the user which input controls which agent setting
    private JLabel lifespanLabel;
    private JLabel energyCapacityLabel;
    private JLabel eatAmountLabel;
    private JLabel visionRangeLabel;
    private JLabel movementRangeLabel;

    // The text field where the agents name is set
    private JTextField agentNameTextField;

    // The spinners which control the agents settings
    private JSpinner lifespanSpinner;
    private JSpinner energyCapacitySpinner;
    private JSpinner eatAmountSpinner;
    private JSpinner visionRangeSpinner;
    private JSpinner movementRangeSpinner;

    private JToggleButton isGrazerToggle;
    private JToggleButton isPredatorToggle;

    private JButton colourChooserButton;
    private Color currentColour;

    public AgentEditorPanel() {
        super();
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(310, 600));
        setBorder(BorderFactory.createLineBorder(Color.darkGray));

        // First all elements are defined
        agentNameTextField = new JTextField("Agent Name");
        agentNameTextField.setPreferredSize(new Dimension(150, 50));

        lifespanLabel = new JLabel("Lifespan: ");
        lifespanLabel.setPreferredSize(new Dimension(150, 50));

        energyCapacityLabel = new JLabel("Energy Capacity: ");
        energyCapacityLabel.setPreferredSize(new Dimension(150, 50));

        eatAmountLabel = new JLabel("Eat Amount: ");
        eatAmountLabel.setPreferredSize(new Dimension(150, 50));

        visionRangeLabel = new JLabel("Vision Range: ");
        visionRangeLabel.setPreferredSize(new Dimension(150, 50));

        movementRangeLabel = new JLabel("Movement Range: ");
        movementRangeLabel.setPreferredSize(new Dimension(150, 50));

        lifespanSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 1000000, 1));
        lifespanSpinner.setPreferredSize(new Dimension(150, 50));

        energyCapacitySpinner = new JSpinner(new SpinnerNumberModel(10, 0, 1000000, 1));
        energyCapacitySpinner.setPreferredSize(new Dimension(150, 50));

        eatAmountSpinner = new JSpinner(new SpinnerNumberModel(2, 0, 1000000, 1));
        eatAmountSpinner.setPreferredSize(new Dimension(150, 50));

        visionRangeSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 100, 1));
        visionRangeSpinner.setPreferredSize(new Dimension(150, 50));

        movementRangeSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 100, 1));
        movementRangeSpinner.setPreferredSize(new Dimension(150, 50));

        isGrazerToggle = new JToggleButton("Grazer");
        isGrazerToggle.setPreferredSize(new Dimension(300, 50));

        isPredatorToggle = new JToggleButton("Predator");
        isPredatorToggle.setPreferredSize(new Dimension(300, 50));

        // Set up colour chooser button
        colourChooserButton = new JButton();
        colourChooserButton.setPreferredSize(new Dimension(75, 75));
        colourChooserButton.addActionListener(this);
        currentColour = new Color(255, 255, 255);
        colourChooserButton.setBackground(currentColour);

        // The GridBag constraints we'll be using to build this panel
        GridBagConstraints c = new GridBagConstraints();
        // This tells the component it should only horizontally span 1 cell
        c.gridwidth = 1;
        c.insets = new Insets(1, 1, 1, 1);
        c.weightx = 1;
        c.weighty = 1;


        // First we add the top row components
        c.gridy = 0;

        c.gridx = 0;
        add(agentNameTextField, c);
        c.gridx = 1;
        add(colourChooserButton, c);

        // Now we add the second row components
        c.gridy = 1;

        c.gridx = 0;
        add(lifespanLabel, c);
        c.gridx = 1;
        add(lifespanSpinner, c);

        // Now we add the third row components
        c.gridy = 2;

        c.gridx = 0;
        add(energyCapacityLabel, c);
        c.gridx = 1;
        add(energyCapacitySpinner, c);

        // Now we add the forth row components
        c.gridy = 3;

        c.gridx = 0;
        add(eatAmountLabel, c);
        c.gridx = 1;
        add(eatAmountSpinner, c);

        // Now we add the fifth row components
        c.gridy = 4;

        c.gridx = 0;
        add(visionRangeLabel, c);
        c.gridx = 1;
        add(visionRangeSpinner, c);

        // Now we add the 6th row components
        c.gridy = 5;

        c.gridx = 0;
        add(movementRangeLabel, c);
        c.gridx = 1;
        add(movementRangeSpinner, c);

        // Now we add the seventh row components
        c.gridy = 6;

        c.gridwidth = 2;
        c.gridx = 0;
        add(isGrazerToggle, c);

        // Now we add the eighth row components
        c.gridy = 7;

        c.gridx = 0;
        add(isPredatorToggle, c);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(colourChooserButton)) {
            setCurrentColour();
        }
    }

    private void setCurrentColour() {
        currentColour = JColorChooser.showDialog(null, "Select Agent Colour", Color.white);
        if (currentColour != null) {
            colourChooserButton.setBackground(currentColour);
        }
    }

    public Color getCurrentColour() {
        return currentColour;
    }

    public JTextField getAgentNameTextField() {
        return agentNameTextField;
    }

    public JSpinner getLifespanSpinner() {
        return lifespanSpinner;
    }

    public JSpinner getEnergyCapacitySpinner() {
        return energyCapacitySpinner;
    }

    public JSpinner getEatAmountSpinner() {
        return eatAmountSpinner;
    }

    public JSpinner getVisionRangeSpinner() {
        return visionRangeSpinner;
    }

    public JSpinner getMovementRangeSpinner() {
        return movementRangeSpinner;
    }

    public JToggleButton getIsGrazerToggle() {
        return isGrazerToggle;
    }

    public JToggleButton getIsPredatorToggle() {
        return isPredatorToggle;
    }

    public JButton getColourChooserButton() {
        return colourChooserButton;
    }

    public void setAgent(AgentSettings agentSettings) {
        lifespanSpinner.setValue((int) agentSettings.getLifespan());
        energyCapacitySpinner.setValue((int) agentSettings.getEnergyCapacity());
        eatAmountSpinner.setValue((int) agentSettings.getEnergyAmount());
        visionRangeSpinner.setValue((int) agentSettings.getVisionRange());
        movementRangeSpinner.setValue((int) agentSettings.getMovementRange());
        colourChooserButton.setBackground(agentSettings.getColor());
    }

    public AgentSettings getAgentSettings() {
        //openAgent.getScores().getAge(), openAgent.getScores().getMAX_HUNGER(), openAgent.getAttributes().getEatAmount(), openAgent.getAttributes().getVision(), openAgent.getAttributes().getSpeed(), openAgent.getColor());
        return new AgentSettings((int) lifespanSpinner.getValue(), (int) energyCapacitySpinner.getValue(), (int) eatAmountSpinner.getValue(), (int) visionRangeSpinner.getValue(), (int) movementRangeSpinner.getValue(), colourChooserButton.getBackground());
    }
}
