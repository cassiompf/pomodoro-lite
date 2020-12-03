package gmail.cassiompf.pomodorolite.objects;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import java.util.Calendar;

import gmail.cassiompf.pomodorolite.RestBreak;
import gmail.cassiompf.pomodorolite.enums.ActivityName;
import gmail.cassiompf.pomodorolite.enums.Task;

public class MyCountDownTimer extends CountDownTimer {

    private gmail.cassiompf.pomodorolite.CountDownTimer countDownTimer;
    private TextView textView;
    private String currentTask;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public MyCountDownTimer(gmail.cassiompf.pomodorolite.CountDownTimer countDownTimer, TextView textView, long millisInFuture, long countDownInterval, String currentTask) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
        this.countDownTimer = countDownTimer;
        this.currentTask = currentTask;
    }

    @Override
    public void onTick(long millisUntilFinished) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millisUntilFinished);

        String aux = calendar.get(Calendar.MINUTE) < 10 ?
                "0" + calendar.get(Calendar.MINUTE) + ":" :
                "" + calendar.get(Calendar.MINUTE) + ":";

        aux += calendar.get(Calendar.SECOND) < 10 ?
                "0" + calendar.get(Calendar.SECOND) :
                "" + calendar.get(Calendar.SECOND);

        textView.setText(aux);
        Log.i("Tempo:", aux);
    }

    @Override
    public void onFinish() {
        textView.setText("00:00");

        if (currentTask.equals(Task.POMODORO.getValor())) {
            countDownTimer.addPomodoros();
            countDownTimer.createNotification();
        }

        if (countDownTimer.isActive()) {
            textView.setText("");
            Bundle bundle = new Bundle();
            bundle.putInt("amountPomodoros", countDownTimer.getPomodoros());
            bundle.putBoolean("intervalo", countDownTimer.isIntervalo());
            bundle.putString("currentTask", currentTask);

            Intent intent = new Intent(countDownTimer, RestBreak.class);
            intent.putExtras(bundle);

            countDownTimer.startActivityForResult(intent, ActivityName.RESTBREAK.getValor());
        }
    }

    public String getCurrentTask() {
        return currentTask;
    }
}
