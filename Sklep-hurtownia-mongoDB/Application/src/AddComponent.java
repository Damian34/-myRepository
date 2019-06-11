
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dami
 */
public class AddComponent extends javax.swing.JFrame {

    public int id_I=-1;
    public int id_O=-1;
    public List<String> nazwy_opis=new ArrayList<>();
    public List<String> zawart_opis=new ArrayList<>();
    public int id=-1;//jesli id<0 to nowy ,a jesli wiekrzy to edytuj
    private JFrame fr=null;
    /**
     * Creates new form AddComponent
     */
    public AddComponent(){
        initComponents();
        new ConfigFrame(this,"Nowy element");
        //new ConfigFrame(this,"Edytuj");
    }
    
    public AddComponent(int id,JFrame fr){
        initComponents();
        this.setVisible(true);
        
        this.id=id;
        this.fr=fr;
        
        if(this.id<0)
        {
            new ConfigFrame(this,"Nowy element");
            this.jButton3.setVisible(false);
        }
        else
        {            
            new ConfigFrame(this,"Edytuj");
            wczytaj();
        }      
        
        //new ConfigFrame(this,"Edytuj");
    }
    
    public void wczytaj()
    {
        try{
            this.id_I=this.id;
            
            Mongo m = new Mongo("LocalHost",27017);
        
            DB db=m.getDB("MyDB");
            DBCollection col=db.getCollection("Item");
            
            BasicDBObject q1 = new BasicDBObject();
            q1.put("_id", this.id_I);
            DBCursor c=col.find(q1);
            DBObject s=c.next();
            Map<String,Object> map = s.toMap();
            
            this.id_O=(int)(double)Double.valueOf(map.get("Opis_id").toString());
            
            /////
            this.jTextField1.setText(map.get("nazwa").toString());
            this.jTextField2.setText(map.get("cena").toString());
            
            ////////////////
            DBCollection col2=db.getCollection("Opis");
            BasicDBObject q2 = new BasicDBObject();
            q2.put("_id", this.id_O);
            DBCursor c2=col2.find(q2);
            DBObject s2=c2.next();
            Map<String,Object> map2 = s2.toMap();
            
            this.nazwy_opis=new ArrayList<>();
            this.nazwy_opis.addAll(s2.keySet());
            this.zawart_opis=new ArrayList<>();
            for(int i=0;i<this.nazwy_opis.size();i++)
            {
                this.zawart_opis.add(map2.get(this.nazwy_opis.get(i)).toString());
            }
            
            
        }catch(Exception e){System.out.println("AddComponent: error: "+e);}
    }
    
    
    
    public void nowy()
    {
        try{
            Mongo m = new Mongo("LocalHost",27017);
        
            DB db=m.getDB("MyDB");
            DBCollection col=db.getCollection("Item");
            
            DBCursor c=col.find();
            
            int id=1;//szukam dostepnego id
            
            while(c.hasNext())
            {
                DBObject s=c.next();
                Map<String,Object> map = s.toMap();
                int id2 = (int)(double)Double.valueOf(map.get("_id").toString());
                if(id2>id)
                {id=id2;}
            }
            id++;
            
            //////////zapisuje nowy element
            if(this.jTextField1.getText().replace(" ","").equals("") || this.jTextField2.getText().replace(" ","").equals(""))
            {return;}
            
            //nie zapisuje jesli cena nie jest wartością liczbową double
            try{
                Double.valueOf(this.jTextField2.getText().replace(" ",""));
            }catch(Exception e){return;}
            
            BasicDBObject doc= new BasicDBObject();
            doc.put("_id",id);
            doc.put("nazwa",this.jTextField1.getText());
            doc.put("Opis_id",id);
            doc.put("cena",this.jTextField2.getText().replace(" ",""));
            doc.put("sprzedane",0);
            doc.put("dostepne",0);
            
            col.insert(doc);
            
            DBCollection col2=db.getCollection("Opis");
            BasicDBObject doc2= new BasicDBObject();
            
            doc2.put("_id",id);            
            for(int i=0;i<this.nazwy_opis.size();i++)
            if(!this.nazwy_opis.get(i).equals(""))// && !this.zawart.get(i).equals(""))
            {
                doc2.put(this.nazwy_opis.get(i),this.zawart_opis.get(i));
            }
            
            col2.insert(doc2);
            
        }catch(Exception e){System.out.println("AddComponent: error: "+e);}
    }
    
    public void edytuj()
    {
        try{            
            //nie zapisuje jesli oba pola sa puste
            if(this.jTextField1.getText().replace(" ","").equals("") || this.jTextField2.getText().replace(" ","").equals(""))
            {return;}
            
            //nie zapisuje jesli cena nie jest wartością liczbową double
            try{
                Double.valueOf(this.jTextField2.getText().replace(" ",""));
            }catch(Exception e){return;}
            
            ///update'uje item
            Mongo m = new Mongo("LocalHost",27017);
        
            DB db=m.getDB("MyDB");
            DBCollection col=db.getCollection("Item");
            BasicDBObject q1 = new BasicDBObject();
            q1.put("_id", this.id_I);
            
            DBCursor c=col.find(q1);
            DBObject s=c.next();
            Map<String,Object> map = s.toMap();
            
            List<String> list=new ArrayList<>();
            list.addAll(s.keySet()); 
                        
            //
            BasicDBObject query1 = new BasicDBObject();
            
            for(int i=0;i<list.size();i++)
            {
                query1.put(list.get(i),map.get(list.get(i)));
            }
            
            query1.put("nazwa",this.jTextField1.getText());
            query1.put("cena",(double)Double.valueOf(this.jTextField2.getText().replace(" ","")));
            
            
            BasicDBObject query2 = new BasicDBObject();
            query2.append("_id", this.id_I);
            
            col.update(query2, query1);
            
            //////////////////////////update'uje opis
            
            DBCollection col2=db.getCollection("Opis");
            BasicDBObject query3 = new BasicDBObject();
                        
            for(int i=0;i<this.nazwy_opis.size();i++)
            if(!(this.nazwy_opis.get(i).equals("")))//jesli nazwa nie jest pusta
            {
                query3.put(this.nazwy_opis.get(i),this.zawart_opis.get(i));
            }
            query3.put("_id",this.id_O);
            
            BasicDBObject query4 = new BasicDBObject();
            query4.append("_id", this.id_O);
                        
            col2.update(query4, query3);            
            
        }catch(Exception e){System.out.println("AddComponent: error: "+e);}
    }
    
    
    
    public void usun()
    {
        try{
            Mongo m = new Mongo("LocalHost",27017);
            DB db=m.getDB("MyDB");
            
            DBCollection col=db.getCollection("Item");            
            BasicDBObject q1 = new BasicDBObject();
            q1.put("_id", this.id_I);
            col.remove(q1);
            
            DBCollection col2=db.getCollection("Opis");            
            BasicDBObject q2 = new BasicDBObject();
            q2.put("_id", this.id_O);
            col2.remove(q2);
            
        }catch(Exception e){System.out.println("AddComponent: error: "+e);}
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("nazwa:  ");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("cena :   ");

        jButton1.setText("Opis");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Zatwierdź");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Usuń");
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
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //Opis
        if(this.id<0)
        {
            new Opis(this,this.nazwy_opis,this.zawart_opis,this.id_O);
        }
        else
        {
            //new Opis(this.id_O);
            new Opis(this,this.nazwy_opis,this.zawart_opis,this.id_O);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // dodaj/zapisz krotke
        if(this.id<0)
        {
            nowy();
            fr.mongo();
        }
        else
        {
            edytuj();
            new JFrame();
            fr.setVisible(false);
            fr.dispose();
            System.out.println("AddComponent: edytuje");
        }
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // usuwam krotke
        usun();
        new JFrame();
        fr.setVisible(false);
        fr.dispose();     
        
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
            java.util.logging.Logger.getLogger(AddComponent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddComponent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddComponent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddComponent.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddComponent().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
