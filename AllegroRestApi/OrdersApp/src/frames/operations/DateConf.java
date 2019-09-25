package frames.operations;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.swing.JLabel;

public class DateConf {

    public List<Payment> selectPayments(String min0, String max0, List<Payment> list, JLabel text) {
        List<Payment> other = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date min = sdf.parse(min0);
            Date max = sdf.parse(max0);
            for (int i = 0; i < list.size(); i++) {
                Date d = sdf.parse(list.get(i).date);
                if(this.dateBetween(d, min, max)){
                    other.add(list.get(i));
                }
            }
            return other;
        } catch (Exception e) {
            text.setText("nie prawidłowy format daty");
        }
        return list;
    }

    public String getToDay(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date());
    }
    
    public boolean dateBetween(Date d, Date min, Date max) {
        return (d.after(min) || d.equals(min)) && (d.before(max) || d.equals(max));
    }
        
    public void sortByDate(List<Payment> list,boolean order){
        if(order){
            list.sort(new Sort());
        }else{
            list.sort(new SortOther());
        }        
    }
    
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private class Sort implements Comparator<Payment>{        
        @Override
        public int compare(Payment p1, Payment p2) {
            try{
            Date d1 = df.parse(p1.date_end);
            Date d2 = df.parse(p2.date_end);
            if(d1.equals(d2)){
                return 0;
            }else if(d1.before(d2)){
                return 1;
            }else{
                return -1;
            }            
            }catch(Exception e){}
            return -1;
        }
        
    }
    
    private class SortOther implements Comparator<Payment>{        
        @Override
        public int compare(Payment p1, Payment p2) {
            try{
            Date d1 = df.parse(p1.date_end);
            Date d2 = df.parse(p2.date_end);
            if(d1.equals(d2)){
                return 0;
            }else if(d1.before(d2)){
                return -1;
            }else{
                return 1;
            }            
            }catch(Exception e){}
            return -1;
        }
        
    }
}
