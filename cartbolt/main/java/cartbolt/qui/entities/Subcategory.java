package cartbolt.qui.entities;

import java.util.List;

/**
 * Created by Job on 29-May-16.
 */
public class Subcategory {

    int id;
    String outlet;
    String name;
    List<Item> items;

    public Subcategory(String n) {
        this.name = n;
    }

    public Subcategory(int n, String o) {
        this.id = n;
        this.name = o;
    }

    public int getId(){
        return this.id;
    }

    public void setId(int n) {
        this.id = n;
    }

    public void setName(String n) {
        this.name = n;
    }

    public void setItems(List<Item> n) {
        this.items = n;
    }

    public void setOutlet(String o) {
        this.outlet = o;
    }

    public String getName() {
        return this.name;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public String getOutlet() {
        return this.outlet;
    }

}
