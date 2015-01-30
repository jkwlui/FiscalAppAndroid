package com.fiscalapp.fiscalapp.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fiscalapp.fiscalapp.MainActivity;
import com.fiscalapp.fiscalapp.NewExpense;
import com.fiscalapp.fiscalapp.R;
import com.fiscalapp.fiscalapp.database.TransactionDatabaseExchanger;
import com.fiscalapp.fiscalapp.model.Category;
import com.fiscalapp.fiscalapp.model.Expense;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import it.gmariotti.cardslib.library.internal.Card;

/**
 * Created by kinwa91 on 2014-03-13.
 */
public class ExpenseCard extends Card {

    private ArrayList<Expense> expenses;
    private TransactionDatabaseExchanger exchanger;
    private Context mContext;

    Typeface amountTypeface;
    Typeface categoryTypeface;
    Typeface dateTypeface;

    private boolean displayed = false;

    public ExpenseCard(Context context) {
        this(context, R.layout.expense_card_layout);
    }


    public ExpenseCard(Context context, int innerLayout) {
        super(context, innerLayout);
        this.mContext = context;

        amountTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf");
        categoryTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Medium.ttf");
        dateTypeface = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Thin.ttf");
        init();

    }

    private void init() {

        exchanger = new TransactionDatabaseExchanger(mContext);
    }

    @Override
    public void setupInnerViewElements(ViewGroup parent, View view) {
        LinearLayout list = (LinearLayout) view.findViewById(R.id.expense_inner_listview);
        //     ExpenseListAdapter mAdapter = new ExpenseListAdapter(super.getContext(), expenses);
        //     list.setAdapter(mAdapter);
        TextView monthTextView = (TextView) view.findViewById(R.id.expense_month_text_view);
        TextView dateTextView = (TextView) view.findViewById(R.id.expense_date_text_view);

        Calendar expenseDate = expenses.get(0).getDate();
        Calendar today = new GregorianCalendar();
        Date currentTime = new Date();
        today.setTime(currentTime);
        int thisYear = today.get(Calendar.YEAR);

        monthTextView.setText(new SimpleDateFormat("MMM").format(expenseDate.getTime()));
        monthTextView.setAllCaps(true);
        monthTextView.setTypeface(dateTypeface);
        dateTextView.setText(new SimpleDateFormat("dd").format(expenseDate.getTime()));
        if (expenseDate.get(Calendar.YEAR) != thisYear)
            monthTextView.setText(monthTextView.getText() + " " + Integer.toString(expenses.get(0).getDate().get(Calendar.YEAR)));
        dateTextView.setTypeface(dateTypeface);

        if (!displayed) {
            for (Expense e : expenses) {
                if (e.getDate().equals(expenseDate)) {

                    final int id = e.getId();
                    final int[] redirectMonth = new int[2];
                    redirectMonth[0] = e.getDate().get(Calendar.YEAR);
                    redirectMonth[1] = e.getDate().get(Calendar.MONTH);
                    LayoutInflater li =
                            (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View newView = li.inflate(R.layout.transaction_listview_item, parent, false);

                    ImageView icon = (ImageView) newView.findViewById(R.id.transaction_listview_category_icon);
                    TextView categoryTextView = (TextView) newView.findViewById(R.id.transaction_list_category_textview);
                    categoryTextView.setTypeface(categoryTypeface);
                    categoryTextView.setAllCaps(true);
                    TextView info = (TextView) newView.findViewById(R.id.transaction_list_info_textview);
                    TextView amount = (TextView) newView.findViewById(R.id.transaction_list_amuont_textview);
                    amount.setTypeface(amountTypeface);

                    exchanger.open();
                    Category category = exchanger.getCategory(e.getCategory());
                    exchanger.close();

                    categoryTextView.setText(category.getName());
                    icon.setImageResource(mContext.getResources()
                            .getIdentifier(category.getIconFileName(), "drawable", mContext.getPackageName()));
                    info.setText(e.getInfo());
                    amount.setText(e.getAmount().toString());

                    newView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {


                            Intent editExpense = new Intent(mContext, NewExpense.class);
                            editExpense.putExtra(NewExpense.MODE, NewExpense.MODE_EDIT);
                            editExpense.putExtra(NewExpense.EDIT_ID, id);
                            Activity parentActivity = (Activity) mContext;
                            parentActivity.startActivityForResult(editExpense, MainActivity.NEW_EXPENSE_REQUEST);
                        }
                    });
                    newView.setOnLongClickListener(new View.OnLongClickListener() {

                        @Override
                        public boolean onLongClick(View view) {

                            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                            builder.setMessage(mContext.getString(R.string.delete_transaction));
                            final int longClickedId = id;
                            final View mView = view;
                            builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    TransactionDatabaseExchanger databaseExchanger = new TransactionDatabaseExchanger(mContext);
                                    databaseExchanger.open();
                                    databaseExchanger.deleteTransaction(longClickedId);
                                    databaseExchanger.close();
                                    mView.setVisibility(View.GONE);
                                }
                            });

                            builder.setNegativeButton(R.string.cancel, null);
                            builder.create().show();
                            return true;
                        }
                    });
                    list.addView(newView);
                } else {
                    continue;
                }
            }

            displayed = true;
        }
    }


    public void setExpenses(ArrayList<Expense> expenses) {
        this.expenses = expenses;
    }


}