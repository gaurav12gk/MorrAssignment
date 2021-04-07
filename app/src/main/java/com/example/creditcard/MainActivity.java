package com.example.creditcard;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {
    TextInputEditText expiry, security;
    TextInputLayout securitybox, cardlayout, firstnamelayout, lastnamelayout, expirylayout;
    EditText cardnumber, firstname, lastname;

    Button submit;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        expiry = findViewById(R.id.expiry);
        cardnumber = findViewById(R.id.cardnumbertext);
        security = findViewById(R.id.security);
        cardlayout = findViewById(R.id.cardnumber);
        securitybox = findViewById(R.id.securitybox);
        firstname = findViewById(R.id.firstname);
        expirylayout = findViewById(R.id.expirylayout);
        lastname = findViewById(R.id.lastname);
        firstnamelayout = findViewById(R.id.firstnamelayout);
        lastnamelayout = findViewById(R.id.lastnamelayout);

        submit = findViewById(R.id.submit);
        submit.setOnClickListener(v -> {
          if(checkcardumber()&&checkcvv()&&checkfirstname()&&checklastname()&&checkexpiry())
          {
              Toast.makeText(this, "fdf", Toast.LENGTH_SHORT).show();
              MaterialAlertDialogBuilder builder=new MaterialAlertDialogBuilder(MainActivity.this)
                      .setTitle("Payment Successfull").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              // DO the work with OK button
                          }
                      });
              builder.show();

          }
          
        });

//Adding Text change listner to add  / in MM/YY
        expiry.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int len = s.toString().length();

                if (before == 0 && len == 2)
                    expiry.append("/");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //funcion to check for expiry date
    private boolean checkexpiry() {
        String exp = expiry.getText().toString();

        if (exp.equals("") ||(exp.length()<5)|| (exp.charAt(0) == '0' && exp.charAt(1) == '0') || (exp.charAt(0) > '1') || (exp.charAt(0) == '1' && exp.charAt(1) > '2') || (exp.charAt(3) < '2')) {
            expirylayout.setError("Invalid Expiry Date");
            expirylayout.setErrorIconDrawable(0);
            return false;
        } else {
            expirylayout.setError(null);
            return true;
        }
    }

    //function to check the first name validity
    private boolean checkfirstname() {
        String first = firstname.getText().toString();
        if (first.equals("")) {
            firstnamelayout.setError("Invalid Name");
            firstnamelayout.setErrorIconDrawable(0);
            return false;
        } else {
            firstnamelayout.setError(null);
            return true;
        }

    }

    //function to check last name valididyt
    private boolean checklastname() {

        String last = lastname.getText().toString();
        if (last.equals("")) {
            lastnamelayout.setError("Invalid Name");
            lastnamelayout.setErrorIconDrawable(0);
            return false;
        } else {
            lastnamelayout.setError(null);
            return true;
        }
    }

//function to check valid card number
    private boolean checkcardumber() {
        String a = cardnumber.getText().toString();
        if (checkLuhn(a) && a.length() >= 13 && a != null) {
            if (checkdifferentcards()) {
                cardlayout.setError(null);
                return true;
            }
        } else {
            cardlayout.setError("Invalid Card Number");
            cardlayout.setErrorIconDrawable(0);
            return false;
        }
        return true;
    }
//function to check different VISA, Mastercard, Discover,American express card
    private boolean checkdifferentcards() {
        String a = cardnumber.getText().toString();

        if (a.charAt(0) == '4' || a.charAt(0) == '5' || a.charAt(0) == '6' || (a.charAt(0) == '3' && a.charAt(1) == '7'))
            return true;
        else return false;
    }

//Function to check whether CVV is not less than 3 cvv can maximum goes to 4
    private boolean checkcvv() {
        String a = security.getText().toString();
        if (a.length() < 3) {
            securitybox.setError("Invalid CVV");
            securitybox.setErrorIconDrawable(0);
            return false;
        } else {
            securitybox.setError(null);
            return true;
        }
    }
//function for checking card number through luhn algo
    private boolean checkLuhn(String str) {

        int[] ints = new int[str.length()];
        for (int i = 0; i < str.length(); i++) {
            ints[i] = Integer.parseInt(str.substring(i, i + 1));
        }
        for (int i = ints.length - 2; i >= 0; i = i - 2) {
            int j = ints[i];
            j = j * 2;
            if (j > 9) {
                j = j % 10 + 1;
            }
            ints[i] = j;
        }
        int sum = 0;
        for (int i = 0; i < ints.length; i++) {
            sum += ints[i];
        }
        if (sum % 10 == 0) {
            Log.d("luh", "valid");
            return true;
        } else {
            Log.d("luht", "invalid");
            return false;
        }
    }

}