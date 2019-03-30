package com.example.travis.familymapclient;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Person;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travis.familymapclient.Model.Event;
import com.example.travis.familymapclient.Result.LoginResult;
import com.example.travis.familymapclient.Result.RegisterResult;
import com.example.travis.familymapclient.UserFamilyData.PersonFamily;
import com.google.android.gms.maps.MapFragment;

import java.io.Serializable;
import java.util.ArrayList;

public class LoginFragment extends Fragment {
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

    private LoginResult result;
    private RegisterResult registerResult;
    private PersonFamily userFamily;
    private ArrayList<Event> allEvents;
    private DataCache dataCache;

    public void setAllEvents(ArrayList<Event> allEvents) {
        this.allEvents = allEvents;
    }

    public ArrayList<Event> getAllEvents() {

        return allEvents;
    }

    public PersonFamily getUserFamily() {
        return userFamily;
    }

    public void setUserFamily(PersonFamily userFamily) {
        this.userFamily = userFamily;
    }

    public void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_login_fragment, container, false);
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            dataCache = DataCache.getInstance();
            serverHostInput = view.findViewById(R.id.serverHostInput);
            serverPortInput = view.findViewById(R.id.serverPortInput);
            userNameInput = view.findViewById(R.id.userNameInput);
            passwordInput = view.findViewById(R.id.passwordInput);
            firstNameInput = view.findViewById(R.id.firstNameInput);
            lastNameInput = view.findViewById(R.id.lastNameInput);
            emailInput = view.findViewById(R.id.emailInput);
            radioGroup = view.findViewById(R.id.radioGroup2);
            maleRadio = view.findViewById(R.id.maleButton);
            femaleRadio = view.findViewById(R.id.radioButton2);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int buttonId = radioGroup.getCheckedRadioButtonId();
                    switch(buttonId) {
                        case R.id.maleButton:
                            if (registerRadioCheck(serverHostInput, serverPortInput, userNameInput, passwordInput,
                                    firstNameInput, lastNameInput, emailInput)) {
                                registerButton.setEnabled(true);
                            }
                            else {
                                registerButton.setEnabled(false);
                            }
                            break;
                        case R.id.radioButton2:
                            if (registerRadioCheck(serverHostInput, serverPortInput, userNameInput, passwordInput,
                                    firstNameInput, lastNameInput, emailInput)) {
                                registerButton.setEnabled(true);
                            }
                            else  {
                                registerButton.setEnabled(false);
                            }
                            break;
                    }
                }
            });

            serverHostInput.setText("10.0.0.196");
            serverPortInput.setText("8080");
            userNameInput.setText("traviswg");
            passwordInput.setText("twerk216");
            firstNameInput.setText("Travis");
            lastNameInput.setText("Graham");
            emailInput.setText("travisgra21@gmail.com");
            signInButton = view.findViewById(R.id.button);
            signInButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String serverHost = serverHostInput.getText().toString();
                    String serverPort = serverPortInput.getText().toString();
                    String userName = userNameInput.getText().toString();
                    String password = passwordInput.getText().toString();
                    MyAsyncTask myAsyncTask = new MyAsyncTask(serverHost, serverPort, userName, password, 1, LoginFragment.this);
                    myAsyncTask.execute();
                }
            });
            registerButton = view.findViewById(R.id.button2);
            registerButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String serverHost = serverHostInput.getText().toString();
                    String serverPort = serverPortInput.getText().toString();
                    String userName = userNameInput.getText().toString();
                    String password = passwordInput.getText().toString();
                    MyAsyncTask myAsyncTask = new MyAsyncTask(serverHost, serverPort, userName, password, 0, LoginFragment.this);
                    myAsyncTask.execute();
                }
            });
            addSignInListeners(serverHostInput,serverPortInput, userNameInput, passwordInput);
            addRegisterListeners(serverHostInput, serverPortInput, userNameInput, passwordInput, firstNameInput, lastNameInput, emailInput, radioGroup);
        }

    public void setResult(LoginResult result) {
        this.result = result;
        if (result.isSuccessful() == true) {
            getData(result);
        }
    }
    public void getData(LoginResult result) {
        String serverHost = serverHostInput.getText().toString();
        String serverPort = serverPortInput.getText().toString();
        String userName = userNameInput.getText().toString();
        String password = passwordInput.getText().toString();

        MyAsyncTask loginTask = new MyAsyncTask(serverHost, serverPort, userName, password,2,this);
        loginTask.setAuthToken(result.getAuthToken());
        loginTask.execute();

        MyAsyncTask eventTask = new MyAsyncTask(serverHost, serverPort, userName, password, 3, this);
        eventTask.setAuthToken(result.getAuthToken());
        eventTask.execute();
        //runMapFrag();
    }
    public void getData(RegisterResult result) {
        String serverHost = serverHostInput.getText().toString();
        String serverPort = serverPortInput.getText().toString();
        String userName = userNameInput.getText().toString();
        String password = passwordInput.getText().toString();

        MyAsyncTask loginTask = new MyAsyncTask(serverHost, serverPort, userName, password,2,this);
        loginTask.setAuthToken(result.getAuthToken());
        loginTask.execute();

        MyAsyncTask eventTask = new MyAsyncTask(serverHost, serverPort, userName, password, 3, this);
        eventTask.setAuthToken(result.getAuthToken());
        eventTask.setRegistering(true);
        eventTask.execute();
        //runMapFrag();
    }

    public void setDataCache() {
        while (allEvents == null || userFamily == null) {
            System.out.println("Data not ready yet");
        }
        dataCache.setEventData(allEvents);
        dataCache.setPeopleData(userFamily.getAllFamily());
    }

    public void runMapFrag() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.maplayout);
        if (fragment == null) {
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_frame_layout, new Map());
            ft.commit();
            ft.addToBackStack(null);
        }
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

    public boolean loginCheck() {
        if (serverPortInput.getText().length() > 0 && serverPortInput.getText().length() > 0 && userNameInput.getText().length() > 0 && passwordInput.getText().length() > 0) {
            return true;
        }
        else {
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

    public EditText getFirstNameInput() {
        return firstNameInput;
    }

    public EditText getLastNameInput() {
        return lastNameInput;
    }

    public EditText getEmailInput() {
        return emailInput;
    }

    public String getGenderSelected() {
        if (femaleRadio.isChecked()) {
            return "f";
        }
        else {
            return "m";
        }
    }

    public RegisterResult getRegisterResult() {
        return registerResult;
    }

    public void setRegisterResult(RegisterResult result) {
        this.registerResult = result;
        if (result.isSuccessful() == true) {
            getData(registerResult);
        }
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
            if (s.length() > 0 && !s.equals("")) {
                if (loginCheck()) {
                    signInButton.setEnabled(true);
                }
                else {
                    signInButton.setEnabled(false);
                }
            }
            else {
                signInButton.setEnabled(false);
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
            if (s.length() > 0 && !s.equals("")) {
                if ((maleRadio.isChecked() || femaleRadio.isChecked()) && registerRadioCheck(serverHostInput, serverPortInput,
                        userNameInput, passwordInput, firstNameInput, lastNameInput, emailInput)) {
                    registerButton.setEnabled(true);
                }
            }
            else {
                registerButton.setEnabled(false);
            }
        }

    };
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
