import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Code Editor UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);
        JTextArea codeArea = new JTextArea();
        codeArea.setBorder(BorderFactory.createTitledBorder("Write the Code here"));

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setBorder(BorderFactory.createTitledBorder("Output:Tokens"));

        JButton runButton = new JButton("Run");
        runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = codeArea.getText();
                outputArea.setText("Output:\n" + inputText);
            }
        });

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        panel.add(new JScrollPane(codeArea), gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.5;
        panel.add(new JScrollPane(outputArea), gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weighty = 0.0;
        panel.add(runButton, gbc);

        frame.add(panel);
        frame.setVisible(true);
    }
}
