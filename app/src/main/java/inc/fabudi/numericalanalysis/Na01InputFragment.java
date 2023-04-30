package inc.fabudi.numericalanalysis;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;

import inc.fabudi.numericalanalysis.views.MatrixView;

public class Na01InputFragment extends Fragment {

    public EditText[][] matrixInput;
    public int x, y;
    private boolean firstClick = false;
    public static final Double[][] primeNumbers = new Double[][]{{1.0, 2.0, 3.0, 5.0, 7.0},
            {11.0, 13.0, 17.0, 19.0, 23.0},
            {29.0, 31.0, 37.0, 41.0, 43.0},
            {47.0, 53.0, 59.0, 61.0, 67.0}};
    private TextView someText;


    public Na01InputFragment() {
        // Required empty public constructor
    }

    public Na01InputFragment(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MatrixView matrixView = view.findViewById(R.id.matrix);
        firstClick = false;
        someText = view.findViewById(R.id.someText);
        view.findViewById(R.id.toInput).setOnClickListener(view1 -> {
            if (!matrixView.isValid()) {
                Snackbar snackbar = Snackbar.make(view1, "Матрица должна быть квадратной!", Snackbar.LENGTH_SHORT);
                View snackBarView = snackbar.getView();
                snackBarView.setBackgroundColor(getResources().getColor(R.color.accent));
                TextView textView = snackBarView.findViewById(com.google.android.material.R.id.snackbar_text);
                textView.setTextColor(getResources().getColor(R.color.white));
                snackbar.show();
            } else {
                if (!firstClick) {
                    firstClick = true;
                    matrixView.changeLayoutToInput();
                    someText.setText("Введите коэффициенты");
                } else {
                    ((MainActivity) requireActivity()).setMyMatrix(matrixView.getMyMatrix());
                    NavHostFragment.findNavController(Na01InputFragment.this)
                            .navigate(R.id.action_NA01MatrixInput_to_NA01Solution);
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                NavHostFragment.findNavController(Na01InputFragment.this)
                        .navigate(R.id.action_NA01MatrixInput_to_FirstFragment);
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
        return inflater.inflate(R.layout.fragment_na01_input, container, false);
    }
}