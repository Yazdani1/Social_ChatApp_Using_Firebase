package yazdaniscodelab.lapitchatapps;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Yazdani on 1/28/2018.
 */

public class SectionPagerAdapter extends FragmentPagerAdapter{

    public SectionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                RequestFragment requestFragment=new RequestFragment();
                return requestFragment;

            case 1:
                FriendsFragment friendsFragment=new FriendsFragment();
                return friendsFragment;

            case 2:

                ChatsFragment chatsFragment=new ChatsFragment();
                return chatsFragment;

                default:
                    return null;

        }

    }

    @Override
    public int getCount() {
        return 3;
    }


    public CharSequence getPageTitle(int position){

        switch (position){

            case 0:
                return "Requests";

            case 1:
                return "Friends";


            case 2:
                return "Chats";

                default:
                    return null;

        }

    }

}
