package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DiagnosticsPanel extends JPanel {

    // The label where the current simulation step is displayed
    private JLabel currentStepLabel;

    // The label which shows which agent stats are which
    private JLabel agentStatsLabel;
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

        agentStatsLabel = new JLabel("Population|Average Energy|Average Lifespan");
        agentStatsLabel.setPreferredSize(new Dimension(400, 25));

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
        add(agentStatsTextArea, c);

        // Now we add the third row components
        c.gridy = 2;
        add(environmentStatsPanel, c);

        // Now we add the forth row components
        c.gridy = 3;
        add(logTextArea, c);
    }

    public void setAgentStats(String agentStat) {
        agentStatsTextArea.setText(agentStat);
    }

}
