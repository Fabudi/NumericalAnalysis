package inc.fabudi.numericalanalysis;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import inc.fabudi.numericalanalysis.interfaces.Function;
import inc.fabudi.numericalanalysis.interfaces.Function1;
import inc.fabudi.numericalanalysis.interfaces.Function2;
import inc.fabudi.numericalanalysis.math.NA05Solver;
import inc.fabudi.numericalanalysis.math.NumberPair;
import inc.fabudi.numericalanalysis.views.NA05Button;

public class Na05Fragment extends Fragment {

    NA05Button[] buttons;
    Function[] functions;
    NumberPair[] borders;

    public Na05Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        functions = new Function[]{
                (Function1) (x) -> Math.sqrt(1 + x * x * x),
                (Function1) (x) -> 1 / Math.sqrt(x * x * x - 1),
                (Function1) (x) -> Math.sqrt(x + x * x * x),
                (Function1) (x) -> (1 + x * x) / (1 + x * x * x),
                (Function1) (x) -> (Math.sin(x) * Math.sin(x)) / Math.sqrt(1 + x * x * x),
                (Function1) (x) -> Math.exp(2 * x) / Math.sqrt(1 - x * x),
                (Function2) (x, y) -> x*x/(1+y*y),
                (Function2) (x, y) -> 4-x*x-y*y
        };
        borders = new NumberPair[]{
                new NumberPair(0.8, 1.762),
                new NumberPair(1.3, 2.621),
                new NumberPair(0.6, 1.724),
                new NumberPair(3.0, 4.254),
                new NumberPair(0.0, 1.234),
                new NumberPair(-1.0, 1.0),
                new NumberPair(0.0, 4.0, 1.0, 2.0),
                new NumberPair(-1.0, 1.0, -1.0, 1.0)
        };

        buttons = new NA05Button[]{
                view.findViewById(R.id.Task1),
                view.findViewById(R.id.Task2),
                view.findViewById(R.id.Task3),
                view.findViewById(R.id.Task4),
                view.findViewById(R.id.Task5),
                view.findViewById(R.id.Task21),
                view.findViewById(R.id.Task29),
                view.findViewById(R.id.Task32)
        };

        for (int i = 0; i < 8; i++) {
            if (buttons[i].solutionCounter == 2) {
                buttons[i].setSolutionLatex1(NA05Solver.solveTrapeze((Function1) functions[i], borders[i]));
                buttons[i].setSolutionLatex2(NA05Solver.solveSimpson((Function1) functions[i], borders[i]));
            } else {
                buttons[i].setSolutionLatex1(NA05Solver.solveSimpson((Function2) functions[i], borders[i]));
            }
        }

        view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Na05Fragment.this)
                        .navigate(R.id.action_na05Fragment_to_FirstFragment);
            }
        });
    }


    @SuppressLint("DefaultLocale")
    private String formatString(Double number) {
        if (Math.abs(number) > 0.00000001) {
            return String.format("%f", number);
        } else {
            return String.format("%6.4e", number);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(Na05Fragment.this)
                        .navigate(R.id.action_na05Fragment_to_FirstFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        return inflater.inflate(R.layout.fragment_na05, container, false);
    }
}