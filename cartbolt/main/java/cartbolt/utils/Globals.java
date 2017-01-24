package cartbolt.utils;

import java.util.ArrayList;
import java.util.List;

import cartbolt.qui.entities.OrderItem;
import cartbolt.qui.entities.Outlet;

/**
 * Created by Job on 29-May-16.
 */
public class Globals {

    public static double total;
    public static double markup;
    public static double marktotal;
    public static double delivery;
    public static ArrayList<OrderItem> cartlist = new ArrayList<OrderItem>();

    public static final int SPLASH_THREAD_DELAY = 1500;
    public static final int HOME_THREAD_DELAY = 1000;

    //public static String server = "http://10.0.2.2:8080/cartiemate";
    //public static String server = "http://10.0.2.2:8989/Cartbolt";
    //public static String server = "http://192.168.201.1:80/cartmate/actions";
    //public static String server = "http://192.168.202.1:80/cartmate";
    //public static String images = "http://192.168.202.1:80/cartmate/images/";
    //public static String server = "http://cartbolt.ug/raw";
    public static String images = "http://cartbolt.ug/images/";
    public static String server = "http://cartbolt.ug:8080/CartBaseEngine";
    public static String imageserver = "http://cartbolt.ug:8080/CartBaseEngine/images/";
    //public static String server = "http://10.0.2.2:80/cartbolt";
    //public static String server = "http://trevorcloud.cloudapp.net/cartbolt";
    //public static String server = "http://10.0.2.2:80/cartmate";
    //public static String server = "http://192.168.56.1:80/cartmate";
    //public static String server = "http://trevorcloud.cloudapp.net/cartmate";

    public static String regstatus;
    public static int userid;
    public static String demoSize = "large";

    //product track
    public static String typeofoutlet;
    public static String outletid;
    public static String sizer;
    public static String outletname;
    public static int subcategoryid;
    public static int itemid;

    //public static String theoutletname;
    public static int categoryid;
    public static int deuxid;
    public static int closerid;
    public static int productid;

    //nonreg guy
    public static String theusername;
    public static String theuseremail;
    public static String theuserphone;
    public static String theuserlocation;
    public static String theuserplot;
    public static String theuserblock;
    public static String theuserdescription;
    public static Boolean theuserentry = true;

    //List
    public static List<Outlet> outlets = new ArrayList<Outlet>();
    public static String connection;

}
