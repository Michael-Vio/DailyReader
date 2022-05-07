package com.example.dailyreader;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dailyreader.databinding.RegisterActivityBinding;
import com.example.dailyreader.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {

    private RegisterActivityBinding binding;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RegisterActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        mAuth = FirebaseAuth.getInstance();

        List<String> list = new ArrayList<String>();
        list.add("Male");
        list.add("Female");
        list.add("Gender");
        final int listsize = list.size() -1;
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list){
            @Override
            public int getCount(){
                return(listsize);
            }
        };
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        binding.genderInput.setAdapter(spinnerAdapter);
        binding.genderInput.setSelection(listsize);

        binding.backButton.setOnClickListener(v ->{
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        });
        
        binding.submitButton.setOnClickListener(v ->{
            submitCheck();
            User user_info = new User(binding.emailInput.getText().toString(), binding.usernameInput.getText().toString(),
                    binding.genderInput.getSelectedItem().toString(), binding.addressInput.getText().toString());
            Map<String, User> userMap = new HashMap<>();
            userMap.put(binding.emailInput.getText().toString(), user_info);
        });


    }

    private void submitCheck() {
        boolean validEmail = false;
        String emailInput = binding.emailInput.getText().toString();
        // Empty email input
        if(emailInput.isEmpty()){
            binding.emailError.setError(getResources().getString(R.string.email_error));
            validEmail = false;
        }
        // Wrong format email input
        else if(!Patterns.EMAIL_ADDRESS.matcher(emailInput.toString()).matches()){
            binding.emailError.setError(getResources().getString(R.string.email_error));
            validEmail = false;
        }
        // Correct email input
        else{
            binding.emailError.setErrorEnabled(false);
            validEmail = true;
        }

        boolean validPassword = false;
        String passwordInput = binding.passwordInput.getText().toString();
        String repeatPasswordInput = binding.repeatPasswordInput.getText().toString();
        // Empty password input
        if(passwordInput.isEmpty()){
            binding.passwordError.setError(getResources().getString(R.string.password_empty_error));
            validPassword = false;
        }
        // Password length error
        else if(passwordInput.length() < 8){
            binding.passwordError.setError(getResources().getString(R.string.password_length_error));
            validPassword = false;
        }
        // Whitespace error
        else if(passwordInput.contains(" ")){
            binding.passwordError.setError(getResources().getString(R.string.password_space_error));
            validPassword = false;
        }
        else {
            binding.passwordError.setErrorEnabled(false);
            validPassword = true;
        }
        // Check whether two passwords are the same
        if(!repeatPasswordInput.equals(passwordInput)){
            binding.repeatPasswordError.setError(getResources().getString(R.string.repeat_password_error));
            validPassword = false;
        }
        else {
            binding.repeatPasswordError.setErrorEnabled(false);
            validPassword = true;
        }

        boolean validUsername = false;
        String usernameInput = binding.usernameInput.getText().toString();
        // Empty username input
        if(usernameInput.isEmpty()){
            binding.usernameError.setError(getResources().getString(R.string.username_empty_error));
            validUsername = false;
        }
        else if(usernameInput.contains(" ")){
            binding.usernameError.setError(getResources().getString(R.string.username_space_error));
            validUsername = false;
        }
        else{
            binding.usernameError.setErrorEnabled(false);
            validUsername = true;
        }

        boolean validAddress = false;
        String addressInput = binding.addressInput.getText().toString();
        // Empty address input
        if (addressInput.isEmpty()){
            binding.addressError.setError(getResources().getString(R.string.address_error));
            validAddress = false;
        }
        else{
            binding.addressError.setErrorEnabled(false);
            validAddress = true;
        }

        boolean validGender = false;
        String genderInput = binding.genderInput.getSelectedItem().toString();
        // Check whether choose a gender
        if(genderInput.equals("Gender")){
            TextView errorText = (TextView) binding.genderInput.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);
            errorText.setText(getResources().getString(R.string.gender_error));
            validGender = false;
        }
        else{
            validGender = true;
        }

        if (validEmail && validAddress && validPassword && validUsername && validGender){
            binding.progressBar.setVisibility(View.VISIBLE);
            User user_info = new User(emailInput, usernameInput, genderInput, addressInput);
            mAuth.createUserWithEmailAndPassword(emailInput, passwordInput)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                String userId = user.getUid();
                                // Sign in success, update users' information to firebase
                                reference = FirebaseDatabase.getInstance().getReference("Users");
                                reference.child(userId).setValue(user_info);
                                // Sign in success, update UI with the signed-in user's information
                                binding.progressBar.setVisibility(View.INVISIBLE);
                                Toast.makeText(RegisterActivity.this, "Congratulations! Register successfully!", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegisterActivity.this, "                     Register failed." +
                                                "\nEmail might has been registered already.",
                                        Toast.LENGTH_LONG).show();
                                binding.progressBar.setVisibility(View.INVISIBLE);
                            }
                        }
                    });
        }

    }
}