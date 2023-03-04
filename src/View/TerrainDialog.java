package View;

import Simulation.SimulationUtility.TerrainSettings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TerrainDialog extends JDialog {

    final private JPanel settingsPanel;
    final private JButton okButton;

    final private JPanel rockSizePanel;
    final private JPanel clusterSizePanel;
    final private JPanel clusterDensityPanel;
    final private JPanel upperCaveSizePanel;
    final private JPanel lowerCaveSizePanel;
    final private JPanel lengthPanel;
    final private JPanel lineDensityPanel;
    final private JPanel bendDensityPanel;
    final private JPanel caveWavePanel;
    final private JPanel terrainDensityPanel;

    final private JLabel rockSizeLabel;
    final private JLabel clusterSizeLabel;
    final private JLabel clusterDensityLabel;
    final private JLabel upperCaveSizeLabel;
    final private JLabel lowerCaveSizeLabel;
    final private JLabel lengthLabel;
    final private JLabel lineDensityLabel;
    final private JLabel bendDensityLabel;
    final private JLabel caveWaveLabel;
    final private JLabel terrainDensityLabel;

    final private JSpinner rockSizeSpinner;
    final private JSpinner clusterSizeSpinner;
    final private JSpinner clusterDensitySpinner;
    final private JSpinner upperCaveSizeSpinner;
    final private JSpinner lowerCaveSizeSpinner;
    final private JSpinner lengthSpinner;
    final private JSpinner lineDensitySpinner;
    final private JSpinner bendDensitySpinner;
    final private JSpinner caveWaveSpinner;
    final private JSpinner terrainDensitySpinner;

    public TerrainDialog(JFrame parent, TerrainSettings terrainSettings) {
        super(parent);
        setSize(new Dimension(400, 300));

        settingsPanel = new JPanel(new GridLayout(4, 2));
        settingsPanel.setPreferredSize(new Dimension(400, 200));

        rockSizeSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 100, 1));
        rockSizeLabel = new JLabel("Rock Size: ");
        rockSizePanel = new JPanel();
        rockSizePanel.add(rockSizeLabel);
        rockSizePanel.add(rockSizeSpinner);

        clusterSizeSpinner = new JSpinner(new SpinnerNumberModel(6, 1, 100, 1));
        clusterSizeLabel = new JLabel("Cluster Size: ");
        clusterSizePanel = new JPanel();
        clusterSizePanel.add(clusterSizeLabel);
        clusterSizePanel.add(clusterSizeSpinner);

        clusterDensitySpinner = new JSpinner(new SpinnerNumberModel(10.0, 0.0001, 100.0, 0.001));
        clusterDensityLabel = new JLabel("Cluster Density: ");
        clusterDensityPanel = new JPanel();
        clusterDensityPanel.add(clusterDensityLabel);
        clusterDensityPanel.add(clusterDensitySpinner);

        upperCaveSizeSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        upperCaveSizeLabel = new JLabel("Upper Cave Size: ");
        upperCaveSizePanel = new JPanel();
        upperCaveSizePanel.add(upperCaveSizeLabel);
        upperCaveSizePanel.add(upperCaveSizeSpinner);

        lowerCaveSizeSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        lowerCaveSizeLabel = new JLabel("Lower Cave Size: ");
        lowerCaveSizePanel = new JPanel();
        lowerCaveSizePanel.add(lowerCaveSizeLabel);
        lowerCaveSizePanel.add(lowerCaveSizeSpinner);

        caveWaveSpinner = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        caveWaveLabel = new JLabel("Cave Wave: ");
        caveWavePanel = new JPanel();
        caveWavePanel.add(caveWaveLabel);
        caveWavePanel.add(caveWaveSpinner);


        lengthSpinner = new JSpinner(new SpinnerNumberModel(400, 1, 20000, 1));
        lengthLabel = new JLabel("Line Size: ");
        lengthPanel = new JPanel();
        lengthPanel.add(lengthLabel);
        lengthPanel.add(lengthSpinner);

        lineDensitySpinner = new JSpinner(new SpinnerNumberModel(10.0, 0.001, 100.0, 0.001));
        lineDensityLabel = new JLabel("Line Density: ");
        lineDensityPanel = new JPanel();
        lineDensityPanel.add(lineDensityLabel);
        lineDensityPanel.add(lineDensitySpinner);

        bendDensitySpinner = new JSpinner(new SpinnerNumberModel(10.0, 0.0001, 100.0, 0.001));
        bendDensityLabel = new JLabel("Bend Density: ");
        bendDensityPanel = new JPanel();
        bendDensityPanel.add(bendDensityLabel);
        bendDensityPanel.add(bendDensitySpinner);

        terrainDensitySpinner = new JSpinner(new SpinnerNumberModel(1.0, 0.001, 100.0, 0.001));
        terrainDensityLabel = new JLabel("Terrain Density: ");
        terrainDensityPanel = new JPanel();
        terrainDensityPanel.add(terrainDensityLabel);
        terrainDensityPanel.add(terrainDensitySpinner);

        setTerrainSettings(terrainSettings);

        settingsPanel.add(rockSizePanel);
        settingsPanel.add(clusterSizePanel);
        settingsPanel.add(upperCaveSizePanel);
        settingsPanel.add(lowerCaveSizePanel);
        settingsPanel.add(clusterDensityPanel);
        settingsPanel.add(bendDensityPanel);
        settingsPanel.add(lengthPanel);
        settingsPanel.add(lineDensityPanel);
        settingsPanel.add(caveWavePanel);
        settingsPanel.add(terrainDensityPanel);

        okButton = new JButton("OK");
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TerrainDialog.this.dispose();
            }
        });

        settingsPanel.add(okButton);

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
                (int) clusterSizeSpinner.getValue(),
                (int) upperCaveSizeSpinner.getValue(),
                (int) lowerCaveSizeSpinner.getValue(),
                (int) ((double) clusterDensitySpinner.getValue() * 1000),
                (int) ((double) bendDensitySpinner.getValue() * 1000),
                (int) lengthSpinner.getValue(),
                (int) ((double) lineDensitySpinner.getValue() * 1000),
                (int) caveWaveSpinner.getValue(),
                (int) ((double) terrainDensitySpinner.getValue() * 1000)
        );
    }

    public void setTerrainSettings(TerrainSettings terrainSettings) {
        rockSizeSpinner.setValue(terrainSettings.getRockSize());
        clusterSizeSpinner.setValue(terrainSettings.getClusterSize());
        upperCaveSizeSpinner.setValue(terrainSettings.getUpperCaveSize());
        lowerCaveSizeSpinner.setValue(terrainSettings.getLowerCaveSize());
        clusterDensitySpinner.setValue((double) terrainSettings.getClusterDensity() / 1000);
        bendDensitySpinner.setValue((double) terrainSettings.getBendDensity() / 1000);
        lengthSpinner.setValue(terrainSettings.getLineSize());
        lineDensitySpinner.setValue((double) terrainSettings.getLineDensity() / 1000);
        caveWaveSpinner.setValue(terrainSettings.getCaveWave());
        terrainDensitySpinner.setValue((double) terrainSettings.getTerrainAmount() / 1000);
    }
}
