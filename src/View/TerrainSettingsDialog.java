package View;

import Simulation.SimulationUtility.TerrainSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TerrainSettingsDialog extends JDialog {

    final private JPanel settingsPanel;
    final private JButton okButton;
    final private JPanel fixedCavePanel;
    final private JPanel variableCavePanel;
    final private JPanel okButtonPanel;

    final private JPanel rockSizePanel;
    final private JPanel caveSizePanel;
    final private JPanel caveDensityPanel;
    final private JPanel caveLengthPanel;
    final private JPanel lineDensityPanel;
    final private JPanel bendDensityPanel;
    final private JPanel terrainNumberPanel;
    final private JPanel cavernSizePanel;
    final private JPanel upperCaveSizePanel;
    final private JPanel lowerCaveSizePanel;
    final private JPanel caveWavePanel;
    final private JPanel isTerrainPanel;

    final private JLabel rockSizeLabel;
    final private JLabel caveSizeLabel;
    final private JLabel caveDensityLabel;
    final private JLabel caveLengthLabel;
    final private JLabel lineDensityLabel;
    final private JLabel bendDensityLabel;
    final private JLabel terrainNumberLabel;
    final private JLabel cavernSizeLabel;
    final private JLabel upperCaveSizeLabel;
    final private JLabel lowerCaveSizeLabel;
    final private JLabel caveWaveLabel;

    final private JSpinner rockSizeSpinner;
    final private JSpinner caveSizeSpinner;
    final private JSpinner caveDensitySpinner;
    final private JSpinner caveLengthSpinner;
    final private JSpinner lineDensitySpinner;
    final private JSpinner bendDensitySpinner;
    final private JSpinner terrainNumberSpinner;
    final private JSpinner cavernSizeSpinner;
    final private JSpinner upperCaveSizeSpinner;
    final private JSpinner lowerCaveSizeSpinner;
    final private JSpinner caveWaveSpinner;
    final private JCheckBox isTerrainCheckBox;

    public TerrainSettingsDialog(JFrame parent, TerrainSettings terrainSettings) {
        super(parent);
        setSize(new Dimension(400, 500));
        settingsPanel = new JPanel();
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.PAGE_AXIS));
        settingsPanel.setPreferredSize(new Dimension(400, 300));

        rockSizeSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 100, 1));
        rockSizeLabel = new JLabel("Rock Size");
        rockSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        rockSizePanel = new JPanel();
        rockSizePanel.add(rockSizeLabel);
        rockSizePanel.add(rockSizeSpinner);

        caveSizeSpinner = new JSpinner(new SpinnerNumberModel(6, 1, 100, 1));
        caveSizeLabel = new JLabel("Cave Size");
        caveSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        caveSizePanel = new JPanel();
        caveSizePanel.add(caveSizeLabel);
        caveSizePanel.add(caveSizeSpinner);

        caveDensitySpinner = new JSpinner(new SpinnerNumberModel(10.0, 0.0001, 100.0, 0.001));
        caveDensityLabel = new JLabel("Cave Density");
        caveDensityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        caveDensityPanel = new JPanel();
        caveDensityPanel.add(caveDensityLabel);
        caveDensityPanel.add(caveDensitySpinner);
        caveDensityPanel.add(new JLabel("%"));

        caveLengthSpinner = new JSpinner(new SpinnerNumberModel(400, 1, 20000, 1));
        caveLengthLabel = new JLabel("Cave Length");
        caveLengthLabel.setHorizontalAlignment(SwingConstants.CENTER);
        caveLengthPanel = new JPanel();
        caveLengthPanel.add(caveLengthLabel);
        caveLengthPanel.add(caveLengthSpinner);

        lineDensitySpinner = new JSpinner(new SpinnerNumberModel(10.0, 0.001, 100.0, 0.001));
        lineDensityLabel = new JLabel("Cave Weight");
        lineDensityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lineDensityPanel = new JPanel();
        lineDensityPanel.add(lineDensityLabel);
        lineDensityPanel.add(lineDensitySpinner);
        lineDensityPanel.add(new JLabel("%"));

        bendDensitySpinner = new JSpinner(new SpinnerNumberModel(10.0, 0.0001, 100.0, 0.001));
        bendDensityLabel = new JLabel("Bend Density");
        bendDensityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bendDensityPanel = new JPanel();
        bendDensityPanel.add(bendDensityLabel);
        bendDensityPanel.add(bendDensitySpinner);
        bendDensityPanel.add(new JLabel("%"));

        terrainNumberSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000.0, 1));
        terrainNumberLabel = new JLabel("Cave No.");
        terrainNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
        terrainNumberPanel = new JPanel();
        terrainNumberPanel.add(terrainNumberLabel);
        terrainNumberPanel.add(terrainNumberSpinner);

        cavernSizeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        cavernSizeLabel = new JLabel("Cavern Size");
        cavernSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        cavernSizePanel = new JPanel();
        cavernSizePanel.add(cavernSizeLabel);
        cavernSizePanel.add(cavernSizeSpinner);

        upperCaveSizeSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        upperCaveSizeLabel = new JLabel("Upper Cave Size");
        upperCaveSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        upperCaveSizePanel = new JPanel();
        upperCaveSizePanel.add(upperCaveSizeLabel);
        upperCaveSizePanel.add(upperCaveSizeSpinner);

        lowerCaveSizeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        lowerCaveSizeLabel = new JLabel("Lower Cave Size");
        lowerCaveSizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lowerCaveSizePanel = new JPanel();
        lowerCaveSizePanel.add(lowerCaveSizeLabel);
        lowerCaveSizePanel.add(lowerCaveSizeSpinner);

        caveWaveSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        caveWaveLabel = new JLabel("Cave Wave");
        caveWaveLabel.setHorizontalAlignment(SwingConstants.CENTER);
        caveWavePanel = new JPanel();
        caveWavePanel.add(caveWaveLabel);
        caveWavePanel.add(caveWaveSpinner);

        isTerrainCheckBox = new JCheckBox("Fill");
        isTerrainPanel = new JPanel();
        isTerrainPanel.add(isTerrainCheckBox);

        setTerrainSettings(terrainSettings);

        fixedCavePanel = new JPanel(new GridLayout(3, 3));

        fixedCavePanel.add(rockSizePanel);
        fixedCavePanel.add(caveSizePanel);
        fixedCavePanel.add(cavernSizePanel);
        fixedCavePanel.add(caveDensityPanel);
        fixedCavePanel.add(bendDensityPanel);
        fixedCavePanel.add(caveLengthPanel);
        fixedCavePanel.add(lineDensityPanel);
        fixedCavePanel.add(terrainNumberPanel);

        variableCavePanel = new JPanel(new GridLayout(1, 3));

        variableCavePanel.add(upperCaveSizePanel);
        variableCavePanel.add(lowerCaveSizePanel);
        variableCavePanel.add(caveWavePanel);

        okButtonPanel = new JPanel(new GridLayout(1, 2));

        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TerrainSettingsDialog.this.dispose();
            }
        });

        okButtonPanel.add(isTerrainPanel);
        okButtonPanel.add(okButton);

        JLabel fixedLabel = new JLabel("Fixed Cave Settings");
        fixedLabel.setAlignmentX(CENTER_ALIGNMENT);
        fixedLabel.setFont(new Font("Dialog", Font.BOLD, 12));
        settingsPanel.add(fixedLabel);
        settingsPanel.add(fixedCavePanel);
        JLabel variableLabel = new JLabel("Variable Cave Settings");
        variableLabel.setAlignmentX(CENTER_ALIGNMENT);
        variableLabel.setFont(new Font("Dialog", Font.BOLD, 12));
        settingsPanel.add(variableLabel);
        settingsPanel.add(variableCavePanel);
        settingsPanel.add(okButtonPanel);

        add(settingsPanel);

        setName("Terrain Settings");
        setModal(true);
        setVisible(true);
        pack();
        setLocationRelativeTo(parent);
    }

    public Integer[] getSettings() {
        return null;
    }

    public TerrainSettings getTerrainSettings() {
        return new TerrainSettings(
                (int) rockSizeSpinner.getValue(),
                (int) caveSizeSpinner.getValue(),
                (int) ((double) caveDensitySpinner.getValue() * 100),
                (int) ((double) bendDensitySpinner.getValue() * 100),
                (int) caveLengthSpinner.getValue(),
                (int) ((double) lineDensitySpinner.getValue() * 100),
                (int) cavernSizeSpinner.getValue(),
                (int) upperCaveSizeSpinner.getValue(),
                (int) lowerCaveSizeSpinner.getValue(),
                (int) caveWaveSpinner.getValue(),
                (int) ((double) terrainNumberSpinner.getValue()),
                isTerrainCheckBox.isSelected()
        );
    }

    public void setTerrainSettings(TerrainSettings terrainSettings) {
        rockSizeSpinner.setValue(terrainSettings.getRockSize());
        caveSizeSpinner.setValue(terrainSettings.getCaveSize());
        caveDensitySpinner.setValue((double) terrainSettings.getCaveDensity() / 100);
        bendDensitySpinner.setValue((double) terrainSettings.getBendDensity() / 100);
        caveLengthSpinner.setValue(terrainSettings.getCaveLength());
        lineDensitySpinner.setValue((double) terrainSettings.getLineDensity() / 100);
        cavernSizeSpinner.setValue(terrainSettings.getCavernSize());
        upperCaveSizeSpinner.setValue(terrainSettings.getUpperCaveSize());
        lowerCaveSizeSpinner.setValue(terrainSettings.getLowerCaveSize());
        caveWaveSpinner.setValue(terrainSettings.getCaveWave());
        terrainNumberSpinner.setValue((double) terrainSettings.getTerrainAmount());
        isTerrainCheckBox.setSelected(terrainSettings.isTerrain());
    }
}
