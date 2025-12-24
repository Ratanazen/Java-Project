package Login;

import Login.DBConnection;
import Login.Login;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ratana
 */
public class Admin extends javax.swing.JFrame {

    public Admin() {
        initComponents();
        showDate();
        showTime();
        loadAllBookings(); 
        loadAllUsers();
        setTitle("Admin Dashboard");
        setLocationRelativeTo(null);
    }

    public void showDate() {
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        date.setText(s.format(d));
    }

    public void showTime() {
        new Timer(1000, (ActionEvent ae) -> {
            time.setText(new SimpleDateFormat("HH:mm:ss").format(new Date()));
        }).start();
    }

    /**
     * Load all bookings from database
     */
    private void loadAllBookings() {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            con = DBConnection.getConnection();
            
            if (con == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed!");
                return;
            }
            
            String sql = "SELECT * FROM bookings ORDER BY booking_time DESC";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            
            DefaultTableModel model = (DefaultTableModel) TableHistory.getModel();
            model.setRowCount(0); // Clear table
            
            int count = 0;
            while (rs.next()) {
                Object[] row = {
                    rs.getString("username"),
                    rs.getString("phone_number"),
                    new SimpleDateFormat("dd-MM-yyyy HH:mm").format(rs.getTimestamp("booking_time")),
                    rs.getString("bank"),
                    "$" + String.format("%.2f", rs.getDouble("money")),
                    rs.getString("location_from"),
                    rs.getString("location_to"),
                    rs.getDate("departure_date"),
                    rs.getDate("return_date")
                };
                model.addRow(row);
                count++;
            }
            
            if (count == 0) {
                // Table is empty - that's okay
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading bookings: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Load all users for the Ticket User tab
     */
    private void loadAllUsers() {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            con = DBConnection.getConnection();
            
            if (con == null) {
                return;
            }
            
            String sql = "SELECT username, phone, role, created_at FROM users ORDER BY created_at DESC";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            
            // Clear any existing components in TicketUser panel
            TicketUser.removeAll();
            
            // Create a table to display users
            JTable userTable = new JTable();
            DefaultTableModel model = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Username", "Phone", "Role", "Created At"}
            );
            userTable.setModel(model);
            
            while (rs.next()) {
                Object[] row = {
                    rs.getString("username"),
                    rs.getString("phone"),
                    rs.getString("role"),
                    new SimpleDateFormat("dd-MM-yyyy HH:mm").format(rs.getTimestamp("created_at"))
                };
                model.addRow(row);
            }
            
            JScrollPane scrollPane = new JScrollPane(userTable);
            TicketUser.setLayout(new java.awt.BorderLayout());
            TicketUser.add(scrollPane, java.awt.BorderLayout.CENTER);
            TicketUser.revalidate();
            TicketUser.repaint();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * Add admin user
     */
    private void addAdminUser() {
        String username = UsernameAdmin.getText().trim();
        String password = PasswordAdmin.getText().trim();
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password");
            return;
        }
        
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters");
            return;
        }
        
        Connection con = null;
        PreparedStatement pst = null;
        
        try {
            con = DBConnection.getConnection();
            
            if (con == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed!");
                return;
            }
            
            // Check if username already exists
            String checkSql = "SELECT username FROM users WHERE username=?";
            pst = con.prepareStatement(checkSql);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Username already exists!");
                return;
            }
            rs.close();
            pst.close();
            
            String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, 'admin')";
            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            
            int result = pst.executeUpdate();
            
            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Admin user added successfully!");
                UsernameAdmin.setText("");
                PasswordAdmin.setText("");
                loadAllUsers(); // Refresh user list
            }
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        time = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        LaHome = new javax.swing.JLabel();
        LaHistory = new javax.swing.JLabel();
        LaTicket = new javax.swing.JLabel();
        LaInfo = new javax.swing.JLabel();
        LaExit = new javax.swing.JLabel();
        Maintb = new javax.swing.JTabbedPane();
        HomeAdmin = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        TicketUser = new javax.swing.JPanel();
        HistoryUser = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableHistory = new javax.swing.JTable();
        AddAdmin = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        UsernameAdmin = new javax.swing.JTextField();
        PasswordAdmin = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        time.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        time.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        time.setText("time");

        date.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        date.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        date.setText("date");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(533, 533, 533)
                .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 462, Short.MAX_VALUE)
                .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(time, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(date, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(56, Short.MAX_VALUE))
        );

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
        jPanel9.add(LaHome, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, 70));

        LaHistory.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        LaHistory.setForeground(new java.awt.Color(255, 255, 255));
        LaHistory.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LaHistory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/History1.png"))); // NOI18N
        LaHistory.setText("  User");
        LaHistory.setToolTipText("");
        LaHistory.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        LaHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LaHistoryMouseClicked(evt);
            }
        });
        jPanel9.add(LaHistory, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 160, 190, 70));

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
        jPanel9.add(LaTicket, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 190, 80));

        LaInfo.setBackground(new java.awt.Color(0, 0, 0));
        LaInfo.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        LaInfo.setForeground(new java.awt.Color(255, 255, 255));
        LaInfo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LaInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/Info1.png"))); // NOI18N
        LaInfo.setText("Add Admin");
        LaInfo.setToolTipText("");
        LaInfo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        LaInfo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LaInfoMouseClicked(evt);
            }
        });
        jPanel9.add(LaInfo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 230, 190, 70));

        LaExit.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        LaExit.setForeground(new java.awt.Color(255, 255, 255));
        LaExit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LaExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/Exit1.png"))); // NOI18N
        LaExit.setText("     Exit");
        LaExit.setToolTipText("");
        LaExit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)));
        LaExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LaExitMouseClicked(evt);
            }
        });
        jPanel9.add(LaExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 580, 190, 70));

        jPanel8.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 680));

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 210, 660));

        HomeAdmin.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel1.setText("Welcome to Admin Dashboard");

        javax.swing.GroupLayout HomeAdminLayout = new javax.swing.GroupLayout(HomeAdmin);
        HomeAdmin.setLayout(HomeAdminLayout);
        HomeAdminLayout.setHorizontalGroup(
            HomeAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomeAdminLayout.createSequentialGroup()
                .addGap(397, 397, 397)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 563, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(370, Short.MAX_VALUE))
        );
        HomeAdminLayout.setVerticalGroup(
            HomeAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HomeAdminLayout.createSequentialGroup()
                .addGap(195, 195, 195)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(259, Short.MAX_VALUE))
        );

        Maintb.addTab("tab1", HomeAdmin);

        TicketUser.setBackground(new java.awt.Color(204, 204, 204));
        TicketUser.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Maintb.addTab("tab2", TicketUser);

        HistoryUser.setBackground(new java.awt.Color(0, 51, 102));
        HistoryUser.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableHistory.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(TableHistory);

        HistoryUser.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1290, 630));

        Maintb.addTab("tab3", HistoryUser);

        AddAdmin.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton1.setBackground(new java.awt.Color(51, 0, 255));
        jButton1.setFont(new java.awt.Font("Segoe UI", 2, 18)); // NOI18N
        jButton1.setText("ADD Admin");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        AddAdmin.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 350, 130, 50));

        UsernameAdmin.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        UsernameAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsernameAdminActionPerformed(evt);
            }
        });
        AddAdmin.add(UsernameAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 130, 450, 60));

        PasswordAdmin.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        AddAdmin.add(PasswordAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 240, 450, 70));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel3.setText("Username Admin");
        AddAdmin.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 100, 230, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Password");
        AddAdmin.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 210, 230, 30));

        Maintb.addTab("tab4", AddAdmin);

        jPanel1.add(Maintb, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 100, 1330, 690));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/logo6.png"))); // NOI18N
        jLabel2.setText("jLabel2");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 130));

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
    }// </editor-fold>                        

    private void LaHomeMouseClicked(java.awt.event.MouseEvent evt) {                                    
        Maintb.setSelectedIndex(0);
    }                                   

    private void LaTicketMouseClicked(java.awt.event.MouseEvent evt) {                                      
        loadAllUsers();
        Maintb.setSelectedIndex(1);
    }                                     

    private void LaExitMouseClicked(java.awt.event.MouseEvent evt) {                                    
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to logout?", 
            "Confirm Logout", 
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            new Login().setVisible(true);
            this.dispose();
        }
    }                                   

    private void LaHistoryMouseClicked(java.awt.event.MouseEvent evt) {                                       
        loadAllBookings(); 
        Maintb.setSelectedIndex(2);
    }                                      

    private void LaInfoMouseClicked(java.awt.event.MouseEvent evt) {                                    
        Maintb.setSelectedIndex(3);
    }                                   

    private void UsernameAdminActionPerformed(java.awt.event.ActionEvent evt) {                                              
        // TODO add your handling code here:
    }                                             
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        addAdminUser();
    }
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {
        JOptionPane.showMessageDialog(this, "Welcome to Admin Dashboard!", "Admin Panel", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new Admin().setVisible(true);
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JPanel AddAdmin;
    private javax.swing.JPanel HistoryUser;
    private javax.swing.JPanel HomeAdmin;
    private javax.swing.JLabel LaExit;
    private javax.swing.JLabel LaHistory;
    private javax.swing.JLabel LaHome;
    private javax.swing.JLabel LaInfo;
    private javax.swing.JLabel LaTicket;
    private javax.swing.JTabbedPane Maintb;
    private javax.swing.JTextField PasswordAdmin;
    private javax.swing.JTable TableHistory;
    private javax.swing.JPanel TicketUser;
    private javax.swing.JTextField UsernameAdmin;
    private javax.swing.JLabel date;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel time;
    // End of variables declaration                   
}