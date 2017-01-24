package cartbolt.qui.entities;

import java.util.List;

/**
 * Created by Job on 29-May-16.
 */
public class Category {

    int id;
    String name, outlet;
    List<Item> items;

    public Category(String n){
        this.name = n;
    }

    public Category(String n, String o){
        this.name = n;
        this.outlet = o;
    }

    public Category(int n, String o){
        this.id = n;
        this.name = o;
    }

    public void setId(int t){
        this.id = t;
    }

    public int getId(){
        return this.id;
    }

    public void setName(String n){
        this.name = n;
    }

    public void setItems(List<Item> n){
        this.items = n;
    }

    public void setOutlet(String o){
        this.outlet = o;
    }

    public String getName(){
        return this.name;
    }

    public List<Item> getItems(){
        return this.items;
    }

    public String getOutlet(){
        return this.outlet;
    }

}
