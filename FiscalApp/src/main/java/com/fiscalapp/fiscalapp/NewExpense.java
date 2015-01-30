package com.fiscalapp.fiscalapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.doomonafireball.betterpickers.calendardatepicker.CalendarDatePickerDialog;
import com.fiscalapp.fiscalapp.database.TransactionDatabaseExchanger;
import com.fiscalapp.fiscalapp.model.Category;
import com.fiscalapp.fiscalapp.model.Expense;
import com.fiscalapp.fiscalapp.model.Place;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Created by kinwa91 on 2014-03-13.
 */
public class NewExpense extends ActionBarActivity implements CalendarDatePickerDialog.OnDateSetListener {

    private Card infoCard;
    private CardView infoCardView;
    private Card amountCard;
    private CardView amountCardView;

    private EditText amountEditText;

    private TextView amountMoneySign;

    private TextView categoryNameTextView;
    private ImageView categoryIconImageView;
    private EditText descriptionEditText;
    private TextView dateEditText;
    private TextView locationTextView;

    private ImageView locationCancelImageView;


    private TableRow categoryTableRow;
    private TableRow dateTableRow;
    private TableRow locationTableRow;

    private Typeface robotoLight;
    private String location;
    private Calendar date;
    private Place place;

    private boolean hasLocation = false;

    public static final int SELECT_CATEGORY_REQUEST_CODE = 1;
    public static final int SELECT_DATE_REQUEST_CODE = 2;

    public static final int SELECT_LOCATION_REQUEST_CODE = 3;
    public static final String RESULT = "result";

    public static final String MODE = "mode";
    public static final String MODE_EDIT = "edit";
    public static final String EDIT_ID = "id";

    private boolean isEditMode = false;
    private int editId;

    // Selected States:
    private int selectedCategoryId = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_expense);

        setTitle(R.string.new_expense);
        Intent thisIntent = getIntent();

        getSupportActionBar().setIcon(R.drawable.ic_action_new_expense);

        robotoLight = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");

        infoCard = new Card(this.getApplication(), R.layout.new_expense_cardview);
        amountCard = new Card(this.getApplication(), R.layout.new_expense_amount_cardview);

        infoCardView = (CardView) findViewById(R.id.new_expense_cardview);
        amountCardView = (CardView) findViewById(R.id.new_expense_amount_cardview);

        infoCardView.setCard(infoCard);
        // Set up amount card view and text watcher
        amountCardView.setCard(amountCard);
        amountEditText = (EditText) amountCardView.findViewById(R.id.new_expense_amount);

        amountEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (!s.toString().matches("(\\d{1,3}(\\,\\d{3})*|(\\d+))(\\.\\d{2})?$")) {
                    String userInput = "" + s.toString().replaceAll("[^\\d]", "");
                    StringBuilder cashAmountBuilder = new StringBuilder(userInput);

                    while (cashAmountBuilder.length() > 3 && cashAmountBuilder.charAt(0) == '0') {
                        cashAmountBuilder.deleteCharAt(0);
                    }
                    while (cashAmountBuilder.length() < 3) {
                        cashAmountBuilder.insert(0, '0');
                    }
                    cashAmountBuilder.insert(cashAmountBuilder.length() - 2, '.');

                    amountEditText.setText(cashAmountBuilder.toString());
                    amountEditText.setTextKeepState(cashAmountBuilder.toString());
                    Selection.setSelection(amountEditText.getText(), cashAmountBuilder.toString().length());
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        amountEditText.setTypeface(robotoLight);
        amountMoneySign = (TextView) amountCardView.findViewById(R.id.new_expense_moneysign);
        amountMoneySign.setTypeface(robotoLight);
        amountEditText.clearFocus();
        Selection.setSelection(amountEditText.getText(), amountEditText.getText().length());


        categoryNameTextView = (TextView) infoCardView.findViewById(R.id.new_expense_category);
        categoryTableRow = (TableRow) infoCardView.findViewById(R.id.new_expense_category_tablerow);
        categoryIconImageView = (ImageView) infoCardView.findViewById(R.id.new_expense_category_icon);
        descriptionEditText = (EditText) infoCardView.findViewById(R.id.new_expense_description);
        dateEditText = (TextView) infoCardView.findViewById(R.id.new_expense_date);
        categoryNameTextView.setTypeface(robotoLight);
        descriptionEditText.setTypeface(robotoLight);
        dateEditText.setTypeface(robotoLight);
        dateTableRow = (TableRow) infoCardView.findViewById(R.id.new_expense_date_tablerow);

        date = new GregorianCalendar();
        Date currentTime = new Date();
        date.setTime(currentTime);


        if(thisIntent != null && thisIntent.getStringExtra(MODE) != null) {
            if(thisIntent.getStringExtra(MODE).equals(MODE_EDIT) && thisIntent.getIntExtra(EDIT_ID, 0) != 0) {
                isEditMode = true;
                editId = thisIntent.getIntExtra(EDIT_ID, 0);

                TransactionDatabaseExchanger exchanger = new TransactionDatabaseExchanger(this);
                exchanger.open();
                Expense e = exchanger.getExpense(editId);
                exchanger.close();

                date = e.getDate();
                place = e.getPlace();
                selectedCategoryId = e.getCategory();
                amountEditText.setText(e.getAmount().toString());
                descriptionEditText.setText(e.getInfo());
                onDateSet(null, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
                updateCategory();
            }
        }

        dateTableRow.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentManager fm = getSupportFragmentManager();
                        CalendarDatePickerDialog calendarDatePickerDialog = CalendarDatePickerDialog
                                .newInstance(NewExpense.this, date.get(Calendar.YEAR), date.get(Calendar.MONTH),
                                        date.get(Calendar.DAY_OF_MONTH));
                        calendarDatePickerDialog.show(fm, "fragment_date_picker_name");

                    }
                });
        locationTableRow = (TableRow) infoCardView.findViewById(R.id.new_expense_location_tablerow);
        locationTableRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent locationIntent = new Intent(getApplication(), SelectLocation.class);
                startActivityForResult(locationIntent, SELECT_LOCATION_REQUEST_CODE);


            }
        });
        locationTextView = (TextView) infoCardView.findViewById(R.id.new_expense_location_textview);
        locationTextView.setTypeface(robotoLight);

        locationCancelImageView = (ImageView) infoCardView.findViewById(R.id.location_cancel);
        if(hasLocation == false) locationCancelImageView.setVisibility(View.INVISIBLE);

        locationCancelImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                place = null;
                locationCancelImageView.setVisibility(View.INVISIBLE);
                locationTextView.setText(R.string.new_expense_location);
            }
        });

        categoryTableRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent selectCategoryIntent = new Intent(getApplication(), SelectCategory.class);
                startActivityForResult(selectCategoryIntent, SELECT_CATEGORY_REQUEST_CODE);
            }
        });



    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SELECT_CATEGORY_REQUEST_CODE) {

            if(resultCode == RESULT_OK){
                selectedCategoryId = data.getIntExtra(RESULT, 0);

                updateCategory();

            }
            if (resultCode == RESULT_CANCELED) {
            }
        } else if (requestCode == SELECT_LOCATION_REQUEST_CODE) {

            if(resultCode == RESULT_OK) {
                place = (Place) data.getSerializableExtra(RESULT);
                // Update View:
                locationTextView.setText(place.name);
                locationCancelImageView.setVisibility(View.VISIBLE);
            }
            if(resultCode == RESULT_CANCELED) {

            }

        }

    }

    private void updateCategory() {
        TransactionDatabaseExchanger dbExchanger = new TransactionDatabaseExchanger(this);
        dbExchanger.open();
        Category category = dbExchanger.getCategory(selectedCategoryId);
        dbExchanger.close();
        int categoryIconId = category.getIcon();
        String categoryName = category.getName();

        // Update View with category
        categoryNameTextView.setText(categoryName);
        categoryIconImageView.setImageResource(getResources()
                .getIdentifier("cat_icon_" + Integer.toString(categoryIconId),
                        "drawable", getApplication().getPackageName()));
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
                createTransaction();
                return true;
            case R.id.menu_back:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void createTransaction() {
        String amount = "";
        String description = "";
        if (amountEditText.getText() != null) {
            amount = amountEditText.getText().toString();
        }
        if (descriptionEditText.getText() != null) {
            description = descriptionEditText.getText().toString();
        }
        String placeName = "";
        if (place != null) placeName = place.name;
        if(amount.equals(getString(R.string.zeroamount))
                || selectedCategoryId == 0) {
            return;
        } else {
            TransactionDatabaseExchanger exchanger = new TransactionDatabaseExchanger(this);
            exchanger.open();
            if(isEditMode) {
                exchanger.updateTransaction(editId,amount,
                        selectedCategoryId,
                        description,
                        placeName,
                        date,
                        Category.TYPE_EXPENSES);
            } else {
                int id = exchanger.createTransaction(amount,
                        selectedCategoryId,
                        description,
                        placeName,
                        date,
                        Category.TYPE_EXPENSES);
            }
            exchanger.close();
            Intent returnIntent = new Intent();
            final int[] redirectMonth = new int[2];
            redirectMonth[0] = date.get(Calendar.YEAR);
            redirectMonth[1] = date.get(Calendar.MONTH);
            returnIntent.putExtra(MainActivity.EXPENSEMONTH, redirectMonth);
            setResult(RESULT_OK,returnIntent);
            finish();

        }

    }

    @Override
    public void onDateSet(CalendarDatePickerDialog calendarDatePickerDialog, int year, int month, int day) {


        Calendar calendar = new GregorianCalendar();
        Date trialTime = new Date();
        calendar.setTime(trialTime);
        String dateToSet;
        if (calendar.get(Calendar.YEAR) == year && calendar.get(Calendar.MONTH) == month
                && calendar.get(Calendar.DAY_OF_MONTH) == day){
            dateToSet = getString(R.string.today);
        } else {
            dateToSet = Integer.toString(year) + "-" + Integer.toString(month +1) + "-"
                    + Integer.toString(day);
        }

        date = new GregorianCalendar();
        date.set(year, month, day);

        dateEditText.setText(dateToSet);
    }

    @Override
     public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }
}
