package View;

import javax.swing.*;
import java.awt.*;

public class ActiveAgentsPanel extends JPanel {

    // The labels which display the agents names
    private JLabel agent0NameLabel;
    private JLabel agent1NameLabel;
    private JLabel agent2NameLabel;
    private JLabel agent3NameLabel;
    private JLabel agent4NameLabel;
    private JLabel agent5NameLabel;
    private JLabel agent6NameLabel;
    private JLabel agent7NameLabel;

    // The labels which display the agents colours
    private JLabel agent0ColourLabel;
    private JLabel agent1ColourLabel;
    private JLabel agent2ColourLabel;
    private JLabel agent3ColourLabel;
    private JLabel agent4ColourLabel;
    private JLabel agent5ColourLabel;
    private JLabel agent6ColourLabel;
    private JLabel agent7ColourLabel;

    private String emptyName;
    private Color emptyColour;

    public ActiveAgentsPanel() {
        super();
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(602, 60));
        setBorder(BorderFactory.createLineBorder(Color.darkGray));

        // First all elements are defined and their attributes set
        emptyName = "Blank Agent";
        emptyColour = new Color(0, 0, 255);

        agent0NameLabel = new JLabel(emptyName);
        agent0NameLabel.setPreferredSize(new Dimension(71, 10));

        agent1NameLabel = new JLabel(emptyName);
        agent1NameLabel.setPreferredSize(new Dimension(71, 10));

        agent2NameLabel = new JLabel(emptyName);
        agent2NameLabel.setPreferredSize(new Dimension(71, 10));

        agent3NameLabel = new JLabel(emptyName);
        agent3NameLabel.setPreferredSize(new Dimension(71, 10));

        agent4NameLabel = new JLabel(emptyName);
        agent4NameLabel.setPreferredSize(new Dimension(71, 10));

        agent5NameLabel = new JLabel(emptyName);
        agent5NameLabel.setPreferredSize(new Dimension(71, 10));

        agent6NameLabel = new JLabel(emptyName);
        agent6NameLabel.setPreferredSize(new Dimension(71, 10));

        agent7NameLabel = new JLabel(emptyName);
        agent7NameLabel.setPreferredSize(new Dimension(71, 10));

        agent0ColourLabel = new JLabel();
        agent0ColourLabel.setBackground(emptyColour);
        agent0ColourLabel.setOpaque(true);
        agent0ColourLabel.setPreferredSize(new Dimension(41, 41));

        agent1ColourLabel = new JLabel();
        agent1ColourLabel.setBackground(emptyColour);
        agent1ColourLabel.setOpaque(true);
        agent1ColourLabel.setPreferredSize(new Dimension(41, 41));

        agent2ColourLabel = new JLabel();
        agent2ColourLabel.setBackground(emptyColour);
        agent2ColourLabel.setOpaque(true);
        agent2ColourLabel.setPreferredSize(new Dimension(41, 41));

        agent3ColourLabel = new JLabel();
        agent3ColourLabel.setBackground(emptyColour);
        agent3ColourLabel.setOpaque(true);
        agent3ColourLabel.setPreferredSize(new Dimension(41, 41));

        agent4ColourLabel = new JLabel();
        agent4ColourLabel.setBackground(emptyColour);
        agent4ColourLabel.setOpaque(true);
        agent4ColourLabel.setPreferredSize(new Dimension(41, 41));

        agent5ColourLabel = new JLabel();
        agent5ColourLabel.setBackground(emptyColour);
        agent5ColourLabel.setOpaque(true);
        agent5ColourLabel.setPreferredSize(new Dimension(41, 41));

        agent6ColourLabel = new JLabel();
        agent6ColourLabel.setOpaque(true);
        agent6ColourLabel.setBackground(emptyColour);
        agent6ColourLabel.setPreferredSize(new Dimension(41, 41));

        agent7ColourLabel = new JLabel();
        agent7ColourLabel.setBackground(emptyColour);
        agent7ColourLabel.setOpaque(true);
        agent7ColourLabel.setPreferredSize(new Dimension(41, 41));

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
        add(agent0ColourLabel, c);

        c.gridx = 1;
        add(agent1ColourLabel, c);

        c.gridx = 2;
        add(agent2ColourLabel, c);

        c.gridx = 3;
        add(agent3ColourLabel, c);

        c.gridx = 4;
        add(agent4ColourLabel, c);

        c.gridx = 5;
        add(agent5ColourLabel, c);

        c.gridx = 6;
        add(agent6ColourLabel, c);

        c.gridx = 7;
        add(agent7ColourLabel, c);

    }



}
