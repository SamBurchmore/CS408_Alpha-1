package View;

import Model.AgentEditor.AgentSettings;
import Model.Agents.AgentConcreteComponents.CreatorMotivation;
import Model.Agents.AgentConcreteComponents.GrazerMotivation;
import Model.Agents.AgentConcreteComponents.PredatorMotivation;
import Model.Agents.AgentInterfaces.Motivation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class AgentEditorPanel extends JPanel implements ActionListener {

    private JTabbedPane mainPane;

    // The panels we'll use to group settings
    private JPanel nameColorPanel;
    private JPanel attributesPanel;
    private JPanel motivationsPanel;
    private JPanel mutationsAndInheritancePanel;
    private JPanel spawningWeightPanel;

    // The text field where the agents name is set
    private JTextField agentNameTextField;

    // The button the agents color is chosen through
    private JButton colourChooserButton;
    private Color currentColour;

    // The panels we'll use to group the attributes labels and spinners
    private JPanel rangePanel;
    private JPanel sizePanel;
    private JPanel energyCapacityPanel;
    private JPanel eatAmountPanel;
    private JPanel lifespanPanel;
    private JPanel creationAgePanel;
    private JPanel creationAmountPanel;
    private JPanel creationDelayPanel;
    private JPanel agentCodePanel;

    // The labels for the agent attributes settings
    private JLabel rangeLabel;
    private JLabel sizeLabel; // How much energy the agent uses and gives when predated on : int 0-255
    private JLabel energyCapacityLabel; // Maximum amount of energy agent can store : int 0 - 999999
    private JLabel eatAmountLabel; // How much energy agent gets from eating : int 0 - 999999
    private JLabel lifespanLabel; // How long the agent can live for : int 0 - 999999
    private JLabel creationAgeLabel; // How old an agent needs to be to breed : int 0 - 999999
    private JLabel creationAmountLabel; // How many agents are created when the agent breeds : int 0 - 8
    private JLabel creationDelayLabel; // How many turns an agent needs to wait before breeding again : int 0 - 999999
    private JLabel agentCodeLabel;

    // The spinners which control the agents settings
    private JSpinner rangeSpinner;
    private JSpinner sizeSpinner;
    private JSpinner energyCapacitySpinner;
    private JSpinner eatAmountSpinner;
    private JSpinner lifespanSpinner;
    private JSpinner creationAgeSpinner;
    private JSpinner creationAmountSpinner;
    private JSpinner creationDelaySpinner;
    private JSpinner agentCodeSpinner;

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
        agentCodeSpinner.setEnabled(true);

        agentCodePanel.add(agentCodeLabel);
        agentCodePanel.add(agentCodeSpinner);

        nameColorPanel = new JPanel();
        nameColorPanel.setPreferredSize(new Dimension(450, 85));

        nameColorPanel.add(agentNameTextField);
        nameColorPanel.add(colourChooserButton);
        nameColorPanel.add(agentCodePanel);
        //--------------------------------------------------------------------------Name and Color Panel End


        //--------------------------------------------------------------------------Attributes Panel Start
        rangePanel = new JPanel();
        rangePanel.setPreferredSize(new Dimension(130, 25));

        sizePanel = new JPanel();
        sizePanel.setPreferredSize(new Dimension(130, 25));

        energyCapacityPanel = new JPanel();
        energyCapacityPanel.setPreferredSize(new Dimension(130, 25));

        eatAmountPanel = new JPanel();
        eatAmountPanel.setPreferredSize(new Dimension(130, 25));

        lifespanPanel = new JPanel();
        lifespanPanel.setPreferredSize(new Dimension(130, 25));

        creationAgePanel = new JPanel();
        creationAgePanel.setPreferredSize(new Dimension(130, 25));

        creationAmountPanel = new JPanel();
        creationAmountPanel.setPreferredSize(new Dimension(130, 25));

        creationDelayPanel = new JPanel();
        creationDelayPanel.setPreferredSize(new Dimension(130, 25));

        rangeLabel = new JLabel("Range: ");
        rangeLabel.setPreferredSize(new Dimension(85, 25));

        sizeLabel = new JLabel("Size: ");
        sizeLabel.setPreferredSize(new Dimension(85, 25));

        energyCapacityLabel = new JLabel("Max Energy: ");
        energyCapacityLabel.setPreferredSize(new Dimension(85, 25));

        eatAmountLabel = new JLabel("Eat Amount: ");
        eatAmountLabel.setPreferredSize(new Dimension(85, 25));

        lifespanLabel = new JLabel("Max Lifespan: ");
        lifespanLabel.setPreferredSize(new Dimension(85, 25));

        creationAgeLabel = new JLabel("Creation Age: ");
        creationAgeLabel.setPreferredSize(new Dimension(85, 25));

        creationAmountLabel = new JLabel("Creation Size: ");
        creationAmountLabel.setPreferredSize(new Dimension(85, 25));

        creationDelayLabel = new JLabel("Creation Delay: ");
        creationDelayLabel.setPreferredSize(new Dimension(85, 25));

        rangeSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 25, 1));
        rangeSpinner.setPreferredSize(new Dimension(65, 25));

        sizeSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 255, 1));
        sizeSpinner.setPreferredSize(new Dimension(65, 25));

        energyCapacitySpinner = new JSpinner(new SpinnerNumberModel(10, 0, 999999, 1));
        energyCapacitySpinner.setPreferredSize(new Dimension(65, 25));

        eatAmountSpinner = new JSpinner(new SpinnerNumberModel(2, 0, 999999, 1));
        eatAmountSpinner.setPreferredSize(new Dimension(65, 25));

        lifespanSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 999999, 1));
        lifespanSpinner.setPreferredSize(new Dimension(65, 25));

        creationAgeSpinner = new JSpinner(new SpinnerNumberModel(5, 0, 999999, 1));
        creationAgeSpinner.setPreferredSize(new Dimension(65, 25));

        creationAmountSpinner = new JSpinner(new SpinnerNumberModel(4, 1, 9, 1));
        creationAmountSpinner.setPreferredSize(new Dimension(65, 25));

        creationDelaySpinner = new JSpinner(new SpinnerNumberModel(1, 0, 999999, 1));
        creationDelaySpinner.setPreferredSize(new Dimension(65, 25));

        rangePanel.add(rangeLabel);
        rangePanel.add(rangeSpinner);

        sizePanel.add(sizeLabel);
        sizePanel.add(sizeSpinner);

        energyCapacityPanel.add(energyCapacityLabel);
        energyCapacityPanel.add(energyCapacitySpinner);

        eatAmountPanel.add(eatAmountLabel);
        eatAmountPanel.add(eatAmountSpinner);

        lifespanPanel.add(lifespanLabel);
        lifespanPanel.add(lifespanSpinner);

        creationAgePanel.add(creationAgeLabel);
        creationAgePanel.add(creationAgeSpinner);

        creationAmountPanel.add(creationAmountLabel);
        creationAmountPanel.add(creationAmountSpinner);

        creationDelayPanel.add(creationDelayLabel);
        creationDelayPanel.add(creationDelaySpinner);

        attributesPanel = new JPanel(new GridLayout(5, 2));
        attributesPanel.setPreferredSize(new Dimension(450, 160));
        attributesPanel.setName("Attributes");

        // Now we add the panels to the attributes panel in the order they'll be displayed in
        attributesPanel.add(rangePanel);
        attributesPanel.add(sizePanel);
        attributesPanel.add(energyCapacityPanel);
        attributesPanel.add(eatAmountPanel);
        attributesPanel.add(lifespanPanel);
        attributesPanel.add(creationAgePanel);
        attributesPanel.add(creationAmountPanel);
        attributesPanel.add(creationDelayPanel);
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
        mutationsAndInheritancePanel.setPreferredSize(new Dimension(450, 160));
        mutationsAndInheritancePanel.setName("Mutation and Inheritance");
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
        spawningWeightPanel.setPreferredSize(new Dimension(220, 40));
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

    public JSpinner getEnergyCapacitySpinner() {
        return energyCapacitySpinner;
    }

    public JSpinner getEatAmountSpinner() {
        return eatAmountSpinner;
    }

    public JSpinner getLifespanSpinner() {
        return lifespanSpinner;
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
        agentNameTextField.setText(agentSettings.getName());
        colourChooserButton.setBackground(agentSettings.getColor());
        sizeSpinner.setValue(agentSettings.getSize());
        rangeSpinner.setValue(agentSettings.getRange());
        energyCapacitySpinner.setValue(agentSettings.getEnergyCapacity());
        eatAmountSpinner.setValue(agentSettings.getEatAmount());
        lifespanSpinner.setValue(agentSettings.getLifespan());
        creationAgeSpinner.setValue(agentSettings.getCreationAge());
        creationAmountSpinner.setValue(agentSettings.getCreationAmount());
        creationDelaySpinner.setValue(agentSettings.getCreationDelay());
        agentCodeSpinner.setValue(agentSettings.getCode());
        spawningWeightSpinner.setValue(agentSettings.getSpawningWeight());
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
        return new AgentSettings((double) spawningWeightSpinner.getValue(),
                                       agentNameTextField.getText(),
                                 (int) agentCodeSpinner.getValue(),
                                       colourChooserButton.getBackground(),
                                 (int) rangeSpinner.getValue(),
                                 (int) sizeSpinner.getValue(),
                                 (int) energyCapacitySpinner.getValue(),
                                 (int) eatAmountSpinner.getValue(),
                                 (int) lifespanSpinner.getValue(),
                                 (int) creationAgeSpinner.getValue(),
                                 (int) creationAmountSpinner.getValue(),
                                 (int) creationDelaySpinner.getValue(),
                                       motivations);
    }
}
