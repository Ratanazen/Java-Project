package Login;

import Login.DBConnection;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Calendar;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;

public class FromMain extends javax.swing.JFrame {
   
    private String username;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat TABLE_DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    
    public FromMain(String user) {
        initComponents();
        this.username = user;
        initCustomComponents();
        showDate();
        showTime();
        loadBookingHistory(); 
        welcomeUser();
    }
    
    private void initCustomComponents() {
        setTitle("Bus Booking System - Welcome " + username);
        setLocationRelativeTo(null);
        
        Maintb.setTitleAt(0, "Home");
        Maintb.setTitleAt(1, "Book Ticket");
        Maintb.setTitleAt(2, "Booking History");
        Maintb.setTitleAt(3, "ABA Payment");
        Maintb.setTitleAt(4, "Acleda Payment");
        
        // Initialize departure date to today + 1 day
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        DepartureDate.setDate(cal.getTime());
    }
    
    private void welcomeUser() {
        String welcomeMessage = "Welcome back, " + username + "!";
        if (bt1.getComponentCount() == 0) {
            JLabel welcomeLabel = new JLabel(welcomeMessage, SwingConstants.CENTER);
            welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
            welcomeLabel.setForeground(new Color(0, 102, 204));
            bt1.add(welcomeLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 100, 1320, 50));
            
            JLabel infoLabel = new JLabel("Book your bus tickets easily with our system!", SwingConstants.CENTER);
            infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            bt1.add(infoLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 160, 1320, 30));
        }
    }

    public void showDate() {
        date.setText(DATE_FORMAT.format(new Date()));
    }

    public void showTime() {
        new Timer(1000, (ActionEvent ae) -> {
            time.setText(TIME_FORMAT.format(new Date()));
        }).start();
    }
    
    private void loadBookingHistory() {
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            con = DBConnection.getConnection();
            
            if (con == null) {
                JOptionPane.showMessageDialog(this, 
                    "Cannot connect to database. Please check your connection.",
                    "Connection Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            String sql = "SELECT * FROM bookings WHERE username=? ORDER BY booking_time DESC";
            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            rs = pst.executeQuery();
            
            DefaultTableModel model = (DefaultTableModel) TableHistory.getModel();
            model.setRowCount(0);
            
            int bookingCount = 0;
            double totalSpent = 0.0;
            
            while (rs.next()) {
                Object[] row = {
                    rs.getString("username"),
                    formatPhoneNumber(rs.getString("phone_number")),
                    rs.getString("location_from"),
                    rs.getString("location_to"),
                    DATE_FORMAT.format(rs.getDate("departure_date")),
                    rs.getDate("return_date") != null ? DATE_FORMAT.format(rs.getDate("return_date")) : "One Way",
                    String.format("$%.2f", rs.getDouble("money")),
                    rs.getString("bank"),
                    TABLE_DATE_FORMAT.format(rs.getTimestamp("booking_time"))
                };
                model.addRow(row);
                bookingCount++;
                totalSpent += rs.getDouble("money");
            }
            
            System.out.println("✓ Booking history loaded for: " + username);
            System.out.println("  Total bookings: " + bookingCount);
            System.out.println("  Total spent: $" + String.format("%.2f", totalSpent));
            
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                "Error loading booking history: " + e.getMessage(),
                "Database Error",
                JOptionPane.ERROR_MESSAGE);
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
    
    private String formatPhoneNumber(String phone) {
        if (phone == null || phone.trim().isEmpty()) return "N/A";
        if (phone.length() == 10) {
            return phone.substring(0, 3) + "-" + phone.substring(3, 6) + "-" + phone.substring(6);
        }
        return phone;
    }

    private String getUserPhone() {
    Connection con = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    String phone = "";
    
    try {
        con = DBConnection.getConnection();
        if (con == null) return "";
        
        String sql = "SELECT phone FROM users WHERE username=?";
        pst = con.prepareStatement(sql);
        pst.setString(1, username);
        rs = pst.executeQuery();
        
        if (rs.next()) {
            phone = rs.getString("phone");
            if (phone == null || phone.trim().isEmpty()) {
                int response = JOptionPane.showConfirmDialog(this,
                    "Phone number not found. Would you like to update your profile?",
                    "Profile Update",
                    JOptionPane.YES_NO_OPTION);
                
                if (response == JOptionPane.YES_OPTION) {
                    phone = JOptionPane.showInputDialog(this,
                        "Enter your phone number:",
                        "Update Phone",
                        JOptionPane.QUESTION_MESSAGE);
                    
                    if (phone != null && !phone.trim().isEmpty()) {
                        updateUserPhone(phone);
                    }
                }
            }
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Close resources properly
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            // Don't close connection here either
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    return phone != null ? phone : "";
}
   
    private void updateUserPhone(String phone) {
        Connection con = null;
        PreparedStatement pst = null;
        
        try {
            con = DBConnection.getConnection();
            if (con == null) return;
            
            String sql = "UPDATE users SET phone=? WHERE username=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, phone);
            pst.setString(2, username);
            
            int result = pst.executeUpdate();
            if (result > 0) {
                System.out.println("✓ Phone number updated for: " + username);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pst != null) pst.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

  private boolean saveBooking(String locationFrom, String locationTo, 
                           Date departureDate, Date returnDate, 
                           double money, String bank) {
    
    try (Connection con = DBConnection.getConnection()) {
        
        if (con == null) {
            JOptionPane.showMessageDialog(this,
                "Database connection failed. Please try again.",
                "Connection Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        String phone = getUserPhone();
        if (phone == null || phone.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please update your phone number in your profile first.",
                "Profile Required",
                JOptionPane.WARNING_MESSAGE);
            return false;
        }
        
        String sql = "INSERT INTO bookings (username, phone_number, location_from, " +
                    "location_to, departure_date, return_date, money, bank) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, username);
            pst.setString(2, phone);
            pst.setString(3, locationFrom);
            pst.setString(4, locationTo);
            pst.setDate(5, new java.sql.Date(departureDate.getTime()));
            pst.setDate(6, returnDate != null ? new java.sql.Date(returnDate.getTime()) : null);
            pst.setDouble(7, money);
            pst.setString(8, bank);
            
            int result = pst.executeUpdate();
            
            if (result > 0) {
                try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int bookingId = generatedKeys.getInt(1);
                        
                        String message = String.format(
                            "Booking Confirmed!\n\n" +
                            "Booking ID: %d\n" +
                            "From: %s\n" +
                            "To: %s\n" +
                            "Departure: %s\n" +
                            "Amount: $%.2f\n" +
                            "Payment: %s\n\n" +
                            "Details sent to: %s",
                            bookingId, locationFrom, locationTo,
                            DATE_FORMAT.format(departureDate),
                            money, bank, phone
                        );
                        
                        JOptionPane.showMessageDialog(this, message,
                            "Booking Successful",
                            JOptionPane.INFORMATION_MESSAGE);
                        return true;
                    }
                }
            }
        }
        
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this,
            "Failed to save booking: " + e.getMessage(),
            "Save Error",
            JOptionPane.ERROR_MESSAGE);
    }
    
    return false;
}
    private boolean validateBookingForm() {
        String locationFrom = (String) LocationFrom.getSelectedItem();
        String locationTo = (String) LocationTo.getSelectedItem();
        Date depDate = DepartureDate.getDate();
        
        if (depDate == null) {
            JOptionPane.showMessageDialog(this,
                "Please select a departure date.",
                "Validation Error",
                JOptionPane.WARNING_MESSAGE);
            DepartureDate.requestFocus();
            return false;
        }
        
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        
        Calendar depCal = Calendar.getInstance();
        depCal.setTime(depDate);
        depCal.set(Calendar.HOUR_OF_DAY, 0);
        depCal.set(Calendar.MINUTE, 0);
        depCal.set(Calendar.SECOND, 0);
        depCal.set(Calendar.MILLISECOND, 0);
        
        if (depCal.before(today)) {
            JOptionPane.showMessageDialog(this,
                "Departure date cannot be in the past.",
                "Date Error",
                JOptionPane.WARNING_MESSAGE);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 1);
            DepartureDate.setDate(cal.getTime());
            return false;
        }
        
        if (locationFrom.equals(locationTo)) {
            JOptionPane.showMessageDialog(this,
                "Departure and destination locations cannot be the same.",
                "Location Error",
                JOptionPane.WARNING_MESSAGE);
            LocationFrom.requestFocus();
            return false;
        }
        
        Date retDate = ReturnDate.getDate();
        if (retDate != null) {
            Calendar retCal = Calendar.getInstance();
            retCal.setTime(retDate);
            retCal.set(Calendar.HOUR_OF_DAY, 0);
            retCal.set(Calendar.MINUTE, 0);
            retCal.set(Calendar.SECOND, 0);
            retCal.set(Calendar.MILLISECOND, 0);
            
            if (retCal.before(depCal)) {
                JOptionPane.showMessageDialog(this,
                    "Return date must be after departure date.",
                    "Date Error",
                    JOptionPane.WARNING_MESSAGE);
                ReturnDate.setDate(depDate);
                return false;
            }
        }
        
        return true;
    }
    
    private double calculatePrice(String locationFrom, String locationTo) {
        if (locationFrom.equals("Phnom Penh") || locationTo.equals("Phnom Penh")) {
            return 29.0;
        }
        return 29.0;
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.JPanel jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        time = new javax.swing.JLabel();
        date = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        LaHome = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        LaTicket = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        LaHistory = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        LaExit = new javax.swing.JLabel();
        Maintb = new javax.swing.JTabbedPane();
        bt1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        tb2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        LocationFrom = new javax.swing.JComboBox<>();
        LocationTo = new javax.swing.JComboBox<>();
        DepartureDate = new com.toedter.calendar.JDateChooser();
        ReturnDate = new com.toedter.calendar.JDateChooser();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        BtnPayNow = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        tb3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TableHistory = new javax.swing.JTable();
        tb5 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        Back29$ = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        tb6 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        Back15$ = new javax.swing.JButton();
        Logo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        time.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        time.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        time.setText("time");
        jPanel4.add(time, new org.netbeans.lib.awtextra.AbsoluteConstraints(1109, 6, 206, 37));

        date.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        date.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        date.setText("date");
        jPanel4.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(897, 8, 206, 33));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(51, 0, 153));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Flowsync Transit");
        jPanel4.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 0, 570, 120));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 0, 1360, 130));

        jPanel8.setBackground(new java.awt.Color(51, 51, 51));
        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204), 2));
        jPanel6.setForeground(new java.awt.Color(153, 204, 255));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        jPanel8.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 7, 176, -1));

        jPanel9.setBackground(new java.awt.Color(0, 0, 102));
        jPanel9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel9.setForeground(new java.awt.Color(153, 204, 255));
        jPanel9.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        LaHome.setBackground(new java.awt.Color(255, 255, 255));
        LaHome.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        LaHome.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LaHome.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/Home1.png"))); // NOI18N
        LaHome.setText("   Home");
        LaHome.setToolTipText("");
        LaHome.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        LaHome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LaHomeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(LaHome, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(LaHome, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel9.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 190, 70));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        LaTicket.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        LaTicket.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LaTicket.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/ticket1.png"))); // NOI18N
        LaTicket.setText("  Ticket");
        LaTicket.setToolTipText("");
        LaTicket.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        LaTicket.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LaTicketMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(LaTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(LaTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 190, 70));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        LaHistory.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        LaHistory.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LaHistory.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/History1.png"))); // NOI18N
        LaHistory.setText("  History");
        LaHistory.setToolTipText("");
        LaHistory.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        LaHistory.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LaHistoryMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(LaHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(LaHistory, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel9.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 190, 70));

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        LaExit.setFont(new java.awt.Font("Yu Gothic UI Semilight", 1, 18)); // NOI18N
        LaExit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LaExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/Exit1.png"))); // NOI18N
        LaExit.setText("     Exit");
        LaExit.setToolTipText("");
        LaExit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        LaExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LaExitMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LaExit, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(LaExit, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel9.add(jPanel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 580, 190, 70));

        jPanel8.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 210, 660));

        jPanel1.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, 210, 660));

        bt1.setBackground(new java.awt.Color(153, 204, 255));
        bt1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/photo.jpg"))); // NOI18N
        bt1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 1010, 650));

        Maintb.addTab("tab2", bt1);

        tb2.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setFont(new java.awt.Font("Sylfaen", 2, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 204));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Book Bus Tickets");

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0))));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        LocationFrom.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        LocationFrom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Phnom Penh", "Kandal", "Kampong Cham", "Kampong Thom", "Kampong Chhnang", "Kampong Speu", "Kampot", "Koh Kong", "Kratie", "Mondulkiri", "Banteay Meanchey", "Battambang", "Pailin", "Preah Vihear", "Pursat", "Prey Veng", "Siem Reap", "Ratanakiri", "Stung Treng", "Svay Rieng", "Takeo", "Tboung Khmum", "Oddar Meanchey", "Kep", "Preah Sihanouk (Sihanoukville)" }));
        LocationFrom.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(LocationFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, 260, 60));

        LocationTo.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        LocationTo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Phnom Penh", "Kandal", "Kampong Cham", "Kampong Thom", "Kampong Chhnang", "Kampong Speu", "Kampot", "Koh Kong", "Kratie", "Mondulkiri", "Banteay Meanchey", "Battambang", "Pailin", "Preah Vihear", "Pursat", "Prey Veng", "Siem Reap", "Ratanakiri", "Stung Treng", "Svay Rieng", "Takeo", "Tboung Khmum", "Oddar Meanchey", "Kep", "Preah Sihanouk (Sihanoukville)" }));
        LocationTo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.add(LocationTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 170, 290, 60));
        jPanel2.add(DepartureDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 170, 230, 60));
        jPanel2.add(ReturnDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 170, 210, 60));

        jLabel3.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/User Location.png"))); // NOI18N
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 370, 40, 40));

        jLabel4.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/Shipping Location.png"))); // NOI18N
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 360, 60, 50));

        jLabel5.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/Date To.png"))); // NOI18N
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 350, -1, 70));

        jLabel6.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/Schedule.png"))); // NOI18N
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 350, 60, 60));

        BtnPayNow.setBackground(new java.awt.Color(0, 51, 153));
        BtnPayNow.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        BtnPayNow.setForeground(new java.awt.Color(0, 204, 204));
        BtnPayNow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/Khqr.png"))); // NOI18N
        BtnPayNow.setText("Pay Now");
        BtnPayNow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPayNowActionPerformed(evt);
            }
        });
        jPanel2.add(BtnPayNow, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 410, 160, 50));
        jPanel2.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 410, 710, 10));

        jLabel10.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/User Location.png"))); // NOI18N
        jLabel10.setText("Location From");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 130, 250, 40));

        jLabel11.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/Shipping Location.png"))); // NOI18N
        jLabel11.setText("Location To");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 120, 280, 50));

        jLabel12.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/Date To.png"))); // NOI18N
        jLabel12.setText("Departure Date");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 120, 220, 50));

        jLabel13.setFont(new java.awt.Font("Segoe UI Light", 1, 18)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/Schedule.png"))); // NOI18N
        jLabel13.setText("Return Date  ");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 110, 210, 60));

        jLabel14.setText("____________________________");
        jPanel2.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 380, -1, -1));

        jLabel15.setText("____________________________");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 380, -1, -1));

        jLabel16.setText("____________________________");
        jPanel2.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 380, -1, -1));

        javax.swing.GroupLayout tb2Layout = new javax.swing.GroupLayout(tb2);
        tb2.setLayout(tb2Layout);
        tb2Layout.setHorizontalGroup(
            tb2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tb2Layout.createSequentialGroup()
                .addGap(420, 420, 420)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(420, 420, 420))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, tb2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(49, 49, 49))
        );
        tb2Layout.setVerticalGroup(
            tb2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tb2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        Maintb.addTab("tab1", tb2);

        tb3.setBackground(new java.awt.Color(153, 153, 153));
        tb3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        TableHistory.setAutoCreateRowSorter(true);
        TableHistory.setBackground(new java.awt.Color(204, 204, 204));
        TableHistory.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        TableHistory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Name", "PhoneNumber", "Location From", "Location To", "Departure Date", "Return Date  ", "Money", "Bank", "Time "
            }
        ));
        TableHistory.setCellSelectionEnabled(true);
        jScrollPane1.setViewportView(TableHistory);

        tb3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 1300, 630));

        Maintb.addTab("tab3", tb3);

        tb5.setBackground(new java.awt.Color(153, 153, 153));
        tb5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Back29$.setBackground(new java.awt.Color(204, 0, 0));
        Back29$.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Back29$.setForeground(new java.awt.Color(255, 255, 255));
        Back29$.setText("Back");
        Back29$.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Back29$ActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/photo_2025-12-23_00-46-24.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addComponent(Back29$, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(140, 140, 140))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(Back29$, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );

        tb5.add(jPanel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 400, 620));

        Maintb.addTab("tab4", tb5);

        tb6.setBackground(new java.awt.Color(153, 153, 153));
        tb6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/photo_2025-12-23_00-46-41.jpg"))); // NOI18N

        Back15$.setBackground(new java.awt.Color(204, 0, 0));
        Back15$.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Back15$.setForeground(new java.awt.Color(255, 255, 255));
        Back15$.setText("Back");
        Back15$.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Back15$ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Back15$, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(137, 137, 137))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel13Layout.createSequentialGroup()
                .addContainerGap(43, Short.MAX_VALUE)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 464, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(Back15$, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );

        tb6.add(jPanel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 400, 620));

        Maintb.addTab("tab4", tb6);

        jPanel1.add(Maintb, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 90, 1330, 700));

        Logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/logo6.png"))); // NOI18N
        Logo.setText("jLabel2");
        jPanel1.add(Logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 130));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 804, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void LaHomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LaHomeMouseClicked
         Maintb.setSelectedIndex(0);
    }//GEN-LAST:event_LaHomeMouseClicked

    private void LaTicketMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LaTicketMouseClicked
        Maintb.setSelectedIndex(1);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 1);
        DepartureDate.setDate(cal.getTime());
        ReturnDate.setDate(null);
        
    }//GEN-LAST:event_LaTicketMouseClicked

    private void LaExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LaExitMouseClicked
         int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to logout?",
            "Confirm Logout",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            new Login().setVisible(true);
            this.dispose();
        }
    }//GEN-LAST:event_LaExitMouseClicked

    private void LaHistoryMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LaHistoryMouseClicked
        loadBookingHistory(); 
        Maintb.setSelectedIndex(2);   
         
    }//GEN-LAST:event_LaHistoryMouseClicked

    private void BtnPayNowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPayNowActionPerformed
        if (!validateBookingForm()) {
            return;
        }
        
        String locationFrom = (String) LocationFrom.getSelectedItem();
        String locationTo = (String) LocationTo.getSelectedItem();
        Date depDate = DepartureDate.getDate();
        Date retDate = ReturnDate.getDate();
        
        double price = calculatePrice(locationFrom, locationTo);
        
        String summary = String.format(
            "Booking Summary:\n\n" +
            "From: %s\n" +
            "To: %s\n" +
            "Departure: %s\n" +
            "Return: %s\n" +
            "Estimated Price: $%.2f\n\n" +
            "Select payment method:",
            locationFrom, locationTo,
            DATE_FORMAT.format(depDate),
            retDate != null ? DATE_FORMAT.format(retDate) : "One Way",
            price
        );
        
        String[] options = {"ABA Bank ($29)", "Acdeda Bank ($29)", "Cancel"};
        int choice = JOptionPane.showOptionDialog(this, 
            summary,
            "Confirm Booking & Payment", 
            JOptionPane.DEFAULT_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, options, options[0]);

        if (choice == 0) {
            if (saveBooking(locationFrom, locationTo, depDate, retDate, price, "ABA")) {
                Maintb.setSelectedIndex(3);
                loadBookingHistory();
            }
        } else if (choice == 1) {
            if (saveBooking(locationFrom, locationTo, depDate, retDate, price, "Acleda")) {
                Maintb.setSelectedIndex(4);
                loadBookingHistory();
            }
        }
    }//GEN-LAST:event_BtnPayNowActionPerformed

    private void Back15$ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Back15$ActionPerformed
       Maintb.setSelectedIndex(1);
    }//GEN-LAST:event_Back15$ActionPerformed

    private void Back29$ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Back29$ActionPerformed
        Maintb.setSelectedIndex(1);
    }//GEN-LAST:event_Back29$ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        java.awt.EventQueue.invokeLater(() -> {
            new FromMain("testuser").setVisible(true);
        });
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Back15$;
    private javax.swing.JButton Back29$;
    private javax.swing.JButton BtnPayNow;
    private com.toedter.calendar.JDateChooser DepartureDate;
    private javax.swing.JLabel LaExit;
    private javax.swing.JLabel LaHistory;
    private javax.swing.JLabel LaHome;
    private javax.swing.JLabel LaTicket;
    private javax.swing.JComboBox<String> LocationFrom;
    private javax.swing.JComboBox<String> LocationTo;
    private javax.swing.JLabel Logo;
    private javax.swing.JTabbedPane Maintb;
    private com.toedter.calendar.JDateChooser ReturnDate;
    private javax.swing.JTable TableHistory;
    private javax.swing.JPanel bt1;
    private javax.swing.JLabel date;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel tb2;
    private javax.swing.JPanel tb3;
    private javax.swing.JPanel tb5;
    private javax.swing.JPanel tb6;
    private javax.swing.JLabel time;
    // End of variables declaration//GEN-END:variables
}