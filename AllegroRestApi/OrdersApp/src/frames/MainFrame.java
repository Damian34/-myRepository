/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frames;

import frames.operations.AuthOperations;
import frames.operations.Config;
import frames.operations.ConfigFrame;
import frames.operations.DateConf;
import frames.operations.FileOperations;
import frames.operations.Payment;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author dami
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public final static Dimension dim = new Dimension(300, 20);
    public Config conf = null;
    public AuthOperations auth = null;
    public String token = "";
    private DefaultTableModel model = new DefaultTableModel();
    private List<Payment> table = new ArrayList<>();
    private boolean order = false;

    public MainFrame() {
        initComponents();
        new ConfigFrame(this, "Pobieranie wpłat z allegro z pomocą REST api");
        this.setLoginPanel1();
        this.setLoginPanel2();
    }

    private void setLoginPanel1() {
        this.jPanel1.setBackground(Color.white);
        this.jLabel1.setText("Link do autoryzacji przez przeglądarkę");
        this.jTextField1.setEditable(true);
        this.jTextField1.setEnabled(true);
        this.jTextField1.setMaximumSize(dim);
        this.jTextField1.setText("");
        this.jLabel2.setText("Proszę podać kod po udanej autoryzacji");
        this.jLabel3.setText("(znajduje się on w adresie URL na przekierowanej stronie)");
        this.jTextField2.setEditable(true);
        this.jTextField2.setEnabled(true);
        this.jTextField2.setMaximumSize(dim);
        this.jTextField2.setText("");
        this.jButton1.setMaximumSize(dim);
        this.jButton1.setText("Zaloguj");
        this.jLabel4.setMaximumSize(dim);
        this.jLabel4.setText("");

        getConfig();
    }

    private void setLoginPanel2() {
        this.jPanel2.setBackground(Color.white);
        this.jButton2.setText("Pobierz");
        this.jButton3.setText("Zaznacz");
        this.jButton4.setText("Odwróć zaznacz");
        this.jButton5.setText("Suma zaznaczone");
        this.jButton6.setText("Zapisz");
        this.jButton7.setText("Sortuj po dacie zakończenia");
        this.jLabel7.setText("");
        this.jLabel8.setText("");

        DateConf dat = new DateConf();
        this.jTextField3.setText(dat.getToDay());
        this.jTextField4.setText(dat.getToDay());
        this.jTextField5.setText(dat.getToDay());
        this.jTextField6.setText(dat.getToDay());
    }

    private void getConfig() {
        FileOperations fo = new FileOperations();
        this.conf = fo.getConfigData(this.jLabel4);
        if (this.conf != null) {
            this.auth = new AuthOperations(this.conf);
            this.jTextField1.setText(auth.getAuthorizationUrl());
        }
    }

    public void getToken() {
        if (this.auth != null) {
            this.token = this.auth.getToken(this.jTextField2.getText());
            if (!this.token.equals("")) {
                this.jLabel4.setText("zostałeś zalogowany");
            } else {
                this.jLabel4.setText("logowanie nie powiodło się");
            }

        } else {
            this.jLabel4.setText("proszę podać kod po udanej autoryzacji");
        }
    }

    public void getPayments() {
        //te linijki są do kasacji i json w auth.getPayments też
        /*this.conf = new Config("09a188e5710941f2b80a56ebc9da35ce", "aSt72fgOpJL38TdTSe2nFiMvS543zHNM1uMPnMv0GW3VxkD61Zc8634c4xiYoCO8", "https://allegro.pl");
        this.token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1Njc2NDEyNzYsInVzZXJfbmFtZSI6Ijk0Nzg4NDQiLCJqdGkiOiJmOTIwY2RiMS01OWRmLTQwNmMtYTIwZi0yMjk0NWEyZThjOWQiLCJjbGllbnRfaWQiOiIwOWExODhlNTcxMDk0MWYyYjgwYTU2ZWJjOWRhMzVjZSIsInNjb3BlIjpbImFsbGVncm9fYXBpIl19.5kV_9ikY1Z6nwaUsgB3jtpbMKsyd9Z7W5qSYVLFrklnaaioB7FVK1oHg9GJheQveFWW5aA-r9u8_d41KubxedP20BKkqQAJoN9sNDqrjPXgYH2h-jyY5Gy02nt7cyy5h3jz-VNUaqqaZLqDWxgw9DxF7R3QebQC9C1CyV3QoiBx73p2wKMX3w1T8Z6_1yvThfpw_QLRrokF-vyi_zFnvoKaB6uv1H_MMHuzTAtKCfF1FEpisqF6CBhIqdvE0jBjljrI1eD_L4N-5UuM8aweyzvaE8HnGiVF9NdQhuocwV8XH25Auv9jbWmS1Z273e5OzjxZ2rgooK4dI8zpaI7iMRQ";
        this.auth = new AuthOperations(this.conf);*/
        
        if (this.token.equals("") || this.auth == null) {
            this.jLabel7.setText("nie jesteś zalogowany");
            return;
        }
        
        this.order = false;
        Runnable runnable = () -> { 
            this.jLabel8.setText("pobierane są dane ,proszę czekać");
            this.table = this.auth.getPayments(token,this.jTextField3.getText(), this.jTextField4.getText(),this.jTextField5.getText(),this.jTextField6.getText());
            this.table = this.modifyPayment(this.table);
            sort();            
            setTable(this.table);
            this.jLabel8.setText("zakończono pobierać dane");
        };
        Thread t = new Thread(runnable);
        t.start();
}

    public void setTable(List<Payment> list) {
        try {
            Object[] columnNames = {"", "ID", "Metoda płatności", "Status", "Kwota", "Data rozpoczęcia", "Data zakończenia", "Dane"};
            Object[][] data = new Object[list.size()][8];
            for (int i = 0; i < list.size(); i++) {
                data[i] = list.get(i).getPayment();
            }
            this.model = new DefaultTableModel(data, columnNames) {
                @Override
                public Class<?> getColumnClass(int column) {
                    if (column == 0) {
                        return Boolean.class;
                    } else {
                        return String.class;
                    }
                }

                public void tableChanged(TableModelEvent e) {
                }
            };
            this.model.addTableModelListener(new TableModelListener() {
                @Override
                public void tableChanged(TableModelEvent e) {
                    if (e.getColumn() == 0) {
                        list.get(e.getFirstRow()).cheack = !list.get(e.getFirstRow()).cheack;
                    }
                }
            });
            jTable1.setModel(model);            
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
    }

    public void reverseTable() {    
        if (this.table.size() == 0) {
            return;
        }
        for (int i = 0; i < this.table.size(); i++) {
            table.get(i).cheack = !table.get(i).cheack;
        }
        setTable(this.table);
    }

    public void markAll() {
        if (this.table.size() == 0) {
            return;
        }
        for (int i = 0; i < this.table.size(); i++) {
            if(table.get(i).status.equals("Zakończona")){
                table.get(i).cheack = true;                
            }
        }
        setTable(this.table);
    }

    public void save() {
        List<String> list = this.table.stream()
                .filter(obj -> obj.cheack)
                .map(obj -> {
                    return obj.toString();
                })
                .collect(Collectors.toList());
        new SaveFrame(list);
    }

    public void sum() {
        double sum = 0;
        for (int i = 0; i < this.table.size(); i++) {
            try {
                if (this.table.get(i).cheack) {
                    String[] pay = this.table.get(i).sum.split(" ");
                    double x = Double.valueOf(pay[0]);
                    sum += x;
                }
            } catch (Exception e) {
            }
        }
        String sum2 = sum + "";
        String sum3 = "";
        if (sum2.length() >= sum2.indexOf('.') + 3) {
            sum3 = sum2.substring(0, sum2.indexOf('.') + 3);
        } else {
            sum3 = sum2;
        }
        new SumFrame(sum3);
    }

    public List<Payment> modifyPayment(List<Payment> pays) {
        List<Payment> list = new ArrayList<>();
        for (int i = 0; i < pays.size(); i++) {
            /*
            READY_FOR_PROCESSING
            FILLED_IN
            BOUGHT
             */
            if (pays.get(i).status.equals("FILLED_IN")) {
                pays.get(i).status = "Nie zakończona";
            }
            if (pays.get(i).status.equals("READY_FOR_PROCESSING")) {
                pays.get(i).status = "Zakończona";
            }
            if (pays.get(i).status.equals("BOUGHT")) {
                pays.get(i).status = "Zakończona";
            }
            if (!pays.get(i).payment_m.equals("null")) {
                list.add(pays.get(i));
            }
        }
        return list;
    }
    
    public void sort(){
        DateConf df = new DateConf();
        df.sortByDate(this.table,this.order);
        setTable(this.table);
        this.order=!this.order;
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton6 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("jLabel1");

        jTextField1.setText("jTextField1");

        jLabel2.setText("jLabel2");

        jLabel3.setText("jLabel3");

        jTextField2.setText("jTextField2");

        jButton1.setText("jButton1");
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton1MousePressed(evt);
            }
        });

        jLabel4.setText("jLabel4");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 271, Short.MAX_VALUE)
                    .addComponent(jTextField2)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(527, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(12, 12, 12)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addContainerGap(315, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Logowanie", jPanel1);

        jLabel5.setText("od");

        jTextField3.setText("2019-08-01");

        jLabel6.setText("do");

        jTextField4.setText("2019-08-02");

        jButton2.setText("jButton2");
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton2MousePressed(evt);
            }
        });

        jButton3.setText("jButton3");
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton3MousePressed(evt);
            }
        });

        jButton4.setText("jButton4");
        jButton4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton4MousePressed(evt);
            }
        });

        jButton5.setText("jButton5");
        jButton5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton5MousePressed(evt);
            }
        });

        jLabel7.setText("jLabel7");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "ID", "Metoda Płatności", "Status", "Kwota", "Data rozpoczęcia", "Data zakończenia", "Dane"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton6.setText("jButton6");
        jButton6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton6MousePressed(evt);
            }
        });

        jLabel8.setText("jLabel8");

        jButton7.setText("jButton7");
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jButton7MousePressed(evt);
            }
        });

        jLabel9.setText("data zakończenia od");

        jTextField5.setText("jTextField5");

        jLabel10.setText("do");

        jTextField6.setText("jTextField6");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 808, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton7)
                        .addGap(99, 99, 99)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)
                        .addGap(108, 108, 108)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton6)
                    .addComponent(jLabel7)
                    .addComponent(jButton7))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Wpłaty", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MousePressed
        // TODO add your handling code here:
        getToken();
    }//GEN-LAST:event_jButton1MousePressed

    private void jButton2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton2MousePressed
        // TODO add your handling code here:
        getPayments();
    }//GEN-LAST:event_jButton2MousePressed

    private void jButton4MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton4MousePressed
        // TODO add your handling code here:
        reverseTable();
    }//GEN-LAST:event_jButton4MousePressed

    private void jButton6MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton6MousePressed
        // TODO add your handling code here:
        save();
    }//GEN-LAST:event_jButton6MousePressed

    private void jButton5MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton5MousePressed
        // TODO add your handling code here:
        sum();
    }//GEN-LAST:event_jButton5MousePressed

    private void jButton3MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton3MousePressed
        // TODO add your handling code here:
        markAll();
    }//GEN-LAST:event_jButton3MousePressed

    private void jButton7MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MousePressed
        // TODO add your handling code here:
        sort();
    }//GEN-LAST:event_jButton7MousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    public javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
