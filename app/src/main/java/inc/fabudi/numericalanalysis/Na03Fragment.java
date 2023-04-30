package inc.fabudi.numericalanalysis;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import inc.fabudi.numericalanalysis.math.NA03Solver;
import inc.fabudi.numericalanalysis.views.MultiTextButtonWithDropdown;
import ru.noties.jlatexmath.JLatexMathView;

public class Na03Fragment extends Fragment {

    private MultiTextButtonWithDropdown button;
    private Plotter plotter;

    public Na03Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @SuppressLint("ClickableViewAccessibility")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        plotter = view.findViewById(R.id.plotter);
        plotter.na03(NA03Solver.solve());
        JLatexMathView jLatexMathView1 = view.findViewById(R.id.cText5),
                jLatexMathView2 = view.findViewById(R.id.cText6),
                jLatexMathView3 = view.findViewById(R.id.cText7),
                jLatexMathView4 = view.findViewById(R.id.cText8);
        jLatexMathView1.setLatex("x'=-xy+\\frac{sint}{t}");
        jLatexMathView2.setLatex("y'=-y^2+\\frac{at}{1+t^2}");
        jLatexMathView3.setLatex("x(0)=0; y(0)=-0.412;");
        jLatexMathView4.setLatex("t\\in [0,1]; a=2.5+\\frac{\\omega}{40}; \\omega=25(1)48");
        view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Na03Fragment.this)
                        .navigate(R.id.action_na03Fragment_to_FirstFragment);
            }
        });
        LinearLayout closedL = view.findViewById(R.id.closedL);
        view.findViewById(R.id.openedL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(closedL.getTag().equals("CLOSE")){
                    closedL.setTag("OPEN");
                    closedL.setVisibility(View.VISIBLE);
                }else{
                    closedL.setTag("CLOSE");
                    closedL.setVisibility(View.GONE);
                }
            }
        });
    }

    @SuppressLint("DefaultLocale")
    private String formatString(Double number) {
        if (Math.abs(number) > 0.000000001) {
            return String.format("%f", number);
        } else {
            return String.format("%6.4e", number);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(Na03Fragment.this)
                        .navigate(R.id.action_na03Fragment_to_FirstFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        return inflater.inflate(R.layout.fragment_na03, container, false);
    }
}