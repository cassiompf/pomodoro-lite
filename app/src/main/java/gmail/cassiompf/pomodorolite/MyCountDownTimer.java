package gmail.cassiompf.pomodorolite;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;
import java.util.Calendar;

public class MyCountDownTimer extends CountDownTimer {

    private Context context;
    private TextView textView;

    /**
     * @param millisInFuture    The number of millis in the future from the call
     *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
     *                          is called.
     * @param countDownInterval The interval along the way to receive
     *                          {@link #onTick(long)} callbacks.
     */
    public MyCountDownTimer(Context context, TextView textView, long millisInFuture, long countDownInterval) {
        super(millisInFuture, countDownInterval);
        this.textView = textView;
        this.context = context;
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
    }

}
