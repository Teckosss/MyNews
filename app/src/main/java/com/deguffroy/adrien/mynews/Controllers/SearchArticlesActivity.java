package com.deguffroy.adrien.mynews.Controllers;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.deguffroy.adrien.mynews.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchArticlesActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.simple_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.search_begin_date)
    EditText mBeginDate;
    @BindView(R.id.search_end_date)
    EditText mEndDate;
    @BindView(R.id.search_button)
    Button mButtonSearch;
    @BindView(R.id.search_editText)
    EditText mSearchText;

    public static String QUERY = "QUERY";
    public static String BEGIN_DATE = "BEGIN_DATE";
    public static String END_DATE = "END_DATE";

    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_articles);
        ButterKnife.bind(this);

        this.configureToolbar();
        this.configureClickListener();

    }

    @Override
    public void onClick(View v) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        if (v == mBeginDate){
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            mBeginDate.setText(sdf.format(new Date(year - 1900,monthOfYear,dayOfMonth)));

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        else if (v == mEndDate){
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            mEndDate.setText(sdf.format(new Date(year - 1900,monthOfYear,dayOfMonth)));

                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.show();
        }
        else if (v == mButtonSearch){
            SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String query = mSearchText.getText().toString();
            String beginDate = null;
            String endDate = null;
            try {
                Date d1 = new SimpleDateFormat("dd/MM/yyyy").parse(mBeginDate.getText().toString());
                Date d2 = new SimpleDateFormat("dd/MM/yyyy").parse(mEndDate.getText().toString());
                beginDate = mSimpleDateFormat.format(d1);
                endDate = mSimpleDateFormat.format(d2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if (!(query.equals(""))){
                Intent intent = new Intent(this, SearchResultActivity.class);
                intent.putExtra(QUERY,query);
                intent.putExtra(BEGIN_DATE,beginDate);
                intent.putExtra(END_DATE,endDate);
                startActivity(intent);
            }else{
                Toast.makeText(this, "Query can't be empty", Toast.LENGTH_SHORT).show();
            }

        }
    }

    // -------------
    // CONFIGURATION
    // -------------

    private void configureToolbar(){
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }

    private void configureClickListener(){
        mBeginDate.setOnClickListener(this);
        mEndDate.setOnClickListener(this);
        mButtonSearch.setOnClickListener(this);
    }
}
