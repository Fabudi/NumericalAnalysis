package inc.fabudi.numericalanalysis.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import inc.fabudi.numericalanalysis.R;

public class TwoTextButton extends ConstraintLayout {

    private TextView textMain;
    private TextView textBottom;

    private String strTextMain;
    private String strTextBottom;

    public TwoTextButton(@NonNull Context context) {
        super(context, null);
    }

    public TwoTextButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.two_text_button, this);

        this.textMain = view.findViewById(R.id.text_main);
        this.textBottom = view.findViewById(R.id.text_bottom);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TwoTextButton, 0, 0);
        try {
            strTextMain = array.getString(R.styleable.TwoTextButton_textMain);
            strTextBottom = array.getString(R.styleable.TwoTextButton_textBottom);
        } finally {
            array.recycle();
        }

        textMain.setText(strTextMain);
        textBottom.setText(strTextBottom);
    }


    public TwoTextButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TwoTextButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public String getStrTextMain() {
        return strTextMain;
    }

    public void setStrTextMain(String strTextMain) {
        this.strTextMain = strTextMain;
    }

    public String getStrTextBottom() {
        return strTextBottom;
    }

    public void setStrTextBottom(String strTextBottom) {
        this.strTextBottom = strTextBottom;
    }
}
