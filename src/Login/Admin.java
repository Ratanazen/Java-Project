package Login;

import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

public class Admin extends javax.swing.JFrame {
    private static final Logger LOGGER = Logger.getLogger(Admin.class.getName());
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    
    public Admin() {
        initComponents();
        initCustomComponents();
        showDate();
        showTime();
        
        // Initialize the AboutUser table
        initializeAboutUserTable();
        
        // Add Enter key listener for search
        addSearchKeyListener();
        
        // Add double-click listener for table
        addTableDoubleClickListener();
        
        // Only load bookings if that tab exists
        if (Maintb.getTabCount() > 2) {
            loadAllBookings();
        }
        
        // Load admin accounts if that tab exists
        if (Maintb.getTabCount() > 3) {
            loadAdminAccounts();
        }
    }
    
    // Custom initialization
    private void initCustomComponents() {
        setTitle("Admin Dashboard - Bus Booking System");
        setLocationRelativeTo(null);
        
        // First, check how many tabs actually exist
        int tabCount = Maintb.getTabCount();
        System.out.println("Number of tabs: " + tabCount);
        
        // Set tab titles based on actual tab count
        if (tabCount >= 1) {
            Maintb.setTitleAt(0, "Dashboard");
        }
        if (tabCount >= 2) {
            Maintb.setTitleAt(1, "User Management");
        }
        if (tabCount >= 3) {
            Maintb.setTitleAt(2, "All Bookings");
        }
        if (tabCount >= 4) {
            Maintb.setTitleAt(3, "Admin Accounts");
        } else {
            System.out.println("Warning: Only " + tabCount + " tabs available");
        }
    }
    
    // === USER MANAGEMENT FUNCTIONALITY ===
    
    // Method to search user bookings by username
    private void searchUserBookings() {
        String username = Nameuse.getText().trim();
        
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter a username to search",
                "Search Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String sql = "SELECT location_from, location_to, departure_date, " +
                    "TIME(booking_time) as booking_time FROM bookings " +
                    "WHERE username = ? ORDER BY booking_time DESC";
        
        DefaultTableModel model = (DefaultTableModel) AboutUser.getModel();
        model.setRowCount(0); // Clear existing data
        
        try (Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setString(1, username);
            
            try (ResultSet rs = pst.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    Object[] row = {
                        rs.getString("location_from"),
                        rs.getString("location_to"),
                        DATE_FORMAT.format(rs.getDate("departure_date")),
                        rs.getString("booking_time")
                    };
                    model.addRow(row);
                    count++;
                }
                
                if (count > 0) {
                    // Update the search button text to show results
                    searchUser.setText("Found: " + count);
                    JOptionPane.showMessageDialog(this,
                        "Found " + count + " booking(s) for user: " + username,
                        "Search Results",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    searchUser.setText("No Results");
                    JOptionPane.showMessageDialog(this,
                        "No bookings found for user: " + username,
                        "Search Results",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error searching user bookings: " + e.getMessage());
            searchUser.setText("Search Error");
            JOptionPane.showMessageDialog(this,
                "Database error: " + e.getMessage(),
                "Search Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Method to clear search results
    private void clearSearchResults() {
        DefaultTableModel model = (DefaultTableModel) AboutUser.getModel();
        model.setRowCount(0);
        Nameuse.setText("");
        searchUser.setText("Search User");
    }
    
    // Initialize the AboutUser table
    private void initializeAboutUserTable() {
        // Set up the table model with exact column names
        DefaultTableModel model = new DefaultTableModel(
            new Object[][] {},
            new String[] {"Location From", "Location To", "Date", "Time"}
        ) {
            Class[] types = new Class[] {
                java.lang.String.class, java.lang.String.class, 
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[] {false, false, false, false};
            
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
            
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        
        AboutUser.setModel(model);
        
        // Enable sorting
        AboutUser.setAutoCreateRowSorter(true);
    }
    
    // Add keyboard shortcut (Enter key) to search
    private void addSearchKeyListener() {
        Nameuse.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchUserBookings();
                }
            }
        });
    }
    
    // Add a double-click listener to the table to show more details
    private void addTableDoubleClickListener() {
        AboutUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    showBookingDetails();
                }
            }
        });
    }
    
    // Method to show detailed booking information
    private void showBookingDetails() {
        int selectedRow = AboutUser.getSelectedRow();
        
        if (selectedRow == -1) {
            return;
        }
        
        // Convert to model index in case of sorting
        int modelRow = AboutUser.convertRowIndexToModel(selectedRow);
        
        DefaultTableModel model = (DefaultTableModel) AboutUser.getModel();
        String locationFrom = (String) model.getValueAt(modelRow, 0);
        String locationTo = (String) model.getValueAt(modelRow, 1);
        String date = (String) model.getValueAt(modelRow, 2);
        
        // Search for full booking details
        String username = Nameuse.getText().trim();
        String sql = "SELECT * FROM bookings WHERE username = ? AND location_from = ? " +
                    "AND location_to = ? AND departure_date = ?";
        
        try (Connection con = DBConnection.getConnection();
            PreparedStatement pst = con.prepareStatement(sql)) {
            
            pst.setString(1, username);
            pst.setString(2, locationFrom);
            pst.setString(3, locationTo);
            pst.setDate(4, java.sql.Date.valueOf(convertDateFormat(date)));
            
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String details = String.format(
                        "Booking Details:\n\n" +
                        "Username: %s\n" +
                        "Phone: %s\n" +
                        "Booking Time: %s\n" +
                        "Bank: %s\n" +
                        "Amount: $%.2f\n" +
                        "From: %s\n" +
                        "To: %s\n" +
                        "Departure: %s\n" +
                        "Return: %s",
                        rs.getString("username"),
                        rs.getString("phone_number"),
                        new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(rs.getTimestamp("booking_time")),
                        rs.getString("bank"),
                        rs.getDouble("money"),
                        rs.getString("location_from"),
                        rs.getString("location_to"),
                        DATE_FORMAT.format(rs.getDate("departure_date")),
                        rs.getDate("return_date") != null ? DATE_FORMAT.format(rs.getDate("return_date")) : "One Way"
                    );
                    
                    JOptionPane.showMessageDialog(this, details,
                        "Booking Details", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            LOGGER.severe("Error getting booking details: " + e.getMessage());
        }
    }
    
    // Helper method to convert date format from dd-MM-yyyy to yyyy-MM-dd
    private String convertDateFormat(String dateStr) {
        try {
            Date date = DATE_FORMAT.parse(dateStr);
            return new SimpleDateFormat("yyyy-MM-dd").format(date);
        } catch (Exception e) {
            return dateStr; // Return original if conversion fails
        }
    }
    
    // Export functionality for search results
    private void exportSearchResults() {
        if (AboutUser.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this,
                "No search results to export",
                "Export Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Export Search Results");
            String username = Nameuse.getText().trim();
            String fileName = username.isEmpty() ? "search_results" : username + "_bookings";
            fileChooser.setSelectedFile(new File(fileName + "_" + DATE_FORMAT.format(new Date()) + ".csv"));
            
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                
                try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                    DefaultTableModel model = (DefaultTableModel) AboutUser.getModel();
                    
                    // Write header
                    for (int i = 0; i < model.getColumnCount(); i++) {
                        writer.write(model.getColumnName(i));
                        if (i < model.getColumnCount() - 1) writer.write(",");
                    }
                    writer.write("\n");
                    
                    // Write data
                    for (int i = 0; i < model.getRowCount(); i++) {
                        for (int j = 0; j < model.getColumnCount(); j++) {
                            Object obj = model.getValueAt(i, j);
                            writer.write(obj != null ? obj.toString().replace(",", ";") : "");
                            if (j < model.getColumnCount() - 1) writer.write(",");
                        }
                        writer.write("\n");
                    }
                    
                    JOptionPane.showMessageDialog(this,
                        "Search results exported successfully to:\n" + file.getAbsolutePath(),
                        "Export Successful",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Error exporting file: " + e.getMessage(),
                "Export Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // === EXISTING METHODS ===
    
    public void showDate() {
        date.setText(DATE_FORMAT.format(new Date()));
    }
    
    public void showTime() {
        new Timer(1000, (ActionEvent ae) -> {
            time.setText(TIME_FORMAT.format(new Date()));
        }).start();
    }
    
    // Load all bookings into jTable1
    private void loadAllBookings() {
        String sql = "SELECT * FROM bookings ORDER BY booking_time DESC";
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            
            if (con == null) {
                JOptionPane.showMessageDialog(this, 
                    "Database connection failed!", 
                    "Connection Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int count = 0;
            while (rs.next()) {
                Object[] row = {
                    rs.getString("username"),
                    rs.getString("phone_number"),
                    new SimpleDateFormat("dd-MM-yyyy HH:mm").format(rs.getTimestamp("booking_time")),
                    rs.getString("bank"),
                    String.format("$%.2f", rs.getDouble("money")),
                    rs.getString("location_from"),
                    rs.getString("location_to"),
                    DATE_FORMAT.format(rs.getDate("departure_date")),
                    rs.getDate("return_date") != null ? DATE_FORMAT.format(rs.getDate("return_date")) : "One Way"
                };
                model.addRow(row);
                count++;
            }
            
            // Update status
            if (count > 0) {
                LOGGER.info("Loaded " + count + " bookings.");
            } else {
                LOGGER.info("No bookings found.");
            }
            
        } catch (SQLException e) {
            LOGGER.severe("Database Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this, 
                "Error loading bookings: " + e.getMessage(), 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Export to Excel functionality
    private void exportToExcel() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Export Bookings to Excel");
            fileChooser.setSelectedFile(new File("bookings_export_" + DATE_FORMAT.format(new Date()) + ".csv"));
            
            if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                
                try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    
                    // Write header
                    for (int i = 0; i < model.getColumnCount(); i++) {
                        writer.write(model.getColumnName(i));
                        if (i < model.getColumnCount() - 1) writer.write(",");
                    }
                    writer.write("\n");
                    
                    // Write data
                    for (int i = 0; i < model.getRowCount(); i++) {
                        for (int j = 0; j < model.getColumnCount(); j++) {
                            Object obj = model.getValueAt(i, j);
                            writer.write(obj != null ? obj.toString().replace(",", ";") : "");
                            if (j < model.getColumnCount() - 1) writer.write(",");
                        }
                        writer.write("\n");
                    }
                    
                    JOptionPane.showMessageDialog(this, 
                        "Data exported successfully to:\n" + file.getAbsolutePath(),
                        "Export Successful",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Error exporting file: " + e.getMessage(),
                "Export Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Method to add new admin account
    private void addAdminAccount() {
        String username = NameAdmin.getText().trim();
        String password = PasswordAdmin.getText().trim();
        
        // Validation
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter both username and password",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (username.length() < 3) {
            JOptionPane.showMessageDialog(this,
                "Username must be at least 3 characters",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            NameAdmin.requestFocus();
            return;
        }
        
        if (password.length() < 4) {
            JOptionPane.showMessageDialog(this,
                "Password must be at least 4 characters",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            PasswordAdmin.requestFocus();
            return;
        }
        
        // Confirm action
        int confirm = JOptionPane.showConfirmDialog(this,
            "Create new admin account: " + username + "?",
            "Confirm Add Admin",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, 'admin')";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            
            int result = pst.executeUpdate();
            
            if (result > 0) {
                JOptionPane.showMessageDialog(this,
                    "Admin account created successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Clear fields
                NameAdmin.setText("");
                PasswordAdmin.setText("");
                
                // Reload admin accounts in the table
                loadAdminAccounts();
            }
            
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                JOptionPane.showMessageDialog(this,
                    "Username '" + username + "' already exists!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                    "Error: " + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    // Method to load admin accounts into Acc table
    private void loadAdminAccounts() {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT username, created_at FROM users WHERE role = 'admin' ORDER BY created_at DESC";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            DefaultTableModel model = (DefaultTableModel) Acc.getModel();
            model.setRowCount(0); // Clear existing data
            
            while (rs.next()) {
                Object[] row = {
                    rs.getString("username"),
                    new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(rs.getTimestamp("created_at"))
                };
                model.addRow(row);
            }
            
        } catch (SQLException e) {
            LOGGER.severe("Error loading admin accounts: " + e.getMessage());
            JOptionPane.showMessageDialog(this,
                "Error loading admin accounts: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Method to delete selected admin account
    private void deleteAdminAccount() {
        int selectedRow = Acc.getSelectedRow();
        
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Please select an admin account to delete",
                "No Selection",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String username = (String) Acc.getValueAt(selectedRow, 0);
        
        // Prevent deleting default admin
        if (username.equals("admin")) {
            JOptionPane.showMessageDialog(this,
                "Cannot delete the default admin account!",
                "Delete Restricted",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to delete admin account: " + username + "?\n\n" +
            "This action cannot be undone!",
            "Confirm Delete",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        
        try (Connection con = DBConnection.getConnection()) {
            String sql = "DELETE FROM users WHERE username = ? AND role = 'admin'";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username);
            
            int result = pst.executeUpdate();
            
            if (result > 0) {
                JOptionPane.showMessageDialog(this,
                    "Admin account deleted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Refresh the table
                loadAdminAccounts();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Failed to delete admin account",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Error deleting admin: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Search functionality
    private void searchBookings(String keyword) {
        String sql = "SELECT * FROM bookings WHERE username LIKE ? OR phone_number LIKE ? OR location_from LIKE ? OR location_to LIKE ? ORDER BY booking_time DESC";
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            
            String searchPattern = "%" + keyword + "%";
            pst.setString(1, searchPattern);
            pst.setString(2, searchPattern);
            pst.setString(3, searchPattern);
            pst.setString(4, searchPattern);
            
            try (ResultSet rs = pst.executeQuery()) {
                int count = 0;
                while (rs.next()) {
                    Object[] row = {
                        rs.getString("username"),
                        rs.getString("phone_number"),
                        new SimpleDateFormat("dd-MM-yyyy HH:mm").format(rs.getTimestamp("booking_time")),
                        rs.getString("bank"),
                        String.format("$%.2f", rs.getDouble("money")),
                        rs.getString("location_from"),
                        rs.getString("location_to"),
                        DATE_FORMAT.format(rs.getDate("departure_date")),
                        rs.getDate("return_date") != null ? DATE_FORMAT.format(rs.getDate("return_date")) : "One Way"
                    };
                    model.addRow(row);
                    count++;
                }
                
                if (count > 0) {
                    JOptionPane.showMessageDialog(this,
                        "Found " + count + " booking(s) matching: " + keyword,
                        "Search Results",
                        JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this,
                        "No bookings found matching: " + keyword,
                        "Search Results",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Search error: " + e.getMessage(),
                "Search Error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {
        loadAllBookings();  // Calls the method when refresh button is clicked
    }
    
    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String keyword = JOptionPane.showInputDialog(this, "Enter search keyword:");
        if (keyword != null && !keyword.trim().isEmpty()) {
            searchBookings(keyword.trim());
        }
    }
    
    private void exportButtonActionPerformed(java.awt.event.ActionEvent evt) {
        exportToExcel();
    }
    
    private void addAdminButtonActionPerformed(java.awt.event.ActionEvent evt) {
         addAdminAccount();
    }
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        LaHome = new javax.swing.JLabel();
        LaTicket = new javax.swing.JLabel();
        LaExit = new javax.swing.JLabel();
        LaHistory = new javax.swing.JLabel();
        AddAm = new javax.swing.JLabel();
        Maintb = new javax.swing.JTabbedPane();
        ForHome = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        HistoryUser = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        AddAdmin = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        PasswordAdmin = new javax.swing.JTextField();
        NameAdmin = new javax.swing.JTextField();
        ADD = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        Acc = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        Show = new javax.swing.JPanel();
        searchUser = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        AboutUser = new javax.swing.JTable();
        Nameuse = new javax.swing.JTextField();
        loadAllBookings = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        time = new javax.swing.JLabel();
        date = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Verdana", 1, 36)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 102));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("FLOWSYNC TRANSIT");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 0, 570, 120));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, 1340, 130));

        jPanel8.setBackground(new java.awt.Color(51, 51, 51));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        jPanel6.setForeground(new java.awt.Color(153, 204, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel8.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 7, 176, -1));

        jPanel9.setBackground(new java.awt.Color(28, 33, 67));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        jPanel9.setForeground(new java.awt.Color(153, 204, 255));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LaHome.setBackground(new java.awt.Color(255, 255, 255));
        LaHome.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        LaHome.setForeground(new java.awt.Color(255, 255, 255));
        LaHome.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LaHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/Home1.png"))); // NOI18N
        LaHome.setText("   Home");
        LaHome.setToolTipText("");
        LaHome.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        LaHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LaHomeMouseClicked(evt);
            }
        });
        jPanel9.add(LaHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 80));

        LaTicket.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        LaTicket.setForeground(new java.awt.Color(255, 255, 255));
        LaTicket.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LaTicket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/ticket1.png"))); // NOI18N
        LaTicket.setText("  Ticket User");
        LaTicket.setToolTipText("");
        LaTicket.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        LaTicket.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LaTicketMouseClicked(evt);
            }
        });
        jPanel9.add(LaTicket, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 210, 80));

        LaExit.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        LaExit.setForeground(new java.awt.Color(255, 255, 255));
        LaExit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LaExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/Arrow Pointing Left.png"))); // NOI18N
        LaExit.setText("     Exit");
        LaExit.setToolTipText("");
        LaExit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        LaExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LaExitMouseClicked(evt);
            }
        });
        jPanel9.add(LaExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 580, 210, 80));

        LaHistory.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        LaHistory.setForeground(new java.awt.Color(255, 255, 255));
        LaHistory.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LaHistory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/User Male.png"))); // NOI18N
        LaHistory.setText("       User");
        LaHistory.setToolTipText("");
        LaHistory.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        LaHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LaHistoryMouseClicked(evt);
            }
        });
        jPanel9.add(LaHistory, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 210, 70));

        AddAm.setBackground(new java.awt.Color(0, 0, 0));
        AddAm.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        AddAm.setForeground(new java.awt.Color(255, 255, 255));
        AddAm.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        AddAm.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/Info1.png"))); // NOI18N
        AddAm.setText("Add Admin");
        AddAm.setToolTipText("");
        AddAm.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        AddAm.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddAmMouseClicked(evt);
            }
        });
        jPanel9.add(AddAm, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 210, 70));

        jPanel8.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 680));

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 210, 660));

        ForHome.setBackground(new java.awt.Color(204, 204, 204));
        ForHome.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/Gemini_Generated.png"))); // NOI18N
        ForHome.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 1310, 670));

        Maintb.addTab("tab2", ForHome);

        HistoryUser.setBackground(new java.awt.Color(0, 51, 102));
        HistoryUser.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTable1.setAutoCreateRowSorter(true);
        jTable1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Name", "PhoneNumber", "Time", "Bank ", "Pay", "Location From", "Location to", "Departure Date", "Return Date  "
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        HistoryUser.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 1290, 600));

        Maintb.addTab("tab3", HistoryUser);

        AddAdmin.setBackground(new java.awt.Color(0, 102, 153));
        AddAdmin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(102, 102, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        NameAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NameAdminActionPerformed(evt);
            }
        });

        ADD.setText("Add");
        ADD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ADDActionPerformed(evt);
            }
        });

        Acc.setAutoCreateRowSorter(true);
        Acc.setBackground(new java.awt.Color(204, 204, 204));
        Acc.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Admin", "Date"
            }
        ));
        jScrollPane2.setViewportView(Acc);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Password");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Name");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(NameAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PasswordAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(93, 93, 93)
                        .addComponent(ADD, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(NameAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(3, 3, 3)
                .addComponent(PasswordAdmin, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(ADD, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(82, 82, 82))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        AddAdmin.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 80, 980, 440));

        Maintb.addTab("tab4", AddAdmin);

        Show.setBackground(new java.awt.Color(0, 102, 153));
        Show.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        searchUser.setText("searchUser ");
        searchUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchUserActionPerformed(evt);
            }
        });
        Show.add(searchUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 310, 110, 40));

        AboutUser.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Location From", "Location To", "Date", "Time"
            }
        ));
        jScrollPane4.setViewportView(AboutUser);

        Show.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 60, -1, -1));

        Nameuse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NameuseActionPerformed(evt);
            }
        });
        Show.add(Nameuse, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 202, 230, 60));

        loadAllBookings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadAllBookingsActionPerformed(evt);
            }
        });
        Show.add(loadAllBookings, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 310, 100, 40));

        Maintb.addTab("tab4", Show);

        jPanel1.add(Maintb, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, 1330, 700));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/logo6.png"))); // NOI18N
        jLabel2.setText("jLabel2");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 130));

        time.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        time.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        time.setText("time");
        jPanel1.add(time, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 196, 38));

        date.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        date.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        date.setText("date");
        jPanel1.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 129, 36));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 1531, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 19, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 804, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LaHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LaHomeMouseClicked
        Maintb.setSelectedIndex(0);
    }//GEN-LAST:event_LaHomeMouseClicked

    private void LaTicketMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LaTicketMouseClicked
        Maintb.setSelectedIndex(1);
    }//GEN-LAST:event_LaTicketMouseClicked

    private void LaExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LaExitMouseClicked
         new Login().setVisible(true);
         this.dispose();
    }//GEN-LAST:event_LaExitMouseClicked

    private void LaHistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LaHistoryMouseClicked
             Maintb.setSelectedIndex(3);
       
    }//GEN-LAST:event_LaHistoryMouseClicked

    private void AddAmMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddAmMouseClicked
        loadAllBookings(); 
        Maintb.setSelectedIndex(2);
    }//GEN-LAST:event_AddAmMouseClicked

    private void NameAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NameAdminActionPerformed
        
    }//GEN-LAST:event_NameAdminActionPerformed

    private void ADDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ADDActionPerformed
        addAdminAccount();
    }//GEN-LAST:event_ADDActionPerformed

    private void NameuseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NameuseActionPerformed
        searchUserBookings();
    }//GEN-LAST:event_NameuseActionPerformed

    private void searchUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchUserActionPerformed
      String keyword = JOptionPane.showInputDialog(this, "Enter search keyword:");
        if (keyword != null && !keyword.trim().isEmpty()) {
            searchBookings(keyword.trim());
        }
    }//GEN-LAST:event_searchUserActionPerformed

    private void loadAllBookingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadAllBookingsActionPerformed
       loadAllBookings();
    }//GEN-LAST:event_loadAllBookingsActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new Admin().setVisible(true);
        });
    }

    
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ADD;
    private javax.swing.JTable AboutUser;
    private javax.swing.JTable Acc;
    private javax.swing.JPanel AddAdmin;
    private javax.swing.JLabel AddAm;
    private javax.swing.JPanel ForHome;
    private javax.swing.JPanel HistoryUser;
    private javax.swing.JLabel LaExit;
    private javax.swing.JLabel LaHistory;
    private javax.swing.JLabel LaHome;
    private javax.swing.JLabel LaTicket;
    private javax.swing.JTabbedPane Maintb;
    private javax.swing.JTextField NameAdmin;
    private javax.swing.JTextField Nameuse;
    private javax.swing.JTextField PasswordAdmin;
    private javax.swing.JPanel Show;
    private javax.swing.JLabel date;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton loadAllBookings;
    private javax.swing.JButton searchUser;
    private javax.swing.JLabel time;
    // End of variables declaration//GEN-END:variables

}