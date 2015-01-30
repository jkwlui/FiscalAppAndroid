package com.fiscalapp.fiscalapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.fiscalapp.fiscalapp.adapter.CategoryListAdapter;
import com.fiscalapp.fiscalapp.database.TransactionDatabaseExchanger;
import com.fiscalapp.fiscalapp.model.Category;

import java.util.ArrayList;

/**
 * Created by kinwa91 on 2014-03-13.
 */
public class SelectCategory extends ActionBarActivity {

    private ListView listView;
    private CategoryListAdapter adapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_categories);

        setTitle(R.string.new_expense_select_category);

        Button newCategoryButton = (Button) findViewById(R.id.new_category_button);
        newCategoryButton.setVisibility(View.GONE);

        // Query Database using DatabaseExchanger
        TransactionDatabaseExchanger dbExchanger = new TransactionDatabaseExchanger(getApplication());
        dbExchanger.open();
        ArrayList<Category> categories = dbExchanger.getAllCategories();
        dbExchanger.close();

        // Setup listView with adapter
        listView = (ListView) findViewById(R.id.categoriesListView);
        adapter = new CategoryListAdapter(getApplicationContext(), R.layout.categories_listview_items, categories);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent returnIntent = new Intent();
                returnIntent.putExtra(NewExpense.RESULT,(int) l);
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });

    }
}
