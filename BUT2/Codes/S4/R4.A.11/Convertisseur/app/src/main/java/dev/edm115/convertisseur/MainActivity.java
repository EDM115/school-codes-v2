package dev.edm115.convertisseur;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {

    private EditText celsiusInput;
    private EditText fahrenheitInput;
    private double lastCelsius = 20.00;
    private double lastFahrenheit = 68.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextInputLayout celsiusInputLayout = findViewById(R.id.celsiusInput);
        TextInputLayout fahrenheitInputLayout = findViewById(R.id.fahrenheitInput);
        celsiusInput = celsiusInputLayout.getEditText();
        fahrenheitInput = fahrenheitInputLayout.getEditText();
        Button convertButton = findViewById(R.id.button);

        celsiusInput.setText(getString(R.string.default_celsius));
        fahrenheitInput.setText(getString(R.string.default_fahrenheit));

        convertButton.setOnClickListener(v -> {
            double currentCelsius;
            double currentFahrenheit;

            try {
                currentCelsius = Double.parseDouble(celsiusInput.getText().toString().replace(',', '.'));
                currentFahrenheit = Double.parseDouble(fahrenheitInput.getText().toString().replace(',', '.'));

                if (Math.abs(currentCelsius - lastCelsius) > 0.01 && Math.abs(currentFahrenheit - lastFahrenheit) <= 0.01) {
                    // Celsius changed, update Fahrenheit
                    double newFahrenheit = convertToFahrenheit(currentCelsius);
                    fahrenheitInput.setText(String.format(Locale.US, "%.2f", newFahrenheit));
                    lastFahrenheit = newFahrenheit;
                } else if (Math.abs(currentFahrenheit - lastFahrenheit) > 0.01) {
                    // Fahrenheit changed, update Celsius
                    double newCelsius = convertToCelsius(currentFahrenheit);
                    celsiusInput.setText(String.format(Locale.US, "%.2f", newCelsius));
                    lastCelsius = newCelsius;
                } else {
                    // Both changed or no significant change detected, default to Celsius to Fahrenheit conversion
                    double newFahrenheit = convertToFahrenheit(currentCelsius);
                    fahrenheitInput.setText(String.format(Locale.US, "%.2f", newFahrenheit));
                    lastFahrenheit = newFahrenheit;
                }

                lastCelsius = currentCelsius;
                lastFahrenheit = currentFahrenheit;
            } catch (NumberFormatException e) {
                System.out.println("NumberFormatException : " + e.getMessage());
            }
        });
    }

    public double convertToCelsius(double fahrenheit) {
        return Double.parseDouble(String.format(Locale.US, "%.2f", (fahrenheit - 32) * 5 / 9));
    }

    public double convertToFahrenheit(double celsius) {
        return Double.parseDouble(String.format(Locale.US, "%.2f", celsius * 9 / 5 + 32));
    }
}
