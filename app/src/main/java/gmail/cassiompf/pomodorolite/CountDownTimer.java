package gmail.cassiompf.pomodorolite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CountDownTimer extends AppCompatActivity {

    private MyCountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down_timer);
    }

    @Override
    protected void onResume() {
        super.onResume();

        TextView textView = (TextView) findViewById(R.id.textView2);

        Intent intent = getIntent();
        boolean intervalo = false;
        long tempo = 10 * 1000L;

        if (intent != null) {
            Bundle bundle = intent.getExtras();
            tempo = bundle.getInt("tempo") * 60 * 1000L;
            intervalo = bundle.getBoolean("intervalo");
        }

        if (intervalo == false) {
            TextView textView2 = findViewById(R.id.textView3);
            textView2.setVisibility(View.GONE);
        }

        timer = new MyCountDownTimer(this, textView, tempo, 1000L);
        timer.start();
    }

    public void clickCancel(View view) {
        if (timer != null) {
            timer.cancel();
        }
        finish();
    }
}