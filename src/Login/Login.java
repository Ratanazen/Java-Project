package Login;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;






/**
 *
 * @author ratana
 */
public class Login extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Login.class.getName());

    /**
     * Creates new form CreateAcc
     */
    public Login() {
        initComponents();
    }

  

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        JTSignup = new javax.swing.JTabbedPane();
        JP1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        javax.swing.JLabel jLabel5 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        JP2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        Signin = new javax.swing.JButton();
        Confirmpassword = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        Username = new javax.swing.JTextField();
        Phonenumber = new javax.swing.JTextField();
        Password = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        JP3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        Resetpassword = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setFocusable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1000, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 120, Short.MAX_VALUE)
        );

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 120));

        JP1.setBackground(new java.awt.Color(255, 255, 255));
        JP1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/LG1.jpg"))); // NOI18N
        JP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 570, 430));

        jButton1.setBackground(new java.awt.Color(0, 153, 153));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("Login");
        jButton1.addActionListener(this::jButton1ActionPerformed);
        JP1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 410, 128, 46));

        jPanel2.setBackground(new java.awt.Color(0, 153, 153));

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/Lock.png"))); // NOI18N

        txtUsername.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtUsername.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUsername.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtUsername.addActionListener(this::txtUsernameActionPerformed);

        txtPassword.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtPassword.addActionListener(this::txtPasswordActionPerformed);

        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel5.setText("Password");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel4.setText("Username ");

        jLabel2.setText("Don't have an account?");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 255));
        jLabel3.setText("SIGN UP");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        jLabel12.setBackground(new java.awt.Color(0, 153, 153));
        jLabel12.setText("forgot password?");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/User Male.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE)
                                .addComponent(txtUsername)))
                        .addGap(98, 98, 98))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(90, 90, 90))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
                    .addComponent(txtUsername))
                .addGap(18, 18, 18)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        JP1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 60, 410, 340));

        JTSignup.addTab("tab2", JP1);

        JP2.setBackground(new java.awt.Color(255, 255, 255));
        JP2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel6.setText("Username");
        JP2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, -1, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel7.setText("Phone number");
        JP2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, -1, -1));

        Signin.setBackground(new java.awt.Color(0, 153, 255));
        Signin.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        Signin.setForeground(new java.awt.Color(102, 102, 102));
        Signin.setText("Sign In");
        Signin.addActionListener(this::SigninActionPerformed);
        JP2.add(Signin, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 400, 100, 40));

        Confirmpassword.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Confirmpassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Confirmpassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Confirmpassword.addActionListener(this::ConfirmpasswordActionPerformed);
        JP2.add(Confirmpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 340, 290, 40));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Confirm password");
        JP2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, -1, -1));

        jLabel9.setBackground(new java.awt.Color(51, 51, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/Create(1).png"))); // NOI18N
        JP2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 0, 360, 430));

        Username.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Username.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Username.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Username.addActionListener(this::UsernameActionPerformed);
        JP2.add(Username, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 290, 40));

        Phonenumber.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Phonenumber.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Phonenumber.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Phonenumber.addActionListener(this::PhonenumberActionPerformed);
        JP2.add(Phonenumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 290, 40));

        Password.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Password.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Password.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Password.addActionListener(this::PasswordActionPerformed);
        JP2.add(Password, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, 290, 40));

        jLabel10.setText("__________________________________________________________");
        JP2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(568, 380, 290, 50));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("Password");
        JP2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, -1, -1));

        jPanel4.setBackground(new java.awt.Color(0, 102, 51));
        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/Arrow Pointing Left.png"))); // NOI18N
        jLabel15.setText(" Back");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 48, Short.MAX_VALUE)
        );

        JP2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 490, 130, 50));

        JTSignup.addTab("tab1", JP2);

        JP3.setBackground(new java.awt.Color(255, 255, 255));
        JP3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/reset-password (1).png"))); // NOI18N
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, 300, 440));

        jTextField1.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField1.addActionListener(this::jTextField1ActionPerformed);
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 240, 340, 50));

        Resetpassword.setBackground(new java.awt.Color(102, 102, 255));
        Resetpassword.setText("Reset password");
        Resetpassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Resetpassword.addActionListener(this::ResetpasswordActionPerformed);
        jPanel1.add(Resetpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 370, 150, 50));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel13.setText("Name");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 240, 50, 50));

        jLabel17.setFont(new java.awt.Font("MV Boli", 3, 48)); // NOI18N
        jLabel17.setText("Reset password");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 90, 400, 130));

        jPanel3.setBackground(new java.awt.Color(0, 255, 51));

        jLabel16.setBackground(new java.awt.Color(255, 0, 102));
        jLabel16.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/Arrow Pointing Left.png"))); // NOI18N
        jLabel16.setText(" Back");
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 500, -1, 40));

        JP3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 910, 570));

        JTSignup.addTab("tab3", JP3);

        getContentPane().add(JTSignup, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 1000, 590));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUsernameActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
              String username = txtUsername.getText().trim();
              String password = txtPassword.getText().trim();

    if (username.isEmpty() || password.isEmpty()) {
        JOptionPane.showMessageDialog(this, 
            "Please enter username and password", 
            "Input Required", 
            JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    try (Connection con = DBConnection.getConnection()) {
        
        if (con == null) {
            JOptionPane.showMessageDialog(this, 
                "Database connection failed!", 
                "Connection Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "SELECT username, role FROM users WHERE username=? AND password=?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, username);
            pst.setString(2, password);

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    String role = rs.getString("role");
                    String user = rs.getString("username");

                    JOptionPane.showMessageDialog(this, 
                        "Login successful! Welcome " + user, 
                        "Success", 
                        JOptionPane.INFORMATION_MESSAGE);

                    if (role.equalsIgnoreCase("admin")) {
                        new Admin().setVisible(true);
                    } else {
                        new FromMain(user).setVisible(true);
                    }
                    this.dispose();

                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Invalid username or password", 
                        "Login Failed", 
                        JOptionPane.ERROR_MESSAGE);
                    txtPassword.setText("");
                }
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, 
            "Database error: " + e.getMessage(), 
            "Error", 
            JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        JTSignup.setSelectedIndex(1);
        
    }//GEN-LAST:event_jLabel3MouseClicked

    private void SigninActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SigninActionPerformed
    String username = Username.getText().trim();
    String phone = Phonenumber.getText().trim();
    String password = Password.getText().trim();
    String confirm = Confirmpassword.getText().trim();

    if (username.isEmpty() || phone.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Please fill all fields");
        return;
    }

    if (!password.equals(confirm)) {
        JOptionPane.showMessageDialog(this, "Passwords do not match");
        return;
    }

    Connection con = null;
    PreparedStatement pst = null;

    try {
        con = DBConnection.getConnection(); // ← Changed from getConnection()

        if (con == null) {
            JOptionPane.showMessageDialog(this, "Database connection failed");
            return;
        }

        String sql = "INSERT INTO users (username, phone, password, role) VALUES (?, ?, ?, 'user')";
        pst = con.prepareStatement(sql);
        pst.setString(1, username);
        pst.setString(2, phone);
        pst.setString(3, password);

        pst.executeUpdate();

        JOptionPane.showMessageDialog(this, "Account created successfully!");
        
        // Clear fields
        Username.setText("");
        Phonenumber.setText("");
        Password.setText("");
        Confirmpassword.setText("");
        
        JTSignup.setSelectedIndex(0); // back to login

    } catch (SQLException e) {
        if (e.getMessage().contains("Duplicate entry")) {
            JOptionPane.showMessageDialog(this, "Username already exists");
        } else {
            JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage());
        }
    } finally {
        try {
            if (pst != null) pst.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }//GEN-LAST:event_SigninActionPerformed

    private void ConfirmpasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ConfirmpasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ConfirmpasswordActionPerformed

    private void UsernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UsernameActionPerformed

    private void PhonenumberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PhonenumberActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PhonenumberActionPerformed

    private void PasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PasswordActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void ResetpasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ResetpasswordActionPerformed
        String username = jTextField1.getText().trim();

    if (username.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Enter username");
        return;
    }

    String newPassword = JOptionPane.showInputDialog(this, "Enter new password");

    if (newPassword == null || newPassword.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Password cannot be empty");
        return;
    }

    Connection con = null;
    PreparedStatement pst = null;

    try {
        con = DBConnection.getConnection(); // ← Changed

        if (con == null) {
            JOptionPane.showMessageDialog(this, "Database connection failed");
            return;
        }

        String sql = "UPDATE users SET password=? WHERE username=?";
        pst = con.prepareStatement(sql);
        pst.setString(1, newPassword);
        pst.setString(2, username);

        int result = pst.executeUpdate();

        if (result > 0) {
            JOptionPane.showMessageDialog(this, "Password reset successful!");
            jTextField1.setText("");
            JTSignup.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(this, "Username not found");
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
    
    }//GEN-LAST:event_ResetpasswordActionPerformed

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel12MouseClicked
        JTSignup.setSelectedIndex(2);
    }//GEN-LAST:event_jLabel12MouseClicked

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel15MouseClicked
       JTSignup.setSelectedIndex(0);
    }//GEN-LAST:event_jLabel15MouseClicked

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel16MouseClicked
       JTSignup.setSelectedIndex(0);
    }//GEN-LAST:event_jLabel16MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
       
        java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
    }
 
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Confirmpassword;
    private javax.swing.JPanel JP1;
    private javax.swing.JPanel JP2;
    private javax.swing.JPanel JP3;
    private javax.swing.JTabbedPane JTSignup;
    private javax.swing.JTextField Password;
    private javax.swing.JTextField Phonenumber;
    private javax.swing.JButton Resetpassword;
    private javax.swing.JButton Signin;
    private javax.swing.JTextField Username;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
