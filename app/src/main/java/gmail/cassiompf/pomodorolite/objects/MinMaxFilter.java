package gmail.cassiompf.pomodorolite.objects;

import android.graphics.drawable.Drawable;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.EditText;

public class MinMaxFilter implements InputFilter {

    private int mIntMin, mIntMax;
    private EditText editTextNumberSigned;

    public MinMaxFilter(String minValue, String maxValue, EditText editTextNumberSigned) {
        this.mIntMin = Integer.parseInt(minValue);
        this.mIntMax = Integer.parseInt(maxValue);
        this.editTextNumberSigned = editTextNumberSigned;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        try {
            int input = Integer.parseInt(dest.toString() + source.toString());
            if (isInRange(mIntMin, mIntMax, input))
                return null;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        editTextNumberSigned.setText("60");
        editTextNumberSigned.setSelection(2);
        return "";
    }

    private boolean isInRange(int a, int b, int c) {
        return b > a ? c >= a && c <= b : c >= b && c <= a;
    }
}
