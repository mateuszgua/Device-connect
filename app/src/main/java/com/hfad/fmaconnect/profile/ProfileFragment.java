package com.hfad.fmaconnect.profile;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hfad.fmaconnect.R;
import com.hfad.fmaconnect.productinfo.UserDeviceListActivity;
import com.hfad.fmaconnect.database.SessionManager;
import com.hfad.fmaconnect.database.UserDatabaseHelper;

import java.util.HashMap;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private NestedScrollView nestedScrollView;
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;
    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;
    private AppCompatTextView textViewLinkRegister;

    private AppCompatButton appCompatButtonLogin;
    private AppCompatCheckBox appCompatCheckBox;
    private InputValidation inputValidation;
    private UserDatabaseHelper userDatabaseHelper;

    private boolean wasChecked;

    private boolean saveLogin;
    private SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPreferencesEditor;
    private String username,password;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            wasChecked = savedInstanceState.getBoolean("wasChecked");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean("wasChecked", wasChecked);
    }

    @Override
    public void onStart() {
        super.onStart();
        initViews();
        initObjects();
        initListeners();
        letTheUserLoggedIn();

        SessionManager sessionManager =
                new SessionManager(getActivity(), SessionManager.SESSION_REMEMBERME);
        if (sessionManager.checkRememberMe()){
            HashMap<String, String> rememberMeDetails =
                    sessionManager.getRememberMeDetailFromSession();
            textInputEditTextEmail.setText(rememberMeDetails.get(SessionManager.KEY_SESSION_EMAIL));
            textInputEditTextPassword.setText(rememberMeDetails.get(SessionManager.KEY_SESSION_PASSWORD));

        } else {
            textInputEditTextEmail.setText(null);
            textInputEditTextPassword.setText(null);
        }

    }

    @Override
    public void onPause() {
        super.onPause();
        wasChecked = appCompatCheckBox.isChecked();
    }

    @Override
    public void onResume() {
        super.onResume();

        if (wasChecked) {
            appCompatCheckBox.setChecked(true);
        }
    }
    /**
     * Initialize views
     *
     */
    private void initViews() {
        View view = getView();

        if (view != null) {
            nestedScrollView = (NestedScrollView)view.findViewById(R.id.nestedScrollView);

            textInputLayoutEmail =
                    (TextInputLayout)view.findViewById(R.id.textInputLayoutEmail);
            textInputLayoutPassword =
                    (TextInputLayout)view.findViewById(R.id.textInputLayoutPassword);

            textInputEditTextEmail =
                    (TextInputEditText)view.findViewById(R.id.textInputEditTextEmail);
            textInputEditTextPassword =
                    (TextInputEditText)view.findViewById(R.id.textInputEditTextPassword);

            appCompatButtonLogin =
                    (AppCompatButton)view.findViewById(R.id.appCompactButtonLogin);
            textViewLinkRegister =
                    (AppCompatTextView)view.findViewById(R.id.textViewLinkRegister);

            appCompatCheckBox =
                    (AppCompatCheckBox) view.findViewById(R.id.appCompactCheckboxRemember);

        }
    }

    public void letTheUserLoggedIn(){
        View view = getView();

        String email = textInputEditTextEmail.getText().toString().trim();
        String password = textInputEditTextPassword.getText().toString().trim();

        if (appCompatCheckBox.isChecked()) {
            SessionManager sessionManager =
                    new SessionManager(getActivity(), SessionManager.SESSION_REMEMBERME);
            sessionManager.createRememberMeSession(email, password);

        } else {
            SessionManager sessionManager =
                    new SessionManager(getActivity(), SessionManager.SESSION_REMEMBERME);
            sessionManager.deleteRememberMeSession(email, password);
            emptyInputEditText();
        }

    }

    /**
     * Method to initialize listeners
     */
    private void initListeners(){
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);

    }

    /**
     * Initialize object to be used
     */
    private void initObjects(){
        userDatabaseHelper = new UserDatabaseHelper(getActivity());
        inputValidation = new InputValidation(getActivity());
    }

    /**
     * Method to listen the click on view
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompactButtonLogin:
                verifyFromSQLite();
                break;

            case R.id.textViewLinkRegister:
                Intent intentRegister = new Intent(getActivity(), RegisterActivity.class);
                getActivity().startActivity(intentRegister);
                break;
        }
    }

    /**
     * Validate the input text fields and verify login
     */
    private void verifyFromSQLite() {
        if ( !inputValidation.isInputEditTextFilled(
                textInputEditTextEmail, textInputLayoutEmail,
                getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.inInputEditTextEmail(
                textInputEditTextEmail, textInputLayoutEmail,
                getString(R.string.error_message_email))){
            return;
        }
        if (!inputValidation.isInputEditTextFilled(
                textInputEditTextPassword, textInputLayoutPassword,
                getString(R.string.error_message_email))) {
            return;
        }
        if (userDatabaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim(),
                textInputEditTextPassword.getText().toString().trim())){
            Intent accountsIntent = new Intent(getActivity(), UserDeviceListActivity.class);
            accountsIntent.putExtra("EMAIL",
                    textInputEditTextEmail.getText().toString().trim());
         //   emptyInputEditText();
            getActivity().startActivity(accountsIntent);

        } else {
            //Snack bar to show success message that record is wrong
            Snackbar.make(nestedScrollView,
                    getString(R.string.error_valid_email_password),
                    Snackbar.LENGTH_LONG).show();
        }
    }

    private void emptyInputEditText(){
            textInputEditTextEmail.setText(null);
            textInputEditTextPassword.setText(null);
    }


}