package com.example.loginfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private EditText signupEmail, signupPassword, signupPassword2;
    private Button signupButton;
    private TextView loginRedirectText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);

        auth = FirebaseAuth.getInstance();
        signupEmail= findViewById(R.id.signup_email);
        signupPassword= findViewById(R.id.signup_password);
        signupPassword2= findViewById(R.id.signup_password2);
        signupButton= findViewById(R.id.signup_button);
        loginRedirectText= findViewById(R.id.loginRedirectText);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                String user = signupEmail.getText().toString().trim();
                String user_password = signupPassword.getText().toString().trim();
                String user_password2 = signupPassword2.getText().toString().trim();

                if(user.isEmpty()){
                    signupEmail.setError("Email is required");
                    signupEmail.requestFocus();
                }
                else if(user_password.isEmpty()){
                    signupPassword.setError("Password is required");
                    signupPassword.requestFocus();
                }
                else if(user_password2.isEmpty()){
                    signupPassword2.setError("Password confirmation is required");
                    signupPassword2.requestFocus();
                }
                if(user_password.length() < 5){
                    signupPassword.setError("Password must be at least 5 characters long");
                    signupPassword.requestFocus();
                }
                else if(!user_password.matches(".*\\d.*")){
                    signupPassword.setError("Password must contain a number");
                    signupPassword.requestFocus();
                }
                else if(!user_password.matches(".*[A-Z].*")){
                    signupPassword.setError("Password must contain an uppercase letter");
                    signupPassword.requestFocus();
                }
                else if(!user_password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")){
                    signupPassword.setError("Password must contain a special character");
                    signupPassword.requestFocus();
                }
                else if(!user_password.equals(user_password2)){
                    signupPassword2.setError("Passwords do not match");
                    signupPassword2.requestFocus();
                }
                else{
                    auth.createUserWithEmailAndPassword(user, user_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                //redirect to user profile
                                Toast.makeText(SignUpActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            }
                            else{
                                //display error message
                                Toast.makeText(SignUpActivity.this, "Sign Up Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }
}