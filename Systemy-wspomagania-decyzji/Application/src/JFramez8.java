
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dami
 */
public class JFramez8 extends javax.swing.JFrame {

    public JFramez3 fr3;
    public DefaultTableModel model;
    public int nag=0;//numer naglowka do pominiecia w tabeli
    /**
     * Creates new form JFramezzz8
     */
    public JFramez8() {
        initComponents();
        this.setTitle("Tryb klasyfikacji");
        
        try {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        SwingUtilities.updateComponentTreeUI(this);
        this.pack();
        } 
        catch (Exception e) {}        
        this.setVisible(true);
    }
    
    public JFramez8(JFramez3 fr) {
        initComponents();
        this.setTitle("Tryb oceny");
        
        try {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        SwingUtilities.updateComponentTreeUI(this);
        this.pack();
        } 
        catch (Exception e) {}
        this.setVisible(true);
        
        this.fr3=fr;
        nag=0;
        
        pisz();
    }

    
    
    
    public void pisz()//wpisuje elementy do jComboBox'ów
    {
        List<String> list=fr3.frame.kol.Naglow();
        String[] tab0 ={"Euklidesowa","Manhattan","Nieskończoność","Mahalanobisa"};
        
        
        jComboBox3.removeAllItems();
    
        for(int i=0;i<list.size();i++)//dodaje naglowki do jComboBox3
        jComboBox3.addItem(list.get(i));
        
        
        jComboBox4.removeAllItems();
        
        for(int i=0;i<tab0.length;i++)//dodaje nazwy metryk do jComboBox4
        jComboBox4.addItem(tab0[i]);
        
        //////////////////////////////////
        // wypełniam tablice
        tabela_rysuj();
    }
    
    public void tabela_rysuj()
    {
        List<String> list = fr3.frame.kol.Naglow();
        
        model = new DefaultTableModel(){
        @Override 
        public boolean isCellEditable(int row, int column)
        {
            if(column == 0)//pierwsza kolumna jest nie edytowalna
            return false;
            else
            return true;
        }
        };
        
        //dodaje nazwy kolumn
        model.addColumn("Kolumny");
        model.addColumn("Wartości");
        
        
        for(int i=0;i<list.size();i++)
        if(i!=nag)
        {
            Object[] ob ={list.get(i),""};
            model.addRow(ob);            
        }
        
        
        jTable1.setModel(model);        
    }
    
    public List<String> tabela_czytaj()
    {
        int p  = fr3.frame.kol.Naglow().size();
        List<String> list = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        
        boolean poprawnosc=true;
        try{
        
        for(int i=0;i<model.getColumnCount();i++)
        {
            String[] s=zmien(i).split(";");
            
            if(s.length!=2)//sprawdzam czy wszedzie jest jakaś wartość
            poprawnosc=false;
        }
        
        if(poprawnosc)
        {
            
        for(int i=0;i<model.getRowCount();i++)
        {
            list.add(zmien(i).split(";")[1]);
        }
        
        
        //dodaje dodatkowy element do listy by mieć rozmiar jak w klasie Kolumny
        for(int i=0;i<list.size();i++)
        {
            if(nag==i){list2.add("pusty");}
            
            list2.add(list.get(i));
        }
        if(nag==list.size())list2.add("pusty");
        
        //przechodze po kolumnach i sprawdzam gdzie nie ma znaków
        List<Integer> list3 = fr3.frame.kol.KolumnyBezZnakow2();
        
        //teraz przechodze po list2 i sprawdzam kolumny z liczbami
        
        for(int i=0;i<list3.size();i++)
        if(i!=nag)
        {
            try{
               String s = list2.get(list3.get(i));
               Double.valueOf(s);
            }catch(Exception e){poprawnosc=false;}
            //jesli wartosc z kolumny gdzie są same liczby jest typu innego niż znakowego to nie ma poprawnosci
        }
                
        }
        }catch(Exception e){poprawnosc=false;}
        
        
        if(poprawnosc)
        {
            return list2;
        }
        else
        {
            return null;
        }
        
    }
    
    public String zmien(int k)//pobieram k'ty wiersz
    {        
        Vector data = model.getDataVector();
        String s = data.get(k).toString().replace(" ","").replace("[","").replace("]","");
        
        s = s.replace(",",";");
        
        return s;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jComboBox3 = new javax.swing.JComboBox<>();
        jComboBox4 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Klasa decyzyjna :");

        jLabel2.setText("Metryka :");

        jLabel3.setText("Liczba sąsiadów :");

        jButton1.setText("Potwierdź");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTextField1.setText("0");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Kolumny", "Wartosci"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox3.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox3ItemStateChanged(evt);
            }
        });

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                            .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox4, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(233, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // pobieram otrzymane wyniki i wywołuje oceny
        
        List<String> wiersz = tabela_czytaj();
        //////////////////////////////////
        if(wiersz!=null)
        {
        String[] tab0 ={"Euklidesowa","Manhattan","nieskończoność","Mahalanobisa"};

        int klasy = jComboBox3.getSelectedIndex();//nr kolumny klasy
        int metryka = jComboBox4.getSelectedIndex();//nr oznaczjący metryke

        int odl=-1;//odleglosc

        boolean poprawne=true;//oznacza że dane są poprawne

        try{
            odl=Integer.valueOf(jTextField1.getText());
        }catch(Exception e){poprawne=false;}


        if(poprawne)
        {
            Tryby tryb=new Tryby();
            fr3.frame.kol.Aktualizacja();
            String s=tryb.klasyfikuj(fr3.frame.kol, wiersz, klasy, metryka, odl);
            
            //podmieniam wartość
            wiersz.set(nag,s);
            
            
            String s2="";
            for(int i=0;i<wiersz.size();i++)
            {
                if(i!=wiersz.size()-1)
                s2=s2+wiersz.get(i)+";";
                else
                s2=s2+wiersz.get(i); 
            }
            //dodaje element do listy w JFramezAplication
            List<String> list0 = fr3.frame.kol.getLinie();
            list0.add(s2);
            fr3.frame.kol.setLinie(list0);
            fr3.update();
            
            //i wychodze
            //this.setVisible(false);
        }
        
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox3ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox3ItemStateChanged
        // TODO add your handling code here:
        if(jComboBox3.getSelectedIndex()>=0)
        {
            nag=jComboBox3.getSelectedIndex();
            tabela_rysuj();
        }
    }//GEN-LAST:event_jComboBox3ItemStateChanged

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
            java.util.logging.Logger.getLogger(JFramez8.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFramez8.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFramez8.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFramez8.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFramez8().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
