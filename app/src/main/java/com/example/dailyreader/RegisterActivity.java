package com.example.dailyreader;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dailyreader.databinding.RegisterActivityBinding;
import com.example.dailyreader.entity.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class RegisterActivity extends AppCompatActivity {

    private RegisterActivityBinding binding;
    private FirebaseAuth mAuth;
    private DatabaseReference reference;
    private Button dateButton;
    private DatePickerDialog datePickerDialog;
    private String date;
    private String todayDate;
    private Date datePicker;
    private Date currentDate;


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
        });

        initDatePicker();
        dateButton = findViewById(R.id.date_picker);
        dateButton.setText(todayDate());


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
        // Check uppercase, lowercase letters and number
        boolean containNum = false;
        boolean containUp = false;
        boolean containLow = false;
        for(int i = 0; i < passwordInput.length(); i++){
            char character = passwordInput.charAt(i);
            if(Character.isDigit(character)){containNum = true;}
            if(Character.isUpperCase(character)){containUp = true;}
            if(Character.isLowerCase(character)){containLow = true;}
        }
        // Empty password input
        if(passwordInput.isEmpty()){
            binding.passwordError.setError(getResources().getString(R.string.password_empty_error));
            validPassword = false;
        }
        // Password length error
        else if(passwordInput.length() < 9){
            binding.passwordError.setError(getResources().getString(R.string.password_length_error));
            validPassword = false;
        }
        // Upper and lowercase letters and number
        else if(containLow == false){
            binding.passwordError.setError(getResources().getString(R.string.password_lower_error));
            validPassword = false;
        }
        else if(containUp == false){
            binding.passwordError.setError(getResources().getString(R.string.password_upper_error));
            validPassword = false;
        }
        else if(containNum == false){
            binding.passwordError.setError(getResources().getString(R.string.password_num_error));
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

        boolean validBirthday = false;
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM yyyy");
        try {
            datePicker = sdf.parse(dateButton.getText().toString());
            currentDate = sdf.parse(todayDate);
        }catch(ParseException e){
            e.printStackTrace();
        }
        if (dateButton.getText().toString().equals(todayDate)){
            binding.datePickerError.setText(getResources().getString(R.string.birthday_empty_error));
            binding.datePickerError.setVisibility(View.VISIBLE);
            validBirthday = false;
        }else if (datePicker.after(currentDate)){
            binding.datePickerError.setText(getResources().getString(R.string.birthday_after_error));
            binding.datePickerError.setVisibility(View.VISIBLE);
            validBirthday = false;
        }else{
            binding.datePickerError.setVisibility((View.INVISIBLE));
            validBirthday = true;
        }

        if (validEmail && validBirthday && validPassword && validUsername && validGender){
            binding.progressBar.setVisibility(View.VISIBLE);
            User user_info = new User(emailInput, usernameInput, genderInput, dateButton.getText().toString());
            mAuth.createUserWithEmailAndPassword(emailInput, passwordInput)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                String userId = user.getUid();
                                // Sign in success, update users' information to firebase
                                reference = FirebaseDatabase.getInstance().getReference("User");
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

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month = month + 1;
                date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar calendar = Calendar.getInstance() ;
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month ,day);
    }

    private String todayDate(){
        Calendar calendar = Calendar.getInstance() ;
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        todayDate = day+ " "+ getMonthFormat(month) +" "+ year;
        return makeDateString(day,month,year);
    }

    private String makeDateString(int day, int month, int year){
        return day+ " "+ getMonthFormat(month) +" "+ year;
    }

    private String getMonthFormat(int month){
        if (month == 1)
            return "Jan";
        if (month == 2)
            return "Feb";
        if (month == 3)
            return "Mar";
        if (month == 4)
            return "Apr";
        if (month == 5)
            return "May";
        if (month == 6)
            return "Jun";
        if (month == 7)
            return "Jul";
        if (month == 8)
            return "Aug";
        if (month == 9)
            return "Sep";
        if (month == 10)
            return "Oct";
        if (month == 11)
            return "Nov";
        if (month == 12)
            return "Dec";

        return "Jan";
    }

    public void openDatePicker(View view){
        datePickerDialog.show();
    }
}