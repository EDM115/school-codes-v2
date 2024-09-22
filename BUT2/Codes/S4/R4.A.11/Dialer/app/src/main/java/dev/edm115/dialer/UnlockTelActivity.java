package dev.edm115.dialer;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UnlockTelActivity extends AppCompatActivity {
    private EditText phoneInput;
    private Button validateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unlock_tel);

        phoneInput = findViewById(R.id.phoneInput);
        validateButton = findViewById(R.id.validateButton);
        validateButton.setEnabled(false);

        phoneInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Nothing needed here
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateButton.setEnabled(!s.toString().trim().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Nothing needed here
            }
        });

        validateButton.setOnClickListener(v -> {
            if (phoneInput.getText().toString().trim().isEmpty()) {
                Toast.makeText(this, getString(R.string.veuillez_saisir_numero), Toast.LENGTH_SHORT).show();
            } else {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("phone", phoneInput.getText().toString().trim());
                setResult(RESULT_OK, resultIntent);
                Toast.makeText(this, getString(R.string.numero_valide), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
