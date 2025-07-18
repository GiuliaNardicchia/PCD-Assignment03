package it.unibo.pcd.assignment03.view;

import it.unibo.pcd.assignment03.controller.Controller;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SessionView extends JFrame {

    private final JTextField sessionIdField = new JTextField(20);

    public SessionView(Controller controller) {
        setTitle("Session View");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Join or Create a Session", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inputPanel.add(new JLabel("Session ID:"));
        inputPanel.add(sessionIdField);
        mainPanel.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
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
        if (!sessionId.isEmpty()) {
            try {
                action.perform(sessionId);
                JOptionPane.showMessageDialog(this, "Successfully " + actionName + " session: " + sessionId);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Failed to " + actionName + " session: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Session ID cannot be empty.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }

    @FunctionalInterface
    private interface SessionAction {
        void perform(String sessionId) throws Exception;
    }

    public void display() {
        SwingUtilities.invokeLater(() -> this.setVisible(true));
    }

    public void close() {
        this.dispose();
    }
}
