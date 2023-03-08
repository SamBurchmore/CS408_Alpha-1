package View;

import javax.swing.*;
import java.awt.*;

public class AgentSelectorPanel extends JPanel {

    final private JPanel agent0Panel;
    final private JPanel agent1Panel;
    final private JPanel agent2Panel;
    final private JPanel agent3Panel;
    final private JPanel agent4Panel;
    final private JPanel agent5Panel;
    final private JPanel agent6Panel;
    final private JPanel agent7Panel;

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
    final private SquareButton agent0Button;
    final private SquareButton agent1Button;
    final private SquareButton agent2Button;
    final private SquareButton agent3Button;
    final private SquareButton agent4Button;
    final private SquareButton agent5Button;
    final private SquareButton agent6Button;
    final private SquareButton agent7Button;

    public AgentSelectorPanel() {
        super();
        setLayout(new GridLayout(1, 8));
        final String emptyName = "_";
        final Color emptyColour = Color.WHITE;

        agent0NameLabel = new JLabel(emptyName);
        agent0NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        agent0Button = new SquareButton();
        agent0Button.setBackground(emptyColour);
        agent0Button.setOpaque(true);
        //agent0Button.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        agent0Button.setPreferredSize(new Dimension((int) agent0Button.getPreferredSize().getWidth()-20, (int) agent0Button.getPreferredSize().getHeight()-20));
        agent0Panel = new JPanel();
        agent0Panel.add(agent0NameLabel);
        agent0Panel.add(agent0Button);

        agent1NameLabel = new JLabel(emptyName);
        agent1NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        agent1Button = new SquareButton();
        agent1Button.setBackground(emptyColour);
        agent1Button.setOpaque(true);
        //agent1Button.setBorder(new EmptyBorder(20, 20, 20, 20));
        agent1Button.setPreferredSize(new Dimension((int) agent1Button.getPreferredSize().getWidth()-20, (int) agent1Button.getPreferredSize().getHeight()-20));
        agent1Panel = new JPanel();
        agent1Panel.add(agent1NameLabel);
        agent1Panel.add(agent1Button);

        agent2NameLabel = new JLabel(emptyName);
        agent2NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        agent2Button = new SquareButton();
        agent2Button.setBackground(emptyColour);
        agent2Button.setOpaque(true);
        //agent2Button.setBorder(new EmptyBorder(20, 20, 20, 20));
        agent2Button.setPreferredSize(new Dimension((int) agent2Button.getPreferredSize().getWidth()-20, (int) agent2Button.getPreferredSize().getHeight()-20));
        agent2Panel = new JPanel();
        agent2Panel.add(agent2NameLabel);
        agent2Panel.add(agent2Button);

        agent3NameLabel = new JLabel(emptyName);
        agent3NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        agent3Button = new SquareButton();
        agent3Button.setBackground(emptyColour);
        agent3Button.setOpaque(true);
        //agent3Button.setBorder(new EmptyBorder(20, 20, 20, 20));
        agent3Button.setPreferredSize(new Dimension((int) agent3Button.getPreferredSize().getWidth()-20, (int) agent3Button.getPreferredSize().getHeight()-20));
        agent3Panel = new JPanel();
        agent3Panel.add(agent3NameLabel);
        agent3Panel.add(agent3Button);

        agent4NameLabel = new JLabel(emptyName);
        agent4NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        agent4Button = new SquareButton();
        agent4Button.setBackground(emptyColour);
        agent4Button.setOpaque(true);
        //agent4Button.setBorder(new EmptyBorder(20, 20, 20, 20));
        agent4Button.setPreferredSize(new Dimension((int) agent4Button.getPreferredSize().getWidth()-20, (int) agent4Button.getPreferredSize().getHeight()-20));
        agent4Panel = new JPanel();
        agent4Panel.add(agent4NameLabel);
        agent4Panel.add(agent4Button);

        agent5NameLabel = new JLabel(emptyName);
        agent5NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        agent5Button = new SquareButton();
        agent5Button.setBackground(emptyColour);
        agent5Button.setOpaque(true);
        //agent5Button.setBorder(new EmptyBorder(20, 20, 20, 20));
        agent5Button.setPreferredSize(new Dimension((int) agent5Button.getPreferredSize().getWidth()-20, (int) agent5Button.getPreferredSize().getHeight()-20));
        agent5Panel = new JPanel();
        agent5Panel.add(agent5NameLabel);
        agent5Panel.add(agent5Button);

        agent6NameLabel = new JLabel(emptyName);
        agent6NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        agent6Button = new SquareButton();
        agent6Button.setBackground(emptyColour);
        agent6Button.setOpaque(true);
        //agent6Button.setBorder(new EmptyBorder(20, 20, 20, 20));
        agent6Button.setPreferredSize(new Dimension((int) agent6Button.getPreferredSize().getWidth()-20, (int) agent6Button.getPreferredSize().getHeight()-20));
        agent6Panel = new JPanel();
        agent6Panel.add(agent6NameLabel);
        agent6Panel.add(agent6Button);

        agent7NameLabel = new JLabel(emptyName);
        agent7NameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        agent7Button = new SquareButton();
        agent7Button.setBackground(emptyColour);
        agent7Button.setOpaque(true);
        //agent7Button.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        agent7Button.setPreferredSize(new Dimension((int) agent7Button.getPreferredSize().getWidth()-20, (int) agent7Button.getPreferredSize().getHeight()-20));
        agent7Panel = new JPanel();
        agent7Panel.add(agent7NameLabel);
        agent7Panel.add(agent7Button);

        add(agent0Panel);
        add(agent1Panel);
        add(agent2Panel);
        add(agent3Panel);
        add(agent4Panel);
        add(agent5Panel);
        add(agent6Panel);
        add(agent7Panel);
    }

    public SquareButton getAgent0Button() {
        return agent0Button;
    }

    public SquareButton getAgent1Button() {
        return agent1Button;
    }

    public SquareButton getAgent2Button() {
        return agent2Button;
    }

    public SquareButton getAgent3Button() {
        return agent3Button;
    }

    public SquareButton getAgent4Button() {
        return agent4Button;
    }

    public SquareButton getAgent5Button() {
        return agent5Button;
    }

    public SquareButton getAgent6Button() {
        return agent6Button;
    }

    public SquareButton getAgent7Button() {
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
