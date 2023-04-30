package inc.fabudi.numericalanalysis.views;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import inc.fabudi.numericalanalysis.R;

public class MatrixElementView extends androidx.appcompat.widget.AppCompatImageView {

    private static final LinearLayout.LayoutParams lp;
    private static final int width = Resources.getSystem().getDisplayMetrics().widthPixels;
    private static final int resId = R.drawable.matrix_element;
    private boolean isActive;
    public static int elemWidth, elemHeight;

    static {
        elemWidth = (int) (width * 0.7 / MatrixView.cols);
        elemHeight = (int) (width * 0.7 / MatrixView.cols);
        lp = new LinearLayout.LayoutParams(elemWidth, elemHeight);
        lp.setMargins(8, 8, 8, 8);
    }

    public MatrixElementView(Context context) {
        super(context);
        init();
    }

    public MatrixElementView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setTag("Blank");
        isActive=true;
        setImageResource(resId);
        setLayoutParams(lp);
        changeColor(0);
    }

    public MatrixElementView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void changeColor(int colorCode) {
        //noinspection StatementWithEmptyBody
        if (isActive && colorCode == 0 || !isActive && colorCode == 1) {
        } else {
            final int color = getResources().getColor(R.color.dark_primary_lighter);
            final ValueAnimator colorAnim = ObjectAnimator.ofFloat(0f, 1f);
            colorAnim.addUpdateListener(animation -> {
                float mul = (colorCode == 1) ? (Float) animation.getAnimatedValue() : 1 - (Float) animation.getAnimatedValue();
                int alphaOrange = adjustAlpha(color, mul);
                setColorFilter(alphaOrange, PorterDuff.Mode.SRC_ATOP);
                if (mul == 0.0) {
                    setColorFilter(null);
                }
            });
            if (colorCode == 0) {
                setTag("Active");
            } else {
                setTag("Blank");
            }
            colorAnim.setDuration(200);
            colorAnim.setRepeatCount(0);
            colorAnim.start();
            isActive = !isActive;
        }
    }

    public int adjustAlpha(int color, float factor) {
        int alpha = Math.round(Color.alpha(color) * factor);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

}
