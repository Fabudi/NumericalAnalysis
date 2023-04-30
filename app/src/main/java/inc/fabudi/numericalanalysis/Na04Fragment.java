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

import inc.fabudi.numericalanalysis.math.NA04Solver;
import inc.fabudi.numericalanalysis.views.MultiTextButtonWithDropdown;
import ru.noties.jlatexmath.JLatexMathView;

public class Na04Fragment extends Fragment {

    private MultiTextButtonWithDropdown button;
    private Plotter plotter;

    public Na04Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @SuppressLint("ClickableViewAccessibility")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        plotter = view.findViewById(R.id.plotter);
        JLatexMathView jLatexMathView1 = view.findViewById(R.id.cText6),
                jLatexMathView2 = view.findViewById(R.id.cText5),
                coefA = view.findViewById(R.id.coefA);
        jLatexMathView1.setLatex("\\begin{tabular}{llllllll} H(км)       & 0.0   & 1.0   & 2.0   & 3.0   & 4.0   & 5.0   & 6.0   \\\\ P(мм рт.ст) & 760.0 & 674.8 & 598.0 & 528.9 & 466.6 & 410.6 & 360.2 \\end{tabular}");
        jLatexMathView2.setLatex("P=a*10^{bH}");
        coefA.setLatex(NA04Solver.solve());
        double h = 10.0/100;
        float[][] coords = new float[100][2];
        for (int i = 0; i < 100; i++) {
            coords[i][0] = (float) (i*h);
            coords[i][1] = (float) (NA04Solver.a*Math.pow(10, NA04Solver.b*coords[i][0]));
        }
        plotter.na04();
        view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Na04Fragment.this)
                        .navigate(R.id.action_NA04_to_FirstFragment);
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
                NavHostFragment.findNavController(Na04Fragment.this)
                        .navigate(R.id.action_NA04_to_FirstFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        return inflater.inflate(R.layout.fragment_na04, container, false);
    }
}