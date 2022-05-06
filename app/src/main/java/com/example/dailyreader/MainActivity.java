package com.example.dailyreader;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dailyreader.databinding.ActivityMainBinding;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        authStateListener = firebaseAuth -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null){
                Toast.makeText(this, "Welcome!", Toast.LENGTH_LONG).show();
                startActivity(new Intent(this, HomeActivity.class));
            }
        };

        binding.signUp.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        binding.loginButton.setOnClickListener(v -> {
            if (loginCheck()) {
                binding.progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(binding.emailInput.getText().toString(), binding.passwordInput.getText().toString())
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                binding.progressBar.setVisibility(View.INVISIBLE);
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(this, "Login successfully!",
                                        Toast.LENGTH_LONG).show();
                                startActivity(new Intent(this, HomeActivity.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(this, "                       Login failed." +
                                                "\nPlease check your Email and password!",
                                        Toast.LENGTH_LONG).show();
                                binding.progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
            }
        });
    }

    private boolean loginCheck() {
        Boolean validEmail = false;
        Boolean validPassword = false;
        Editable emailInput = binding.emailInput.getText();
        Editable passwordInput = binding.passwordInput.getText();
        if (emailInput.toString().isEmpty()) {
            binding.emailError.setError(getResources().getString(R.string.email_error));
            validEmail = false;
        } else {
            binding.emailError.setErrorEnabled(false);
            validEmail = true;
        }
        if (passwordInput.toString().isEmpty()) {
            binding.passwordError.setError(getResources().getString(R.string.password_empty_error));
            validPassword = false;
        } else {
            binding.passwordError.setErrorEnabled(false);
            validPassword = true;
        }
        if (validEmail && validPassword) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    public void onStop(){
        super.onStop();
        if(authStateListener != null){
            mAuth.removeAuthStateListener(authStateListener);
        }
    }
}
