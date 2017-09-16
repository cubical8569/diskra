import java.util.Arrays;

public class Task1 {
    final static String illegalSizeErrorMessage =
            "You haven't provided appropriate number of coefficients";
    final static String illegalCoefficientErrorMessage =
            "You have specified illegal coefficient";

    final static int N = 8;

    public static int[][] getEquationMatrix(int[] coefficients)
            throws IllegalArgumentException, IllegalAccessException {

        if (coefficients.length != N + 1) {
            throw new IllegalAccessException(illegalSizeErrorMessage);
        }

        int[][] equationMatrix = new int[N][N + 1];

        for (int i = 0; i < N + 1; i ++) {
            int coefficient = coefficients[i];

            for (int j = N - 1; j >= 0; j --) {
                equationMatrix[j][i] = coefficient % 2;
                coefficient /= 2;
            }

            if (coefficient != 0) {
                throw new IllegalArgumentException(illegalCoefficientErrorMessage);
            }
        }

        return equationMatrix;
    }

    public static int[] solveEquationSystem(int[][] matrix) {
        int[] variables = new int[N];
        Arrays.fill(variables, Integer.MIN_VALUE);

        boolean[] usedRow = new boolean[N];
        Arrays.fill(usedRow, false);

        for (int i = 0; i < N; i ++) {
            for (int j = 0; j < N; j ++) {
                if (matrix[j][i] == 1 && !usedRow[j]) {
                    for (int k = 0; k < N; k ++) {
                        if (k == j) continue;

                        if (matrix[k][i] == 1) {
                            for (int z = 0; z < N + 1; z++) {
                                matrix[k][z] = matrix[k][z] ^ matrix[j][z];
                            }
                        }
                    }

                    usedRow[j] = true;
                    break;
                }
            }
        }

        for (int i = 0; i < N; i ++) {
            for (int j = 0; j < N; j ++) {
                if (matrix[i][j] == 1) {
                    variables[j] = matrix[i][N];
                }
            }
        }

        System.out.print("\n\n\n");
        outputMatrix(matrix);
        System.out.print("\n\n\n");

        return variables;
    }

    public static void outputMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            outputArray(row);
            System.out.println();
        }
    }

    public static void outputArray(int[] array) {
        for (int x : array) {
            System.out.print(x);
            System.out.print(" ");
        }
    }

    public static void main(String[] args) {
        try {
            int[][] matrix = getEquationMatrix(new int[]{216, 46, 80, 38, 191, 43, 228, 144, 102});

            outputMatrix(matrix);

            int[] vars = solveEquationSystem(matrix);

            System.out.println("Variables: "); 
            outputArray(vars);

        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
