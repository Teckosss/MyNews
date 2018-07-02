package com.deguffroy.adrien.mynews.Controllers;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.deguffroy.adrien.mynews.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HelpActivity extends AppCompatActivity  implements View.OnClickListener {

    @BindView(R.id.simple_toolbar)Toolbar mToolbar;
    @BindView(R.id.help_activity_subject)EditText mSubject;
    @BindView(R.id.help_activity_body)EditText mBody;
    @BindView(R.id.help_activity_submit_button)Button mSubmit;

    // FOR DATA
    public static final String EMAIL_TO_SEND = "teckos.poub@gmail.com";
    public static final String TEXT_TO_ADD_TO_SUBJECT = "MyNews";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        ButterKnife.bind(this);

        this.configureToolbar();
        this.configureClickListener();
    }

    @Override
    public void onClick(View v) {
        if (v == mSubmit){
            String subject = mSubject.getText().toString();
            String body = mBody.getText().toString();
            if (TextUtils.isEmpty(subject)){
                mSubject.setError("Subject is required");
                mSubject.requestFocus();
            }
            if (TextUtils.isEmpty(body)){
                mBody.setError("Body is required");
                mBody.requestFocus();
            }

            Intent sendEmail = new Intent(android.content.Intent.ACTION_SENDTO);

            /* Fill it with Data */
            sendEmail.setData(Uri.parse("mailto:"));
            sendEmail.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { EMAIL_TO_SEND });
            sendEmail.putExtra(android.content.Intent.EXTRA_SUBJECT, TEXT_TO_ADD_TO_SUBJECT + " - " +subject);
            sendEmail.putExtra(android.content.Intent.EXTRA_TEXT, body);

            /* Send it off to the Activity-Chooser */
            startActivity(Intent.createChooser(sendEmail, "Send mail..."));
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
        mSubmit.setOnClickListener(this);
    }
}
