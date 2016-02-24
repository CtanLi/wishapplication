package com.example.ruanlopes.wishapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    private EditText mNameText;
    private EditText mEmailText;
    private EditText mPasswordText;
    private EditText mCPasswordText;
    private Button mSignupButton;
    private TextView mLoginLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mNameText = (EditText) findViewById(R.id.input_name);
        mEmailText = (EditText) findViewById(R.id.input_email);
        mPasswordText = (EditText) findViewById(R.id.input_password);
        mCPasswordText = (EditText) findViewById(R.id.input_cpassword);

        mSignupButton = (Button) findViewById((R.id.btn_signup));
        mLoginLink = (TextView) findViewById(R.id.link_login);

        mSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        //Todo: Fix login link

        mLoginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);

                // Finish the registration screen and return to the Login activity
                //finish();
            }
        });

    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        mSignupButton.setEnabled(false);
        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = mNameText.getText().toString();
        final String email = mEmailText.getText().toString();
        final String password = mPasswordText.getText().toString();

        // TODO: Implement your own signup logic here.
        final Firebase ref = new Firebase("https://blazing-inferno-9139.firebaseio.com");
        ref.createUser(email, password, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                System.out.println("Successfully created user account with uid: " + result.get("uid"));
                onSignupSuccess();
            }
            @Override
            public void onError(FirebaseError firebaseError) {
                System.out.println("On Error authenticating newly created user. This could be an issue. ");
                System.out.println(firebaseError.getMessage());
                Toast.makeText(SignupActivity.this, firebaseError.toString(), Toast.LENGTH_LONG).show();
                return;

            }

        });



    }

//        new Handler.postDelayed(
//                new Runnable() {
//                    public void run() {
//                        // On complete call either onSignupSuccess or onSignupFailed
//                        // depending on success
//                        onSignupSuccess();
//                        // onSignupFailed();
//                        progressDialog.dismiss();
//                    }
//                }, 3000);
//    }


    public void onSignupSuccess() {
        mSignupButton.setEnabled(true);
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
        mSignupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = mNameText.getText().toString();
        String email = mEmailText.getText().toString();
        String password = mPasswordText.getText().toString();
        String cpassword = mCPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            mNameText.setError("at least 3 characters");
            valid = false;
        } else {
            mNameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmailText.setError("enter a valid email address");
            valid = false;
        } else {
            mEmailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            mPasswordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            mPasswordText.setError(null);
        }

        if (cpassword != password || cpassword.isEmpty() || cpassword.length() < 4 || cpassword.length() > 10) {
            mPasswordText.setError("Password doesnt match");
            valid = false;
        } else {
            mPasswordText.setError(null);
        }

        return valid;
    }
}

