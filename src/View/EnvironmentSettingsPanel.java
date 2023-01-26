package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnvironmentSettingsPanel extends JPanel implements ActionListener {

    // The labels for the user to tell which spinner corresponds to which setting
    private JLabel maxEnergyLabel;
    private JLabel minEnergyLabel;
    private JLabel energyRegenChanceLabel;
    private JLabel energyRegenAmountLabel;
    private JLabel environmentSizeLabel;

    // The spinners which control the value of the settings
    private JSpinner maxEnergySpinner;
    private JSpinner minEnergySpinner;
    private JSpinner energyRegenChanceSpinner;
    private JSpinner energyRegenAmountSpinner;
    private JSpinner environmentSizeSpinner;

    // The button which will refresh the environment settings
    private JButton refreshSettingsButton;

    // The panel where the environment colours are set
    private JPanel colorsPanel;

    private JButton maxColorButton;
    private JButton highColorButton;
    private JButton mediumHighColorButton;
    private JButton mediumLowColorButton;
    private JButton lowColorButton;
    private JButton minColorButton;

    public EnvironmentSettingsPanel() {
        super();
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(300, 204));
        setBorder(BorderFactory.createLineBorder(Color.darkGray));

        // First all elements are defined and their attributes set
        maxEnergyLabel = new JLabel("Max Tile Energy: ");
        maxEnergyLabel.setPreferredSize(new Dimension(150, 20));

        minEnergyLabel = new JLabel("Min Tile Energy: ");
        minEnergyLabel.setPreferredSize(new Dimension(150, 20));

        energyRegenChanceLabel = new JLabel("Energy Regeneration Chance: ");
        energyRegenChanceLabel.setPreferredSize(new Dimension(150, 20));

        energyRegenAmountLabel = new JLabel("Energy Regeneration Amount: ");
        energyRegenAmountLabel.setPreferredSize(new Dimension(150, 20));

        environmentSizeLabel = new JLabel("Environment Size: ");
        environmentSizeLabel.setPreferredSize(new Dimension(150, 20));

        maxEnergySpinner = new JSpinner(new SpinnerNumberModel(5, 0, 1000000, 1));
        maxEnergySpinner.setPreferredSize(new Dimension(150, 20));

        minEnergySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 1000000, 1));
        minEnergySpinner.setPreferredSize(new Dimension(150, 20));

        energyRegenChanceSpinner = new JSpinner(new SpinnerNumberModel(100.0, 0, 100.0, 0.01));
        energyRegenChanceSpinner.setPreferredSize(new Dimension(150, 20));

        energyRegenAmountSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 1000000, 1));
        energyRegenAmountSpinner.setPreferredSize(new Dimension(150, 20));

        environmentSizeSpinner = new JSpinner(new SpinnerNumberModel(300, 1, 600, 1));
        environmentSizeSpinner.setPreferredSize(new Dimension(150, 20));

        refreshSettingsButton = new JButton("Refresh");
        refreshSettingsButton.setPreferredSize(new Dimension(300, 50));

        // Now we set up the color panel
        colorsPanel = new JPanel();
        colorsPanel.setPreferredSize(new Dimension(300, 50));

        maxColorButton = new JButton();
        maxColorButton.setPreferredSize(new Dimension(35, 35));
        maxColorButton.addActionListener(this);

        highColorButton = new JButton();
        highColorButton.setPreferredSize(new Dimension(35, 35));
        highColorButton.addActionListener(this);

        mediumHighColorButton = new JButton();
        mediumHighColorButton.setPreferredSize(new Dimension(35, 35));
        mediumHighColorButton.addActionListener(this);

        mediumLowColorButton = new JButton();
        mediumLowColorButton.setPreferredSize(new Dimension(35, 35));
        mediumLowColorButton.addActionListener(this);

        lowColorButton = new JButton();
        lowColorButton.setPreferredSize(new Dimension(35, 35));
        lowColorButton.addActionListener(this);

        minColorButton = new JButton();
        minColorButton.setPreferredSize(new Dimension(35, 35));
        minColorButton.addActionListener(this);

        colorsPanel.add(maxColorButton);
        colorsPanel.add(highColorButton);
        colorsPanel.add(mediumHighColorButton);
        colorsPanel.add(mediumLowColorButton);
        colorsPanel.add(lowColorButton);
        colorsPanel.add(minColorButton);

        // The GridBag constraints we'll be using to build this panel
        GridBagConstraints c = new GridBagConstraints();

        // Now all elements are added to the panel

        // This forces components to be fill their container horizontally, we want this for every component
        c.fill = GridBagConstraints.HORIZONTAL;
        // This tells the component it should only horizontally span 1 cell
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 1;

        // First we add the top row components
        c.gridy = 0;

        c.gridx = 0;
        this.add(maxEnergyLabel, c);

        c.gridx = 1;
        this.add(maxEnergySpinner, c);

        // Now we add the second row components
        c.gridy = 1;

        c.gridx = 0;
        this.add(minEnergyLabel, c);

        c.gridx = 1;
        this.add(minEnergySpinner, c);

        // Now we add the third row components
        c.gridy = 2;

        c.gridx = 0;
        this.add(energyRegenChanceLabel, c);

        c.gridx = 1;
        this.add(energyRegenChanceSpinner, c);

        // Now we add the forth row of components
        c.gridy = 3;

        c.gridx = 0;
        this.add(energyRegenAmountLabel, c);

        c.gridx = 1;
        this.add(energyRegenAmountSpinner, c);

        // Now we add the fifth row of components
        c.gridy = 4;

        c.gridx = 0;
        this.add(environmentSizeLabel, c);

        c.gridx = 1;
        c.gridwidth = 3;
        this.add(environmentSizeSpinner, c);

        // Now we add the sixth row of components
        c.gridy = 5;

        c.gridx = 0;
        c.gridwidth = 4;
        this.add(refreshSettingsButton, c);

        // Now we add the seventh row of components
        c.gridy = 6;
        this.add(colorsPanel, c);
    }

    public JSpinner getMaxEnergySpinner() {
        return maxEnergySpinner;
    }

    public JSpinner getMinEnergySpinner() {
        return minEnergySpinner;
    }

    public JSpinner getEnergyRegenChanceSpinner() {
        return energyRegenChanceSpinner;
    }

    public JSpinner getEnergyRegenAmountSpinner() {
        return energyRegenAmountSpinner;
    }

    public JButton getRefreshSettingsButton() {
        return refreshSettingsButton;
    }

    public JSpinner getEnvironmentSizeSpinner() {
        return environmentSizeSpinner;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(maxColorButton)) {
            setMaxColor();
        }
        if (actionEvent.getSource().equals(highColorButton)) {
            setHighColor();
        }
        if (actionEvent.getSource().equals(mediumHighColorButton)) {
            setMediumHighColor();
        }
        if (actionEvent.getSource().equals(mediumLowColorButton)) {
            setMediumLowColor();
        }
        if (actionEvent.getSource().equals(lowColorButton)) {
            setLowColor();
        }
        if (actionEvent.getSource().equals(minColorButton)) {
            setMinColor();
        }
    }

    public void setMaxColor() {
        maxColorButton.setBackground(JColorChooser.showDialog(null, "Select Agent Colour", Color.white));
    }

    public void setHighColor() {
        highColorButton.setBackground(JColorChooser.showDialog(null, "Select Agent Colour", Color.white));
    }

    public void setMediumHighColor() {
        mediumHighColorButton.setBackground(JColorChooser.showDialog(null, "Select Agent Colour", Color.white));
    }

    public void setMediumLowColor() {
        mediumLowColorButton.setBackground(JColorChooser.showDialog(null, "Select Agent Colour", Color.white));
    }

    public void setLowColor() {
        lowColorButton.setBackground(JColorChooser.showDialog(null, "Select Agent Colour", Color.white));
    }

    public void setMinColor() {
        minColorButton.setBackground(JColorChooser.showDialog(null, "Select Agent Colour", Color.white));
    }
}
