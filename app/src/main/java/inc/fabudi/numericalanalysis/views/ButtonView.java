package inc.fabudi.numericalanalysis.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import inc.fabudi.numericalanalysis.R;

public class ButtonView extends androidx.appcompat.widget.AppCompatButton {

    public ButtonView(@NonNull Context context) {
        super(context);
        init();
    }

    public ButtonView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ButtonView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        lp.setMargins(32, 48, 32, 8);
        setLayoutParams(lp);
        setBackgroundResource(R.drawable.button_card_shape_full);
        setTextColor(getResources().getColor(R.color.white));
    }

}
