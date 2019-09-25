package pack;

public class Row {

    public String id_product = "";
    public String nr_reference = "";
    public String price = "";
    public String reduction = "";
    public String quantity_full = "";
    public String name="";
    public String description="";
    public String description_short="";
    public String category="";

    public Row() {
    }

    public Row(String id_product, String nr_reference, String price, String reduction, String quantity_full,String name,String description,String description_short,String category) {
        this.id_product = id_product;
        this.nr_reference = nr_reference;
        this.price = price;
        this.reduction = reduction;
        this.quantity_full = quantity_full;
        this.name = name;
        this.description = description;
        this.description_short = description_short;
        this.category = category;
    }

    public Object[] getRow() {
        Object[] obj = {this.id_product, this.nr_reference, this.price, this.reduction, this.quantity_full,this.name,this.category};
        return obj;
    }
    
    public Row getClone(){
        return new Row(this.id_product,this.nr_reference,this.price,this.reduction,this.quantity_full,this.name,this.description,this.description_short,this.category);
    }
}
