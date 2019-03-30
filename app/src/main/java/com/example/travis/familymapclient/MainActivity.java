package com.example.travis.familymapclient;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener
{
    private final int REQ_CODE_LOGIN_FRAG = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = this.getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.loginFragLayout);
        if (fragment == null) {
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.main_frame_layout, new LoginFragment());
            ft.commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_CODE_LOGIN_FRAG && resultCode == RESULT_OK && data != null) {
            //switch to login fragment
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri){
        //you can leave it empty
    }
}
