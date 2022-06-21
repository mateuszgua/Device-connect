package com.hfad.fmaconnect.profile;

import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.hfad.fmaconnect.R;
import com.hfad.fmaconnect.database.UserDatabaseHelper;

public class RegisterActivity extends AppCompatActivity
        implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterActivity.this;
    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutName;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputLayout textInputLayoutConfirmPassword;

    private TextInputEditText textInputEditTextName;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private TextInputEditText textInputEditTextConfirmPassword;

    private AppCompatButton appCompatButtonRegister;
    private AppCompatTextView appCompatTextViewLoginLink;

    private InputValidation inputValidation;
    private UserDatabaseHelper userDatabaseHelper;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        initViews();
        initListeners();
        initObjects();
    }

    /**
     * Initialize views
     */
    private void initViews(){
        nestedScrollView =
                (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutName =
                (TextInputLayout) findViewById(R.id.textInputLayoutName);
        textInputLayoutEmail =
                (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword =
                (TextInputLayout) findViewById(R.id.textInputLayoutPassword);
        textInputLayoutConfirmPassword =
                (TextInputLayout) findViewById(R.id.textInputLayoutConfirmPassword);

        textInputEditTextName =
                (TextInputEditText) findViewById(R.id.textInputEditTextName);
        textInputEditTextEmail =
                (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword =
                (TextInputEditText) findViewById(R.id.textInputEditTextPassword);
        textInputEditTextConfirmPassword =
                (TextInputEditText) findViewById(R.id.textInputEditTextConfirmPassword);

        appCompatButtonRegister =
                (AppCompatButton) findViewById(R.id.appCompactButtonRegister);
        appCompatTextViewLoginLink =
                (AppCompatTextView) findViewById(R.id.appCompactTextViewLoginLink);
    }

    /**
     * Initialize listeners
     */
    private void initListeners(){
        appCompatButtonRegister.setOnClickListener(this);
        appCompatTextViewLoginLink.setOnClickListener(this);
    }

    /**
     * Initialize object
     */
    private void initObjects(){
        inputValidation = new InputValidation(activity);
        userDatabaseHelper = new UserDatabaseHelper(activity);
        user = new User();
    }

    /**
     * Method to listen the click on view
     * @param v
     */
    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.appCompactButtonRegister:
                postDataToSQLite();
                break;

            case R.id.appCompactTextViewLoginLink:
                finish();
                break;
        }
    }

    /**
     * Validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite(){
        if (!inputValidation.isInputEditTextFilled(
                textInputEditTextName, textInputLayoutName,
                getString(R.string.error_message_name))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(
                textInputEditTextEmail,textInputLayoutEmail,
                getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.inInputEditTextEmail(
                textInputEditTextEmail, textInputLayoutEmail,
                getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(
                textInputEditTextPassword, textInputLayoutPassword,
                getString(R.string.error_message_password))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(
                textInputEditTextPassword, textInputEditTextConfirmPassword,
                textInputLayoutConfirmPassword,
                getString(R.string.error_password_match))) {
            return;
        }
        if (!userDatabaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim())){
            user.setName(textInputEditTextName.getText().toString().trim());
            user.setEmail(textInputEditTextEmail.getText().toString().trim());
            user.setPassword(textInputEditTextPassword.getText().toString().trim());

            userDatabaseHelper.addUser(user);

            //Snack Bar to show success message that record saved successfully
            Snackbar.make(nestedScrollView,
                    getString(R.string.success_message),
                    Snackbar.LENGTH_LONG).show();
            emptyInputEditText();

        } else {
            //Snack Bar to show error message that record already exist
            Snackbar.make(nestedScrollView,
                    getString(R.string.error_emil_exist),
                    Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Clear all input edit text
     */
    private void emptyInputEditText(){
        textInputEditTextName.setText(null);
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
        textInputEditTextConfirmPassword.setText(null);
    }

}