package com.example.wal.chooza_12;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class signup extends AppCompatActivity {

    Button btnSignUp, btnClear;
    AutoCompleteTextView txtmobileno, txtemail, txtname;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        //getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
        txtemail = (AutoCompleteTextView) findViewById(R.id.txtemail);
        txtmobileno = (AutoCompleteTextView) findViewById(R.id.txtmobileno);
        txtname = (AutoCompleteTextView) findViewById(R.id.txtname);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnClear = (Button) findViewById(R.id.btnClear);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtemail.getText().toString();
                String mobileno = txtmobileno.getText().toString();
                String name = txtemail.getText().toString();
                if (name.trim().equals(""))
                    txtname.setError("Please enter your name");
                else if (email.trim().equals(""))
                    txtemail.setError("Please enter email Address");
                else if (!email.contains("@") || !email.contains("."))
                    txtemail.setError("Please enter a valid email Address");

                else if (mobileno.trim().length() < 10)
                    txtmobileno.setError("Not a valid mobile no"); // I'm finding solution to validate mobile no
                else {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Success", Snackbar.LENGTH_LONG)
                            .setAction("HIDE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            });

                    snackbar.show();
                }


            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtemail.setText("");
                txtmobileno.setText("");
                txtname.setText("");
                txtname.setError(null);
                txtemail.setError(null);
                txtmobileno.setError(null);

            }
        });


    }


}
