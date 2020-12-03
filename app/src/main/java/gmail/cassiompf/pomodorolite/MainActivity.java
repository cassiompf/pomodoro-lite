package gmail.cassiompf.pomodorolite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import gmail.cassiompf.pomodorolite.objects.MinMaxFilter;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNumberSigned;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextNumberSigned = findViewById(R.id.main_editTextNumberSigned);
        editTextNumberSigned.setFilters(new InputFilter[]{new MinMaxFilter("1", "60", editTextNumberSigned)});
    }

    public void sendValues(View view) {

        Switch intervalIsEnable = findViewById(R.id.main_switch);

        if (editTextNumberSigned.getText().length() == 0) {
            editTextNumberSigned.setError("Campo vazio");
            return;
        }

        if (Integer.valueOf(editTextNumberSigned.getText().toString()) == 0) {
            editTextNumberSigned.setError("Insira um valor maior que 0");
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