package Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.aatmikjain.memberdirectory.FirstFragment;
import com.example.aatmikjain.memberdirectory.SecondFragment;
import com.example.aatmikjain.memberdirectory.ThirdFragment;

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
            case 0 : FirstFragment tab1 = new FirstFragment();
            return tab1;
            case 1: SecondFragment tab2 = new SecondFragment();
            return tab2;
            case 2: ThirdFragment tab3 = new ThirdFragment();
            return tab3;
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}