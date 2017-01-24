package cartbolt.qui.entities;

/**
 * Created by Job on 15-Jul-16.
 */
public class Order {

    String id, name, location, type, time;

    public Order(String id, String name, String location, String type, String time) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.type = type;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
