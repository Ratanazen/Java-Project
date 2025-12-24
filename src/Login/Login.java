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
    
    /**
     * Creates new form CreateAcc
     */
    public Login() {
        initComponents();
        setTitle("Bus Booking System - Login");
        setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        JTSignup = new javax.swing.JTabbedPane();
        JP1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel4 = new javax.swing.JLabel();
        javax.swing.JLabel jLabel5 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
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
        jLabel15 = new javax.swing.JLabel();
        JP3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        Resetpassword = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

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

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/LG1.jpg"))); // NOI18N
        jLabel1.setText("jLabel1");
        JP1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -10, 611, 543));

        txtUsername.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtUsername.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtUsername.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtUsername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUsernameActionPerformed(evt);
            }
        });
        JP1.add(txtUsername, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 130, 320, 54));

        txtPassword.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        txtPassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtPassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });
        JP1.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 240, 320, 54));

        jButton1.setBackground(new java.awt.Color(0, 153, 153));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setText("Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        JP1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(722, 342, 128, 46));

        jLabel2.setText("Don't have an account?");
        JP1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(675, 406, -1, 25));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 102, 255));
        jLabel3.setText("SIGN UP");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        JP1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 400, -1, 30));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel4.setText("Username ");
        JP1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 100, 140, 30));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel5.setText("Password");
        JP1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 210, 140, 30));

        jLabel12.setBackground(new java.awt.Color(0, 153, 153));
        jLabel12.setText("forgot password?");
        jLabel12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel12MouseClicked(evt);
            }
        });
        JP1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 300, 100, -1));

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
        Signin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SigninActionPerformed(evt);
            }
        });
        JP2.add(Signin, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 400, -1, -1));

        Confirmpassword.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Confirmpassword.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Confirmpassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Confirmpassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ConfirmpasswordActionPerformed(evt);
            }
        });
        JP2.add(Confirmpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 340, 290, 40));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel8.setText("Confirm password");
        JP2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, -1, -1));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/Create(1).png"))); // NOI18N
        JP2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, -60, 430, 440));

        Username.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Username.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Username.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Username.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsernameActionPerformed(evt);
            }
        });
        JP2.add(Username, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, 290, 40));

        Phonenumber.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Phonenumber.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Phonenumber.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Phonenumber.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PhonenumberActionPerformed(evt);
            }
        });
        JP2.add(Phonenumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 290, 40));

        Password.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        Password.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        Password.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PasswordActionPerformed(evt);
            }
        });
        JP2.add(Password, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 260, 290, 40));

        jLabel10.setText("__________________________________________________________");
        JP2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 350, -1, -1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel11.setText("Password");
        JP2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 230, -1, -1));

        jLabel15.setText("< Back");
        jLabel15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel15MouseClicked(evt);
            }
        });
        JP2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 510, 60, 40));

        JTSignup.addTab("tab1", JP2);

        JP3.setBackground(new java.awt.Color(255, 255, 255));
        JP3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgfile/reset-password (1).png"))); // NOI18N
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 0, 400, 560));

        jTextField1.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 280, 340, 50));

        Resetpassword.setBackground(new java.awt.Color(102, 102, 255));
        Resetpassword.setText("Reset password");
        Resetpassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        Resetpassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ResetpasswordActionPerformed(evt);
            }
        });
        jPanel1.add(Resetpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 350, 150, 40));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        jLabel13.setText("Nhmber Phone");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 260, 110, -1));

        jLabel16.setBackground(new java.awt.Color(51, 51, 51));
        jLabel16.setForeground(new java.awt.Color(51, 51, 51));
        jLabel16.setText("< Back");
        jLabel16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel16MouseClicked(evt);
            }
        });
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 510, 60, 40));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel17.setText("Reset password");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, 180, 50));

        JP3.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 910, 570));

        JTSignup.addTab("tab3", JP3);

        getContentPane().add(JTSignup, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 1000, 590));

        pack();
    }// </editor-fold>                        

    private void txtUsernameActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        String username = txtUsername.getText().trim();
        String password = txtPassword.getText().trim();
        
        // Validation
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter username and password", 
                "Input Required", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Connection con = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        try {
            con = DBConnection.getConnection();
            
            if (con == null) {
                JOptionPane.showMessageDialog(this, 
                    "Database connection failed!", 
                    "Connection Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            String sql = "SELECT username, role FROM users WHERE username=? AND password=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);

            rs = pst.executeQuery();

            if (rs.next()) {
                String role = rs.getString("role");
                String user = rs.getString("username");

                JOptionPane.showMessageDialog(this, 
                    "Login successful! Welcome " + user, 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);

                // Redirect based on role
                if (role != null && role.equalsIgnoreCase("admin")) {
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

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Database error: " + e.getMessage(), 
                "Error", 
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

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {                                     
        JTSignup.setSelectedIndex(1);
    }                                    

    private void SigninActionPerformed(java.awt.event.ActionEvent evt) {                                       
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

        // Validate phone number (basic validation)
        if (!phone.matches("\\d+")) {
            JOptionPane.showMessageDialog(this, "Phone number should contain only digits");
            return;
        }

        Connection con = null;
        PreparedStatement pst = null;

        try {
            con = DBConnection.getConnection();

            if (con == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed");
                return;
            }

            // Check if username already exists
            String checkSql = "SELECT username FROM users WHERE username=?";
            pst = con.prepareStatement(checkSql);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "Username already exists");
                return;
            }
            rs.close();
            pst.close();

            // Insert new user
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
    }                                      

    private void ConfirmpasswordActionPerformed(java.awt.event.ActionEvent evt) {                                                
        // TODO add your handling code here:
    }                                               

    private void UsernameActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void PhonenumberActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void PasswordActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void ResetpasswordActionPerformed(java.awt.event.ActionEvent evt) {                                              
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
            con = DBConnection.getConnection();

            if (con == null) {
                JOptionPane.showMessageDialog(this, "Database connection failed");
                return;
            }

            // First check if user exists
            String checkSql = "SELECT username FROM users WHERE username=?";
            pst = con.prepareStatement(checkSql);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            
            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Username not found");
                return;
            }
            rs.close();
            pst.close();

            // Update password
            String sql = "UPDATE users SET password=? WHERE username=?";
            pst = con.prepareStatement(sql);
            pst.setString(1, newPassword);
            pst.setString(2, username);

            int result = pst.executeUpdate();

            if (result > 0) {
                JOptionPane.showMessageDialog(this, "Password reset successful!");
                jTextField1.setText("");
                JTSignup.setSelectedIndex(0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
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

    private void jLabel12MouseClicked(java.awt.event.MouseEvent evt) {                                      
        JTSignup.setSelectedIndex(2);
    }                                     

    private void jLabel15MouseClicked(java.awt.event.MouseEvent evt) {                                      
        JTSignup.setSelectedIndex(0);
    }                                     

    private void jLabel16MouseClicked(java.awt.event.MouseEvent evt) {                                      
        JTSignup.setSelectedIndex(0);
    }                                     

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new Login().setVisible(true);
        });
    }
 
    // Variables declaration - do not modify                     
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration                   
}