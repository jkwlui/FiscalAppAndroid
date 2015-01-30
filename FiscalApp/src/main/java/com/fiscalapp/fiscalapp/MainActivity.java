package com.fiscalapp.fiscalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.fiscalapp.fiscalapp.drawer.AbstractNavDrawerActivity;
import com.fiscalapp.fiscalapp.drawer.NavDrawerActivityConfiguration;
import com.fiscalapp.fiscalapp.drawer.NavDrawerAdapter;
import com.fiscalapp.fiscalapp.drawer.NavDrawerItem;
import com.fiscalapp.fiscalapp.drawer.NavMenuItem;
import com.fiscalapp.fiscalapp.drawer.NavMenuSection;


public class MainActivity extends AbstractNavDrawerActivity {

    public static String REDIRECT = "redirect";
    public static final int OVERVIEWFRAGMENT = 101;
    public static final int EXPENSESFRAGMENT = 201;
    public static final String EXPENSEMONTH = "expenseMonth";
    public static final int INCOMEFRAGMENT = 202;
    public static final int CATEGORIESFRAGMENT = 203;
    public static final int BILLSFRAGMENT = 204;
    public static final int SETTINGSFRAGMENT = 301;
    public static final int ABOUTFRAGMENT = 302;

    public static final int NEW_EXPENSE_REQUEST = 1;

    private static int[] expenseMonth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected NavDrawerActivityConfiguration getNavDrawerConfiguration() {

        NavDrawerItem[] menu = new NavDrawerItem[] {
                NavMenuItem.create(OVERVIEWFRAGMENT, getString(R.string.overview), "ic_action_chart13", true, this),
                NavMenuSection.create(200, getString(R.string.transactions)),
                NavMenuItem.create(EXPENSESFRAGMENT, getString(R.string.expenses), "ic_action_dollar41", true, this),
                NavMenuItem.create(INCOMEFRAGMENT, getString(R.string.income), "ic_action_accept", true, this),
                NavMenuItem.create(CATEGORIESFRAGMENT, getString(R.string.categories), "ic_action_accept", true, this),
                NavMenuItem.create(BILLSFRAGMENT, getString(R.string.bills), "ic_action_bill", true, this),
                NavMenuSection.create(300, getString(R.string.extras)),
                NavMenuItem.create(SETTINGSFRAGMENT, getString(R.string.settings), "ic_action_accept", true, this),
                NavMenuItem.create(ABOUTFRAGMENT, getString(R.string.about), "ic_action_accept", true, this)};

        NavDrawerActivityConfiguration navDrawerActivityConfiguration = new NavDrawerActivityConfiguration();
        navDrawerActivityConfiguration.setMainLayout(R.layout.activity_main);
        navDrawerActivityConfiguration.setDrawerLayoutId(R.id.drawer_layout);
        navDrawerActivityConfiguration.setLeftDrawerId(R.id.left_drawer);
        navDrawerActivityConfiguration.setNavItems(menu);
        navDrawerActivityConfiguration.setDrawerShadow(R.drawable.drawer_shadow);
        navDrawerActivityConfiguration.setDrawerOpenDesc(R.string.drawer_open);
        navDrawerActivityConfiguration.setDrawerCloseDesc(R.string.drawer_close);
        navDrawerActivityConfiguration.setBaseAdapter(
                new NavDrawerAdapter(this, R.layout.navdrawer_item, menu ));
        return navDrawerActivityConfiguration;
    }

    @Override
    protected void onNavItemSelected(int id) {
        switch ((int)id) {
            case OVERVIEWFRAGMENT:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new OverviewFragment()).commit();
                break;
            case EXPENSESFRAGMENT:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new ExpensesFragment(expenseMonth)).commit();
                break;
            case INCOMEFRAGMENT:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new IncomeFragment()).commit();
                break;
            case CATEGORIESFRAGMENT:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new CategoriesViewPager()).commit();
                break;
            case BILLSFRAGMENT:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new BillsFragment()).commit();
                break;
            case SETTINGSFRAGMENT:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new SettingsFragment()).commit();
                break;
            case ABOUTFRAGMENT:
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, new AboutFragment()).commit();
                break;


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.menu_new_expense) {
            Intent newExpenseIntent = new Intent(getApplication(), NewExpense.class);
            startActivityForResult(newExpenseIntent, NEW_EXPENSE_REQUEST);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_EXPENSE_REQUEST) {
            if (resultCode == RESULT_OK) {

                int[] redirectMonth = data.getIntArrayExtra(EXPENSEMONTH);
                if (redirectMonth != null) {
                    expenseMonth = redirectMonth;
                }

                onNavItemSelected(EXPENSESFRAGMENT);
            }
        }
    }
}
