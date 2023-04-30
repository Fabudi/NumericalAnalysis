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

import java.util.ArrayList;

import inc.fabudi.numericalanalysis.math.NA02Solver;
import inc.fabudi.numericalanalysis.math.NumberPair;
import inc.fabudi.numericalanalysis.views.MultiTextButtonWithDropdown;

public class Na02Fragment extends Fragment {

    private static final char[] variables = new char[]{'x', 'y', 'z'};

    private MultiTextButtonWithDropdown[] buttonArray;

    public Na02Fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        buttonArray = new MultiTextButtonWithDropdown[]{
                view.findViewById(R.id.b1),
                view.findViewById(R.id.b2),
                view.findViewById(R.id.b3),
                view.findViewById(R.id.b4),
                view.findViewById(R.id.b5),
                view.findViewById(R.id.b6),
                view.findViewById(R.id.b7),
                view.findViewById(R.id.b8),
                view.findViewById(R.id.b9),
                view.findViewById(R.id.b10),
                view.findViewById(R.id.b11),
                view.findViewById(R.id.b12),
                view.findViewById(R.id.b13),
                view.findViewById(R.id.b14)
        };

        for (MultiTextButtonWithDropdown button : buttonArray) {
            button.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("DefaultLocale")
                @Override
                public void onClick(View view) {
                    MultiTextButtonWithDropdown button = (MultiTextButtonWithDropdown) view;
                    if (!button.isSolved()) {
                        ArrayList<NumberPair> iterationList = NA02Solver.solve(button.getTaskID(), button.getcCounter());
                        int height = iterationList.size();
                        for (int i = 0; i < iterationList.size(); i++) {
                            button.addIterationLabel(i + 1);
                            for (int j = 0; j < iterationList.get(0).getSize(); j++) {
                                button.addLatexTextToIterationList(variables[j] + "_{" + (i + 1) + "} = " + formatString(iterationList.get(i).getByIndex(j)), view);
                            }
                            button.addDivider(view);
                        }
                        button.setcTextField1("x = " + formatString(iterationList.get(height - 1).getA()));
                        button.setcTextField2("y = " + formatString(iterationList.get(height - 1).getB()));
                        if (button.getcCounter() > 2)
                            button.setcTextField3("z = " + formatString(iterationList.get(height - 1).getC()));
                    }
                    //minimizeAllActiveButtons();
                    button.onExpand(view);
                }
            });
        }

        view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Na02Fragment.this)
                        .navigate(R.id.action_NA02_to_FirstFragment);
            }
        });

    }

    private void minimizeAllActiveButtons() {
        for (MultiTextButtonWithDropdown button : buttonArray) {
            if (!button.isExpanded()) {
                button.onExpand(button);
            }
        }
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
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(Na02Fragment.this)
                        .navigate(R.id.action_NA02_to_FirstFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        return inflater.inflate(R.layout.fragment_na02, container, false);
    }
}