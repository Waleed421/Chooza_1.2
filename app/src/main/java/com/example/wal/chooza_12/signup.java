package com.example.wal.chooza_12;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.List;

public class signup extends AppCompatActivity {
    private MyDBHandler databaseHandler;
    private List<Student> studentList;
    Button btnSignUp, btnClear;
    AutoCompleteTextView txtmobileno, txtemail, txtname, txtcity, txtpassword, txtusername, txtdob;
    private RadioButton radioM, radioF;
    private RadioGroup radioGroupGender;
    private CoordinatorLayout coordinatorLayout;
    private Vibrator vib;
    public String name, username, password, city, dob, mobileno, email, gender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        //getWindow().getDecorView().setBackgroundColor(Color.YELLOW);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
        databaseHandler = new MyDBHandler(signup.this);
        txtemail = (AutoCompleteTextView) findViewById(R.id.txtemail);
        txtmobileno = (AutoCompleteTextView) findViewById(R.id.txtmobileno);
        txtname = (AutoCompleteTextView) findViewById(R.id.txtname);
        txtusername = (AutoCompleteTextView) findViewById(R.id.txtusername);
        txtpassword = (AutoCompleteTextView) findViewById(R.id.txtpassword);
        txtemail = (AutoCompleteTextView) findViewById(R.id.txtemail);
        txtdob = (AutoCompleteTextView) findViewById(R.id.txtdob);
        txtcity = (AutoCompleteTextView) findViewById(R.id.txtcity);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnClear = (Button) findViewById(R.id.btnClear);
        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        radioGroupGender = (RadioGroup) findViewById(R.id.radioGrpGender);
        radioM = (RadioButton) findViewById(R.id.radioM);
        radioF = (RadioButton) findViewById(R.id.radioF);
        name = txtname.getText().toString();
        txtname.setValidator(new AutoCompleteTextView.Validator() {
            @Override
            public boolean isValid(CharSequence text) {
                if (name.trim().equals("")) {
                    txtname.setError("Please enter your name");
                    vib.vibrate(120);
                    return true;
                }
                return false;
            }

            @Override
            public CharSequence fixText(CharSequence invalidText) {
                return null;
            }
        });
        /*{

                                             @Override
                                             public void onFocusChange(View v, boolean hasFocus) {
                                                 if (name.trim().equals("")) {
                                                     txtname.setError("Please enter your name");
                                                     vib.vibrate(120);
                                                     txtname.setFocusable(true);
                                                 }
                                             }
                                         }

        );*/
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = txtemail.getText().toString();
                mobileno = txtmobileno.getText().toString();
                name = txtname.getText().toString();
                username= txtusername.getText().toString();
                password= txtpassword.getText().toString();
                city= txtcity.getText().toString();
                dob= txtdob.getText().toString();
                int selectedId = radioGroupGender.getCheckedRadioButtonId();
                if (name.trim().equals("")) {
                    txtname.setError("Please enter your name");
                    vib.vibrate(120);
                }
                else if (username.trim().equals("")) {
                    txtusername.setError("Please enter username");
                    vib.vibrate(120);
                }
                else if (password.trim().equals("")) {
                    txtpassword.setError("Please enter password");
                    vib.vibrate(120);
                }
                else if (city.trim().equals("")) {
                    txtcity.setError("Please enter your city");
                    vib.vibrate(120);
                }
                else if (mobileno.trim().equals("")) {
                    txtmobileno.setError("Please enter your mobile number");
                    vib.vibrate(120);
                }
                else if (email.trim().equals("")){
                    txtemail.setError("Please enter email Address");
                vib.vibrate(120);
                }
                else if (!email.contains("@") || !email.contains(".")){
                    txtemail.setError("Please enter a valid email Address");
                    vib.vibrate(120);
                }
                else if (mobileno.trim().length() < 10){
                    txtmobileno.setError("Not a valid mobile no");
                    vib.vibrate(120);
                }


                else {
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Success", Snackbar.LENGTH_LONG)
                            .setAction("HIDE", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                }
                            });

                    snackbar.show();
                    if(selectedId == radioM.getId()) {
                        gender= radioM.getText().toString();

                    } else if(selectedId == radioF.getId()) {

                        gender= radioF.getText().toString();

                    }
                    new SignupStudent().execute();
                    //Intent i = new Intent(signup.this, MainActivity.class);
                    //startActivity(i);
                }


            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtemail.setText("");
                txtmobileno.setText("");
                txtname.setText("");
                txtdob.setText("");
                txtusername.setText("");
                txtpassword.setText("");
                txtcity.setText("");
                txtname.setError(null);
                txtemail.setError(null);
                txtmobileno.setError(null);
                txtdob.setError(null);
                txtcity.setError(null);
                txtpassword.setError(null);
                txtusername.setError(null);
            }
        });


    }
    private class SignupStudent extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            // Creating service handler class instance
            ServiceHandler sh = new ServiceHandler();
            String url="http://192.168.100.37/chooza1/API/StudentSignup?";
            String urldata= url+"name="+name+"&username="+username+"&password="+password+"&gender="+gender+"&city="+city+"&phone="+mobileno+"&dob="+dob+"&email="+email;
            String jsonStr = (sh.makeServiceCall(urldata, ServiceHandler.GET));
            databaseHandler.addStudent(new Student(name, username, password));
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Intent i = new Intent(signup.this, MainActivity.class);
            startActivity(i);
            super.onPostExecute(aVoid);
        }
    }
    public void login(View view)
    {
        TextView login= (TextView) findViewById(R.id.link_login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), login.class);
                //intent.putExtra("Pname", pname);
                startActivity(intent);
            }
        });
    }

}
