package cartbolt.qui.entities;

/**
 * Created by Job on 09-Jun-16.
 */
public class User {

    String id, fname, surname, email, phone, location;

    public User(String id, String fname, String surname, String email, String phone, String location) {
        this.id = id;
        this.fname = fname;
        this.surname = surname;
        this.email = email;
        this.phone = phone;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
