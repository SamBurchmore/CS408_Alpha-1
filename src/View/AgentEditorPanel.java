package View;

import Simulation.Agent.AgentConcreteComponents.*;
import Simulation.Agent.AgentInterfaces.Attributes;
import Simulation.Agent.AgentStructs.ColorModel;
import Simulation.Agent.AgentUtility.AgentSettings;
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
    final private JPanel motivationsPanel;
    final private JPanel attributesPanel;
    final private JPanel mutationChanceAndSpawningPanel;

    // The components where the agents name and seed color are set-----------------------------</
    final private JTextField agentNameTextField;
    final private SquareButton seedColourChooserButton;
    private Color currentSeedColour;
    final private JPanel agentCodePanel;
    final private JLabel agentCodeValue;
    final private JLabel agentCodeLabel;
    //-----------------------------------------------------------------------------------------/>

    // The components where the mutable attributes are set-----------------------------</
    final private JPanel mutatingAttributesPanel;
    final private JPanel rangePanel;
    final private JPanel sizePanel;
    final private JPanel creationAmountPanel;
    final private JSpinner rangeSpinner;
    final private JSpinner sizeSpinner;
    final private JSpinner creationAmountSpinner;
    final private JLabel rangeLabel;
    final private JLabel sizeLabel;
    final private JLabel creationAmountLabel;
    //----------------------------------------------------------------------------------/>

    // The components where the calculated attributes are displayed---------------------</
    final private JPanel calculatedAttributesPanel;
    final private JPanel energyCapacityPanel;
    final private JPanel eatAmountPanel;
    final private JPanel energyLostPerTurnPanel;
    final private JPanel lifespanPanel;
    final private JPanel creationAgePanel;
    final private JPanel creationDelayPanel;
    final private JPanel creationCostPanel;
    final private JPanel generatedColorPanel;
    final private JLabel energyCapacityValue;
    final private JLabel eatAmountValue;
    final private JLabel energyLostPerTurnValue;
    final private JLabel lifespanValue;
    final private JLabel creationAgeValue;
    final private JLabel creationDelayValue;
    final private JLabel creationCostValue;
    final private JLabel generatedColorValue;
    final private JLabel energyCapacityLabel;
    final private JLabel eatAmountLabel;
    final private JLabel energyLostPerTurnLabel;
    final private JLabel lifespanLabel;
    final private JLabel creationAgeLabel;
    final private JLabel creationDelayLabel;
    final private JLabel creationCostLabel;
    final private JLabel generatedColorLabel;
    //----------------------------------------------------------------------------------/>

    // The components where the motivations are toggled and their weights and biases selected---------------------</
    final private JPanel grazerPanel;
    final private JPanel predatorPanel;
    final private JCheckBox isGrazerToggle;
    final private JCheckBox isPredatorToggle;
    final private JLabel grazerBiasSpinnerLabel;
    final private JLabel predatorBiasSpinnerLabel;
    final private JLabel grazerWeightSpinnerLabel;
    final private JLabel predatorWeightSpinnerLabel;
    final private JSpinner grazerBiasSpinner;
    final private JSpinner predatorBiasSpinner;
    final private JSpinner grazerWeightSpinner;
    final private JSpinner predatorWeightSpinner;
    //------------------------------------------------------------------------------------------------------------/>

    // The components where the color model is selected---------------------</
    final private ButtonGroup colorModelButtonGroup;
    final private JPanel colorModelPanel;
    final private JPanel staticModelPanel;
    final private JPanel attributesModelPanel;
    final private JPanel randomModelPanel;
    final private JRadioButton staticModelButton;
    final private JRadioButton attributesModelButton;
    final private JRadioButton randomModelButton;
    final private JPanel randomMagnitudePanel;
    final private JLabel randomMagnitudeLabel;
    final private JSpinner randomMagnitudeSpinner;
    //----------------------------------------------------------------------</

    // The components where the spawning weight and mutation chance are selected---------------------</
    final private JPanel spawningWeightPanel;
    final private JPanel mutationMagnitudePanel;
    final private JLabel mutationMagnitudeLabel;
    final private JSpinner mutationMagnitudeSpinner;
    final private JLabel spawningWeightLabel;
    final private JSpinner spawningWeightSpinner;
    //-----------------------------------------------------------------------------------------------/>

    final private JButton updateSettingsButton;

    public AgentEditorPanel() {
        super();
        setLayout(new GridBagLayout());
        //setPreferredSize(new Dimension(460, 382));
        //setBorder(BorderFactory.createLineBorder(Color.darkGray));

        GridBagConstraints c = new GridBagConstraints(); // The gridBagConstraints we'll be using when setting this class up

        //--------------------------------------------------------------------------Name and Color Panel Start
        seedColourChooserButton = new SquareButton();
        seedColourChooserButton.addActionListener(this);
        currentSeedColour = new Color(255, 255, 255);
        seedColourChooserButton.setBackground(currentSeedColour);

        agentNameTextField = new JTextField("Agent Name");

        agentCodeValue = new JLabel("Agent Code: ");
        agentCodeLabel = new JLabel("Agent ID: ");
        agentCodePanel = new JPanel(new GridLayout(1, 2));
        agentCodePanel.add(agentCodeLabel);
        agentCodePanel.add(agentCodeValue);

        nameColorPanel = new JPanel(new GridBagLayout());

        c.gridwidth = 2;
        nameColorPanel.add(agentNameTextField, c);
        nameColorPanel.add(seedColourChooserButton, c);
        nameColorPanel.add(new JPanel(), c);
        nameColorPanel.add(agentCodePanel, c);
        //--------------------------------------------------------------------------Name and Color Panel End


        //--------------------------------------------------------------------------Attributes Panel Start
        // Mutable attributes first
        mutatingAttributesPanel= new JPanel();

        sizePanel = new JPanel();
        sizeLabel = new JLabel("Size: ");
        sizeLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        sizeSpinner = new JSpinner(new SpinnerNumberModel(2, 2, 100, 1));

        creationAmountPanel = new JPanel();
        creationAmountLabel = new JLabel("Creation Size: ");
        creationAmountLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        creationAmountSpinner = new JSpinner(new SpinnerNumberModel(4, 1, 8, 1));

        rangePanel = new JPanel();
        rangeLabel = new JLabel("Range: ");
        rangeLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        rangeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));

        mutatingAttributesPanel.add(sizeLabel);
        mutatingAttributesPanel.add(sizeSpinner);
        mutatingAttributesPanel.add(creationAmountLabel);
        mutatingAttributesPanel.add(creationAmountSpinner);
        mutatingAttributesPanel.add(rangeLabel);
        mutatingAttributesPanel.add(rangeSpinner);

        // Energy cap panel
        energyCapacityPanel = new JPanel();

        energyCapacityLabel = new JLabel("Max Energy:     ");
        energyCapacityLabel.setFont(new Font("Dialog", Font.PLAIN, 12));

        energyCapacityValue = new JLabel("0");
        energyCapacityValue.setHorizontalAlignment(SwingConstants.RIGHT);
        energyCapacityValue.setFont(new Font("Dialog", Font.BOLD, 12));

        energyCapacityPanel.add(energyCapacityLabel);
        energyCapacityPanel.add(energyCapacityValue);

        // Eat amount panel
        eatAmountPanel = new JPanel();

        eatAmountLabel = new JLabel("Eat Amount:         ");
        eatAmountLabel.setFont(new Font("Dialog", Font.PLAIN, 12));

        eatAmountValue = new JLabel("0");
        eatAmountValue.setHorizontalAlignment(SwingConstants.RIGHT);
        eatAmountValue.setFont(new Font("Dialog", Font.BOLD, 12));

        eatAmountPanel.add(eatAmountLabel);
        eatAmountPanel.add(eatAmountValue);

        // Energy lost per turn panel
        energyLostPerTurnPanel = new JPanel();

        energyLostPerTurnLabel = new JLabel("Energy Burned: ");
        energyLostPerTurnLabel.setFont(new Font("Dialog", Font.PLAIN, 12));

        energyLostPerTurnValue = new JLabel("0");
        energyLostPerTurnValue.setHorizontalAlignment(SwingConstants.RIGHT);
        energyLostPerTurnValue.setFont(new Font("Dialog", Font.BOLD, 12));

        energyLostPerTurnPanel.add(energyLostPerTurnLabel);
        energyLostPerTurnPanel.add(energyLostPerTurnValue);

        // Lifespan panel
        lifespanPanel = new JPanel();

        lifespanLabel = new JLabel("Lifespan:         ");
        lifespanLabel.setFont(new Font("Dialog", Font.PLAIN, 12));

        lifespanValue = new JLabel("0");
        lifespanValue.setHorizontalAlignment(SwingConstants.RIGHT);
        lifespanValue.setFont(new Font("Dialog", Font.BOLD, 12));

        lifespanPanel.add(lifespanLabel);
        lifespanPanel.add(lifespanValue);

        // Creation age panel
        creationAgePanel = new JPanel();

        creationAgeLabel = new JLabel("Creation Age:       ");
        creationAgeLabel.setFont(new Font("Dialog", Font.PLAIN, 12));

        creationAgeValue = new JLabel("0");
        creationAgeValue.setHorizontalAlignment(SwingConstants.RIGHT);
        creationAgeValue.setFont(new Font("Dialog", Font.BOLD, 12));

        creationAgePanel.add(creationAgeLabel);
        creationAgePanel.add(creationAgeValue);

        // Creation cost panel
        creationCostPanel = new JPanel();

        creationCostLabel = new JLabel("Creation Cost:   ");
        creationCostLabel.setFont(new Font("Dialog", Font.PLAIN, 12));

        creationCostValue = new JLabel("0");
        creationCostValue.setHorizontalAlignment(SwingConstants.RIGHT);
        creationCostValue.setFont(new Font("Dialog", Font.BOLD, 12));

        creationCostPanel.add(creationCostLabel);
        creationCostPanel.add(creationCostValue);

        // Creation delay panel
        creationDelayPanel = new JPanel();

        creationDelayLabel = new JLabel("Creation Delay: ");
        creationDelayLabel.setFont(new Font("Dialog", Font.PLAIN, 12));

        creationDelayValue = new JLabel("0");
        creationDelayValue.setHorizontalAlignment(SwingConstants.RIGHT);
        creationDelayValue.setFont(new Font("Dialog", Font.BOLD, 12));

        creationDelayPanel.add(creationDelayLabel);
        creationDelayPanel.add(creationDelayValue);

        // Generated color panel
        generatedColorPanel = new JPanel();

        generatedColorLabel = new JLabel("Mutating Color: ");
        generatedColorLabel.setFont(new Font("Dialog", Font.PLAIN, 12));

        generatedColorValue = new JLabel("      ");
        generatedColorValue.setHorizontalAlignment(SwingConstants.RIGHT);
        generatedColorValue.setFont(new Font("Dialog", Font.PLAIN, 12));
        generatedColorValue.setOpaque(true);

        generatedColorPanel.add(generatedColorLabel);
        generatedColorPanel.add(generatedColorValue);

        // Calculated attributes panel
        calculatedAttributesPanel = new JPanel(new GridLayout(3, 3, 5, 5));

        // Add all the panels
        calculatedAttributesPanel.add(energyCapacityPanel);
        calculatedAttributesPanel.add(eatAmountPanel);
        calculatedAttributesPanel.add(energyLostPerTurnPanel);
        calculatedAttributesPanel.add(lifespanPanel);
        calculatedAttributesPanel.add(creationAgePanel);
        calculatedAttributesPanel.add(creationCostPanel);
        calculatedAttributesPanel.add(creationDelayPanel);
        calculatedAttributesPanel.add(generatedColorPanel);
        calculatedAttributesPanel.add(new JLabel());

        // Now we add the panels to the attributes panel in the order they'll be displayed in
        c = new GridBagConstraints(); // reset the constraints
        attributesPanel = new JPanel(new GridBagLayout());
        attributesPanel.setName("Attributes");
        attributesPanel.add(mutatingAttributesPanel, c);
        c.gridy = 1;
        c.gridheight = 3;
        attributesPanel.add(calculatedAttributesPanel, c);

        //--------------------------------------------------------------------------Attributes Panel End


        //--------------------------------------------------------------------------Motivations Panel End
        motivationsPanel = new JPanel(new GridLayout(1, 2));
        motivationsPanel.setName("Motivations");

        grazerPanel = new JPanel(new GridLayout(3, 2));

        predatorPanel = new JPanel(new GridLayout(3, 2));

        grazerBiasSpinnerLabel = new JLabel("Bias: ");

        predatorBiasSpinnerLabel = new JLabel("Bias: ");

        grazerWeightSpinnerLabel = new JLabel("Weight: ");

        predatorWeightSpinnerLabel = new JLabel("Weight: ");

        grazerBiasSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 10, 1));

        predatorBiasSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 10, 1));

        grazerWeightSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 10, 1));

        predatorWeightSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 10, 1));

        isGrazerToggle = new JCheckBox("Grazer");

        isPredatorToggle = new JCheckBox("Predator");

        grazerPanel.add(isGrazerToggle);
        grazerPanel.add(new JPanel());
        grazerPanel.add(grazerBiasSpinnerLabel);
        grazerPanel.add(grazerBiasSpinner);
        grazerPanel.add(grazerWeightSpinnerLabel);
        grazerPanel.add(grazerWeightSpinner);

        predatorPanel.add(isPredatorToggle);
        predatorPanel.add(new JPanel());
        predatorPanel.add(predatorBiasSpinnerLabel);
        predatorPanel.add(predatorBiasSpinner);
        predatorPanel.add(predatorWeightSpinnerLabel);
        predatorPanel.add(predatorWeightSpinner);

        motivationsPanel.add(grazerPanel);
        motivationsPanel.add(predatorPanel);
        //--------------------------------------------------------------------------Motivations Panel End


        //--------------------------------------------------------------------------Mutations and Inheritance Panel Start
        mutationChanceAndSpawningPanel = new JPanel(new GridLayout(1, 2));
        mutationChanceAndSpawningPanel.setName("Mutation and Inheritance");

        mutationMagnitudePanel = new JPanel();

        mutationMagnitudeLabel = new JLabel("Mutation Chance: ");
        //mutationMagnitudeLabel(new Dimension(360, 25));

        mutationMagnitudeSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));

        mutationMagnitudePanel.add(mutationMagnitudeLabel);
        mutationMagnitudePanel.add(mutationMagnitudeSpinner);
        mutationMagnitudePanel.add(new JLabel("%"));

        mutationChanceAndSpawningPanel.add(mutationMagnitudePanel);
        //--------------------------------------------------------------------------Mutations and Inheritance Panel End

        //--------------------------------------------------------------------------Colour Model Panel Start
        staticModelPanel = new JPanel();
        attributesModelPanel = new JPanel();
        randomModelPanel = new JPanel();
        colorModelPanel = new JPanel();
        colorModelPanel.setName("Color Model");

        colorModelButtonGroup = new ButtonGroup();
        staticModelButton = new JRadioButton("Static");
        attributesModelButton = new JRadioButton("Attributes");
        randomModelButton = new JRadioButton("Random");
        colorModelButtonGroup.add(staticModelButton);
        colorModelButtonGroup.add(attributesModelButton);
        colorModelButtonGroup.add(randomModelButton);

        colorModelPanel.add(staticModelButton);
        colorModelPanel.add(attributesModelButton);
        colorModelPanel.add(randomModelButton);

        randomMagnitudePanel = new JPanel();
        randomMagnitudeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 255, 1));
        randomMagnitudeLabel = new JLabel("Random Magnitude: ");
        randomMagnitudePanel.add(randomMagnitudeLabel);
        randomMagnitudePanel.add(randomMagnitudeSpinner);

        colorModelPanel.add(randomMagnitudePanel);

        //--------------------------------------------------------------------------Colour Model Panel End

        //--------------------------------------------------------------------------Main Pane Start
        mainPane = new JTabbedPane();
        mainPane.setBackground(new Color(224, 224, 224));
        mainPane.add(attributesPanel);
        mainPane.add(motivationsPanel);
        mainPane.add(colorModelPanel);
        //--------------------------------------------------------------------------Main Pane End

        spawningWeightPanel = new JPanel();

        spawningWeightLabel = new JLabel("Spawn Weight: ");
        spawningWeightLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        spawningWeightLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        spawningWeightSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 100, 1));

        spawningWeightPanel.add(spawningWeightLabel);
        spawningWeightPanel.add(spawningWeightSpinner);
        spawningWeightPanel.add(new JLabel("%"));

        mutationChanceAndSpawningPanel.add(spawningWeightPanel);

        updateSettingsButton = new JButton("Update Agent Settings");
        updateSettingsButton.setPreferredSize(new Dimension(updateSettingsButton.getWidth(), 50));

        //nameColorPanel.setBackground(new Color(255, 0, 0));
        //mainPane.setBackground(new Color(0, 255, 0));
        //mutationChanceAndSpawningPanel.setBackground(new Color(0, 0, 255));
        //updateSettingsButton.setBackground(new Color(255, 0, 255));

        c = new GridBagConstraints(); // reset the constraints
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1;
        c.weighty = 1;
        c.gridheight = 2;
        add(nameColorPanel, c);
        c.gridy = 2;
        c.gridheight = 4;
        add(mainPane, c);
        c.gridy = 6;
        c.gridheight = 1;
        add(mutationChanceAndSpawningPanel, c);
        c.gridy = 7;
        add(updateSettingsButton, c);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(seedColourChooserButton)) {
            setCurrentSeedColour();
        }
    }

    private void setCurrentSeedColour() {
        currentSeedColour = JColorChooser.showDialog(null, "Select Agent Colour", Color.white);
        if (currentSeedColour != null) {
            seedColourChooserButton.setBackground(currentSeedColour);
        }
    }

    public JButton getUpdateSettingsButton() {
        return updateSettingsButton;
    }

    public void setAgentSettings(AgentSettings agentSettings) {
        spawningWeightSpinner.setValue(agentSettings.getSpawningWeight());
        agentNameTextField.setText(agentSettings.getName());
        seedColourChooserButton.setBackground(agentSettings.getSeedColor());
        agentCodeValue.setText(agentSettings.getCode().toString());

        rangeSpinner.setValue(agentSettings.getRange());
        sizeSpinner.setValue(agentSettings.getSize());
        creationAmountSpinner.setValue(agentSettings.getCreationAmount());

        energyCapacityValue.setText(agentSettings.getEnergyCapacity().toString());
        eatAmountValue.setText(agentSettings.getEatAmount().toString());
        energyLostPerTurnValue.setText(agentSettings.getEnergyLostPerTurn().toString());
        lifespanValue.setText(agentSettings.getLifespan().toString());
        creationAgeValue.setText(agentSettings.getCreationAge().toString());
        creationDelayValue.setText(agentSettings.getCreationDelay().toString());
        creationCostValue.setText(agentSettings.getCreationCost().toString());

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
        mutationMagnitudeSpinner.setValue(agentSettings.getAttributes().getMutationChance());
        if (agentSettings.getAttributes().getMutationChance() > 0 && agentSettings.getAttributes().getColorModel().equals(ColorModel.ATTRIBUTES)) {
            generatedColorValue.setBackground(agentSettings.getAttributes().getMutatingColor());
        }
        else {
            generatedColorValue.setBackground(agentSettings.getAttributes().getSeedColor());
        }
        if (agentSettings.getColorModel().equals(ColorModel.STATIC)) {
            colorModelButtonGroup.setSelected(staticModelButton.getModel(), true);
        }
        else if (agentSettings.getColorModel().equals(ColorModel.ATTRIBUTES)) {
            colorModelButtonGroup.setSelected(attributesModelButton.getModel(), true);
        }
        else {
            colorModelButtonGroup.setSelected(randomModelButton.getModel(), true);
        }
        randomMagnitudeSpinner.setValue(agentSettings.getRandomColorModelMagnitude());
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
        ColorModel colorModel = ColorModel.STATIC;
        if (colorModelButtonGroup.getSelection().equals(attributesModelButton.getModel())) {
            colorModel = ColorModel.ATTRIBUTES;
        }
        else if (colorModelButtonGroup.getSelection().equals(randomModelButton.getModel())) {
            colorModel = ColorModel.RANDOM;
        }
        Attributes attributes;
        if  ((int) mutationMagnitudeSpinner.getValue() <= 0) {
            attributes = new BasicAttributes(
                    (int) spawningWeightSpinner.getValue(),
                    agentNameTextField.getText(),
                    Integer.parseInt(agentCodeValue.getText()),
                    seedColourChooserButton.getBackground(),
                    colorModel,
                    (int) randomMagnitudeSpinner.getValue(),
                    (int) mutationMagnitudeSpinner.getValue(),
                    (int) rangeSpinner.getValue(),
                    (int) sizeSpinner.getValue(),
                    (int) creationAmountSpinner.getValue());
        } else {
            attributes = new MutatingAttributes(
                    (int) spawningWeightSpinner.getValue(),
                    agentNameTextField.getText(),
                    Integer.parseInt(agentCodeValue.getText()),
                    seedColourChooserButton.getBackground(),
                    colorModel,
                    (int) randomMagnitudeSpinner.getValue(),
                    (int) mutationMagnitudeSpinner.getValue(),
                    (int) rangeSpinner.getValue(),
                    (int) sizeSpinner.getValue(),
                    (int) creationAmountSpinner.getValue());
        }
        return new AgentSettings(attributes, motivations);
    }
}
