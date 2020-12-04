package gmail.cassiompf.pomodorolite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import gmail.cassiompf.pomodorolite.enums.ActivityName;
import gmail.cassiompf.pomodorolite.enums.Task;
import gmail.cassiompf.pomodorolite.objects.MyCountDownTimer;

public class CountDownTimer extends AppCompatActivity {

    private MyCountDownTimer timer;
    private Integer pomodoros = 0;
    private Long timeConfigured = 3 * 1000L;
    private TextView textViewUpdate;
    private boolean intervalo = false;
    private boolean active = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down_timer);
        createNotificationChannel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (timer == null) {
            textViewUpdate = findViewById(R.id.cdt_textView2);

            Intent intent = getIntent();

            if (intent != null) {
                Bundle bundle = intent.getExtras();
                timeConfigured = bundle.getInt("tempo") * 60 * 1000L;
                intervalo = bundle.getBoolean("intervalo");

                if (intervalo == false) {
                    TextView textView2 = findViewById(R.id.cdt_textView3);
                    textView2.setVisibility(View.GONE);
                }
            }

            timer = new MyCountDownTimer(this, textViewUpdate, timeConfigured, 1000L, Task.POMODORO.getValor());
            timer.start();
        } else if (textViewUpdate.getText().equals("00:00")) {
            textViewUpdate.setText("");
            Bundle bundle = new Bundle();
            bundle.putInt("amountPomodoros", getPomodoros());
            bundle.putBoolean("intervalo", isIntervalo());
            bundle.putString("currentTask", timer.getCurrentTask());

            Intent intent = new Intent(this, RestBreak.class);
            intent.putExtras(bundle);

            startActivityForResult(intent, ActivityName.RESTBREAK.getValor());
        }
    }

    public void clickCancel(View view) {
        if (timer != null) {
            timer.cancel();
        }
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ActivityName.RESTBREAK.getValor()) {
            Log.i("Result", "" + resultCode);
            switch (resultCode) {
                case RESULT_OK:
                    Bundle bundle = data.getExtras();
                    String nextTask = bundle.getString("nextTask");
                    Log.i("NextTask", "" + nextTask);
                    if (nextTask.equals(Task.POMODORO.getValor())) {
                        timer = new MyCountDownTimer(this, textViewUpdate, timeConfigured, 1000L, Task.POMODORO.getValor());
                        timer.start();
                    } else if (nextTask.equals(Task.DESCANSO.getValor())) {
                        long time = 5 * 60 * 1000L;
                        if (pomodoros >= 4) {
                            time = 10 * 60 * 1000L;
                            pomodoros = 0;
                        }
//                        long time = 3 * 1000L;
                        timer = new MyCountDownTimer(this, textViewUpdate, time, 1000L, Task.DESCANSO.getValor());
                        timer.start();
                    }
                    break;
                default:
                    finish();
            }
        }
    }

    public void notificationPomodoroFinished() {

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "channel1");

        builder.setSmallIcon(R.drawable.ic_cronometro);
        builder.setContentTitle("Pomodoro");
        builder.setContentText("Um pomodoro foi finalizado!");
        builder.setStyle(new NotificationCompat.BigTextStyle()
                .bigText("Clique para iniciar o modo intervalo de descanso."));
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setCategory(NotificationCompat.CATEGORY_ALARM);
        builder.setDefaults(Notification.DEFAULT_LIGHTS);

        Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone ringtone = RingtoneManager.getRingtone(this, sound);
        ringtone.play();

        long[] pattern = {0, 1000};//Vibra durante 1000 milisegundos
        builder.setVibrate(pattern);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.name_notification);
            String description = getString(R.string.desc_notification);

            NotificationChannel channel = new NotificationChannel(
                    "channel1",
                    name,
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel.setDescription(description);
            channel.enableVibration(true);
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            Uri sound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            channel.setSound(sound, attributes);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public Integer getPomodoros() {
        return pomodoros;
    }

    public void addPomodoros() {
        pomodoros += 1;

        if (pomodoros == 5) {
            pomodoros = 1;
        }
    }

    public boolean isIntervalo() {
        return intervalo;
    }

    @Override
    protected void onStart() {
        super.onStart();
        active = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        active = false;
    }

    public boolean isActive() {
        return active;
    }
}