package operations;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStreamWriter;
import pack.Row;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import pack.Attribute;

public class TableOperations {

    private String username = "";
    private String pass = "";
    private String url = "";

    public TableOperations(Config conf) {
        this.username = conf.getUsername();
        this.pass = conf.getPass();
        this.url = conf.getUrl();
    }

    public List<Row> getTable() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, pass);
            Statement stmt = conn.createStatement();
            String q = "select p.id_product,p.reference,p.price,sa.quantity,sp.reduction,pl.name,pl.description,pl.description_short,cat.cg2 from (select GROUP_CONCAT(cl2.name,' ') cg2,cp2.id_product id2 from ps_category_lang cl2 ,ps_category_product cp2 where cl2.id_category=cp2.id_category group by cp2.id_product) cat,ps_product_lang pl,ps_stock_available sa, ps_product p LEFT OUTER JOIN ps_specific_price sp ON p.id_product=sp.id_product where p.id_product=sa.id_product and sa.id_product_attribute = 0 and p.id_product=pl.id_product and cat.id2=p.id_product order by p.id_product";
            ResultSet rs = stmt.executeQuery(q);
            List<Row> list = new ArrayList<>();
            while (rs.next()) {
                String id = rs.getString("p.id_product");
                String reference = rs.getString("p.reference");
                String price = rs.getString("p.price");
                String reduction = rs.getString("sp.reduction");
                String quantity_full = rs.getString("sa.quantity");
                String name = rs.getString("pl.name");
                String description = rs.getString("pl.description");
                String description_short = rs.getString("pl.description_short");
                String category = rs.getString("cat.cg2");
                list.add(new Row(id, reference, price, reduction, quantity_full, name, description, description_short, category));
            }
            rs.close();
            stmt.close();
            conn.close();
            return list;
        } catch (Exception e) {
            System.out.println("error: " + e);
            return null;
        }
    }

    public List<Object[]> getStates(String id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, pass);
            Statement stmt = conn.createStatement();
            String q = "select sa.quantity,sa.id_product_attribute ,al.name from ps_stock_available sa , ps_product_attribute_combination pac ,ps_attribute_lang al where sa.id_product_attribute=pac.id_product_attribute and pac.id_attribute=al.id_attribute and sa.id_product = " + id + " order by sa.id_product_attribute";
            ResultSet rs = stmt.executeQuery(q);
            List<String[]> list = new ArrayList();
            while (rs.next()) {
                String quantity = rs.getString("sa.quantity");
                String id_product_attribute = rs.getString("sa.id_product_attribute");
                String name = rs.getString("al.name");
                String[] r = {quantity, id_product_attribute, name};
                list.add(r);
            }
            rs.close();
            stmt.close();
            conn.close();
            int con = 0;
            String s = list.get(0)[1];
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i)[1].equals(s)) {
                    con++;
                } else {
                    break;
                }
            }   
            List<Object[]> list2 = new ArrayList();
            for (int i = 0; i < list.size(); i += con) {
                Object[] r = new Object[con + 2];
                r[0] = list.get(i)[0];
                for (int j = 0; j < con; j++) {
                    r[j + 1] = list.get(i + j)[2];
                }
                r[con + 1] = list.get(i)[1];
                list2.add(r);
            }
            return list2;
        } catch (Exception e) {
            System.out.println("error: " + e);
        }
        return null;
    }

    public void saveData(Attribute orginal, Attribute readed, JLabel text) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, pass);
            Statement stmt = conn.createStatement();
            if (!orginal.row.nr_reference.equals(readed.row.nr_reference)) {
                String q = "update ps_product set reference = ? where id_product = ?";
                PreparedStatement ps = conn.prepareStatement(q);
                ps.setString(1, readed.row.nr_reference);
                ps.setString(2, readed.row.id_product);
                ps.executeUpdate();
                System.out.println("nr_reference done");
            }
            if (!orginal.row.price.equals(readed.row.price)) {
                String q = "update ps_product set price = ? where id_product = ?";
                PreparedStatement ps = conn.prepareStatement(q);
                ps.setString(1, readed.row.price);
                ps.setString(2, readed.row.id_product);
                ps.executeUpdate();
                String q2 = "update ps_product_shop set price = ? where id_product = ?";
                PreparedStatement ps2 = conn.prepareStatement(q2);
                ps2.setString(1, readed.row.price);
                ps2.setString(2, readed.row.id_product);
                ps2.executeUpdate();
                System.out.println("price done");
            }
            String red = "";
            if (orginal.row.reduction != null) {
                red = orginal.row.reduction;
            }
            if (!red.equals(readed.row.reduction)) {
                String q = "select id_product from ps_specific_price where id_product=" + readed.row.id_product;
                ResultSet rs = stmt.executeQuery(q);
                int size = 0;
                while (rs.next()) {
                    size++;
                }
                rs.close();
                if (size != 0) {
                    if (readed.row.reduction.equals("")) {
                        String q2 = "DELETE FROM ps_specific_price WHERE id_product = ?";
                        PreparedStatement ps = conn.prepareStatement(q2);
                        ps.setString(1, readed.row.id_product);
                        ps.executeUpdate();;
                    } else {
                        String q2 = "update ps_specific_price set reduction = ? where id_product = ?";
                        PreparedStatement ps = conn.prepareStatement(q2);
                        ps.setString(1, readed.row.reduction);
                        ps.setString(2, readed.row.id_product);
                        ps.executeUpdate();
                    }
                } else {
                    String q2 = "INSERT INTO ohque9_prestash.ps_specific_price (id_specific_price_rule, id_cart, id_product, id_shop, id_shop_group, id_currency, id_country, id_group, id_customer, id_product_attribute, price, from_quantity, reduction, reduction_tax, reduction_type, `from`, `to`) VALUES (0, 0, 8, 0, 0, 0, 0, 0, 0, 0, -1.000000, 1, " + readed.row.reduction + ", true, 'percentage', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)";
                    stmt.executeUpdate(q2);
                }
                System.out.println("reduction done");
            }
            int quantity_full = 0;
            for (int i = 0; i < readed.state.size(); i++) {
                quantity_full += Integer.valueOf(readed.state.get(i)[0].toString());
            }
            if (!orginal.row.quantity_full.equals(quantity_full + "")) {
                String q = "update ps_stock_available set quantity = ? where id_product_attribute=0 and id_product= ?";
                PreparedStatement ps = conn.prepareStatement(q);
                ps.setString(1, readed.row.quantity_full);
                ps.setString(2, readed.row.id_product);
                ps.executeUpdate();
                System.out.println("quantity_full done");
            }
            ////////////////////////////
            for (int i = 0; i < readed.state.size(); i++) {
                if (!comapreTab(orginal.state.get(i), readed.state.get(i))) {
                    String q = "update ps_stock_available set quantity = ? where id_product_attribute= ?";
                    PreparedStatement ps = conn.prepareStatement(q);
                    ps.setString(1, readed.state.get(i)[0].toString());
                    ps.setString(2, readed.state.get(i)[readed.state.get(i).length - 1].toString());
                    ps.executeUpdate();
                }
            }
            System.out.println("state done");
            if (!orginal.row.name.equals(readed.row.name)) {
                String q = "update ps_product_lang set name = ? where id_product = ?";
                PreparedStatement ps = conn.prepareStatement(q);
                ps.setString(1, readed.row.name);
                ps.setString(2, readed.row.id_product);
                ps.executeUpdate();
                System.out.println("name done");
            }
            if (!orginal.row.description.equals(readed.row.description)) {
                String q = "update ps_product_lang set description = ? where id_product = ?";
                PreparedStatement ps = conn.prepareStatement(q);
                ps.setString(1, readed.row.description);
                ps.setString(2, readed.row.id_product);
                ps.executeUpdate();
                System.out.println("description done");
            }
            if (!orginal.row.description_short.equals(readed.row.description_short)) {
                String q = "update ps_product_lang set description_short = ? where id_product = ?";
                PreparedStatement ps = conn.prepareStatement(q);
                ps.setString(1, readed.row.description_short);
                ps.setString(2, readed.row.id_product);
                ps.executeUpdate();
                System.out.println("description_short done");
            }
            stmt.close();
            //////////////
            conn.close();
        } catch (Exception e) {
            System.out.println("error1: " + e);
        }
    }

    public boolean comapreTab(Object[] a, Object[] b) {
        for (int i = 0; i < a.length; i++) {
            if (!a[i].equals(b[i])) {
                return false;
            }
        }
        return true;
    }

    public void saveByFilePath(String source, String raport, JLabel text) {

        new Thread(() -> {
            BufferedReader br;
            BufferedWriter bw;
            try {
                text.setText("dane są zapisywane");
                br = new BufferedReader(new FileReader(source));
                bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(raport))));
                String line = br.readLine();
                bw.write(line + ",raport\n");
                while ((line = br.readLine()) != null) {
                    String[] tab = line.split(",");
                    String id = tab[0].replace(" ", "");
                    String price = tab[1].replace(" ", "");
                    if (this.checkInteger(id) && this.checkDouble(price)) {
                        if (changePriceById(id, modifyNumber(price))) {
                            bw.write(line + ",TAK\n");
                        } else {
                            bw.write(line + ",NIE\n");
                        }
                    }
                }
                bw.close();
                br.close();
                text.setText("zapisano dane");
            } catch (Exception e) {
                text.setText("zapisanie danych nie powiodło się");
            }
        }).start();
    }

    public boolean changePriceById(String id, String price) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, username, pass);
            Statement stmt = conn.createStatement();
            String q = "update ps_product set price = ? where id_product = ?";
            PreparedStatement ps = conn.prepareStatement(q);
            ps.setString(1, price);
            ps.setString(2, id);
            ps.executeUpdate();
            String q2 = "update ps_product_shop set price = ? where id_product = ?";
            PreparedStatement ps2 = conn.prepareStatement(q2);
            ps2.setString(1, price);
            ps2.setString(2, id);
            ps2.executeUpdate();
            stmt.close();
            conn.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String modifyNumber(String s) {
        try {
            Double.valueOf(s);
            if (s.indexOf(".") == -1) {
                s += ".000000";
            } else {
                String[] s2 = s.split("\\.");
                if (s2[1].length() < 6) {
                    int size = 6 - s2[1].length();
                    for (int i = 0; i < size; i++) {
                        s += "0";
                    }
                } else {
                    s = s2[0] + ".";
                    for (int i = 0; i < 6; i++) {
                        s += s2[1].charAt(i);
                    }
                }
            }
        } catch (Exception e) {
        }
        return s;
    }

    private boolean checkInteger(String nr) {
        try {
            Integer.valueOf(nr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean checkDouble(String nr) {
        try {
            Double.valueOf(nr);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
