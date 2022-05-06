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

import androidx.appcompat.app.AppCompatActivity;

import com.example.dailyreader.databinding.RegisterActivityBinding;

import java.util.ArrayList;
import java.util.List;


public class RegisterActivity extends AppCompatActivity {

    private RegisterActivityBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RegisterActivityBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

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
            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        });
        
        binding.submitButton.setOnClickListener(v ->{
            submitCheck();
        });


    }

    private void submitCheck() {
        Boolean validEmail = false;
        Editable emailInput = binding.emailInput.getText();
        // Empty email input
        if(emailInput.toString().isEmpty()){
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

        Boolean validPassword = false;
        Editable passwordInput = binding.passwordInput.getText();
        Editable repeatPasswordInput = binding.repeatPasswordInput.getText();
        // Empty password input
        if(passwordInput.toString().isEmpty()){
            binding.passwordError.setError(getResources().getString(R.string.password_empty_error));
            validPassword = false;
        }
        // Password length error
        else if(passwordInput.toString().length() < 8){
            binding.passwordError.setError(getResources().getString(R.string.password_length_error));
            validPassword = false;
        }
        // Whitespace error
        else if(passwordInput.toString().contains(" ")){
            binding.passwordError.setError(getResources().getString(R.string.password_space_error));
            validPassword = false;
        }
        else {
            binding.passwordError.setErrorEnabled(false);
            validPassword = true;
        }
        // Check whether two passwords are the same
        if(!repeatPasswordInput.toString().equals(passwordInput.toString())){
            binding.repeatPasswordError.setError(getResources().getString(R.string.repeat_password_error));
            validPassword = false;
        }
        else {
            binding.repeatPasswordError.setErrorEnabled(false);
            validPassword = true;
        }

        Boolean validUsername = false;
        Editable usernameInput = binding.usernameInput.getText();
        // Empty username input
        if(usernameInput.toString().isEmpty()){
            binding.usernameError.setError(getResources().getString(R.string.username_empty_error));
            validUsername = false;
        }
        else if(usernameInput.toString().contains(" ")){
            binding.usernameError.setError(getResources().getString(R.string.username_space_error));
            validUsername = false;
        }
        else{
            binding.usernameError.setErrorEnabled(false);
            validUsername = true;
        }

        Boolean validAddress = false;
        Editable addressInput = binding.addressInput.getText();
        // Empty address input
        if (addressInput.toString().isEmpty()){
            binding.addressError.setError(getResources().getString(R.string.address_error));
            validAddress = false;
        }
        else{
            binding.addressError.setErrorEnabled(false);
            validAddress = true;
        }

        Boolean validGender = false;
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
            new Thread(() ->{
                try{
                    Thread.sleep(5000);
                } catch (Exception e){
                    e.printStackTrace();
                }
                runOnUiThread(() -> {
                    Toast.makeText(RegisterActivity.this, "Congratulations! Register successfully!", Toast.LENGTH_LONG).show();
                    binding.progressBar.setVisibility(View.INVISIBLE);
                });
            }).start();


        }

    }
}