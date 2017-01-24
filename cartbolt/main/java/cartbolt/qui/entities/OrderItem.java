package cartbolt.qui.entities;

/**
 * Created by Job on 10-Oct-16.
 */
public class OrderItem {

    int id;
    String prd, name, order, quantity, outlet, description, pic, person;
    String price;

    public OrderItem(){}

    public OrderItem(int id, String prd, String name, String order, String quantity, String outlet, String description, String pic, String person, String price) {
        this.id = id;
        this.prd = prd;
        this.name = name;
        this.order = order;
        this.quantity = quantity;
        this.outlet = outlet;
        this.description = description;
        this.pic = pic;
        this.person = person;
        this.price = price;
    }

    public OrderItem(int id, String name, String order, String quantity, String outlet, String description, String pic, String person, String price) {
        this.id = id;
        this.name = name;
        this.order = order;
        this.quantity = quantity;
        this.outlet = outlet;
        this.description = description;
        this.pic = pic;
        this.person = person;
        this.price = price;
    }

    public OrderItem(int id, String name, String order, String quantity, String outlet, String description, String pic, String person) {
        this.id = id;
        this.name = name;
        this.order = order;
        this.quantity = quantity;
        this.outlet = outlet;
        this.description = description;
        this.pic = pic;
        this.person = person;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrd() {
        return prd;
    }

    public void setPrd(String prd) {
        this.prd = prd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getOutlet() {
        return outlet;
    }

    public void setOutlet(String outlet) {
        this.outlet = outlet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
