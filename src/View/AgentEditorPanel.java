package View;

import Model.AgentEditor.AgentSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AgentEditorPanel extends JPanel implements ActionListener {

    // The text field where the agents name is set
    private JTextField agentNameTextField;

    // The labels for the agent attributes settings
    private JLabel visionRangeLabel; // How far the agent can see : int 0-25
    private JLabel movementRangeLabel; // How far the agent can move in one turn : int 0-25
    private JLabel sizeLabel; // How much energy the agent uses and gives when predated on : int 0-255
    private JLabel energyCapacityLabel; // Maximum amount of energy agent can store : int 0 - 999999
    private JLabel eatAmountLabel; // How much energy agent gets from eating : int 0 - 999999
    private JLabel lifespanLabel; // How long the agent can live for : int 0 - 999999
    private JLabel creationAgeLabel; // How old an agent needs to be to breed : int 0 - 999999
    private JLabel creationAmountLabel; // How many agents are created when the agent breeds : int 0 - 8
    private JLabel creationDelayLabel; // How many turns an agent needs to wait before breeding again : int 0 - 999999
    private JLabel agentCodeLabel;

    // The spinners which control the agents settings
    private JSpinner visionRangeSpinner;
    private JSpinner movementRangeSpinner;
    private JSpinner sizeSpinner;
    private JSpinner energyCapacitySpinner;
    private JSpinner eatAmountSpinner;
    private JSpinner lifespanSpinner;
    private JSpinner creationAgeSpinner;
    private JSpinner creationAmountSpinner;
    private JSpinner creationDelaySpinner;
    private JSpinner agentCodeSpinner;

    private JToggleButton isGrazerToggle;
    private JToggleButton isPredatorToggle;

    private JButton colourChooserButton;
    private Color currentColour;

    private JButton updateSettingsButton;

    public AgentEditorPanel() {
        super();
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(310, 650));
        setBorder(BorderFactory.createLineBorder(Color.darkGray));
        setBackground(new Color(224, 224, 224));


        // First all elements are defined and their attributes set

        // Set up colour chooser button
        colourChooserButton = new JButton();
        colourChooserButton.setPreferredSize(new Dimension(75, 75));
        colourChooserButton.addActionListener(this);
        currentColour = new Color(255, 255, 255);
        colourChooserButton.setBackground(currentColour);

        // Set up the agent name text input
        agentNameTextField = new JTextField("Agent Name");
        agentNameTextField.setPreferredSize(new Dimension(150, 30));

        visionRangeLabel = new JLabel("Vision Range: ");
        visionRangeLabel.setPreferredSize(new Dimension(150, 30));

        movementRangeLabel = new JLabel("Movement Range: ");
        movementRangeLabel.setPreferredSize(new Dimension(150, 30));

        sizeLabel = new JLabel("Size: ");
        sizeLabel.setPreferredSize(new Dimension(150, 30));

        energyCapacityLabel = new JLabel("Energy Capacity: ");
        energyCapacityLabel.setPreferredSize(new Dimension(150, 30));

        eatAmountLabel = new JLabel("Eat Amount: ");
        eatAmountLabel.setPreferredSize(new Dimension(150, 30));

        lifespanLabel = new JLabel("Lifespan: ");
        lifespanLabel.setPreferredSize(new Dimension(150, 30));

        creationAgeLabel = new JLabel("Creation Age: ");
        creationAgeLabel.setPreferredSize(new Dimension(150, 30));

        creationAmountLabel = new JLabel("Creation Amount: ");
        creationAmountLabel.setPreferredSize(new Dimension(150, 30));

        creationDelayLabel = new JLabel("Creation Delay: ");
        creationDelayLabel.setPreferredSize(new Dimension(150, 30));

        agentCodeLabel = new JLabel("Agent Code: ");
        agentCodeLabel.setPreferredSize(new Dimension(150, 30));

        // Now we do the spinners
        visionRangeSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 25, 1));
        visionRangeSpinner.setPreferredSize(new Dimension(150, 30));

        movementRangeSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 25, 1));
        movementRangeSpinner.setPreferredSize(new Dimension(150, 30));

        sizeSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 255, 1));
        sizeSpinner.setPreferredSize(new Dimension(150, 30));

        energyCapacitySpinner = new JSpinner(new SpinnerNumberModel(10, 0, 999999, 1));
        energyCapacitySpinner.setPreferredSize(new Dimension(150, 30));

        eatAmountSpinner = new JSpinner(new SpinnerNumberModel(2, 0, 999999, 1));
        eatAmountSpinner.setPreferredSize(new Dimension(150, 30));

        lifespanSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 999999, 1));
        lifespanSpinner.setPreferredSize(new Dimension(150, 30));

        creationAgeSpinner = new JSpinner(new SpinnerNumberModel(5, 0, 999999, 1));
        creationAgeSpinner.setPreferredSize(new Dimension(150, 30));

        creationAmountSpinner = new JSpinner(new SpinnerNumberModel(4, 1, 9, 1));
        creationAmountSpinner.setPreferredSize(new Dimension(150, 30));

        creationDelaySpinner = new JSpinner(new SpinnerNumberModel(1, 0, 999999, 1));
        creationDelaySpinner.setPreferredSize(new Dimension(150, 30));

        agentCodeSpinner = new JSpinner();
        agentCodeSpinner.setPreferredSize(new Dimension(150, 30));
        agentCodeSpinner.setEnabled(false);

        updateSettingsButton = new JButton("Update Settings");
        updateSettingsButton.setBackground(new Color(204, 204, 204));
        updateSettingsButton.setPreferredSize(new Dimension(300, 80));

        isGrazerToggle = new JToggleButton("Grazer");
        isGrazerToggle.setPreferredSize(new Dimension(300, 60));

        isPredatorToggle = new JToggleButton("Predator");
        isPredatorToggle.setPreferredSize(new Dimension(300, 60));

        // The GridBag constraints we'll be using to build this panel
        GridBagConstraints c = new GridBagConstraints();
        // This tells the component it should only horizontally span 1 cell
        c.gridwidth = 1;
        c.insets = new Insets(1, 1, 1, 1);
        c.weightx = 1;
        c.weighty = 0;

        // Now we add the top row components
        c.gridy = 0;

        c.gridx = 0;
        add(agentNameTextField, c);
        c.gridx = 1;
        add(colourChooserButton, c);

        // Now we add the second row components
        c.gridy = 1;

        c.gridx = 0;
        add(visionRangeLabel, c);
        c.gridx = 1;
        add(visionRangeSpinner, c);

        // Now we add the third row components
        c.gridy = 2;

        c.gridx = 0;
        add(movementRangeLabel, c);
        c.gridx = 1;
        add(movementRangeSpinner, c);

        // Now we add the forth row components
        c.gridy = 3;

        c.gridx = 0;
        add(sizeLabel, c);
        c.gridx = 1;
        add(sizeSpinner, c);

        // Now we add the fifth row components
        c.gridy = 4;

        c.gridx = 0;
        add(energyCapacityLabel, c);
        c.gridx = 1;
        add(energyCapacitySpinner, c);

        // First we add the sixth row components
        c.gridy = 5;

        c.gridx = 0;
        add(eatAmountLabel, c);
        c.gridx = 1;
        add(eatAmountSpinner, c);


        // Now we add the seventh row components
        c.gridy = 6;

        c.gridx = 0;
        add(lifespanLabel, c);
        c.gridx = 1;
        add(lifespanSpinner, c);


        // Now we add the eighth row components
        c.gridy = 7;

        c.gridx = 0;
        add(creationAgeLabel, c);
        c.gridx = 1;
        add(creationAgeSpinner, c);

        // Now we add the ninth row components
        c.gridy = 8;

        c.gridx = 0;
        add(creationAmountLabel, c);
        c.gridx = 1;
        add(creationAmountSpinner, c);

        // Now we add the tenth row components
        c.gridy = 9;

        c.gridx = 0;
        add(creationDelayLabel, c);
        c.gridx = 1;
        add(creationDelaySpinner, c);

        // Now we add the eleventh row components
        c.gridy = 10;

        c.gridx = 0;
        add(agentCodeLabel, c);
        c.gridx = 1;
        add(agentCodeSpinner, c);

        // Now we add the twelfth row components
        c.gridy = 11;

        c.gridwidth = 2;
        c.gridx = 0;
        add(isGrazerToggle, c);

        // Now we add the thirteenth row components
        c.gridy = 12;

        c.gridx = 0;
        add(isPredatorToggle, c);

        // Now we add the bottom row components
        c.gridy = 13;
        add(updateSettingsButton, c);

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

    public JSpinner getEnergyCapacitySpinner() {
        return energyCapacitySpinner;
    }

    public JSpinner getEatAmountSpinner() {
        return eatAmountSpinner;
    }

    public JSpinner getLifespanSpinner() {
        return lifespanSpinner;
    }

    public JSpinner getVisionRangeSpinner() {
        return visionRangeSpinner;
    }

    public JSpinner getMovementRangeSpinner() {
        return movementRangeSpinner;
    }

    public JSpinner getSizeSpinner() {
        return sizeSpinner;
    }

    public JSpinner getCreationAgeSpinner() {
        return creationAgeSpinner;
    }

    public JSpinner getCreationAmountSpinner() {
        return creationAmountSpinner;
    }

    public JSpinner getCreationDelaySpinner() {
        return creationDelaySpinner;
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

    public JButton getUpdateSettingsButton() {
        return updateSettingsButton;
    }

    public void setAgentSettings(AgentSettings agentSettings) {
        agentNameTextField.setText(agentSettings.getName());
        colourChooserButton.setBackground(agentSettings.getColor());
        sizeSpinner.setValue(agentSettings.getSize());
        visionRangeSpinner.setValue(agentSettings.getVisionRange());
        movementRangeSpinner.setValue(agentSettings.getMovementRange());
        energyCapacitySpinner.setValue(agentSettings.getEnergyCapacity());
        eatAmountSpinner.setValue(agentSettings.getEatAmount());
        lifespanSpinner.setValue(agentSettings.getLifespan());
        creationAgeSpinner.setValue(agentSettings.getCreationAge());
        creationAmountSpinner.setValue(agentSettings.getCreationAmount());
        creationDelaySpinner.setValue(agentSettings.getCreationDelay());
        agentCodeSpinner.setValue(agentSettings.getCode());
    }

    public AgentSettings getAgentSettings() {
                                //String name, int code, Color color, int visionRange, int movementRange, int size, int energyCapacity, int eatAmount, int lifespan, int creationAge, int creationAmount, int creationDelay
        return new AgentSettings(agentNameTextField.getText(), (int) agentCodeSpinner.getValue(), colourChooserButton.getBackground(), (int) visionRangeSpinner.getValue(), (int) movementRangeSpinner.getValue(), (int) sizeSpinner.getValue(), (int) energyCapacitySpinner.getValue(), (int) eatAmountSpinner.getValue(), (int) lifespanSpinner.getValue(), (int) creationAgeSpinner.getValue(), (int) creationAmountSpinner.getValue(), (int) creationDelaySpinner.getValue());
    }
}
