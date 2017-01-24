package cartbolt.qui.entities;

import cartbolt.utils.Cart;

/**
 * Created by Job on 29-May-16.
 */
public class CartItem {

    public int id, qty;
    String name;
    Double price;

    public CartItem(String n, Double d){
        this.name = n;
        this.price = d;
    }

    public CartItem(String n, Double d, int i){
        this.name = n;
        this.price = d;
        this.id=i;
    }

    //setters
    public void setName(String n){
        this.name=n;
    }
    public void setPrice(Double n){
        this.price=n;
    }
    public void setId(int n){
        this.id=n;
    }
    public void setQuantity(int n){
        this.qty = n;
    }

    //getters
    public String getName(){
        return this.name;
    }
    public Double getPrice(){
        return this.price;
    }
    public int getId(){
        return this.id;
    }
    public int getQuantity(){
        return this.qty;
    }

    public void enCart(CartItem item){
        item.qty = 1;
        Cart.cartlists.add(item);
    }

    public void inCart(CartItem item){
        //boolean tracker = false;
        if(Cart.cartlists.isEmpty()){
            item.enCart(item);
        } else  {
			/*for(int zed=0; zed<Cart.cartlists.size(); zed++){
				CartItem test = Cart.cartlists.get(zed);
				if(/*Cart.cartlistscontains(test)*//*test.getName()==item.getName()){
					System.out.println("ALREADY CONTAINED ALREADY CONTAINED ALREADY CONTAINED ");
					tracker = true;
					int q = test.getQuantity();
					q++;
					Cart.cartlists.get(zed).setQuantity(q);
				} else {
					//enCart(item);
				}
			}*/
            //if(tracker = false){
            item.enCart(item);
            //}
        }
    }

}
