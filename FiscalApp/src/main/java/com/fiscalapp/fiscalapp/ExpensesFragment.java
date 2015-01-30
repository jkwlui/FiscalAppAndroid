package com.fiscalapp.fiscalapp;

import android.content.Context;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fiscalapp.fiscalapp.adapter.ExpenseCard;
import com.fiscalapp.fiscalapp.database.TransactionDatabaseExchanger;
import com.fiscalapp.fiscalapp.model.Expense;

import java.math.BigDecimal;
import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.view.CardView;

/**
 * Created by kinwa91 on 2014-03-11.
 */
public class ExpensesFragment extends Fragment {

    private TransactionDatabaseExchanger exchanger;
    private ArrayList<Expense> expenses;

    LinearLayout linearLayout;
    Bundle state;
    Context mContext;

    View rootView;
    TextView monthTotalTextView;
    BigDecimal thisMonthTotal = null;

    ImageView leftButton;
    ImageView rightButton;
    TextView monthTextView;
    private int[] monthInView;

    private Typeface robotoLight;

    public ExpensesFragment() {}

    public ExpensesFragment(int[] jumpMonth) {
        if(jumpMonth != null)
            monthInView = jumpMonth;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        state = savedInstanceState;
        mContext = getActivity();
        rootView = inflater.inflate(R.layout.fragment_expenses, container, false);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.fragment_expenses_linearlayout);

        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);


        Calendar date = new GregorianCalendar();
        Date currentTime = new Date();
        date.setTime(currentTime);

        robotoLight = Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Light.ttf");
        if(monthInView == null ) {
            monthInView= new int[2];
            monthInView[0] = date.get(Calendar.YEAR);
            monthInView[1] = date.get(Calendar.MONTH);
        } else {

        }
        displayExpenses();

    }

    public void displayExpenses() {

        new ExpenseAsyncTask().execute(monthInView[0], monthInView[1]);
    }

    public void updateSummaryCard() {
        thisMonthTotal = Expense.totalAmount(expenses);
        if(monthTotalTextView != null) {
            monthTotalTextView.setText(thisMonthTotal.toString());
        }

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(monthInView[1] == 0) {
                    monthInView[0] -= 1;
                    monthInView[1] = 11;
                }else {
                    monthInView[1] -= 1;
                }
                displayExpenses();
            }
        });

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(monthInView[1] == 11) {
                    monthInView[0] += 1;
                    monthInView[1] = 0;
                } else {
                    monthInView[1] += 1;
                }
                displayExpenses();
            }
        });

        monthTextView.setText(new DateFormatSymbols().getMonths()[monthInView[1]] + " " + Integer.toString(monthInView[0]));
    }


    private class ExpenseAsyncTask extends AsyncTask<Integer, Integer, Long> {



        @Override
        protected Long doInBackground(Integer... integers) {

            exchanger = new TransactionDatabaseExchanger(getActivity());
            exchanger.open();
            expenses = exchanger.getMonthExpenses(integers[0], integers[1]);
            exchanger.close();

            return null;
        }

        @Override
        protected void onPostExecute(Long result) {


            linearLayout.removeAllViews();

            LinearLayout infoCardLayout = (LinearLayout) getLayoutInflater(state).inflate(R.layout.expenses_cardview, null);
            CardView infoCardView = ((CardView) infoCardLayout.findViewById(R.id.expense_card_listview));
            infoCardView.setCard(new Card(mContext, R.layout.expense_summary));

            monthTotalTextView = (TextView) infoCardView.findViewById(R.id.this_month_amount_textview);
            leftButton = (ImageView) infoCardView.findViewById(R.id.expense_last_month_button);
            rightButton = (ImageView) infoCardView.findViewById(R.id.expense_next_month_button);
            monthTextView = (TextView) infoCardView.findViewById(R.id.expense_summary_month_textview);
            monthTextView.setTypeface(robotoLight);
            updateSummaryCard();
            linearLayout.addView(infoCardLayout);

            if (expenses != null && expenses.size() > 0) {
                Collections.sort(expenses);
                String currentYear = Long.toString(expenses.get(0).getDate().get(Calendar.YEAR));
                String currentDayOfYear = Long.toString(expenses.get(0).getDate().get(Calendar.DAY_OF_YEAR));
                ArrayList<Expense> cache = new ArrayList<Expense>();
                ExpenseCard card = new ExpenseCard(getActivity());
                for (Expense e : expenses) {
                    String expenseYear = Long.toString(e.getDate().get(Calendar.YEAR));
                    String expenseDayOfYear = Long.toString(e.getDate().get(Calendar.DAY_OF_YEAR));
                    if (expenseYear.equals(currentYear) && expenseDayOfYear.equals(currentDayOfYear)) {
                        cache.add(e);
                    } else {
                        currentYear = expenseYear;
                        currentDayOfYear = expenseDayOfYear;
                        card.setExpenses(cache);

                        LinearLayout cardLayout = (LinearLayout) getLayoutInflater(state).inflate(R.layout.expenses_cardview, null);
                        CardView cardView = ((CardView) cardLayout.findViewById(R.id.expense_card_listview));
                        cardView.setCard(card);
                        linearLayout.addView(cardLayout);
                        card = new ExpenseCard(mContext);
                        cache = new ArrayList<Expense>();
                        cache.add(e);
                    }
                }
                card.setExpenses(cache);
                card.setType(0);
                LinearLayout cardLayout = (LinearLayout) getLayoutInflater(state).inflate(R.layout.expenses_cardview, null);
                CardView cardView = ((CardView) cardLayout.findViewById(R.id.expense_card_listview));
                cardView.setCard(card);
                linearLayout.addView(cardLayout);

            } else {
                Card card = new Card(mContext, R.layout.no_expense) {
                    @Override
                    public void setupInnerViewElements(ViewGroup parent, View view) {
                        TextView noExpense = (TextView) view.findViewById(R.id.no_expense_textview);
                        noExpense.setTypeface(Typeface.createFromAsset(mContext.getAssets(), "fonts/Roboto-Italic.ttf"));
                    }

                };

                LinearLayout cardLayout = (LinearLayout) getLayoutInflater(state).inflate(R.layout.expenses_cardview, null);
                CardView cardView = ((CardView) cardLayout.findViewById(R.id.expense_card_listview));
                cardView.setCard(card);

                linearLayout.addView(cardLayout);
            }
        }
    }



}
