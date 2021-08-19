package internship.InternetStore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class SignupActivity extends AppCompatActivity {

    RadioGroup gender;
    Spinner citySpinner;

    ArrayList<String> cityArray;

    ImageView dobIv;

    Calendar calendar;

    EditText password,confirmPassword,name,email,contact,dob;
    ImageView hideIv,showIv,hideConfirmIv,showConfirmIv;

    Button login,signup;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    String sGender,sCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().setTitle("Signup");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.signup_name);
        email = findViewById(R.id.signup_email);
        contact = findViewById(R.id.signup_contact);

        login= findViewById(R.id.signup_login);
        signup = findViewById(R.id.signup_button);

        password = findViewById(R.id.signup_password);
        hideIv = findViewById(R.id.signup_password_hide_iv);
        showIv = findViewById(R.id.signup_password_show_iv);

        hideIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideIv.setVisibility(View.GONE);
                showIv.setVisibility(View.VISIBLE);
                password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        });

        showIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideIv.setVisibility(View.VISIBLE);
                showIv.setVisibility(View.GONE);
                password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        confirmPassword = findViewById(R.id.signup_confirm_password);
        hideConfirmIv = findViewById(R.id.signup_confirm_password_hide_iv);
        showConfirmIv = findViewById(R.id.signup_confirm_password_show_iv);

        hideConfirmIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideConfirmIv.setVisibility(View.GONE);
                showConfirmIv.setVisibility(View.VISIBLE);
                confirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
        });

        showConfirmIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideConfirmIv.setVisibility(View.VISIBLE);
                showConfirmIv.setVisibility(View.GONE);
                confirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        dobIv = findViewById(R.id.signup_dob_iv);
        dob = findViewById(R.id.signup_dob);

        calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener dateClick = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                dob.setText(dateFormat.format(calendar.getTime()));
            }
        };

        dobIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(SignupActivity.this,dateClick,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                datePickerDialog.show();
            }
        });

        citySpinner = findViewById(R.id.signup_city);

        cityArray = new ArrayList<>();
        cityArray.add("Ahmedabad");
        cityArray.add("Rajkot");
        cityArray.add("SURAT");
        cityArray.add("Test");
        cityArray.add("Vadodara");
        cityArray.add("Gandhinagar");

        cityArray.set(2,"Surat");
        cityArray.remove(3);

        ArrayAdapter adapter = new ArrayAdapter(SignupActivity.this, android.R.layout.simple_list_item_1,cityArray);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_checked);
        citySpinner.setAdapter(adapter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //cityArray[i];
                //new CommonMethod(SignupActivity.this,cityArray[i]);
                //String s = String.valueOf(adapterView.getItemAtPosition(i));
                sCity = cityArray.get(i);
                new CommonMethod(SignupActivity.this,sCity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        gender = findViewById(R.id.signup_gender);

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int id = gender.getCheckedRadioButtonId(); //R.id.signup_male
                RadioButton rb = findViewById(id);
                sGender = rb.getText().toString();
                new CommonMethod(SignupActivity.this,sGender);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().trim().equals("")){
                    name.setError("Name Required");
                }
                else if(email.getText().toString().trim().equals("")){
                    email.setError("Email Id Required");
                }
                else if(!email.getText().toString().matches(emailPattern)){
                    email.setError("Valid Email Id Required");
                }
                else if(contact.getText().toString().trim().equals("")){
                    contact.setError("Contact No. Required");
                }
                else if(contact.getText().toString().length()<10 || contact.getText().toString().length()>10){
                    contact.setError("Valid Contact No. Required");
                }
                else if(password.getText().toString().trim().equals("")){
                    password.setError("Password Required");
                }
                else if(confirmPassword.getText().toString().trim().equals("")){
                    confirmPassword.setError("Confirm Password Required");
                }
                else if(!confirmPassword.getText().toString().matches(password.getText().toString())){
                    confirmPassword.setError("Password Does Not Match");
                }
                else if(dob.getText().toString().trim().equals("")){
                    dob.setError("Date Of Birth Required");
                }
                else if(gender.getCheckedRadioButtonId()==-1){
                    new CommonMethod(SignupActivity.this,"Please Select Gender");
                }

            }
        });

        /*male = findViewById(R.id.signup_male);
        female = findViewById(R.id.signup_female);
        transgender = findViewById(R.id.signup_transgender);

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(SignupActivity.this,male.getText().toString());
            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(SignupActivity.this,female.getText().toString());
            }
        });

        transgender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CommonMethod(SignupActivity.this,transgender.getText().toString());
            }
        });*/

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private class signupData extends AsyncTask<String,String,String> {

        ProgressDialog pd;

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(SignupActivity.this);
            pd.setMessage("Please Wait...");
            pd.setCancelable(false);
            pd.show();
        }




    }
}