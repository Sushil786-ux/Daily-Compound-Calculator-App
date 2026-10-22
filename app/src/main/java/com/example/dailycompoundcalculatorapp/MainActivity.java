package com.example.dailycompoundcalculatorapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private EditText etPrincipal, etRate, etTime;
    private Button btnCalculate;
    private TextView tvTotalAmount, tvInterestEarned;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        etPrincipal = findViewById(R.id.etPrincipal);
        etRate = findViewById(R.id.etRate);
        etTime = findViewById(R.id.etTime);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
        tvInterestEarned = findViewById(R.id.tvInterestEarned);
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calculateCompoundInterest();
            }
        });

    }

    private void calculateCompoundInterest() {
        String strPrincipal = etPrincipal.getText().toString().trim();
        String strRate = etRate.getText().toString().trim();
        String strTime = etTime.getText().toString().trim();

        // Validate inputs
        if (strPrincipal.isEmpty() || strRate.isEmpty() || strTime.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double principal = Double.parseDouble(strPrincipal);
            double annualRate = Double.parseDouble(strRate) / 100.0;
            double timeYears = Double.parseDouble(strTime);

            // Daily Compounding Formula: A = P * (1 + r/365)^(365 * t)
            int compoundingDaysPerYear = 365;
            double base = 1.0 + (annualRate / compoundingDaysPerYear);
            double exponent = compoundingDaysPerYear * timeYears;
            double totalAmount = principal * Math.pow(base, exponent);
            double interestEarned = totalAmount - principal;

            // Format output to 2 decimal places
            DecimalFormat df = new DecimalFormat("#,##0.00");

            tvTotalAmount.setText("Total Amount: $" + df.format(totalAmount));
            tvInterestEarned.setText("Interest Earned: $" + df.format(interestEarned));

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show();
        }
    }
}