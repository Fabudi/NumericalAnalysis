package inc.fabudi.numericalanalysis.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Arrays;

import inc.fabudi.numericalanalysis.Na01InputFragment;
import inc.fabudi.numericalanalysis.R;
import inc.fabudi.numericalanalysis.math.Matrix;
import inc.fabudi.numericalanalysis.math.VectorU;

public class MatrixView extends TableLayout {

    public static int cols, rows;
    private boolean isInputMode;
    private LinearLayout ParentMatrixLayout;
    private MatrixElementView[][] buttons;
    private EditText[][] inputs;
    private int x, y;
    private View view;
    private static final int ACTIVE = 0, BLANK = 1;

    public MatrixView(@NonNull Context context) {
        super(context, null);
    }

    public MatrixView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(Context context, AttributeSet attrs) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.matrix, this);
        ParentMatrixLayout = view.findViewById(R.id.parent_matrix);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MatrixView, 0, 0);
        try {
            cols = array.getInteger(R.styleable.MatrixView_cols, 2);
            rows = array.getInteger(R.styleable.MatrixView_rows, 2);
            isInputMode = array.getBoolean(R.styleable.MatrixView_inputMode, false);
        } finally {
            array.recycle();
        }
        x = cols;
        y = rows;
        if(isInputMode){
            createMatrixInputGrid(view);
        }else{
            createMatrixGrid(view);
            ParentMatrixLayout.setOnTouchListener(handleTouch);
        }
    }

    public Matrix getMyMatrix(){
        Double[][] coefficients = new Double[x][y];
        Double[] coordinates = new Double[x];
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                coefficients[i][j] = (!inputs[i][j].getText().toString().equals("")) ? Double.parseDouble(inputs[i][j].getText().toString()) : Na01InputFragment.primeNumbers[i][j];
            }
        }
        for (int i = 0; i < y; i++) {
            coordinates[i] = (!inputs[i][y].getText().toString().equals("")) ? Double.parseDouble(inputs[i][y].getText().toString()) : Na01InputFragment.primeNumbers[i][y];
        }
        System.out.println(Arrays.toString(coordinates));
        return new Matrix(coefficients, x, y, new VectorU(x, coordinates));
    }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void createMatrixGrid(View view) {
        buttons = new MatrixElementView[cols][rows];
        for (int i = 0; i < cols; i++) {
            LinearLayout ll = new LinearLayout(view.getContext());
            ll.setOrientation(HORIZONTAL);
            for (int j = 0; j < rows; j++) {
                MatrixElementView elementView = new MatrixElementView(view.getContext());
                buttons[i][j] = elementView;
                ll.addView(elementView);
            }
            ParentMatrixLayout.addView(ll);
        }
    }

    private void changeAllColor(int[] coordinates) {
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (i > coordinates[0] || j > coordinates[1]) {
                    buttons[i][j].changeColor(BLANK);
                } else {
                    buttons[i][j].changeColor(ACTIVE);
                }
            }
        }
        this.x = coordinates[0] + 1;
        this.y = coordinates[1] + 1;
    }

    @SuppressLint("ClickableViewAccessibility")
    private final View.OnTouchListener handleTouch = (v, event) -> {
        if (event.getAction() == MotionEvent.ACTION_MOVE ||
                event.getAction() == MotionEvent.ACTION_UP ||
                event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            changeAllColor(findArrayCoordinates(x, y));
        }
        return true;
    };

    private int[] findArrayCoordinates(int x, int y) {
        int arrayY = (x - cols * 2 * 8) / MatrixElementView.elemWidth;
        int arrayX = (y - cols * 2 * 8) / MatrixElementView.elemWidth;
        return new int[]{arrayX, arrayY};
    }

    public boolean isValid() {
        return this.x == this.y;
    }

    private void removeButtons() {
        ParentMatrixLayout.removeAllViews();
        buttons = null;
    }

    public void changeLayoutToInput() {
        removeButtons();
        createMatrixInputGrid(view);
    }

    @SuppressLint({"SetTextI18n", "ClickableViewAccessibility"})
    private void createMatrixInputGrid(View view) {
        inputs = new EditText[x][y+1];
        GridLayout layout = new GridLayout(view.getContext());
        layout.setColumnCount(x+1);
        layout.setUseDefaultMargins(true);
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y+1; j++) {
                EditText editText = new EditText(view.getContext());
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(MatrixElementView.elemWidth*cols/(cols+1),MatrixElementView.elemWidth*cols/(cols+1));
                lp.setMargins(10,10,10,10);
                editText.setGravity(Gravity.CENTER);
                editText.setLayoutParams(lp);
                editText.setHintTextColor(getResources().getColor(R.color.dark_primary_lighter));
                editText.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL |InputType.TYPE_NUMBER_FLAG_SIGNED);
                editText.setHint(Na01InputFragment.primeNumbers[i][j].toString());
                editText.setBackgroundResource(R.drawable.card_shape);
                editText.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.primary_variant)));
                layout.addView(editText);
                inputs[i][j] = editText;
            }
        }
        ParentMatrixLayout.addView(layout);
    }

}
