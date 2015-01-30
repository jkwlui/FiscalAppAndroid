package com.fiscalapp.fiscalapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.fiscalapp.fiscalapp.adapter.IconAdapter;
import com.fiscalapp.fiscalapp.database.TransactionDatabaseExchanger;
import com.fiscalapp.fiscalapp.model.Category;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Created by kinwa91 on 2014-03-12.
 */
public class NewCategory extends ActionBarActivity {

    private Card infoCard;
    private CardView infoCardView;
    private Card iconCard;
    private CardView iconCardView;
    private GridView iconGridView;
    private TableRow typeTableRow;
    private TextView typeTextView;
    private TextView nameTextView;
    private TextView selectIconTextView;
    private int type = 0;
    private int selectedIcon = 0;
    private ImageView selectedIconImageView;
    private String[] types;
    private int selected = 0;
    private boolean editMode = false;
    private int editId = 0;
    private Typeface robotoLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        getSupportActionBar().setIcon(R.drawable.ic_action_storage);
        setContentView(R.layout.new_category);


        robotoLight = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

        selectIconTextView = (TextView) findViewById(R.id.selectIconTextView);
        selectIconTextView.setTypeface(robotoLight);

        // Info Card

        infoCard = new Card(this.getApplication(), R.layout.new_category_name_card_view);
        infoCardView = (CardView) findViewById(R.id.new_category_cardview);
        infoCardView.setCard(infoCard);

        typeTextView = (TextView) infoCardView.findViewById(R.id.typeTextView);
        typeTextView.setTypeface(robotoLight);
        setTypeTextView();

        typeTableRow = (TableRow) infoCardView.findViewById(R.id.typeTableRow);
        typeTableRow.setOnClickListener(new TableRow.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                showTypeDialog();
            }
        });

        nameTextView = (TextView) infoCardView.findViewById(R.id.categoryNameTextView);
        nameTextView.setTypeface(robotoLight);


        // Icon Card

        iconCard = new Card(this.getApplication(), R.layout.new_category_icons_card_view);
        iconCardView = (CardView) findViewById(R.id.new_category_icon_cardview);
        iconCardView.setCard(iconCard);

        // selectedIconImageView

        selectedIconImageView = (ImageView) findViewById(R.id.selectedIconImageView);

        // iconGridView

        iconGridView = (GridView) iconCardView.findViewById(R.id.iconGridView);
        iconGridView.setAdapter(new IconAdapter(this));
        iconGridView.setOnItemClickListener(new GridView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position,
                                    long arg3) {
                selectedIcon = position + 1;
                setSelectedIconImage();
            }

        });

        // Show category being edited

        Intent intent = getIntent();
        String mode = intent.getStringExtra(CategoriesFragment.CATEGORIES_MODE);
        if (mode != null && mode.equals(CategoriesFragment.CATEGORIES_EDIT_MODE)) {
            editMode = true;
            editId = intent.getIntExtra(CategoriesFragment.CATEGORIES_ID, 0);
            TransactionDatabaseExchanger databaseExchanger = new TransactionDatabaseExchanger(this);
            databaseExchanger.open();
            Category editCategory = databaseExchanger.getCategory(editId);
            databaseExchanger.close();
            nameTextView.setText(editCategory.getName());
            selectedIcon = editCategory.getIcon();
            setSelectedIconImage();
            type = Category.getTypeEnum(editCategory.getType());
            setTypeTextView();
        }

        setTitle(editMode? R.string.edit_category: R.string.new_category);

    }

    private void setSelectedIconImage() {

        selectedIconImageView.setImageResource(getResources()
                .getIdentifier("cat_icon_" + Integer.toString(selectedIcon),
                        "drawable", getApplication().getPackageName()));
    }

    private void setTypeTextView() {

        if(types == null) types = getResources().getStringArray(R.array.type_selections);
        typeTextView.setText(types[type]);

    }

    private void showTypeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.new_category_type);
        builder.setPositiveButton(R.string.new_category_set_type, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                type = selected;
                setTypeTextView();
            }
        });
        builder.setCancelable(false);

        builder.setSingleChoiceItems(R.array.type_selections, type, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selected = i;
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_category, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_tick:
                updateCategory();
                return true;
            case R.id.menu_back:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateCategory() {


        if (nameTextView.getText().toString().isEmpty()
                || selectedIcon == 0) {
            return;
        } else {
            String updateName = nameTextView.getText().toString();
            String updateType = Category.getTypeString(type);

            TransactionDatabaseExchanger databaseExchanger = new TransactionDatabaseExchanger(this);
            databaseExchanger.open();
            if (editMode) {
                databaseExchanger.updateCategory(editId, updateName, selectedIcon,updateType);
            } else {
                int id = databaseExchanger.createCategory(updateName,selectedIcon,updateType);
            }
            databaseExchanger.close();
            Intent returnIntent = new Intent();
            setResult(RESULT_OK,returnIntent);
            finish();
        }
    }


}
