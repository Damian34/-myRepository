package frames.operations;

public class Payment {

    public boolean cheack = false;
    public String id = "";
    public String payment_m = "";
    public String status = "";
    public String sum = "";
    public String date = "";
    public String date_end = "";
    public String login = "";
    public String name = "";
    public String surname = "";

    public Payment() {
    }

    public Payment(String id, String payment_m, String status, String sum, String date,String date_end, String login,String name,String surname) {
        this.id = id;
        this.payment_m = payment_m;
        this.status = status;
        this.sum = sum;
        this.date = date;
        this.date_end = date_end;
        this.login = login;
        this.name = name;
        this.surname = surname;
    }
    
    public Object[] getPayment(){
        Object[] obj = {this.cheack,this.id,this.payment_m,this.status,this.sum,this.date,this.date_end,this.surname+","+this.name+","+this.login};
        return obj;
    }

    @Override
    public String toString() {
        String d0 = date.substring(2, 4)+date.substring(5, 7)+date.substring(8, 10);
        String d1 = "";
        if(!date_end.equals("")){
            d1 = date_end.substring(5, 7)+date_end.substring(8, 10);
        }
        String c0 = sum.split(" ")[0];
        return "\""+d0+d1+"C"+c0+"\",\""+this.surname+","+this.name+","+this.login+"\"";
    }
    
    
}
