package e.geertvanleuven.chatapp;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class SectionPageAdapter extends FragmentPagerAdapter {

    public SectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                profile requestsFragment = new profile();
                return requestsFragment;
            case 1:
                users friendsFragment = new users();
                return friendsFragment;

            default:
                return null;
        }


    }


    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        switch (position) {
            case 0:
                return "Profile";
            case 1:
                return "All Users";


        }

        return super.getPageTitle(position);


    }
}
