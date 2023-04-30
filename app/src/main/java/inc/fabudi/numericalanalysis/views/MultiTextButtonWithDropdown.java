package inc.fabudi.numericalanalysis.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;

import inc.fabudi.numericalanalysis.R;
import ru.noties.jlatexmath.JLatexMathView;

public class MultiTextButtonWithDropdown extends ConstraintLayout {

    private boolean mathStyle, expanded, solved;
    private int oCounter, cCounter, taskID;

    JLatexMathView oTextField1;
    JLatexMathView oTextField2;
    JLatexMathView oTextField3;
    JLatexMathView oTextField4;
    JLatexMathView cTextField1;
    JLatexMathView cTextField2;
    JLatexMathView cTextField3;
    JLatexMathView cTextField4;
    LinearLayout ll;
    MaterialButton materialButton;

    public MultiTextButtonWithDropdown(@NonNull Context context) {
        super(context, null);
    }

    public MultiTextButtonWithDropdown(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.multi_text_button_with_dropdown, this);
        oTextField1 = view.findViewById(R.id.oText1);
        oTextField2 = view.findViewById(R.id.oText2);
        oTextField3 = view.findViewById(R.id.oText3);
        oTextField4 = view.findViewById(R.id.oText4);
        cTextField1 = view.findViewById(R.id.cText1);
        cTextField2 = view.findViewById(R.id.cText2);
        cTextField3 = view.findViewById(R.id.cText3);
        cTextField4 = view.findViewById(R.id.cText4);

        ll = view.findViewById(R.id.iterationList);
        materialButton = view.findViewById(R.id.moreNA02);
        materialButton.setOnClickListener(new OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                if (materialButton.getTag().equals("UP")) {
                    materialButton.setIcon(getResources().getDrawable(R.drawable.arrow_down));
                    materialButton.setTag("DOWN");
                    ll.setVisibility(GONE);
                } else {
                    materialButton.setIcon(getResources().getDrawable(R.drawable.arrow_up));
                    materialButton.setTag("UP");
                    ll.setVisibility(VISIBLE);
                }
            }
        });

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MultiTextButtonWithDropdown, 0, 0);
        try {
            this.cCounter = array.getInteger(R.styleable.MultiTextButtonWithDropdown_cCounter, 0);
            this.oCounter = array.getInteger(R.styleable.MultiTextButtonWithDropdown_oCounter, 0);
            this.taskID = array.getInteger(R.styleable.MultiTextButtonWithDropdown_taskId, 0);
            this.mathStyle = array.getBoolean(R.styleable.MultiTextButtonWithDropdown_mathStyle, false);
            this.solved = array.getBoolean(R.styleable.MultiTextButtonWithDropdown_solved, false);
            this.expanded = array.getBoolean(R.styleable.MultiTextButtonWithDropdown_dropped, false);
            oTextField1.setLatex(array.getString(R.styleable.MultiTextButtonWithDropdown_oText1));
            oTextField2.setLatex(array.getString(R.styleable.MultiTextButtonWithDropdown_oText2));
            oTextField3.setLatex(array.getString(R.styleable.MultiTextButtonWithDropdown_oText3));
            oTextField4.setLatex(array.getString(R.styleable.MultiTextButtonWithDropdown_oText4));
            cTextField1.setLatex(array.getString(R.styleable.MultiTextButtonWithDropdown_cText1));
            cTextField2.setLatex(array.getString(R.styleable.MultiTextButtonWithDropdown_cText2));
            cTextField3.setLatex(array.getString(R.styleable.MultiTextButtonWithDropdown_cText3));
            cTextField4.setLatex(array.getString(R.styleable.MultiTextButtonWithDropdown_cText4));
        } finally {
            array.recycle();
        }

        onExpand(view);

        JLatexMathView[] textViews = new JLatexMathView[]{
                oTextField1, oTextField2, oTextField3, oTextField4,
                cTextField1, cTextField2, cTextField3, cTextField4
        };

        for (int i = oCounter; i < 4; i++) {
            textViews[i].setVisibility(GONE);
        }

        for (int i = 4 + cCounter; i < 8; i++) {
            textViews[i].setVisibility(GONE);
        }
    }

    public void onExpand(View view) {
        if (!expanded) {
            if (!solved) solved = true;
            view.findViewById(R.id.closedLayout).setVisibility(VISIBLE);
            expanded = true;
        } else {
            view.findViewById(R.id.closedLayout).setVisibility(GONE);
            if (materialButton.getTag().equals("UP")) {
                materialButton.setIcon(getResources().getDrawable(R.drawable.arrow_down));
                materialButton.setTag("DOWN");
                ll.setVisibility(GONE);
            }
            expanded = false;
        }
    }

    public void expandIterationList() {
        if (materialButton.getTag().equals("UP")) {
            materialButton.setIcon(getResources().getDrawable(R.drawable.arrow_down));
            materialButton.setTag("DOWN");
            ll.setVisibility(GONE);
        } else {
            materialButton.setIcon(getResources().getDrawable(R.drawable.arrow_up));
            materialButton.setTag("UP");
            ll.setVisibility(VISIBLE);
        }
    }

    public void addLatexTextToIterationList(String latex, View view) {
        JLatexMathView latexView = new JLatexMathView(view.getContext());
        latexView.textColor(Color.parseColor("#ffffff"));
        latexView.background(getResources().getDrawable(R.drawable.rect_zeus));
        int sp = 16;
        int px = (int) (sp * getResources().getDisplayMetrics().scaledDensity);
        latexView.textSize(px);
        latexView.setLatex(latex);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int sizeInDP = 15;
        int marginInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeInDP, getResources().getDisplayMetrics());
        int sizeInDP2 = 5;
        int marginInDp2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeInDP2, getResources().getDisplayMetrics());

        layoutParams.setMargins(marginInDp, marginInDp2, 0, marginInDp2);
        latexView.setLayoutParams(layoutParams);
        ll.addView(latexView);
    }

    public MultiTextButtonWithDropdown(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MultiTextButtonWithDropdown(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setoTextField1(String cTextField) {
        this.cTextField1.setLatex(cTextField);
    }

    public void setoTextField2(String cTextField) {
        this.cTextField2.setLatex(cTextField);
    }

    public void setoTextField3(String cTextField) {
        this.cTextField3.setLatex(cTextField);
    }

    public void setoTextField4(String cTextField) {
        this.cTextField4.setLatex(cTextField);
    }

    public void setcTextField1(String cTextField) {
        this.cTextField1.setLatex(cTextField);
    }

    public void setcTextField2(String cTextField) {
        this.cTextField2.setLatex(cTextField);
    }

    public void setcTextField3(String cTextField) {
        this.cTextField3.setLatex(cTextField);
    }

    public void setcTextField4(String cTextField) {
        this.cTextField4.setLatex(cTextField);
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public int getcCounter() {
        return cCounter;
    }

    public int getTaskID() {
        return taskID;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void addDivider(View view) {
        View divider = new View(view.getContext());
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 4);
        int sizeInDP2 = 5;
        int marginInDp2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeInDP2, getResources().getDisplayMetrics());
        layoutParams.setMargins(0, marginInDp2, 0, marginInDp2);
        divider.setLayoutParams(layoutParams);
        divider.setBackgroundColor(getResources().getColor(R.color.dark_primary_lighter));
        ll.addView(divider);
    }

    public void addIterationLabel(int i) {
        TextView textView = new TextView(getContext());
        textView.setText("Итерация #" + i);
        textView.setTextColor(Color.parseColor("#ffffff"));
        textView.setTextSize(15);
        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int sizeInDP2 = 15;
        int marginInDp2 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeInDP2, getResources().getDisplayMetrics());
        layoutParams.setMargins(marginInDp2, 0, 0, 0);
        textView.setLayoutParams(layoutParams);

        ll.addView(textView);
    }
}
