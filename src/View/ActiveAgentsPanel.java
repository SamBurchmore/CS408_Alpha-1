package View;

import javax.swing.*;
import java.awt.*;

public class ActiveAgentsPanel extends JPanel {

    // The labels which display the agents names
    final private JLabel agent0NameLabel;
    final private JLabel agent1NameLabel;
    final private JLabel agent2NameLabel;
    final private JLabel agent3NameLabel;
    final private JLabel agent4NameLabel;
    final private JLabel agent5NameLabel;
    final private JLabel agent6NameLabel;
    final private JLabel agent7NameLabel;

    // The labels which display the agents colours
    final private JButton agent0Button;
    final private JButton agent1Button;
    final private JButton agent2Button;
    final private JButton agent3Button;
    final private JButton agent4Button;
    final private JButton agent5Button;
    final private JButton agent6Button;
    final private JButton agent7Button;

    public ActiveAgentsPanel() {
        super();
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(600, 65));
        setBorder(BorderFactory.createLineBorder(Color.darkGray));

        final String emptyName = "Blank Agent";
        final Color emptyColour = new Color(0, 0, 255);

        agent0NameLabel = new JLabel(emptyName);
        agent0NameLabel.setPreferredSize(new Dimension(70, 15));
        agent0NameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        agent1NameLabel = new JLabel(emptyName);
        agent1NameLabel.setPreferredSize(new Dimension(70, 15));
        agent1NameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        agent2NameLabel = new JLabel(emptyName);
        agent2NameLabel.setPreferredSize(new Dimension(70, 15));
        agent2NameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        agent3NameLabel = new JLabel(emptyName);
        agent3NameLabel.setPreferredSize(new Dimension(70, 15));
        agent3NameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        agent4NameLabel = new JLabel(emptyName);
        agent4NameLabel.setPreferredSize(new Dimension(70, 15));
        agent4NameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        agent5NameLabel = new JLabel(emptyName);
        agent5NameLabel.setPreferredSize(new Dimension(70, 15));
        agent5NameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        agent6NameLabel = new JLabel(emptyName);
        agent6NameLabel.setPreferredSize(new Dimension(70, 15));
        agent6NameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        agent7NameLabel = new JLabel(emptyName);
        agent7NameLabel.setPreferredSize(new Dimension(70, 15));
        agent7NameLabel.setHorizontalAlignment(SwingConstants.CENTER);

        agent0Button = new JButton();
        agent0Button.setBackground(emptyColour);
        agent0Button.setOpaque(true);
        agent0Button.setPreferredSize(new Dimension(41, 41));

        agent1Button = new JButton();
        agent1Button.setBackground(emptyColour);
        agent1Button.setOpaque(true);
        agent1Button.setPreferredSize(new Dimension(41, 41));

        agent2Button = new JButton();
        agent2Button.setBackground(emptyColour);
        agent2Button.setOpaque(true);
        agent2Button.setPreferredSize(new Dimension(41, 41));

        agent3Button = new JButton();
        agent3Button.setBackground(emptyColour);
        agent3Button.setOpaque(true);
        agent3Button.setPreferredSize(new Dimension(41, 41));

        agent4Button = new JButton();
        agent4Button.setBackground(emptyColour);
        agent4Button.setOpaque(true);
        agent4Button.setPreferredSize(new Dimension(41, 41));

        agent5Button = new JButton();
        agent5Button.setBackground(emptyColour);
        agent5Button.setOpaque(true);
        agent5Button.setPreferredSize(new Dimension(41, 41));

        agent6Button = new JButton();
        agent6Button.setOpaque(true);
        agent6Button.setBackground(emptyColour);
        agent6Button.setPreferredSize(new Dimension(41, 41));

        agent7Button = new JButton();
        agent7Button.setBackground(emptyColour);
        agent7Button.setOpaque(true);
        agent7Button.setPreferredSize(new Dimension(41, 41));

        // The GridBag constraints we'll be using to build this panel
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(1, 2, 1 ,2);

        // First we add the top row components
        c.gridy = 0;

        c.gridx = 0;
        add(agent0NameLabel, c);

        c.gridx = 1;
        add(agent1NameLabel, c);

        c.gridx = 2;
        add(agent2NameLabel, c);

        c.gridx = 3;
        add(agent3NameLabel, c);

        c.gridx = 4;
        add(agent4NameLabel, c);

        c.gridx = 5;
        add(agent5NameLabel, c);

        c.gridx = 6;
        add(agent6NameLabel, c);

        c.gridx = 7;
        add(agent7NameLabel, c);

        // Now we add the second row components
        c.gridy = 1;

        c.gridx = 0;
        add(agent0Button, c);

        c.gridx = 1;
        add(agent1Button, c);

        c.gridx = 2;
        add(agent2Button, c);

        c.gridx = 3;
        add(agent3Button, c);

        c.gridx = 4;
        add(agent4Button, c);

        c.gridx = 5;
        add(agent5Button, c);

        c.gridx = 6;
        add(agent6Button, c);

        c.gridx = 7;
        add(agent7Button, c);

    }

    public JButton getAgent0Button() {
        return agent0Button;
    }

    public JButton getAgent1Button() {
        return agent1Button;
    }

    public JButton getAgent2Button() {
        return agent2Button;
    }

    public JButton getAgent3Button() {
        return agent3Button;
    }

    public JButton getAgent4Button() {
        return agent4Button;
    }

    public JButton getAgent5Button() {
        return agent5Button;
    }

    public JButton getAgent6Button() {
        return agent6Button;
    }

    public JButton getAgent7Button() {
        return agent7Button;
    }

    public void setAgentSelector(int index, Color color, String name) {
        switch (index) {
            case 0 -> {
                this.agent0Button.setBackground(color);
                this.agent0NameLabel.setText(name);
            }
            case 1 -> {
                this.agent1Button.setBackground(color);
                this.agent1NameLabel.setText(name);
            }
            case 2 -> {
                this.agent2Button.setBackground(color);
                this.agent2NameLabel.setText(name);
            }
            case 3 -> {
                this.agent3Button.setBackground(color);
                this.agent3NameLabel.setText(name);
            }
            case 4 -> {
                this.agent4Button.setBackground(color);
                this.agent4NameLabel.setText(name);
            }
            case 5 -> {
                this.agent5Button.setBackground(color);
                this.agent5NameLabel.setText(name);
            }
            case 6 -> {
                this.agent6Button.setBackground(color);
                this.agent6NameLabel.setText(name);
            }
            case 7 -> {
                this.agent7Button.setBackground(color);
                this.agent7NameLabel.setText(name);
            }
        }
    }

}
