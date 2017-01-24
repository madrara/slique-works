package cartbolt.qui.entities;

import cartbolt.utils.Cart;

/**
 * Created by Job on 29-May-16.
 */
public class Item {

    String name;
    Double price;
    String unit;
    String image;
    String category, splitter, deux, closer, description;
    String outletid , outletname;
    int id, itemid;
    public int quantity;

    public void setOutletName(String i){
        this.outletid = i;
    }

    public String getOutletName(){
        return this.outletid;
    }

    public void setOutletid(String i){
        this.outletid = i;
    }

    public String getOutletId(){
        return this.outletid;
    }

    public void setDescription(String b){
        this.description = b;
    }

    public String getDescription(){
        return this.description;
    }

    public void setDeux(String y){
        this.deux =y;
    }

    public void setCloser(String y){
        this.closer =y;
    }

    public void setImage(String y){
        this.image =y;
    }

    public String getDeux(){
        return this.deux;
    }

    public String getCloser(){
        return this.closer;
    }

    public String getImage(){
        return this.image;
    }

    public Item(){
        //empty
    }

    public Item(String n, Double d){
        this.quantity = 1;
        this.name = n;
        this.price = d;
    }

    public Item(int i,String n, String u){
        this.id = i;
        this.name = n;
        this.image = u;
    }

    public Item(int i,String n, Double d, String u){
        this.id = i;
        this.quantity = 1;
        this.name = n;
        this.price = d;
        this.unit = u;
    }

    public Item(int i,String n, String pic, String pr){
        this.id = i;
        this.quantity = 1;
        this.name = n;
        this.image = pic;
        this.price = Double.parseDouble(pr);
    }

    public Item(String n, Double d, String c){
        this.quantity = 1;
        this.name = n;
        this.price = d;
        this.category = c;
    }

    public Item(String n, Double d, int i){
        this.quantity = 1;
        this.name = n;
        this.price = d;
        this.id=i;
    }

    //setters
    public void setName(String n){
        this.name=n;
    }
    public void setSplitter(String n){
        this.splitter=n;
    }
    public void setUnit(String n){
        this.unit=n;
    }
    public void setPrice(Double n){
        this.price=n;
    }
    public void setId(int n){
        this.id=n;
    }
    public void setItemId(int n){
        this.itemid=n;
    }
    public void setOutletId(String n){
        this.outletid=n;
    }
    public void setQuantity(int n){
        this.quantity = n;
    }
    public void setCategory(String n){
        this.category = n;
    }

    //getters
    public String getName(){
        return this.name;
    }

    public String getSplitter(){
        return this.splitter;
    }

    public String getUnit(){
        return this.unit;
    }

    public Double getPrice(){
        return this.price;
    }
    public int getId(){
        return this.id;
    }
    public int getItemId(){
        return this.itemid;
    }
    public int getQuantity(){
        return this.quantity;
    }
    public String getCategory(){
        return this.category;
    }

    //cart
    public void enCart(Item item){
        Cart.cartlist.add(item);
    }

    public void inCart(Item item){
        String h = item.getName();
        int tracker;
        //int entry = 0;
        int positioner = Cart.cartlist.size()/* + 20*/;
        if(Cart.cartlist.isEmpty()){
            item.enCart(item);
            //Cart.total = Cart.total + item.getPrice() * item.getQuantity();
        } else  {
            // tracker = 0;
            // entry = 0;
            for(int zed=0; zed<Cart.cartlist.size(); zed++){
                Item test = Cart.cartlist.get(zed);
                if(test.getName() == h){
                    tracker = 0;
                }else {
                    tracker = 1;
                }
                if(tracker == 0){
                    positioner = zed;
					/*System.out.println("ALREADY CONTAINED ALREADY CONTAINED ALREADY CONTAINED ");
					tracker = 0;
					int q = test.getQuantity();
					q++;
					Cart.cartlist.get(zed).setQuantity(q);
					System.out.println("Incremented"+item.getName());
					zed = Cart.cartlist.size();
					h = "never add again";*/
                    //break;
                    //Cart.total = Cart.total + Cart.cartlist.get(zed).getPrice() * Cart.cartlist.get(zed).getQuantity()-1;
                } else if (tracker == 1){
                    //enCart(item);
                    //entry = entry + 1;
                }
                System.out.println("Position"+zed+" Tracker "+tracker);
            }
            if(positioner < Cart.cartlist.size()){//if(entry >= 1){
                int q = Cart.cartlist.get(positioner).getQuantity();
                q++;
                Cart.cartlist.get(positioner).setQuantity(q);
                System.out.println("Added "+item.getName());
            } else {
                Cart.cartlist.add(item);
            }

        }
    }

    public void deCart(Item item){
        for(int zed=0; zed<Cart.cartlist.size(); zed++){
            Item test = Cart.cartlist.get(zed);
            if(test.getId() == item.getId() && test.getName() == item.getName()){
                int q = test.getQuantity();
                q--;
                if(q<1){
                    Cart.cartlist.remove(zed);
                } else {
                    test.setQuantity(q);
                    Cart.cartlist.add(zed, test);
                }
            }
        }
    }

}
