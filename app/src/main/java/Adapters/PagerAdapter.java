package Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import Fragments.GeneralDetailFragment;
import Fragments.EducationFragment;
import Fragments.ProfessionFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    int numberOfTabs;
    public PagerAdapter(FragmentManager fm, int numOfTabs)
    {
        super(fm);
        this.numberOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch(i)
        {
            case 0 : GeneralDetailFragment tab1 = new GeneralDetailFragment();
            return tab1;
            case 1: EducationFragment tab2 = new EducationFragment();
            return tab2;
            case 2: ProfessionFragment tab3 = new ProfessionFragment();
            return tab3;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
