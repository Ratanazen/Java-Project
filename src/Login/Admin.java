
package Login;

import Login.DBConnection;
import Login.Login;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

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

    private void loadAllBookings() {
        String sql = "SELECT * FROM bookings ORDER BY booking_time DESC";
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); 

        try (Connection con = DBConnection.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {

            if (con == null) return;

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
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        }
    }
}


  
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
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
        TicketUser = new javax.swing.JPanel();
        HistoryUser = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        InfoUser = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        javax.swing.GroupLayout HomeAdminLayout = new javax.swing.GroupLayout(HomeAdmin);
        HomeAdmin.setLayout(HomeAdminLayout);
        HomeAdminLayout.setHorizontalGroup(
            HomeAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1330, Short.MAX_VALUE)
        );
        HomeAdminLayout.setVerticalGroup(
            HomeAdminLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 655, Short.MAX_VALUE)
        );

        Maintb.addTab("tab1", HomeAdmin);

        TicketUser.setBackground(new java.awt.Color(204, 204, 204));
        TicketUser.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Maintb.addTab("tab2", TicketUser);

        HistoryUser.setBackground(new java.awt.Color(0, 51, 102));
        HistoryUser.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        HistoryUser.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1290, 630));

        Maintb.addTab("tab3", HistoryUser);

        InfoUser.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        Maintb.addTab("tab4", InfoUser);

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

       loadAllBookings(); 
    Maintb.setSelectedIndex(2);
    }//GEN-LAST:event_LaHistoryMouseClicked

    private void LaInfoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LaInfoMouseClicked
         Maintb.setSelectedIndex(3);
    }//GEN-LAST:event_LaInfoMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new Admin().setVisible(true);
        });
    }

    
   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel HistoryUser;
    private javax.swing.JPanel HomeAdmin;
    private javax.swing.JPanel InfoUser;
    private javax.swing.JLabel LaExit;
    private javax.swing.JLabel LaHistory;
    private javax.swing.JLabel LaHome;
    private javax.swing.JLabel LaInfo;
    private javax.swing.JLabel LaTicket;
    private javax.swing.JTabbedPane Maintb;
    private javax.swing.JPanel TicketUser;
    private javax.swing.JLabel date;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel time;
    // End of variables declaration//GEN-END:variables

