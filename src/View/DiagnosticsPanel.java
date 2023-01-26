package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;

public class DiagnosticsPanel extends JPanel {

    // The label where the current simulation step is displayed
    private JLabel currentStepLabel;

    // The panel where the agent stat components are held
    private JPanel agentStatsPanel;

    // The labels which identify which agent stats are which
    private JLabel agentPopulationLabel;
    private JLabel agentEnergyLabel;
    private JLabel agentAgeLabel;

    // The labels which identify which agent stats belong to which agent
    private JLabel agent0Label;
    private JLabel agent1Label;
    private JLabel agent2Label;
    private JLabel agent3Label;
    private JLabel agent4Label;
    private JLabel agent5Label;
    private JLabel agent6Label;
    private JLabel agent7Label;

    private Object[][] agentStatistics = {{"Agent 1", 0, 0.0, 0.0}, {"Agent 2", 0, 0.0, 0.0}, {"Agent 3", 0, 0.0, 0.0}, {"Agent 4", 0, 0.0, 0.0}, {"Agent 5", 0, 0.0, 0.0}, {"Agent 6", 0, 0.0, 0.0}, {"Agent 7", 0, 0.0, 0.0}, {"Agent 8", 0, 0.0, 0.0}};
    private String[] agentStatNames = {"<html>Agent<br></html>" , "<html>Population<br></html>", "<html>Average<br>Energy</html>", "<html>Average<br>Age</html>"}; //{"<html>Population<br></html>", "<html>Average<br>Energy</html>", "<html>Average<br>Age</html>"};
    private JTable agentStatsTable;
    private JScrollPane agentStatsTableScrollPane;

    // The text area where the agent statistics are displayed
    private JTextArea agentStatsTextArea;

    // This is where log messages will be output, stuff like "[AGENT] step 5343: blue agent has gone extinct", or [ENVIRONMENT] step 1000: year 4
    private JTextArea logTextArea;

    // This is where the environments statistics will be shown, stuff like total energy, available space, etc
    private JPanel environmentStatsPanel;


    public DiagnosticsPanel() {
        super();
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(410, 665));
        setBorder(BorderFactory.createLineBorder(Color.darkGray));
        setBackground(new Color(224, 224, 224));

        // First all elements are defined and their attributes set
        currentStepLabel = new JLabel("Day 0");
        currentStepLabel.setHorizontalAlignment(SwingConstants.CENTER);
        currentStepLabel.setPreferredSize(new Dimension(400, 35));
        currentStepLabel.setFont(new Font("Dialog", Font.BOLD, 20));

        // Here we build the agent stats table
        TableModel tableModel = new DefaultTableModel(agentStatistics, agentStatNames);
        agentStatsTable = new JTable(tableModel);
        //agentStatsTable.setModel(new DefaultTableModel());
        agentStatsTableScrollPane = new JScrollPane(agentStatsTable);
        agentStatsTableScrollPane.setBorder(null);
        agentStatsTableScrollPane.setPreferredSize(new Dimension(400, 166));
        agentStatsTable.setFillsViewportHeight(true);
        agentStatsTable.setDefaultEditor(Object.class, null);
        agentStatsTable.getTableHeader().setPreferredSize(new Dimension(400, 35));
        agentStatsTable.getTableHeader().setFont(new Font("Dialog", Font.BOLD, 12));


        // Now we configure the log output text area
        agentStatsTextArea = new JTextArea(8, 10);
        agentStatsTextArea.setFont(new Font("Dialog", Font.BOLD, 12));
        agentStatsTextArea.setPreferredSize(new Dimension(400, 180));
        agentStatsTextArea.setBackground(Color.lightGray);

        // Now we configure the log output text area
        logTextArea = new JTextArea(20, 10);
        logTextArea.setPreferredSize(new Dimension(400, 150));
        logTextArea.setBackground(Color.lightGray);

        // Now we configure the environment statistics panel
        environmentStatsPanel = new JPanel();
        environmentStatsPanel.setBackground(Color.lightGray);
        environmentStatsPanel.setPreferredSize(new Dimension(400, 100));

        // The GridBag constraints we'll be using to build this panel
        GridBagConstraints c = new GridBagConstraints();
        c.gridwidth = 2;
        c.insets = new Insets(1, 1, 1, 1);
        c.weightx = 1;
        c.weighty = 0;

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
        add(logTextArea, c);

    }

    public void setAgentStats(Object[][] agentStats) {
        DefaultTableModel model = (DefaultTableModel) agentStatsTable.getModel();
        Object[] row = new Object[8];
        for (int i = 0; i < 8; i++) {
            row[0] = agentStats[0][i];
            row[1] = agentStats[1][i];
            row[2] = agentStats[2][i];
            row[3] = agentStats[3][i];
            model.removeRow(i);
            model.insertRow(i, row);
        }
        agentStatsTable.setModel(model);
    }

}
