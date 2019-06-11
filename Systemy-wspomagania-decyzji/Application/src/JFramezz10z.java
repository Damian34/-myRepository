
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dami
 */
public class JFramezz10z extends javax.swing.JFrame {

    public JFramez3 app;
    public zGrupy grupowanie;
    /**
     * Creates new form JFramezzzz10z
     */
    public JFramezz10z() {
        initComponents();
        this.setTitle("Grupowanie");
        
        try {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        SwingUtilities.updateComponentTreeUI(this);
        this.pack();
        } 
        catch (Exception e) {}        
        this.setVisible(true);  
        
        grupowanie = new zGrupy(new Tabela());
    }
    
    public JFramezz10z(JFramez3 fr3) {
        initComponents();
        this.setTitle("Grupowanie");
        
        try {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        SwingUtilities.updateComponentTreeUI(this);
        this.pack();
        } 
        catch (Exception e) {}        
        this.setVisible(true);  
        
        this.app=fr3;
        this.grupowanie = new zGrupy(fr3.frame.kol);
        
        pisz();
        
    }
    
    
    public void pisz()
    {
        //wpisuje dane do pól
        List<String> list=app.frame.kol.Naglow();
        String[] tab0 ={"Euklidesowa","Manhattan","Nieskończoność","Mahalanobisa"};
        String[] tab1 ={"Median","Średnich"};
        
        jComboBox1.removeAllItems();
        for(int i=0;i<list.size();i++)//dodaje naglowki do jComboBox1
        jComboBox1.addItem(list.get(i));
        
        jComboBox2.removeAllItems();
        for(int i=0;i<tab0.length;i++)//dodaje nazwy metryk do jComboBox2
        jComboBox2.addItem(tab0[i]);
        
        jComboBox3.removeAllItems();
        for(int i=0;i<tab1.length;i++)//dodaje nazwy do optymalizacji do jComboBox3
        jComboBox3.addItem(tab1[i]);
        
    }
    
    public void czytaj(int pozycja)
    {
        //odczytuje dane        
        int klasy = jComboBox1.getSelectedIndex();//nr kolumny klasy
        int metryka = jComboBox2.getSelectedIndex();//nr oznaczjący metryke
        
        
        //odczytuje liczbe skupien
        boolean poprawne=true;//oznacza że dane są poprawne
        int kl=-1;
        
        try{
            kl=Integer.valueOf(jTextField1.getText());            
        }catch(Exception e){poprawne=false;}
        
        if(poprawne)
        {this.grupowanie = new zGrupy(app.frame.kol);}
        
        if(kl<0){kl=0;}
        if(kl>app.frame.kol.ileWierszy)//jesli liczba jest za duża to daje max
        {kl=app.frame.kol.ileWierszy;}
        
        //dla metody k-srednich
        if(poprawne && pozycja==0)
        {            
            //przedostatni null oznacza ze metoda bedzie liczyć aż znajdzie ,i może się zapętlić:)
            try{
            List<Integer> list = null;
            jTextArea1.setText("prosze czekac..");
            
            while(list==null)
            list=grupowanie.grupuj(app.frame.kol,kl,metryka,klasy,null,false);
            
            jTextArea1.setText("dodano nową klase do tabeli");            
            
            List<String> list1=new ArrayList<>();
            for(int i=0;i<list.size();i++)
            list1.add(list.get(i)+"");
            
            dodaj(list1,"Nowe");
            return;
            }catch(Exception e){System.out.println("blad: "+e);}
        }
        
        
        //do wylosowania nowych klas o liczbie dokladnej jak wczesniej ,dodatkowo podpisanych
        if(poprawne && pozycja==1)
        {                
            //List<Integer> ZamienKlasy(Tabela tab,int klasa,int metryka,Integer ile_petli)
            //grupuj(Tabela kol ,int k,int typ, int klas,Integer iteracje,boolean decyzyjna)
            
            //przedostatni null oznacza ze metoda bedzie liczyć aż znajdzie ,i może się zapętlić:)
            try{
            List<String> list = null;
            jTextArea1.setText("prosze czekac..");
            
            while(list==null)
            list=grupowanie.ZamienKlasy2(app.frame.kol,klasy,metryka,null);
                      
            
            jTextArea1.setText("dodano nową klase do tabeli");            
            
            dodaj(list,"Klasy");
            return;
            }catch(Exception e){System.out.println("blad: "+e);}
        }
        
        int opt = jComboBox3.getSelectedIndex();//typ optymalizacji
        
        
        //do wyznaczenia optymalnej liczby klas przy urzyciu klastracji hierarchicznej
        if(poprawne && pozycja==2)
        {            
            try{
            //int klasteryzacja(Tabela tab,int klasa,int metryka,int wynik) 
            jTextArea1.setText("prosze czekac..");
            
            int nr = grupowanie.klasteryzacja(app.frame.kol,klasy,metryka,opt);
            
            
            jTextArea1.setText("optymalna liczba klas:  "+nr); 
            
            return;
            }catch(Exception e){System.out.println("blad: "+e);}
        }
        
        
        //List<Integer> grupuj(Tabela kol ,int k,int typ, int klas,Integer iteracje,boolean decyzyjna)
    }
    
    public void dodaj(List<String> kolumna,String nazwa)
    {
        System.out.println(kolumna);
        //metoda dodaje nowa kolumne do danych
        
        List<String> list3 = new ArrayList<>(app.frame.kol.getLinie());
        int l=0;
        if(app.frame.kol.naglowki)//jeśli są nagłówki
        {
           //dodaje nagłówek
            list3.set(0, list3.get(0)+";"+nazwa+(app.frame.kol.ileKolumn()+1));
            l=1;
        }
        
        for(;l<list3.size();l++)
        {
            //System.out.println(list3.get(i));
            String pom="";
            if(app.frame.kol.naglowki)
            {
                pom=kolumna.get(l-1);
                list3.set(l, list3.get(l)+";"+pom);
            }
            else
            {
                pom=kolumna.get(l);
                list3.set(l, list3.get(l)+";"+pom);
            } 
        }
        
        
        app.frame.kol.setLinie(list3);
        
        app.update();//uaktualniam
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jButton4 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Times New Roman", 0, 12)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setText("Potwierdz");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Wyjdź");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Klasa decyzyjna :");

        jLabel2.setText("Metryka :");

        jLabel3.setText("Liczba skupień :");

        jTextField1.setText("1");
        jTextField1.setToolTipText("");

        jButton3.setText("Optymalna liczba klas");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel4.setText("optymalizuj wzgledem:");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton4.setText("Wyznacz nowe klasy");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.TRAILING, 0, 94, Short.MAX_VALUE)
                                    .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jTextField1)))
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //do wyznaczania nowych klas metodą k-średnich
        
        czytaj(0);        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        //do optymalnej liczby klas
        
        czytaj(2);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        //wyznaczam nowe klasy z podpisanymi klasami ,tak by muc potem je porownac ewentualnie
        
        czytaj(1);
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(JFramezz10z.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFramezz10z.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFramezz10z.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFramezz10z.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFramezz10z().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
