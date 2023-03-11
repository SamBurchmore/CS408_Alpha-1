package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;

public class DiagnosticsPanel extends JPanel {

    // The label where the current simulation step is displayed
    final private JLabel currentStepLabel;

    // The components where the agent statistics are displayed---------------------</
    private Object[][] agentStatistics = {
            {"Agent 1", 0, 0.0, 0.0, 0, 0.0, 0.0, 0.0},
            {"Agent 2", 0, 0.0, 0.0, 0, 0.0, 0.0, 0.0},
            {"Agent 3", 0, 0.0, 0.0, 0, 0.0, 0.0, 0.0},
            {"Agent 4", 0, 0.0, 0.0, 0, 0.0, 0.0, 0.0},
            {"Agent 5", 0, 0.0, 0.0, 0, 0.0, 0.0, 0.0},
            {"Agent 6", 0, 0.0, 0.0, 0, 0.0, 0.0, 0.0},
            {"Agent 7", 0, 0.0, 0.0, 0, 0.0, 0.0, 0.0},
            {"Agent 8", 0, 0.0, 0.0, 0, 0.0, 0.0, 0.0}
    };
    private String[] agentStatNames = {
            "<html>Agent<br></html>",
            "<html>Population<br></html>",
            "<html>Average<br>Energy</html>",
            "<html>Average<br>Age</html>",
            "<html>Born Last<br>Step</html>",
            "<html>Average<br>Size</html>",
            "<html>Average<br>C-Size</html>",
            "<html>Average<br>Range</html>",}; //{"<html>Population<br></html>", "<html>Average<br>Energy</html>", "<html>Average<br>Age</html>"};
    final private JTable agentStatsTable;
    private JScrollPane agentStatsTableScrollPane;
    //-----------------------------------------------------------------------------</

    // The components where the environments statistics are displayed---------------------</
    final private JPanel environmentStatsPanel;
    final private JLabel maxEnvironmentEnergyLabel;
    final private JLabel maxEnvironmentEnergyValueLabel;
    final private JLabel currentEnvironmentEnergyLabel;
    final private JLabel currentEnvironmentEnergyValueLabel;
    final private JLabel currentEnvironmentEnergyPercentLabel;
    final private JLabel currentEnvironmentEnergyPercentValueLabel;
    //------------------------------------------------------------------------------------</

    // The components that make up the info log---------------------</
    final private JTextArea logTextArea;
    final private JScrollPane logScrollPane;
    final private JLabel logTextAreaLabel;
    final private JRadioButton lowDiagnosticsRadioButton;
    final private JRadioButton highDiagnosticsRadioButton;
    final private ButtonGroup diagnosticsVerbosityButtonGroup;
    final private JPanel diagnosticsVerbosityPanel;
    final private JLabel diagnosticsVerbosityLabel;
    final private JButton clearLogButton;
    //--------------------------------------------------------------</

    public DiagnosticsPanel() {
        super();
        setLayout(new GridBagLayout());

        //--------------------------------------------------------------------------Current Step Label Start
        currentStepLabel = new JLabel("Day 0");
        currentStepLabel.setHorizontalAlignment(SwingConstants.CENTER);
        currentStepLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        //--------------------------------------------------------------------------Current Step Label End

        //--------------------------------------------------------------------------Agent Stats Table Start
        TableModel tableModel = new DefaultTableModel(agentStatistics, agentStatNames);
        agentStatsTable = new JTable(tableModel);
        agentStatsTable.setPreferredSize(new Dimension(550, 160));
        agentStatsTable.setDefaultEditor(Object.class, null);
        agentStatsTable.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 11));
        agentStatsTable.getTableHeader().setPreferredSize(new Dimension(550, 30));
        agentStatsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        agentStatsTableScrollPane = new JScrollPane(agentStatsTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        //agentStatsTableScrollPane.setViewportView(agentStatsTable);
        agentStatsTableScrollPane.setBorder(null);
        agentStatsTable.setPreferredScrollableViewportSize(new Dimension(350, 175));
        //--------------------------------------------------------------------------Agent Stats Table End

        //--------------------------------------------------------------------------Environment Stats Panel Start
        environmentStatsPanel = new JPanel(new GridLayout(3, 2, 3, 3));
        maxEnvironmentEnergyLabel = new JLabel("     Max Environment Energy: ");
        currentEnvironmentEnergyLabel = new JLabel("     Current Environment Energy: ");
        currentEnvironmentEnergyPercentLabel = new JLabel("     Available Energy Percent: ");
        maxEnvironmentEnergyValueLabel = new JLabel();
        maxEnvironmentEnergyLabel.setHorizontalAlignment(SwingConstants.LEFT);
        currentEnvironmentEnergyValueLabel = new JLabel();
        currentEnvironmentEnergyValueLabel.setHorizontalAlignment(SwingConstants.LEFT);
        currentEnvironmentEnergyPercentValueLabel = new JLabel();
        currentEnvironmentEnergyPercentValueLabel.setHorizontalAlignment(SwingConstants.LEFT);
        environmentStatsPanel.add(maxEnvironmentEnergyLabel);
        environmentStatsPanel.add(maxEnvironmentEnergyValueLabel);
        environmentStatsPanel.add(currentEnvironmentEnergyLabel);
        environmentStatsPanel.add(currentEnvironmentEnergyValueLabel);
        environmentStatsPanel.add(currentEnvironmentEnergyPercentLabel);
        environmentStatsPanel.add(currentEnvironmentEnergyPercentValueLabel);
        //--------------------------------------------------------------------------Environment Stats Panel End

        //--------------------------------------------------------------------------Info Log Start
        logTextArea = new JTextArea(18, 36);
        logScrollPane = new JScrollPane(logTextArea);
        logTextAreaLabel = new JLabel("Info Log:");
        logTextAreaLabel.setFont(new Font("Dialog", Font.BOLD, 12));
        lowDiagnosticsRadioButton = new JRadioButton("Low");
        //medDiagnosticsRadioButton = new JRadioButton("Standard");
        highDiagnosticsRadioButton = new JRadioButton("High");
        diagnosticsVerbosityButtonGroup = new ButtonGroup();
        diagnosticsVerbosityButtonGroup.add(lowDiagnosticsRadioButton);
        //diagnosticsVerbosityButtonGroup.add(medDiagnosticsRadioButton);
        diagnosticsVerbosityButtonGroup.add(highDiagnosticsRadioButton);
        diagnosticsVerbosityButtonGroup.setSelected(lowDiagnosticsRadioButton.getModel(), true);
        diagnosticsVerbosityPanel = new JPanel(new GridLayout(1, 4));
        diagnosticsVerbosityLabel = new JLabel("Info Log Verbosity:");
        clearLogButton = new JButton("Clear");
        diagnosticsVerbosityPanel.add(diagnosticsVerbosityLabel);
        diagnosticsVerbosityPanel.add(lowDiagnosticsRadioButton);
        diagnosticsVerbosityPanel.add(highDiagnosticsRadioButton);
        diagnosticsVerbosityPanel.add(clearLogButton);
        //--------------------------------------------------------------------------Info Log End


        // The GridBag constraints we'll be using to build this panel
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;

        // Now we add the top row components
        c.gridy = 0;
        add(currentStepLabel, c);

        // Now we add the second row components
        c.gridy = 1;
        add(agentStatsTableScrollPane, c);

        // Now we add the third row components
        c.gridy = 2;
        add(environmentStatsPanel, c);

        // Now we add the forth row components
        c.gridy = 3;
        //c.insets = new Insets(11, 11, 10, 11);
        add(logTextAreaLabel, c);

        // Now we add the fifth row components
        c.gridy = 4;
        //c.insets = new Insets(10, 11, 10, 11);
        add(logScrollPane, c);

        // Now we add the sixth row components
        c.gridy = 5;
        //c.insets = new Insets(0, 1, 0, 1);
        add(diagnosticsVerbosityPanel, c);

    }

    public void setAgentStats(Object[][] agentStats) {
        DefaultTableModel model = (DefaultTableModel) agentStatsTable.getModel();
        Object[] row = new Object[8];
        for (int i = 0; i < 8; i++) {
            row[0] = agentStats[0][i];
            row[1] = agentStats[1][i];
            row[2] = agentStats[2][i];
            row[3] = agentStats[3][i];
            row[4] = agentStats[4][i];
            row[5] = agentStats[5][i];
            row[6] = agentStats[6][i];
            row[7] = agentStats[7][i];
            model.removeRow(i);
            model.insertRow(i, row);
        }
        agentStatsTable.setModel(model);
    }

    public void setEnvironmentStats(Object[] environmentStats) {
        maxEnvironmentEnergyValueLabel.setText(environmentStats[0].toString());
        currentEnvironmentEnergyValueLabel.setText(environmentStats[1].toString());
        currentEnvironmentEnergyPercentValueLabel.setText(environmentStats[2].toString() + "%");
    }

    public JButton getClearLogButton() {
        return clearLogButton;
    }

    public void clearLog() {
        logTextArea.setText("");
    }

    public void addLogMessage(String logMessage) {
        logTextArea.append(logMessage + "\n");
    }

    public void setStepLabel(long step) {
        currentStepLabel.setText("Step: " + step);
    }

    public void clearStepLabel() {
        currentStepLabel.setText("Step: 0");
    }

    public int getDiagnosticsVerbosity() {
        if (diagnosticsVerbosityButtonGroup.getSelection().equals(lowDiagnosticsRadioButton.getModel())) {
            return 0;
        }
        return 1;
    }
}
