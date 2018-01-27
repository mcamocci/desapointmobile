package com.desapoint.desapoint.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.desapoint.desapoint.fragments.Announcements;
import com.desapoint.desapoint.fragments.Articles;
import com.desapoint.desapoint.fragments.Books;
import com.desapoint.desapoint.fragments.Notes;
import com.desapoint.desapoint.fragments.OpportunitiesFragment;
import com.desapoint.desapoint.fragments.PastPaperFragment;
import com.desapoint.desapoint.fragments.Subjects;
import com.desapoint.desapoint.pojos.WindowInfo;
import com.desapoint.desapoint.toolsUtilities.PreferenceStorage;

/**
 * Created by hikaroseworx on 1/24/18.
 */

public class MyFragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {

    private Context context;

    private String titles[] = {"Announcements","Notes","Books","Subjects","PastPapers","Articles","Opportunities"};
    public MyFragmentPagerAdapter(Context context, FragmentManager fragmentManager){
        super(fragmentManager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new Announcements();
            case 1:
                PreferenceStorage.addWindowInfo(context,WindowInfo.NOTES);
                return new Notes();
            case 2:
                PreferenceStorage.addWindowInfo(context,WindowInfo.BOOK);
                return new Books();
            case 3:
                PreferenceStorage.addWindowInfo(context, WindowInfo.SUBJECT);
                return new Subjects();
            case 4:
                PreferenceStorage.addWindowInfo(context,WindowInfo.PASTPAPER);
                return new PastPaperFragment();
            case 5:
                PreferenceStorage.addWindowInfo(context,WindowInfo.ARTICLE);
                return new Articles();
            default:
                return new OpportunitiesFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titles[position];
    }

    @Override
    public int getCount() {
        return 6;
    }
}
