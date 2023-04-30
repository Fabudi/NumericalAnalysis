package inc.fabudi.numericalanalysis.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import inc.fabudi.numericalanalysis.R;
import ru.noties.jlatexmath.JLatexMathView;

public class NA05Button extends ConstraintLayout {

    private String strCondition1, strCondition2, strCondition3, strCondition4;
    private JLatexMathView conditionLatex1, conditionLatex2, conditionLatex3, conditionLatex4, solutionLatex1, solutionLatex2, solutionLatex3;
    public int solutionCounter, conditionCounter;

    public NA05Button(@NonNull Context context) {
        super(context, null);
    }

    public NA05Button(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.na05_button, this);

        conditionLatex1 = view.findViewById(R.id.na05_condition_latex1);
        conditionLatex2 = view.findViewById(R.id.na05_condition_latex2);
        conditionLatex3 = view.findViewById(R.id.na05_condition_latex3);
        conditionLatex4 = view.findViewById(R.id.na05_condition_latex4);


        solutionLatex1 = view.findViewById(R.id.na05_solution_latex_1);
        solutionLatex2 = view.findViewById(R.id.na05_solution_latex_2);
        solutionLatex3 = view.findViewById(R.id.na05_solution_latex_3);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.NA05Button, 0, 0);
        try {
            strCondition1 = array.getString(R.styleable.NA05Button_condition1);
            strCondition2 = array.getString(R.styleable.NA05Button_condition2);
            strCondition3 = array.getString(R.styleable.NA05Button_condition3);
            strCondition4 = array.getString(R.styleable.NA05Button_condition4);
            conditionCounter = array.getInteger(R.styleable.NA05Button_conditionCounter, 0);
            solutionCounter = array.getInteger(R.styleable.NA05Button_solutionCounter, 0);
        } finally {
            array.recycle();
        }

        conditionLatex1.setLatex(strCondition1);
        conditionLatex2.setLatex(strCondition2);
        conditionLatex3.setLatex(strCondition3);
        conditionLatex4.setLatex(strCondition4);

        switch (conditionCounter) {
            case 1:
                conditionLatex1.setVisibility(VISIBLE);
                break;
            case 2:
                conditionLatex1.setVisibility(VISIBLE);
                conditionLatex2.setVisibility(VISIBLE);
                break;
            case 3:
                conditionLatex1.setVisibility(VISIBLE);
                conditionLatex2.setVisibility(VISIBLE);
                conditionLatex3.setVisibility(VISIBLE);
                break;
            case 4:
                conditionLatex1.setVisibility(VISIBLE);
                conditionLatex2.setVisibility(VISIBLE);
                conditionLatex3.setVisibility(VISIBLE);
                conditionLatex4.setVisibility(VISIBLE);
                break;
        }
        switch (solutionCounter) {
            case 1:
                solutionLatex1.setVisibility(VISIBLE);
                break;
            case 2:
                solutionLatex1.setVisibility(VISIBLE);
                solutionLatex2.setVisibility(VISIBLE);
                break;
            case 3:
                solutionLatex1.setVisibility(VISIBLE);
                solutionLatex2.setVisibility(VISIBLE);
                solutionLatex3.setVisibility(VISIBLE);
                break;
        }

    }

    public void setSolutionLatex1(String solutionLatex1) {
        this.solutionLatex1.setLatex(solutionLatex1);
    }

    public void setSolutionLatex2(String solutionLatex2) {
        this.solutionLatex2.setLatex(solutionLatex2);
    }

    public void setSolutionLatex3(String solutionLatex3) {
        this.solutionLatex3.setLatex(solutionLatex3);
    }

    public NA05Button(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public NA05Button(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
