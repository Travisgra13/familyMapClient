package com.example.travis.familymapclient;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class LoginFragment extends FragmentActivity {
    private Button signInButton;
    private Button registerButton;
    private RadioButton maleRadio;
    private RadioButton femaleRadio;
    private RadioGroup radioGroup;

    private EditText serverHostInput;
    private EditText serverPortInput;
    private EditText userNameInput;
    private EditText passwordInput;
    private EditText firstNameInput;
    private EditText lastNameInput;
    private EditText emailInput;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_fragment);
        serverHostInput = findViewById(R.id.serverHostInput);
        serverPortInput = findViewById(R.id.serverPortInput);
        userNameInput = findViewById(R.id.userNameInput);
        passwordInput = findViewById(R.id.passwordInput);
        firstNameInput = findViewById(R.id.firstNameInput);
        lastNameInput = findViewById(R.id.lastNameInput);
        emailInput = findViewById(R.id.emailInput);
        radioGroup = findViewById(R.id.radioGroup2);
        maleRadio = findViewById(R.id.maleButton);
        femaleRadio = findViewById(R.id.radioButton2);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int buttonId = radioGroup.getCheckedRadioButtonId();
                switch(buttonId) {
                    case R.id.maleButton:
                        if (registerRadioCheck(serverHostInput, serverPortInput, userNameInput, passwordInput,
                                firstNameInput, lastNameInput, emailInput)) {
                            registerButton.setClickable(true);
                        }
                        else {
                            registerButton.setClickable(false);
                        }
                        break;
                    case R.id.radioButton2:
                        if (registerRadioCheck(serverHostInput, serverPortInput, userNameInput, passwordInput,
                                firstNameInput, lastNameInput, emailInput)) {
                            registerButton.setClickable(true);
                        }
                        else  {
                            registerButton.setClickable(false);
                        }
                        break;
                }
            }
        });
        signInButton = findViewById(R.id.button);
        registerButton = findViewById(R.id.button2);
        addSignInListeners(serverHostInput,serverPortInput, userNameInput, passwordInput);
        addRegisterListeners(serverHostInput, serverPortInput, userNameInput, passwordInput, firstNameInput, lastNameInput, emailInput, radioGroup);
    }

    public boolean registerRadioCheck(EditText serverHostInput, EditText serverPortInput, EditText userNameInput, EditText passwordInput,
                                      EditText firstNameInput, EditText lastNameInput, EditText emailInput) {
        if (serverHostInput.getText().length() > 0 && serverPortInput.getText().length() > 0 && userNameInput.getText().length() > 0
            && passwordInput.getText().length() > 0 && firstNameInput.getText().length() > 0 && lastNameInput.getText().length() > 0 && emailInput.getText().length() > 0) {
            return true;
        }
        else  {
            return false;
        }
    }

    public void addSignInListeners(EditText serverHostInput, EditText serverPortInput, EditText userNameInput, EditText passwordInput) {
        serverHostInput.addTextChangedListener(signInWatcher);
        serverPortInput.addTextChangedListener(signInWatcher);
        userNameInput.addTextChangedListener(signInWatcher);
        passwordInput.addTextChangedListener(signInWatcher);
    }
    public void addRegisterListeners(EditText serverHostInput, EditText serverPortInput, EditText userNameInput, EditText passwordInput,
                                     EditText firstNameInput, EditText lastNameInput, EditText emailInput, RadioGroup radioGroup) {
        serverHostInput.addTextChangedListener(registerWatcher);
        serverPortInput.addTextChangedListener(registerWatcher);
        userNameInput.addTextChangedListener(registerWatcher);
        passwordInput.addTextChangedListener(registerWatcher);
        firstNameInput.addTextChangedListener(registerWatcher);
        lastNameInput.addTextChangedListener(registerWatcher);
        emailInput.addTextChangedListener(registerWatcher);
    }

    private final TextWatcher signInWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {
                signInButton.setClickable(true);
            }
            else {
                signInButton.setClickable(false);
            }
        }
    };

    private final TextWatcher registerWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {
                if ((maleRadio.isChecked() || femaleRadio.isChecked()) && registerRadioCheck(serverHostInput, serverPortInput,
                        userNameInput, passwordInput, firstNameInput, lastNameInput, emailInput)) {
                    registerButton.setClickable(true);
                }
            }
            else {
                registerButton.setClickable(false);
            }
        }
    };

}
