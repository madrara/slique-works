package cartbolt.utils;

import java.util.ArrayList;

import cartbolt.qui.entities.CartItem;
import cartbolt.qui.entities.Item;

/**
 * Created by Job on 29-May-16.
 */
public class Cart {

    public static ArrayList<Item> cartlist = new ArrayList<Item>();
    public static ArrayList<CartItem> cartlists = new ArrayList<CartItem>();
    public static ArrayList<String> neworders = new ArrayList<String>();
    public static double total;
    public static double markup;
    public static double marktotal;
    public static double delivery;
    public static double grand;

}
