package ui;

import Blog.User;
import Blog.UserService;
import Blog.UserTableModel;
import adapter.UserAdapter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class BlogApp extends JFrame {

    private JTextField usernameField, passwordField;
    private JButton loginButton, logoutButton, deleteUserButton;
    private JTable userDataTable;


    private User currentUser = null;

    private UserService userService = new UserService();



    public BlogApp() {
        setTitle("Blog Application");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        loginButton = new JButton("Login");
        logoutButton = new JButton("Logout");
        deleteUserButton = new JButton("Delete User");
        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(((JPasswordField) passwordField).getPassword());

            currentUser = userService.authenticateUser(username, password);
            if (currentUser != null && currentUser.isAdmin()) {
                List<User> users = userService.getAllUsers(currentUser);
                userDataTable.setModel(new UserTableModel(convertToUserAdapterList(users)));
                logoutButton.setVisible(true);
                deleteUserButton.setVisible(true);
                loginButton.setVisible(false);

            } else {
                userDataTable.setModel(new DefaultTableModel());
                logoutButton.setVisible(false);
                deleteUserButton.setVisible(false);
                logoutAndShowErrorMessage("Authentication failed or you are not an admin!");
            }
        });

        logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            userService.logoutUser();
            userDataTable.setModel(new DefaultTableModel());
            JOptionPane.showMessageDialog(BlogApp.this, "You have been logged out!", "Logout", JOptionPane.INFORMATION_MESSAGE);
            logoutButton.setVisible(false);
            deleteUserButton.setVisible(false);
            loginButton.setVisible(true);
        });

        deleteUserButton.addActionListener(e -> {
            int selectedRow = userDataTable.getSelectedRow();
            if (selectedRow != -1) {
                UserTableModel userTableModel = (UserTableModel) userDataTable.getModel();
                String userId = userTableModel.getValueAt(selectedRow, 1).toString(); // Get the User ID

                boolean success = userService.deleteUser(userId, currentUser);
                if (success) {
                    JOptionPane.showMessageDialog(BlogApp.this, "User deleted!", "Delete User", JOptionPane.INFORMATION_MESSAGE);
                    List<User> users = userService.getAllUsers(currentUser);  // refresh the table
                    userDataTable.setModel(new UserTableModel(convertToUserAdapterList(users)));
                } else {
                    JOptionPane.showMessageDialog(BlogApp.this, "Failed to delete user.", "Delete User", JOptionPane.ERROR_MESSAGE);
                }
            }
        });





        userDataTable = new JTable();

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Username: "));
        topPanel.add(usernameField);
        topPanel.add(new JLabel("Password: "));
        topPanel.add(passwordField);
        topPanel.add(loginButton);


        add(new JScrollPane(userDataTable), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(deleteUserButton);
        bottomPanel.add(logoutButton);
        add(bottomPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        deleteUserButton.setVisible(false);
        logoutButton.setVisible(false);


        pack();
        setVisible(true);
    }
    private List<UserAdapter> convertToUserAdapterList(List<User> users) {
        return users.stream()
                .map(UserAdapter::new)
                .collect(Collectors.toList());
    }

    private void logoutAndShowErrorMessage(String message) {
        userService.logoutUser();
        currentUser = null;
        JOptionPane.showMessageDialog(BlogApp.this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }


}
