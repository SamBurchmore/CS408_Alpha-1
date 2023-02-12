package View;

import Simulation.AgentUtility.AgentSettings;
import Simulation.Agent.AgentConcreteComponents.CreatorMotivation;
import Simulation.Agent.AgentConcreteComponents.GrazerMotivation;
import Simulation.Agent.AgentConcreteComponents.PredatorMotivation;
import Simulation.Agent.AgentInterfaces.Motivation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AgentEditorPanel extends JPanel implements ActionListener {

    final private JTabbedPane mainPane;

    // The panels we'll use to group settings
    final private JPanel nameColorPanel;
    final private JPanel attributesPanel;
    final private JPanel motivationsPanel;
    final private JPanel mutationsAndInheritancePanel;
    final private JPanel spawningWeightPanel;

    // The text field where the agents name is set
    final private JTextField agentNameTextField;

    // The button the agents color is chosen through
    final private JButton colourChooserButton;
    private Color currentColour;

    final private JSpinner agentCodeSpinner;
    final private JPanel agentCodePanel;
    final private JLabel agentCodeLabel;

    // The components where the mutable attributes are set-----------------------------</
    final private JPanel rangePanel;
    final private JPanel sizePanel;
    final private JPanel creationAgePanel;
    final private JPanel creationAmountPanel;

    final private JSpinner rangeSpinner;
    final private JSpinner sizeSpinner;
    final private JSpinner creationAgeSpinner;
    final private JSpinner creationAmountSpinner;

    final private JLabel rangeLabel;
    final private JLabel sizeLabel;
    final private JLabel creationAgeLabel;
    final private JLabel creationAmountLabel;
    //----------------------------------------------------------------------------------/>

    // The components where the constant attributes are set-----------------------------</
    final private JPanel creationDelayPanel;

    final private JSpinner creationDelaySpinner;

    final private JLabel creationDelayLabel;
    //----------------------------------------------------------------------------------/>

    // The components where the calculated attributes are displayed---------------------</
    final private JPanel calculatedAttributesPanel;

    final private JPanel energyCapacityPanel;
    final private JPanel eatAmountPanel;
    final private JPanel energyLostPerTurnPanel;
    final private JPanel lifespanPanel;

    final private JLabel energyCapacityValue;
    final private JLabel eatAmountValue;
    final private JLabel energyLostPerTurnValue;
    final private JLabel lifespanValue;

    final private JLabel energyCapacityLabel;
    final private JLabel eatAmountLabel;
    final private JLabel energyLostPerTurnLabel;
    final private JLabel lifespanLabel;
    //----------------------------------------------------------------------------------/>


    // The panels where the motivations and toggled and their weights and biases selected
    private JPanel grazerPanel;
    private JPanel predatorPanel;

    private JCheckBox isGrazerToggle;
    private JCheckBox isPredatorToggle;

    private JLabel grazerBiasSpinnerLabel;
    private JLabel predatorBiasSpinnerLabel;

    private JLabel grazerWeightSpinnerLabel;
    private JLabel predatorWeightSpinnerLabel;

    private JSpinner grazerBiasSpinner;
    private JSpinner predatorBiasSpinner;

    private JSpinner grazerWeightSpinner;
    private JSpinner predatorWeightSpinner;

    // The panel where the mutation flag is toggled and the mutationMagnitude is selected
    private JPanel mutationMagnitudePanel;

    private JCheckBox mutatesCheckBox;
    private JLabel mutationMagnitudeLabel;
    private JSpinner mutationMagnitudeSpinner;

    private JLabel spawningWeightLabel;
    private JSpinner spawningWeightSpinner; // The spinner which controls how many agents are created when the population button is clicked.

    private JButton updateSettingsButton;

    public AgentEditorPanel() {
        super();
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(460, 382));
        setBorder(BorderFactory.createLineBorder(Color.darkGray));

        //--------------------------------------------------------------------------Name and Color Panel Start
        colourChooserButton = new JButton();
        colourChooserButton.setPreferredSize(new Dimension(75, 75));
        colourChooserButton.addActionListener(this);
        currentColour = new Color(255, 255, 255);
        colourChooserButton.setBackground(currentColour);

        agentNameTextField = new JTextField("Agent Name");
        agentNameTextField.setPreferredSize(new Dimension(150, 25));

        agentCodePanel = new JPanel();
        agentCodePanel.setPreferredSize(new Dimension(180, 30));

        agentCodeLabel = new JLabel("Agent Code: ");
        agentCodeLabel.setPreferredSize(new Dimension(75, 25));

        agentCodeSpinner = new JSpinner();
        agentCodeSpinner.setPreferredSize(new Dimension(60, 25));
        agentCodeSpinner.setEnabled(false);

        agentCodePanel.add(agentCodeLabel);
        agentCodePanel.add(agentCodeSpinner);

        nameColorPanel = new JPanel();
        nameColorPanel.setPreferredSize(new Dimension(450, 85));

        nameColorPanel.add(agentNameTextField);
        nameColorPanel.add(colourChooserButton);
        nameColorPanel.add(agentCodePanel);
        //--------------------------------------------------------------------------Name and Color Panel End


        //--------------------------------------------------------------------------Attributes Panel Start
        attributesPanel = new JPanel();
        attributesPanel.setPreferredSize(new Dimension(450, 160));
        attributesPanel.setName("Attributes");

        // Mutable attributes first
        rangePanel = new JPanel();
        rangePanel.setPreferredSize(new Dimension(220, 28));

        sizePanel = new JPanel();
        sizePanel.setPreferredSize(new Dimension(220, 28));

        creationAgePanel = new JPanel();
        creationAgePanel.setPreferredSize(new Dimension(220, 28));

        creationAmountPanel = new JPanel();
        creationAmountPanel.setPreferredSize(new Dimension(220, 28));

        rangeLabel = new JLabel("Range: ");
        rangeLabel.setPreferredSize(new Dimension(120, 20));

        sizeLabel = new JLabel("Size: ");
        sizeLabel.setPreferredSize(new Dimension(120, 20));

        creationAgeLabel = new JLabel("Creation Age: ");
        creationAgeLabel.setPreferredSize(new Dimension(120, 20));

        creationAmountLabel = new JLabel("Creation Size: ");
        creationAmountLabel.setPreferredSize(new Dimension(120, 20));

        rangeSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 25, 1));
        rangeSpinner.setPreferredSize(new Dimension(85, 24));

        sizeSpinner = new JSpinner(new SpinnerNumberModel(2, 2, 10, 1));
        sizeSpinner.setPreferredSize(new Dimension(85, 24));

        creationAgeSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 250, 1));
        creationAgeSpinner.setPreferredSize(new Dimension(85, 24));

        creationAmountSpinner = new JSpinner(new SpinnerNumberModel(4, 1, 8, 1));
        creationAmountSpinner.setPreferredSize(new Dimension(85, 24));

        rangePanel.add(rangeLabel);
        rangePanel.add(rangeSpinner);

        sizePanel.add(sizeLabel);
        sizePanel.add(sizeSpinner);

        creationAgePanel.add(creationAgeLabel);
        creationAgePanel.add(creationAgeSpinner);

        creationAmountPanel.add(creationAmountLabel);
        creationAmountPanel.add(creationAmountSpinner);

        // Calculated attributes
        calculatedAttributesPanel = new JPanel();
        calculatedAttributesPanel.setPreferredSize(new Dimension(450, 100));

        energyCapacityPanel = new JPanel();
        energyCapacityPanel.setPreferredSize(new Dimension(220, 25));

        eatAmountPanel = new JPanel();
        eatAmountPanel.setPreferredSize(new Dimension(220, 25));

        lifespanPanel = new JPanel();
        lifespanPanel.setPreferredSize(new Dimension(220, 25));

        energyLostPerTurnPanel = new JPanel();
        energyLostPerTurnPanel.setPreferredSize(new Dimension(220, 25));

        energyCapacityLabel = new JLabel("Max Energy: ");
        energyCapacityLabel.setPreferredSize(new Dimension(90, 20));
        energyCapacityLabel.setFont(new Font("Dialog", Font.PLAIN, 11));

        eatAmountLabel = new JLabel("Eat Amount: ");
        eatAmountLabel.setPreferredSize(new Dimension(90, 20));
        eatAmountLabel.setFont(new Font("Dialog", Font.PLAIN, 11));

        lifespanLabel = new JLabel("Lifespan: ");
        lifespanLabel.setPreferredSize(new Dimension(90, 20));
        lifespanLabel.setFont(new Font("Dialog", Font.PLAIN, 11));

        energyLostPerTurnLabel = new JLabel("Energy Burned: ");
        energyLostPerTurnLabel.setPreferredSize(new Dimension(90, 20));
        energyLostPerTurnLabel.setFont(new Font("Dialog", Font.PLAIN, 11));

        energyCapacityValue = new JLabel("0");
        energyCapacityValue.setPreferredSize(new Dimension(30, 20));
        energyCapacityValue.setHorizontalAlignment(SwingConstants.RIGHT);
        energyCapacityValue.setFont(new Font("Dialog", Font.BOLD, 12));

        eatAmountValue = new JLabel("");
        eatAmountValue.setPreferredSize(new Dimension(30, 20));
        eatAmountValue.setHorizontalAlignment(SwingConstants.RIGHT);
        eatAmountValue.setFont(new Font("Dialog", Font.BOLD, 12));

        energyLostPerTurnValue = new JLabel("0");
        energyLostPerTurnValue.setPreferredSize(new Dimension(30, 20));
        energyLostPerTurnValue.setHorizontalAlignment(SwingConstants.RIGHT);
        energyLostPerTurnValue.setFont(new Font("Dialog", Font.BOLD, 12));

        lifespanValue = new JLabel("");
        lifespanValue.setPreferredSize(new Dimension(30, 20));
        lifespanValue.setHorizontalAlignment(SwingConstants.RIGHT);
        lifespanValue.setFont(new Font("Dialog", Font.BOLD, 12));

        energyCapacityPanel.add(energyCapacityLabel);
        energyCapacityPanel.add(energyCapacityValue);

        eatAmountPanel.add(eatAmountLabel);
        eatAmountPanel.add(eatAmountValue);

        energyLostPerTurnPanel.add(energyLostPerTurnLabel);
        energyLostPerTurnPanel.add(energyLostPerTurnValue);

        lifespanPanel.add(lifespanLabel);
        lifespanPanel.add(lifespanValue);

        calculatedAttributesPanel.add(energyCapacityPanel);
        calculatedAttributesPanel.add(eatAmountPanel);
        calculatedAttributesPanel.add(energyLostPerTurnPanel);
        calculatedAttributesPanel.add(lifespanPanel);

        // Constant attributes
        creationDelayPanel = new JPanel();
        creationDelayPanel.setPreferredSize(new Dimension(220, 28));

        creationDelayLabel = new JLabel("Creation Delay: ");
        creationDelayLabel.setPreferredSize(new Dimension(120, 20));

        creationDelaySpinner = new JSpinner(new SpinnerNumberModel(1, 0, 999999, 1));
        creationDelaySpinner.setPreferredSize(new Dimension(85, 24));

        creationDelayPanel.add(creationDelayLabel);
        creationDelayPanel.add(creationDelaySpinner);


        // Now we add the panels to the attributes panel in the order they'll be displayed in
        attributesPanel.add(rangePanel);
        attributesPanel.add(sizePanel);
        attributesPanel.add(creationAgePanel);
        attributesPanel.add(creationAmountPanel);

        attributesPanel.add(creationDelayPanel);

        attributesPanel.add(calculatedAttributesPanel);

        //attributesPanel.add(eatAmountPanel);
        //attributesPanel.add(lifespanPanel);

        //--------------------------------------------------------------------------Attributes Panel End


        //--------------------------------------------------------------------------Motivations Panel End
        motivationsPanel = new JPanel(new GridLayout(1, 2));
        motivationsPanel.setPreferredSize(new Dimension(450, 160));
        motivationsPanel.setName("Motivations");

        grazerPanel = new JPanel();
        grazerPanel.setPreferredSize(new Dimension(225, 50));

        predatorPanel = new JPanel();
        predatorPanel.setPreferredSize(new Dimension(225, 50));

        grazerBiasSpinnerLabel = new JLabel("Bias: ");
        grazerBiasSpinnerLabel.setPreferredSize(new Dimension(150, 25));

        predatorBiasSpinnerLabel = new JLabel("Bias: ");
        predatorBiasSpinnerLabel.setPreferredSize(new Dimension(150, 25));

        grazerWeightSpinnerLabel = new JLabel("Weight: ");
        grazerWeightSpinnerLabel.setPreferredSize(new Dimension(150, 25));

        predatorWeightSpinnerLabel = new JLabel("Weight: ");
        predatorWeightSpinnerLabel.setPreferredSize(new Dimension(150, 25));

        grazerBiasSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 10, 1));
        grazerBiasSpinner.setPreferredSize(new Dimension(60, 25));

        predatorBiasSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 10, 1));
        predatorBiasSpinner.setPreferredSize(new Dimension(60, 25));

        grazerWeightSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 10, 1));
        grazerWeightSpinner.setPreferredSize(new Dimension(60, 25));

        predatorWeightSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 10, 1));
        predatorWeightSpinner.setPreferredSize(new Dimension(60, 25));

        isGrazerToggle = new JCheckBox("Grazer");
        isGrazerToggle.setPreferredSize(new Dimension(225, 25));

        isPredatorToggle = new JCheckBox("Predator");
        isPredatorToggle.setPreferredSize(new Dimension(225, 25));

        grazerPanel.add(isGrazerToggle);
        grazerPanel.add(grazerBiasSpinnerLabel);
        grazerPanel.add(grazerBiasSpinner);
        grazerPanel.add(grazerWeightSpinnerLabel);
        grazerPanel.add(grazerWeightSpinner);

        predatorPanel.add(isPredatorToggle);
        predatorPanel.add(predatorBiasSpinnerLabel);
        predatorPanel.add(predatorBiasSpinner);
        predatorPanel.add(predatorWeightSpinnerLabel);
        predatorPanel.add(predatorWeightSpinner);

        motivationsPanel.add(grazerPanel);
        motivationsPanel.add(predatorPanel);
        //--------------------------------------------------------------------------Motivations Panel End


        //--------------------------------------------------------------------------Mutations and Inheritance Panel Start
        mutationsAndInheritancePanel = new JPanel();
        mutationsAndInheritancePanel.setPreferredSize(new Dimension(450, 50));
        mutationsAndInheritancePanel.setName("Mutation and Inheritance");

        mutatesCheckBox = new JCheckBox("Mutates");
        mutatesCheckBox.setPreferredSize(new Dimension(400, 25));

        mutationMagnitudePanel = new JPanel(new GridLayout(1, 2));
        mutationMagnitudePanel.setPreferredSize(new Dimension(400, 25));

        mutationMagnitudeLabel = new JLabel("Mutation Magnitude: ");
        mutationMagnitudeLabel.setPreferredSize(new Dimension(360, 25));

        mutationMagnitudeSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 100, 1));
        mutationMagnitudeSpinner.setPreferredSize(new Dimension(40, 25));

        mutationMagnitudePanel.add(mutationMagnitudeLabel);
        mutationMagnitudePanel.add(mutationMagnitudeSpinner);

        mutationsAndInheritancePanel.add(mutatesCheckBox);
        mutationsAndInheritancePanel.add(mutationMagnitudePanel);
        //--------------------------------------------------------------------------Mutations and Inheritance Panel End


        //--------------------------------------------------------------------------Main Pane Start
        mainPane = new JTabbedPane();
        mainPane.setBackground(new Color(224, 224, 224));
        mainPane.add(attributesPanel);
        mainPane.add(motivationsPanel);
        mainPane.add(mutationsAndInheritancePanel);
        //--------------------------------------------------------------------------Main Pane End

        updateSettingsButton = new JButton("Update Settings");
        updateSettingsButton.setPreferredSize(new Dimension(450, 40));

        spawningWeightLabel = new JLabel("Spawning Weight: ");
        spawningWeightLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        spawningWeightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        spawningWeightLabel.setPreferredSize(new Dimension(120, 20));

        spawningWeightSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 1, 0.01));
        spawningWeightSpinner.setPreferredSize(new Dimension(60, 20));

        spawningWeightPanel = new JPanel();
        spawningWeightPanel.setPreferredSize(new Dimension(220, 25));
        spawningWeightPanel.add(spawningWeightLabel);
        spawningWeightPanel.add(spawningWeightSpinner);

        add(nameColorPanel);
        add(mainPane);
        add(spawningWeightPanel);
        add(updateSettingsButton);

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

    public JLabel getEnergyCapacityValue() {
        return energyCapacityValue;
    }

    public JLabel getEatAmountValue() {
        return eatAmountValue;
    }

    public JLabel getLifespanValue() {
        return lifespanValue;
    }

    public JSpinner getRangeSpinner() {
        return rangeSpinner;
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
        spawningWeightSpinner.setValue(agentSettings.getSpawningWeight());
        agentNameTextField.setText(agentSettings.getName());
        colourChooserButton.setBackground(agentSettings.getColor());
        agentCodeSpinner.setValue(agentSettings.getCode());

        rangeSpinner.setValue(agentSettings.getRange());
        sizeSpinner.setValue(agentSettings.getSize());
        creationAgeSpinner.setValue(agentSettings.getCreationAge());
        creationAmountSpinner.setValue(agentSettings.getCreationAmount());

        energyCapacityValue.setText(agentSettings.getEnergyCapacity().toString());
        eatAmountValue.setText(agentSettings.getEatAmount().toString());
        energyLostPerTurnValue.setText(agentSettings.getEnergyLostPerTurn().toString());
        lifespanValue.setText(agentSettings.getLifespan().toString());

        creationDelaySpinner.setValue(agentSettings.getCreationDelay());

        isGrazerToggle.setSelected(false);
        isPredatorToggle.setSelected(false);
        for (Motivation motivation : agentSettings.getMotivations()) {
            if (motivation.getCode() == 1) {
                isGrazerToggle.setSelected(true);
                grazerBiasSpinner.setValue(motivation.getBias());
                grazerWeightSpinner.setValue(motivation.getWeight());
            }
            if (motivation.getCode() == 2) {
                isPredatorToggle.setSelected(true);
                predatorBiasSpinner.setValue(motivation.getBias());
                predatorWeightSpinner.setValue(motivation.getWeight());
            }
        }
        mutatesCheckBox.setSelected(agentSettings.getAttributes().getMutates());
        mutationMagnitudeSpinner.setValue(agentSettings.getAttributes().getMutationMagnitude());
    }

    public AgentSettings getAgentSettings() {
        ArrayList<Motivation> motivations = new ArrayList<>();
        motivations.add(new CreatorMotivation(20, 1));
        if (isGrazerToggle.isSelected()) {
            motivations.add(new GrazerMotivation((int) grazerBiasSpinner.getValue(), (int) grazerWeightSpinner.getValue()));
        }
        if (isPredatorToggle.isSelected()) {
            motivations.add(new PredatorMotivation((int) predatorBiasSpinner.getValue(), (int) predatorWeightSpinner.getValue()));
        }
        return new AgentSettings(
                              (double) spawningWeightSpinner.getValue(),
                                       agentNameTextField.getText(),
                                 (int) agentCodeSpinner.getValue(),
                                       colourChooserButton.getBackground(),
                                       mutatesCheckBox.isSelected(),
                                 (int) mutationMagnitudeSpinner.getValue(),

                                 (int) rangeSpinner.getValue(),
                                 (int) sizeSpinner.getValue(),
                                 (int) creationAgeSpinner.getValue(),
                                 (int) creationAmountSpinner.getValue(),

                                 (int) creationDelaySpinner.getValue(),
                                       motivations);
    }
}
