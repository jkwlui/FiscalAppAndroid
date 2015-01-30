package com.fiscalapp.fiscalapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.fiscalapp.fiscalapp.adapter.CategoryListAdapter;
import com.fiscalapp.fiscalapp.database.TransactionDatabaseExchanger;
import com.fiscalapp.fiscalapp.model.Category;

import java.util.ArrayList;

/**
 * Created by kinwa91 on 2014-03-11.
 */
public class CategoriesFragment extends Fragment{

    public CategoriesFragment() {}

    private ListView listView;
    private CategoryListAdapter adapter;
    private Button newCategoryButton;

    private String displayType;

    public static String CATEGORIES_MODE = "mode";
    public static String CATEGORIES_ID = "editId";
    public static String CATEGORIES_EDIT_MODE = "edit";

    public CategoriesFragment(String displayType) {
        super();
        this.displayType = displayType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.fragment_categories, container, false);

        listView = (ListView) rootView.findViewById(R.id.categoriesListView);
        // Add OnClickListener to New Category Button
        newCategoryButton = (Button) rootView.findViewById(R.id.new_category_button);
        Button.OnClickListener clickListener = new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), NewCategory.class);
                startActivityForResult(i,1);
            }
        };
        newCategoryButton.setOnClickListener(clickListener);
        displayList();
        return rootView;
    }

    private void displayList() {

        // Query Database using DatabaseExchanger
        TransactionDatabaseExchanger dbExchanger = new TransactionDatabaseExchanger(getActivity());
        dbExchanger.open();
        ArrayList<Category> categories = dbExchanger.getCategoriesByType(displayType);
        dbExchanger.close();

        // Setup listView with adapter
        adapter = new CategoryListAdapter(getActivity().getApplicationContext(), R.layout.categories_listview_items, categories);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long id) {
                Intent editIntent = new Intent(getActivity(), NewCategory.class);
                editIntent.putExtra(CATEGORIES_MODE, CATEGORIES_EDIT_MODE);
                editIntent.putExtra(CATEGORIES_ID, (int) id);
                startActivityForResult(editIntent, 1);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage(getString(R.string.delete_category));
                final int longClickedId = (int) id;
                builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TransactionDatabaseExchanger databaseExchanger = new TransactionDatabaseExchanger(getActivity());
                        databaseExchanger.open();
                        databaseExchanger.deleteCategory(longClickedId);
                        databaseExchanger.close();
                        displayList();
                    }
                });
                builder.setNegativeButton(R.string.cancel, null);
                builder.create().show();
                return true;
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {

            if(resultCode == NewCategory.RESULT_OK){
                displayList();
            }
            if (resultCode == NewCategory.RESULT_CANCELED) {

            }
        }
    }//onActivityResult

}
