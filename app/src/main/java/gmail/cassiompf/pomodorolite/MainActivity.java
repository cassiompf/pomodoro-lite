package gmail.cassiompf.pomodorolite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void enviarDados(View view) {

        EditText editTextNumberSigned = findViewById(R.id.editTextNumberSigned);
        Switch intervalIsEnable = findViewById(R.id.switch1);

        if (editTextNumberSigned.getText().length() == 0) {
            editTextNumberSigned.setError("Campo vazio");
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putInt("tempo", Integer.valueOf(editTextNumberSigned.getText().toString()));
        bundle.putBoolean("intervalo", intervalIsEnable.isChecked());

        Intent intent = new Intent(this, CountDownTimer.class);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}