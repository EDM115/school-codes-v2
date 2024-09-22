package dev.edm115.dialer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText usernameInput;
    private EditText passwordInput;
    private Button loginButton;
    private Button callButton;
    private final String VALID_USERNAME = "admin";
    private final String VALID_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginButton = findViewById(R.id.loginButton);
        callButton = findViewById(R.id.callButton);

        loginButton.setOnClickListener(v -> {
            if (usernameInput.getText().toString().equals(VALID_USERNAME) &&
                    passwordInput.getText().toString().equals(VALID_PASSWORD)) {
                Intent intent = new Intent(MainActivity.this, UnlockTelActivity.class);
                startActivityForResult(intent, 1);
                Toast.makeText(this, getString(R.string.connexion_reussie), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.identifiants_invalides), Toast.LENGTH_SHORT).show();
            }
        });

        callButton.setOnClickListener(v -> {
            if (callButton.isEnabled()) {
                Toast.makeText(this, getString(R.string.appel_en_cours), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.veuillez_vous_connecter), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            callButton.setEnabled(true);
            String phoneNumber = data.getStringExtra("phone");
            callButton.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(intent);
                Toast.makeText(this, getString(R.string.appel_reussi), Toast.LENGTH_SHORT).show();
            });
        }
    }
}
