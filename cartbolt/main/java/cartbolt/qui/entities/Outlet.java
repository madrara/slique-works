package cartbolt.qui.entities;

/**
 * Created by Job on 29-May-16.
 */
public class Outlet {

    String id, name, location, type, sizer;

    public Outlet(String id, String name, String location, String type) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.type = type;
    }

    public Outlet(String id, String name, String location, String type, String sizer) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.type = type;
        this.sizer = sizer;
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

    public String getSizer() {
        return sizer;
    }

    public void setSizer(String sizer) {
        this.sizer = sizer;
    }

}
