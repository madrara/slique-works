package cartbolt.qui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import cartbolt.qui.screens.Outlethome;
import cartbolt.qui.screens.fragments.FragmentTabLarge;

/**
 * Created by Job on 29-May-16.
 */
public class TabsAdapterLarge extends FragmentPagerAdapter {

    //Get and Declare the number of ViewPager pages
    final int PAGE_COUNT = Outlethome.getTabCounts();

    public TabsAdapterLarge(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        int m = position;
        for(int mnt=0; mnt<PAGE_COUNT; mnt++){
            //System.out.println(mnt);
            FragmentTabLarge emer = new FragmentTabLarge();
            emer.tabIndex = m;
            return emer;
        }

        return null;
    }

    public CharSequence getPageTitle(int position) {
        return Outlethome.getTabValues().get(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}
