
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dami
 */
public class Opis extends javax.swing.JFrame {

    public int id=-1;
    //public boolean zapis=true;
    public AddComponent addC=null;
    DefaultTableModel model = new DefaultTableModel();
    
    public List<String> nazwy=new ArrayList<>();
    public List<String> zawart=new ArrayList<>();
    
    /**
     * Creates new form Opis
     */
    public Opis() {
        initComponents();
        new ConfigFrame(this,"Opis");
        this.setVisible(true);
        jTable1.setShowGrid(true);
        
        create();
    }
    
    public Opis(int id) {
        initComponents();
        new ConfigFrame(this,"Opis");
        this.setVisible(true);
        jTable1.setShowGrid(true);
        
        this.id=id;
        readMongo();
        create();
    }
    
    public Opis(AddComponent addC,List<String> n2,List<String> z2,int id) {
        initComponents();
        new ConfigFrame(this,"Opis");
        this.setVisible(true);
        jTable1.setShowGrid(true);
        
        this.addC=addC;
        this.nazwy=new ArrayList<>(n2);
        this.zawart=new ArrayList<>(z2);
        
        //usuwam _id z listy do wyswietlenia
        this.id=id;
        if(this.id>0)
        {
        this.nazwy.remove(0);
        this.zawart.remove(0);
        }
        
        create();
    }

    
    public void readMongo()
    {
        
        try{
            Mongo m = new Mongo("LocalHost",27017);
            DB db=m.getDB("MyDB");
            DBCollection col=db.getCollection("Opis");
                        
            BasicDBObject query = new BasicDBObject();
            query.put("_id", this.id);
            
            DBCursor c=col.find(query);
            DBObject s=c.next(); 
            /////////
            List<String> list=new ArrayList<>();
            list.addAll(s.keySet());                          
            
            Map<String,Object> map = s.toMap();
            for(int i=1;i<list.size();i++)
            {
                this.nazwy.add(list.get(i));
                
                String pp=map.get(list.get(i)).toString();
                this.zawart.add(pp);
            }
            
                        
        }catch(Exception e){System.out.println("error: "+e);}
    }
    
    public void zapiszMongo()
    {
        //na koniec zapisuje lub nawet tylko updatuje informacje w opisie
        try{            
            Mongo m = new Mongo("LocalHost",27017);
            DB db=m.getDB("MyDB");
            DBCollection col=db.getCollection("Opis");
                        
            BasicDBObject query = new BasicDBObject();
            query.append("_id", this.id);
            
            ///dododaje zmieniony dokument pod te same id
            BasicDBObject query2 = new BasicDBObject();
            query2.put("_id", this.id);
                        
            for(int i=0;i<this.nazwy.size();i++)
            if(!this.nazwy.get(i).equals(""))// && !this.zawart.get(i).equals(""))
            {
                query2.put(this.nazwy.get(i),this.zawart.get(i));
            }
            
            col.update(query, query2);

            System.out.println("Opis: wpisano do bazy");
            
        }catch(Exception e){System.out.println("Opis: error: "+e);}
        
    }
    
    public void create()
    {
        //System.out.println(jTable1.getWidth());
        
        model = new DefaultTableModel();
        model.addColumn("nazwa");
        model.addColumn("zawartość");
        
        if(nazwy.size()==0 && zawart.size()==0)
        {
            for(int i=0;i<4;i++)
            {
                String[] a ={"",""};
                model.addRow(a);
                nazwy.add(a[0]);
                zawart.add(a[1]);
            }
        }
        else
        {
            for(int i=0;i<nazwy.size();i++)
            {
                String[] a ={nazwy.get(i),zawart.get(i)};
                model.addRow(a);
            }
        }
        
        
        jTable1.setModel(model);
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(jTable1.getWidth()/3);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth((2*jTable1.getWidth())/3+jTable1.getWidth()%3);
    }
    
    public void readTab()
    {        
        for(int i=0;i<model.getRowCount();i++)
        {
            Vector data = model.getDataVector();
            String s = data.get(i).toString().replace("[","").replace("]","");//.replace(" ","")
                        
            String[] s2 = s.split(", ",2);  
            
            nazwy.set(i, s2[0]);
            zawart.set(i, s2[1]);
        }        
    }
    
    public void usunTab()
    {
        try{
        readTab();
        int k=jTable1.getSelectedRow();
        nazwy.remove(k);
        zawart.remove(k);
        create();        
        
        }catch(Exception e){}        
    }
    
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null},
                {null},
                {null},
                {null}
            },
            new String [] {
                "Title 1"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("dodaj element");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("usuń element");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Zapisz zmiany");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(196, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // dodaj element
        nazwy.add("");
        zawart.add("");
        
        readTab();
        create();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // usun zaznaczony element
        usunTab();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // zapisz zmiany        
        readTab();
        if(this.addC==null)
        {zapiszMongo();}
        else
        {
            List<String> n1=new ArrayList<>(),z1=new ArrayList<>();
            if(this.id>0)        
            n1.add(this.addC.nazwy_opis.get(0));            
            n1.addAll(this.nazwy);
            if(this.id>0)        
            z1.add(this.addC.zawart_opis.get(0));
            z1.addAll(this.zawart);
            
            this.addC.nazwy_opis=n1;
            this.addC.zawart_opis=z1;
                        
            System.out.println("Opis: zczytano dane z tablicy do AddComponent");
        }
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(Opis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Opis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Opis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Opis.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Opis().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
