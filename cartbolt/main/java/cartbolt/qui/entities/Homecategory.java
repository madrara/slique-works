package cartbolt.qui.entities;

/**
 * Created by Job on 29-May-16.
 */
public class Homecategory {

    String name, intro, imageurl;

    public Homecategory(String name) {
        this.name = name;
    }

    public Homecategory(String name, String imageurl) {
        this.name = name;
        this.imageurl = imageurl;
    }

    public Homecategory(String name, String intro, String imageurl) {
        this.name = name;
        this.intro = intro;
        this.imageurl = imageurl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

}
