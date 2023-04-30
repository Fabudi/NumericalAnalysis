package inc.fabudi.numericalanalysis;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.button.MaterialButton;

import inc.fabudi.numericalanalysis.math.Matrix;
import inc.fabudi.numericalanalysis.math.VectorU;
import ru.noties.jlatexmath.JLatexMathView;

public class Na01SolveFragment extends Fragment {

    VectorU b;
    Matrix workMatrix, cloneMatrix;

    public JLatexMathView[] solutionOutput, residualOutput;
    public int x, y;

    public Na01SolveFragment() {
    }

    public Na01SolveFragment(int x, int y, Double[][] coefficients, Double[] coordinates) {
        this.x = x;
        this.y = y;
        b = new VectorU(x, coordinates);
        workMatrix = new Matrix(coefficients, x, y, b);
        cloneMatrix = (Matrix) workMatrix.clone();

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetTextI18n")
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Matrix matrix = ((MainActivity) requireActivity()).getMyMatrix();
        x = matrix.rows;
        y = matrix.cols;
        workMatrix = matrix;
        cloneMatrix = (Matrix) workMatrix.clone();

        JLatexMathView jLatexMathView1;
        jLatexMathView1 = view.findViewById(R.id.condition);
        StringBuilder conditionString = new StringBuilder("\\begin{bmatrix}\n");
        for (int i = 0; i < x; i++) {
            conditionString.append(matrix.getCoefficientByID(i, 0));
            for (int j = 1; j < y; j++) {
                conditionString.append("&").append(matrix.getCoefficientByID(i, j).toString());
            }
            conditionString.append("\\\\ \n");
        }
        conditionString.append(" \\end{bmatrix}");
        if (y == 4) {
            conditionString.append("\\begin{bmatrix}\n" +
                    "x \\\\ \n" +
                    "y \\\\ \n" +
                    "z \\\\ \n" +
                    "\\omega \n" +
                    "\\end{bmatrix}");
        }
        if (y == 3) {
            conditionString.append("\\begin{bmatrix}\n" +
                    "x \\\\ \n" +
                    "y \\\\ \n" +
                    "z \n" +
                    "\\end{bmatrix}");
        }
        if (y == 2) {
            conditionString.append("\\begin{bmatrix}\n" +
                    "x \\\\ \n" +
                    "y \\\\ \n" +
                    "\\end{bmatrix}");
        }
        if (y == 1) {
            conditionString.append("\\begin{bmatrix}\n" +
                    "x \\\\ \n" +
                    "\\end{bmatrix}");
        }
        conditionString.append("=");
        conditionString.append("\\begin{bmatrix}\n");
        for (int i = 0; i < y; i++) {
            conditionString.append(matrix.vectorU.coordinates[i]).append("\\\\");
        }
        conditionString.append(" \\end{bmatrix}");
        jLatexMathView1.setLatex(String.valueOf(conditionString));

        solutionOutput = new JLatexMathView[]{view.findViewById(R.id.solution_x), view.findViewById(R.id.solution_y), view.findViewById(R.id.solution_z), view.findViewById(R.id.solution_w)};
        residualOutput = new JLatexMathView[]{view.findViewById(R.id.residual_x), view.findViewById(R.id.residual_y), view.findViewById(R.id.residual_z), view.findViewById(R.id.residual_w)};
        try {
            workMatrix.makeTriangleMatrix();
        } catch (Exception ex) {
            Toast.makeText(getContext(), "Ошибка", Toast.LENGTH_SHORT).show();
            System.out.println(ex.toString());
        }


        JLatexMathView triangleMatrix = view.findViewById(R.id.triangle_matrix);


        StringBuilder triangleMatrixString = new StringBuilder("\\left[\n" +
                "\\begin{array}{");
        for (int i = 0; i < y; i++) {
            triangleMatrixString.append("c");
        }
        triangleMatrixString.append("|c}\n");
        /*
        \left[
        \begin{array}{cccc|c}
        1 & 0 & 3 & -1 & 0 \\
        0 & 1 & 1 & -1 & 0 \\
        0 & 0 & 0 & 0 & 0 \\
        \end{array}
        \right]
         */

        for (int i = 0; i < x; i++) {
            triangleMatrixString.append(formatString(matrix.getCoefficientByID(i, 0)));
            for (int j = 1; j < y; j++) {
                triangleMatrixString.append("&").append(formatString(workMatrix.getCoefficientByID(i, j)).toString());
            }
            triangleMatrixString.append("&").append(formatString(workMatrix.vectorU.coordinates[i]));
            triangleMatrixString.append("\\\\ \n");
        }
        triangleMatrixString.append("\\end{array}\n" +
                "\\right]");
        triangleMatrix.setLatex(String.valueOf(triangleMatrixString));
        VectorU solution = workMatrix.solveMatrix();
        VectorU residualVector = cloneMatrix.multiplyByVector(solution).subtractVector(cloneMatrix.vectorU);

        char[] answersLetters = new char[]{'x', 'y', 'z', 'w'};
        String[] residualAnswersLetters = new String[]{"F_{1}", "F_{2}", "F_{3}", "F_{4}"};
        for (int i = 0; i < x; i++) {
            solutionOutput[i].setVisibility(View.VISIBLE);
            solutionOutput[i].setLatex(answersLetters[i] + " = " + formatString(solution.coordinates[i]));
            residualOutput[i].setVisibility(View.VISIBLE);
            residualOutput[i].setLatex(residualAnswersLetters[i] + " = " + formatString(residualVector.coordinates[i]));
        }

        view.findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Na01SolveFragment.this)
                        .navigate(R.id.action_NA01Solution_to_FirstFragment);
            }
        });
        TextView gauss = view.findViewById(R.id.textViewGauss);
        TextView tv3 = view.findViewById(R.id.textView3);
        TextView tv5 = view.findViewById(R.id.textViewGauss2);
        JLatexMathView jLatexMathView = view.findViewById(R.id.triangle_matrix);
        TextView tv4 = view.findViewById(R.id.textView4);
        view.findViewById(R.id.more).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View view) {
                MaterialButton materialButton = view.findViewById(R.id.more);
                if (materialButton.getTag().equals("UP")) {
                    materialButton.setIcon(getResources().getDrawable(R.drawable.arrow_down));
                    materialButton.setTag("DOWN");
                    gauss.setVisibility(View.VISIBLE);
                    tv3.setVisibility(View.GONE);
                    tv5.setVisibility(View.VISIBLE);
                    jLatexMathView.setVisibility(View.GONE);
                    tv4.setVisibility(View.GONE);
                } else {
                    materialButton.setIcon(getResources().getDrawable(R.drawable.arrow_up));
                    materialButton.setTag("UP");
                    gauss.setVisibility(View.GONE);
                    tv5.setVisibility(View.GONE);
                    tv3.setVisibility(View.VISIBLE);
                    jLatexMathView.setVisibility(View.VISIBLE);
                    tv4.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    @SuppressLint("DefaultLocale")
    private String formatString(Double number) {
        if (Math.abs(number) < 0.0000000001) {
            return new String("0.0");
        }
        if (Math.abs(number) > 0.001) {
            return String.format("%f", number);
        } else {
            return String.format("%1.3e", number);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_na01_solve, container, false);
    }
}