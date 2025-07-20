package it.unibo.pcd.assignment03.view;

import it.unibo.pcd.assignment03.controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SessionView extends JFrame {

    private final JTextField sessionIdField = new JTextField("session1", 20);
    private final JTextField hostField = new JTextField("localhost", 20);
    private final JTextField portField = new JTextField("1099", 20);

    public SessionView(Controller controller) {
        setTitle("Session View");
        setSize(450, 300);
        setLocationRelativeTo(null); // Center window
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Join or Create a Session", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.add(new JLabel("Host:"));
        inputPanel.add(hostField);
        inputPanel.add(new JLabel("Port:"));
        inputPanel.add(portField);
        inputPanel.add(new JLabel("Session ID:"));
        inputPanel.add(sessionIdField);
        mainPanel.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton createButton = new JButton("Create Session");
        JButton joinButton = new JButton("Join Session");
        buttonPanel.add(createButton);
        buttonPanel.add(joinButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);

        createButton.addActionListener(e -> handleSessionAction(controller::createSession, "created"));
        joinButton.addActionListener(e -> handleSessionAction(controller::joinSession, "joined"));
    }

    private void handleSessionAction(SessionAction action, String actionName) {
        String sessionId = sessionIdField.getText().trim();
        String host = hostField.getText().trim();
        String port = portField.getText().trim();

        if (sessionId.isEmpty() || host.isEmpty() || port.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled.", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            action.perform(sessionId, host, Integer.parseInt(port));
            JOptionPane.showMessageDialog(this, "Successfully " + actionName + " session: " + sessionId);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Failed to " + actionName + " session: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FunctionalInterface
    private interface SessionAction {
        void perform(String sessionId, String host, int port) throws Exception;
    }

    public void display() {
        SwingUtilities.invokeLater(() -> {
            this.pack();
            this.setVisible(true);
        });
    }

    public void close() {
        this.dispose();
    }
}