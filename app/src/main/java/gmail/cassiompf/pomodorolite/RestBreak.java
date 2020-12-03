package gmail.cassiompf.pomodorolite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import gmail.cassiompf.pomodorolite.enums.Task;

public class RestBreak extends AppCompatActivity {

    private String nextTask = Task.POMODORO.getValor();
    private Boolean isIntervalo = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_break);

        Intent intent = getIntent();

        if (intent != null) {
            Bundle param = intent.getExtras();
            Integer amountPomodoros = param.getInt("amountPomodoros");
            String currentTask = param.getString("currentTask");
            isIntervalo = param.getBoolean("intervalo");

            TextView textView = findViewById(R.id.rb_textView);
            TextView textView2 = findViewById(R.id.rb_textView2);

            textView.setText("Iniciar Pomodoro");
            textView2.setText("Você deseja iniciar outro pomodoro?");

            if (isIntervalo) {
                if (currentTask.equals(Task.POMODORO.getValor())) {
                    textView.setText("Tempo para Descanso");
                    if (amountPomodoros >= 4) {
                        textView2.setText("Você deseja iniciar o tempo de descanso? (10 Minutos)");
                    } else {
                        textView2.setText("Você deseja iniciar o tempo de descanso? (5 Minutos)");
                    }
                    nextTask = Task.DESCANSO.getValor();
                }
            }
        }
    }

    public void clickConfirm(View view) {
        RadioGroup radioGroup = findViewById(R.id.rb_radioGroup);
        int result = RESULT_CANCELED;

        if (radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Favor, selecione uma das duas opções.", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton radioButton = radioGroup.findViewById(radioGroup.getCheckedRadioButtonId());

        if (radioButton.equals(findViewById(R.id.rb_radioButton))) {
            result = RESULT_OK;
        }

        Bundle bundle = new Bundle();
        bundle.putString("nextTask", nextTask);

        Intent intent = new Intent();
        intent.putExtras(bundle);

        setResult(result, intent);
        finish();
    }
}