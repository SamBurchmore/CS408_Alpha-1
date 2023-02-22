package View;

import Simulation.Environment.EnvironmentSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnvironmentSettingsPanel extends JPanel implements ActionListener {

    private JPanel environmentSettingsPanel;

    private JPanel maxEnergyPanel;
    private JPanel minEnergyPanel;
    private JPanel energyRegenChancePanel;
    private JPanel energyRegenAmountPanel;
    private JPanel environmentSizePanel;

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

    // The panel where the environment colours are set
    private JPanel colorsPanel;

    private SquareButton maxColorButton;
    private SquareButton highColorButton;
    private SquareButton mediumHighColorButton;
    private SquareButton mediumLowColorButton;
    private SquareButton lowColorButton;
    private SquareButton minColorButton;

    private JPanel terrainColorPanel;
    private JLabel terrainColorLabel;
    private SquareButton terrainColorButton;

    // The button which will refresh the environment settings
    private JButton refreshSettingsButton;

    public EnvironmentSettingsPanel() {
        super();
        setLayout(new GridBagLayout());

        environmentSettingsPanel = new JPanel(new GridLayout(3, 2));

        // First all elements are defined and their attributes set
        maxEnergyPanel = new JPanel();
        maxEnergyLabel = new JLabel("Max Energy:         ");
        maxEnergySpinner = new JSpinner(new SpinnerNumberModel(5, 0, 999, 1));
        maxEnergyPanel.add(maxEnergyLabel);
        maxEnergyPanel.add(maxEnergySpinner);

        minEnergyLabel = new JLabel("Min Energy:           ");
        minEnergySpinner = new JSpinner(new SpinnerNumberModel(0, 0, 999, 1));
        minEnergyPanel = new JPanel();
        minEnergyPanel.add(minEnergyLabel);
        minEnergyPanel.add(minEnergySpinner);

        energyRegenChanceLabel = new JLabel("Regen Chance:     ");
        energyRegenChanceSpinner = new JSpinner(new SpinnerNumberModel(100.0, 0, 100.0, 0.01));
        energyRegenChancePanel = new JPanel();
        energyRegenChancePanel.add(energyRegenChanceLabel);
        energyRegenChancePanel.add(energyRegenChanceSpinner);

        energyRegenAmountLabel = new JLabel("Regen Amount:     ");
        energyRegenAmountSpinner = new JSpinner(new SpinnerNumberModel(1, 0, 999, 1));
        energyRegenAmountPanel = new JPanel();
        energyRegenAmountPanel.add(energyRegenAmountLabel);
        energyRegenAmountPanel.add(energyRegenAmountSpinner);

        environmentSizeLabel = new JLabel("Environment Size:");
        environmentSizeSpinner = new JSpinner(new SpinnerNumberModel(300, 1, 600, 1));
        environmentSizePanel = new JPanel();
        environmentSizePanel.add(environmentSizeLabel);
        environmentSizePanel.add(environmentSizeSpinner);

        environmentSettingsPanel.add(maxEnergyPanel);
        environmentSettingsPanel.add(minEnergyPanel);
        environmentSettingsPanel.add(energyRegenChancePanel);
        environmentSettingsPanel.add(energyRegenAmountPanel);
        environmentSettingsPanel.add(environmentSizePanel);

        maxColorButton = new SquareButton("100%");
        maxColorButton.addActionListener(this);
        highColorButton = new SquareButton(">75%");
        highColorButton.addActionListener(this);
        mediumHighColorButton = new SquareButton(">50%");
        mediumHighColorButton.addActionListener(this);
        mediumLowColorButton = new SquareButton(">25%");
        mediumLowColorButton.addActionListener(this);
        lowColorButton = new SquareButton(" >0%");
        lowColorButton.addActionListener(this);
        minColorButton = new SquareButton("  0%");
        minColorButton.addActionListener(this);

        colorsPanel = new JPanel(new GridLayout());
        //colorsPanel.setPreferredSize(new Dimension((int) maxColorButton.getPreferredSize().getWidth() + 10, (int) maxColorButton.getPreferredSize().getHeight() + 10));

        colorsPanel.add(maxColorButton);
        colorsPanel.add(highColorButton);
        colorsPanel.add(mediumHighColorButton);
        colorsPanel.add(mediumLowColorButton);
        colorsPanel.add(lowColorButton);
        colorsPanel.add(minColorButton);

        terrainColorLabel = new JLabel("Terrain Color: ");
        terrainColorButton = new SquareButton();
        terrainColorButton.addActionListener(this);
        terrainColorPanel = new JPanel();
        terrainColorPanel.add(terrainColorLabel);
        terrainColorPanel.add(terrainColorButton);

        refreshSettingsButton = new JButton("Refresh Environment Settings");
        refreshSettingsButton.setPreferredSize(new Dimension(refreshSettingsButton.getWidth(), 50));

        // The GridBag constraints we'll be using to build this panel
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 1;
        c.weightx = 1;
        c.weighty = 1;

        // First we add the top row components
        c.gridy = 0;

        c.gridx = 0;
        add(environmentSettingsPanel, c);

        c.gridy = 1;
        add(terrainColorPanel, c);

        c.gridy = 2;
        add(colorsPanel, c);

        c.gridy = 3;
        add(refreshSettingsButton, c);
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
        if (actionEvent.getSource().equals(terrainColorButton)) {
            setTerrainColor();
        }
    }

    private static Color getContrastingColor(Color color) {
        if (color.getRed() + color.getGreen() + color.getBlue() < 300) {
            return Color.WHITE;
        }
        else return Color.BLACK;
    }

    public void setTerrainColor() {
        Color color = JColorChooser.showDialog(null, "Select Terrain Colour", Color.white);
        if (color != null) {
            terrainColorButton.setBackground(color);
            //terrainColorButton.setForeground(getContrastingColor(color));
        }
    }

    public void setMaxColor() {
        Color color = JColorChooser.showDialog(null, "Select Max Colour", Color.white);
        if (color != null) {
            maxColorButton.setBackground(color);
            maxColorButton.setForeground(getContrastingColor(color));
        }
    }

    public void setHighColor() {
        Color color = JColorChooser.showDialog(null, "Select High Colour", Color.white);
        if (color != null) {
            highColorButton.setBackground(color);
            highColorButton.setForeground(getContrastingColor(color));
        }
    }

    public void setMediumHighColor() {
        Color color = JColorChooser.showDialog(null, "Select Medium High Colour", Color.white);
        if (color != null) {
            mediumHighColorButton.setBackground(color);
            mediumHighColorButton.setForeground(getContrastingColor(color));
        }
    }

    public void setMediumLowColor() {
        Color color = JColorChooser.showDialog(null, "Select Medium Low Colour", Color.white);
        if (color != null) {
            mediumLowColorButton.setBackground(color);
            mediumLowColorButton.setForeground(getContrastingColor(color));
        }
    }

    public void setLowColor() {
        Color color = JColorChooser.showDialog(null, "Select Low Colour", Color.white);
        if (color != null) {
            lowColorButton.setBackground(color);
            lowColorButton.setForeground(getContrastingColor(color));
        }
    }

    public void setMinColor() {
        Color color = JColorChooser.showDialog(null, "Select Min Colour", Color.white);
        if (color != null) {
            minColorButton.setBackground(color);
            minColorButton.setForeground(getContrastingColor(color));
        }
    }

    public Color[] getColors() {
        Color[] colors = new Color[7];
        colors[0] = minColorButton.getBackground();
        colors[1] = lowColorButton.getBackground();
        colors[2] = mediumLowColorButton.getBackground();
        colors[3] = mediumHighColorButton.getBackground();
        colors[4] = highColorButton.getBackground();
        colors[5] = maxColorButton.getBackground();
        colors[6] = terrainColorButton.getBackground();
        return colors;
    }

    public void setColors(Color[] colors) {
        minColorButton.setBackground(colors[0]);
        minColorButton.setForeground(getContrastingColor(colors[0]));
        lowColorButton.setBackground(colors[1]);
        lowColorButton.setForeground(getContrastingColor(colors[1]));
        mediumLowColorButton.setBackground(colors[2]);
        mediumLowColorButton.setForeground(getContrastingColor(colors[2]));
        mediumHighColorButton.setBackground(colors[3]);
        mediumHighColorButton.setForeground(getContrastingColor(colors[3]));
        highColorButton.setBackground(colors[4]);
        highColorButton.setForeground(getContrastingColor(colors[4]));
        maxColorButton.setBackground(colors[5]);
        maxColorButton.setForeground(getContrastingColor(colors[5]));
        terrainColorButton.setBackground(colors[6]);
    }

    public EnvironmentSettings getEnvironmentSettings() {
       return new EnvironmentSettings(
                (int) getEnvironmentSizeSpinner().getValue(),
                (int) getMaxEnergySpinner().getValue(),
                (int) getMinEnergySpinner().getValue(),
                (double) getEnergyRegenChanceSpinner().getValue(),
                (int) getEnergyRegenAmountSpinner().getValue(),
                getColors());
    }

    public void setEnvironmentSettings(EnvironmentSettings environmentSettings) {
        this.environmentSizeSpinner.setValue(environmentSettings.getSize());
        this.getMaxEnergySpinner().setValue(environmentSettings.getMaxEnergyLevel());
        this.getMinEnergySpinner().setValue(environmentSettings.getMinEnergyLevel());
        this.getEnergyRegenChanceSpinner().setValue(environmentSettings.getEnergyRegenChance());
        this.getEnergyRegenAmountSpinner().setValue(environmentSettings.getEnergyRegenAmount());
        setColors(environmentSettings.getEnvironmentColors());
    }
}
