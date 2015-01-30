package com.fiscalapp.fiscalapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fiscalapp.fiscalapp.model.Category;

/**
 * Created by kinwa91 on 2014-03-28.
 */
public class CategoriesViewPager extends Fragment {

    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.categories_viewpager, container, false);
        viewPager = (ViewPager) rootView.findViewById(R.id.categories_viewpager);
        pagerAdapter = new CategoryPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        return rootView;

    }

    private class CategoryPagerAdapter extends FragmentStatePagerAdapter {

        public CategoryPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new Fragment();
            switch (position) {
                case 0:
                    fragment = new CategoriesFragment(Category.TYPE_EXPENSES);
                    break;
                case 1:
                    fragment = new CategoriesFragment(Category.TYPE_INCOME);
                    break;
                case 2:
                    fragment = new CategoriesFragment(Category.TYPE_BILLS);
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            CharSequence title = "";
            switch (position) {
                case 0:
                    title = getString(R.string.expenses);
                    break;
                case 1:
                    title = getString(R.string.income);
                    break;
                case 2:
                    title = getString(R.string.bills);
                    break;
            }
            return title;
        }
    }
}
