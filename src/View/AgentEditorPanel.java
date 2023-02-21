package View;

import Simulation.Agent.AgentStructs.ColorModel;
import Simulation.Agent.AgentUtility.AgentSettings;
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
    final private JPanel motivationsPanel;
    final private JPanel attributesPanel;
    final private JPanel mutationChanceAndSpawningPanel;

    // The components where the agents name and seed color are set-----------------------------</
    final private JTextField agentNameTextField;
    final private JButton seedColourChooserButton;
    private Color currentSeedColour;
    final private JSpinner agentCodeSpinner;
    final private JPanel agentCodePanel;
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
        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(460, 382));
        setBorder(BorderFactory.createLineBorder(Color.darkGray));

        //--------------------------------------------------------------------------Name and Color Panel Start
        seedColourChooserButton = new JButton();
        seedColourChooserButton.setPreferredSize(new Dimension(75, 75));
        seedColourChooserButton.addActionListener(this);
        currentSeedColour = new Color(255, 255, 255);
        seedColourChooserButton.setBackground(currentSeedColour);

        agentNameTextField = new JTextField("Agent Name");
        agentNameTextField.setPreferredSize(new Dimension(150, 25));

        agentCodePanel = new JPanel();
        agentCodePanel.setPreferredSize(new Dimension(180, 30));
        agentCodePanel.setVisible(false);

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
        nameColorPanel.add(seedColourChooserButton);
        nameColorPanel.add(agentCodePanel);
        //--------------------------------------------------------------------------Name and Color Panel End


        //--------------------------------------------------------------------------Attributes Panel Start
        attributesPanel = new JPanel();
        attributesPanel.setPreferredSize(new Dimension(450, 130));
        attributesPanel.setName("Attributes");

        // Mutable attributes first
        mutatingAttributesPanel= new JPanel();
        mutatingAttributesPanel.setPreferredSize(new Dimension(450,  30));

        sizePanel = new JPanel();
        sizePanel.setPreferredSize(new Dimension(150, 25));
        sizeLabel = new JLabel("Size: ");
        sizeLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        sizeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));

        creationAmountPanel = new JPanel();
        creationAmountPanel.setPreferredSize(new Dimension(150, 25));
        creationAmountLabel = new JLabel("Creation Size: ");
        creationAmountLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        creationAmountSpinner = new JSpinner(new SpinnerNumberModel(4, 1, 8, 1));

        rangePanel = new JPanel();
        rangePanel.setPreferredSize(new Dimension(150, 25));
        rangeLabel = new JLabel("Range: ");
        rangeLabel.setFont(new Font("Dialog", Font.PLAIN, 12));
        rangeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 5, 1));

        mutatingAttributesPanel.add(sizeLabel);
        mutatingAttributesPanel.add(sizeSpinner);
        mutatingAttributesPanel.add(creationAmountLabel);
        mutatingAttributesPanel.add(creationAmountSpinner);
        mutatingAttributesPanel.add(rangeLabel);
        mutatingAttributesPanel.add(rangeSpinner);

        // Calculated attributes panel
        calculatedAttributesPanel = new JPanel();
        calculatedAttributesPanel.setPreferredSize(new Dimension(450, 100));
        calculatedAttributesPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 1));

        // Energy cap panel
        energyCapacityPanel = new JPanel();
        energyCapacityPanel.setPreferredSize(new Dimension(140, 25));

        energyCapacityLabel = new JLabel("Max Energy: ");
        energyCapacityLabel.setPreferredSize(new Dimension(100, 25));
        energyCapacityLabel.setFont(new Font("Dialog", Font.PLAIN, 12));

        energyCapacityValue = new JLabel("0");
        energyCapacityValue.setPreferredSize(new Dimension(30, 25));
        energyCapacityValue.setHorizontalAlignment(SwingConstants.RIGHT);
        energyCapacityValue.setFont(new Font("Dialog", Font.BOLD, 12));

        energyCapacityPanel.add(energyCapacityLabel);
        energyCapacityPanel.add(energyCapacityValue);

        // Eat amount panel
        eatAmountPanel = new JPanel();
        eatAmountPanel.setPreferredSize(new Dimension(140, 25));

        eatAmountLabel = new JLabel("| Eat Amount: ");
        eatAmountLabel.setPreferredSize(new Dimension(100, 25));
        eatAmountLabel.setFont(new Font("Dialog", Font.PLAIN, 12));

        eatAmountValue = new JLabel("0");
        eatAmountValue.setPreferredSize(new Dimension(30, 25));
        eatAmountValue.setHorizontalAlignment(SwingConstants.RIGHT);
        eatAmountValue.setFont(new Font("Dialog", Font.BOLD, 12));

        eatAmountPanel.add(eatAmountLabel);
        eatAmountPanel.add(eatAmountValue);

        // Energy lost per turn panel
        energyLostPerTurnPanel = new JPanel();
        energyLostPerTurnPanel.setPreferredSize(new Dimension(140, 25));

        energyLostPerTurnLabel = new JLabel("| Energy Burned: ");
        energyLostPerTurnLabel.setPreferredSize(new Dimension(100, 25));
        energyLostPerTurnLabel.setFont(new Font("Dialog", Font.PLAIN, 12));

        energyLostPerTurnValue = new JLabel("0");
        energyLostPerTurnValue.setPreferredSize(new Dimension(30, 25));
        energyLostPerTurnValue.setHorizontalAlignment(SwingConstants.RIGHT);
        energyLostPerTurnValue.setFont(new Font("Dialog", Font.BOLD, 12));

        energyLostPerTurnPanel.add(energyLostPerTurnLabel);
        energyLostPerTurnPanel.add(energyLostPerTurnValue);

        // Lifespan panel
        lifespanPanel = new JPanel();
        lifespanPanel.setPreferredSize(new Dimension(140, 25));

        lifespanLabel = new JLabel("Lifespan: ");
        lifespanLabel.setPreferredSize(new Dimension(100, 25));
        lifespanLabel.setFont(new Font("Dialog", Font.PLAIN, 12));

        lifespanValue = new JLabel("0");
        lifespanValue.setPreferredSize(new Dimension(30, 25));
        lifespanValue.setHorizontalAlignment(SwingConstants.RIGHT);
        lifespanValue.setFont(new Font("Dialog", Font.BOLD, 12));

        lifespanPanel.add(lifespanLabel);
        lifespanPanel.add(lifespanValue);

        // Creation age panel
        creationAgePanel = new JPanel();
        creationAgePanel.setPreferredSize(new Dimension(140, 25));

        creationAgeLabel = new JLabel("| Creation Age: ");
        creationAgeLabel.setPreferredSize(new Dimension(100, 25));
        creationAgeLabel.setFont(new Font("Dialog", Font.PLAIN, 12));

        creationAgeValue = new JLabel("0");
        creationAgeValue.setPreferredSize(new Dimension(30, 25));
        creationAgeValue.setHorizontalAlignment(SwingConstants.RIGHT);
        creationAgeValue.setFont(new Font("Dialog", Font.BOLD, 12));

        creationAgePanel.add(creationAgeLabel);
        creationAgePanel.add(creationAgeValue);

        // Creation cost panel
        creationCostPanel = new JPanel();
        creationCostPanel.setPreferredSize(new Dimension(140, 25));

        creationCostLabel = new JLabel("| Creation Cost: ");
        creationCostLabel.setPreferredSize(new Dimension(100, 25));
        creationCostLabel.setFont(new Font("Dialog", Font.PLAIN, 12));

        creationCostValue = new JLabel("0");
        creationCostValue.setPreferredSize(new Dimension(30, 25));
        creationCostValue.setHorizontalAlignment(SwingConstants.RIGHT);
        creationCostValue.setFont(new Font("Dialog", Font.BOLD, 12));

        creationCostPanel.add(creationCostLabel);
        creationCostPanel.add(creationCostValue);

        // Creation delay panel
        creationDelayPanel = new JPanel();
        creationDelayPanel.setPreferredSize(new Dimension(140, 25));

        creationDelayLabel = new JLabel("Creation Delay: ");
        creationDelayLabel.setPreferredSize(new Dimension(100, 25));
        creationDelayLabel.setFont(new Font("Dialog", Font.PLAIN, 12));

        creationDelayValue = new JLabel("0");
        creationDelayValue.setPreferredSize(new Dimension(30, 25));
        creationDelayValue.setHorizontalAlignment(SwingConstants.RIGHT);
        creationDelayValue.setFont(new Font("Dialog", Font.BOLD, 12));

        creationDelayPanel.add(creationDelayLabel);
        creationDelayPanel.add(creationDelayValue);

        // Generated color panel
        generatedColorPanel = new JPanel();
        generatedColorPanel.setPreferredSize(new Dimension(140, 25));

        generatedColorLabel = new JLabel("| Color: ");
        generatedColorLabel.setPreferredSize(new Dimension(100, 25));
        generatedColorLabel.setFont(new Font("Dialog", Font.PLAIN, 12));

        generatedColorValue = new JLabel();
        generatedColorValue.setPreferredSize(new Dimension(30, 25));
        generatedColorValue.setHorizontalAlignment(SwingConstants.RIGHT);
        generatedColorValue.setFont(new Font("Dialog", Font.PLAIN, 12));
        generatedColorValue.setOpaque(true);

        generatedColorPanel.add(generatedColorLabel);
        generatedColorPanel.add(generatedColorValue);

        JLabel blankLabel = new JLabel(" |");
        blankLabel.setPreferredSize(new Dimension(140, 25));

        // Add all the panels
        calculatedAttributesPanel.add(energyCapacityPanel);
        calculatedAttributesPanel.add(eatAmountPanel);
        calculatedAttributesPanel.add(energyLostPerTurnPanel);
        calculatedAttributesPanel.add(lifespanPanel);
        calculatedAttributesPanel.add(creationAgePanel);
        calculatedAttributesPanel.add(creationCostPanel);
        calculatedAttributesPanel.add(creationDelayPanel);
        calculatedAttributesPanel.add(generatedColorPanel);
        calculatedAttributesPanel.add(blankLabel);

        // Now we add the panels to the attributes panel in the order they'll be displayed in
        attributesPanel.add(mutatingAttributesPanel);
        attributesPanel.add(calculatedAttributesPanel);

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
        mutationChanceAndSpawningPanel = new JPanel(new GridLayout(1, 2));
        mutationChanceAndSpawningPanel.setPreferredSize(new Dimension(400, 30));
        mutationChanceAndSpawningPanel.setName("Mutation and Inheritance");

        mutationMagnitudePanel = new JPanel();
        mutationMagnitudePanel.setPreferredSize(new Dimension(100, 25));

        mutationMagnitudeLabel = new JLabel("Mutation Chance: ");
        //mutationMagnitudeLabel.setPreferredSize(new Dimension(360, 25));

        mutationMagnitudeSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        mutationMagnitudeSpinner.setPreferredSize(new Dimension(60, 25));

        mutationMagnitudePanel.add(mutationMagnitudeLabel);
        mutationMagnitudePanel.add(mutationMagnitudeSpinner);

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

        //--------------------------------------------------------------------------Colour Model Panel End

        //--------------------------------------------------------------------------Main Pane Start
        mainPane = new JTabbedPane();
        mainPane.setBackground(new Color(224, 224, 224));
        mainPane.setPreferredSize(new Dimension(450, 180));
        mainPane.add(attributesPanel);
        mainPane.add(motivationsPanel);
        mainPane.add(colorModelPanel);
        //--------------------------------------------------------------------------Main Pane End

        spawningWeightPanel = new JPanel();
        spawningWeightPanel.setPreferredSize(new Dimension(200, 30));

        spawningWeightLabel = new JLabel("Spawn Weight: ");
        spawningWeightLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        spawningWeightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        spawningWeightLabel.setPreferredSize(new Dimension(90, 25));

        spawningWeightSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 1, 0.01));
        spawningWeightSpinner.setPreferredSize(new Dimension(60, 25));

        spawningWeightPanel.add(spawningWeightLabel);
        spawningWeightPanel.add(spawningWeightSpinner);

        mutationChanceAndSpawningPanel.add(spawningWeightPanel);

        updateSettingsButton = new JButton("Update Settings");
        updateSettingsButton.setPreferredSize(new Dimension(450, 50));

        add(nameColorPanel);
        add(mainPane);
        add(mutationChanceAndSpawningPanel);
        add(updateSettingsButton);

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
        seedColourChooserButton.setBackground(agentSettings.getColor());
        agentCodeSpinner.setValue(agentSettings.getCode());

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
        return new AgentSettings(
                              (double) spawningWeightSpinner.getValue(),
                                       agentNameTextField.getText(),
                                 (int) agentCodeSpinner.getValue(),
                                       seedColourChooserButton.getBackground(),
                                 colorModel,
                                 (int) mutationMagnitudeSpinner.getValue(),
                                 (int) rangeSpinner.getValue(),
                                 (int) sizeSpinner.getValue(),
                                 (int) creationAmountSpinner.getValue(),
                                       motivations);
    }
}
