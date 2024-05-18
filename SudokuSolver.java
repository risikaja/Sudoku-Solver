import java.util.InputMismatchException;
import java.util.Scanner;

public class SudokuSolver {
    Scanner scan = new Scanner(System.in);
    public static final int GRID_SIZE = 9;

    private final int[][] board = new int[9][9];
    public void enterBoard() {

        System.out.println("Enter board:");

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                while (true) {
                    try {
                        board[i][j] = scan.nextInt();
                        if (board[i][j] < 0 || board[i][j] > 9) {
                            throw new InputMismatchException("Number out of range.");
                        }
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input.");
                        scan.next();
                    }
                }
            }
        }
    }

    public static void printBoard(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            if (row % 3 == 0 && row != 0) {
                System.out.println("-----------");
            }
            for (int column = 0; column < GRID_SIZE; column++) {
                if (column % 3 == 0 && column != 0) {
                    System.out.print("|");
                }
                System.out.print(board[row][column]);
            }
            System.out.println();
        }
    }


    public static boolean isNumberInRow(int[][] board, int number, int row) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[row][i] == number) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNumberInColumn(int[][] board, int number, int column) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[i][column] == number) {
                return true;
            }
        }
        return false;
    }

    public static boolean isNumberInBox(int[][] board, int number, int row, int column) {
        int localBoxRow = row - row % 3;
        int localBoxColumn = column - column % 3;

        for (int i = localBoxRow; i < localBoxRow + 3; i++) {
            for (int j = localBoxColumn; j < localBoxColumn + 3; j++) {
                if (board[i][j] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isValidPlacement(int[][] board, int number, int row, int column) {
        return !isNumberInRow(board, number, row) &&
                !isNumberInColumn(board, number, column) &&
                !isNumberInBox(board, number, row, column);
    }

    public static boolean solveBoard(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int column = 0; column < GRID_SIZE; column++) {
                if (board[row][column] == 0) {
                    for (int numberToTry = 1; numberToTry <= GRID_SIZE; numberToTry++) {
                        if (isValidPlacement(board, numberToTry, row, column)) {
                            board[row][column] = numberToTry;

                            if (solveBoard(board)) {
                                return true;
                            }
                            else {
                                board[row][column] = 0;
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }


    public void menu(){
        System.out.println("Sudoku Solver");
        System.out.println("1. Enter board");
        System.out.println("2. Print board");
        System.out.println("3. Solve board");
        System.out.println("0. Exit");
    }

    public void sudokuSolver() {
        boolean condition = true;
        while (condition) {
            menu();
            System.out.println("Enter a choice:");

            int choice;
            try {
                choice = scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 0 and 3.");
                scan.next();
                continue;
            }

            switch (choice) {
                case 0:
                    condition = false;
                    System.out.println("Good bye");
                    break;
                case 1:
                    enterBoard();
                    break;
                case 2:
                    printBoard(board);
                    break;
                case 3:
                    if (solveBoard(board)) {
                        System.out.println("Solved successfully!");
                        printBoard(board);
                    } else {
                        System.out.println("Unsolvable board :(");
                    }
                    break;
                default:
                    System.out.println("Enter a valid option.");
            }
        }
    }
}