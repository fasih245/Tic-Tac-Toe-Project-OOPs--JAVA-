import java.util.Random;
import java.util.Scanner;

class Board {
    private char[][] grid = new char[3][3];

    public Board() {
        // Initialize the grid with empty spaces
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                grid[i][j] = ' ';
            }
        }
    }

    public void displayBoard() {
        System.out.println("Current Board:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(" " + grid[i][j]);
                if (j < 2) System.out.print(" |");
            }
            System.out.println();
            if (i < 2) System.out.println("---+---+---");
        }
    }

    public boolean placeMarker(int row, int col, char symbol) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && grid[row][col] == ' ') {
            grid[row][col] = symbol;
            return true;
        }
        return false;
    }

    public boolean checkWin(char symbol) {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (grid[i][0] == symbol && grid[i][1] == symbol && grid[i][2] == symbol) return true;
            if (grid[0][i] == symbol && grid[1][i] == symbol && grid[2][i] == symbol) return true;
        }
        if (grid[0][0] == symbol && grid[1][1] == symbol && grid[2][2] == symbol) return true;
        if (grid[0][2] == symbol && grid[1][1] == symbol && grid[2][0] == symbol) return true;
        return false;
    }

    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] == ' ') return false;
            }
        }
        return true;
    }

    // New method to access a specific cell
    public char getCell(int row, int col) {
        return grid[row][col];
    }
}

class Player {
    private char symbol;

    public Player(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}

public class TicTacToe {
    private Board board;
    private Player user;
    private Player computer;
    private Player currentPlayer;
    private Random random;

    public TicTacToe() {
        board = new Board();
        user = new Player('X');
        computer = new Player('O');
        currentPlayer = user; // User starts first
        random = new Random();
    }

    public void startGame() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            board.displayBoard();

            if (currentPlayer == user) {
                // User's turn
                System.out.println("Your turn (Player X). Enter row and column (1-3): ");
                int row = scanner.nextInt() - 1;
                int col = scanner.nextInt() - 1;

                if (board.placeMarker(row, col, user.getSymbol())) {
                    if (board.checkWin(user.getSymbol())) {
                        board.displayBoard();
                        System.out.println("You win!");
                        break;
                    } else if (board.isFull()) {
                        board.displayBoard();
                        System.out.println("It's a draw!");
                        break;
                    }
                    currentPlayer = computer; // Switch to computer
                } else {
                    System.out.println("Invalid move. Try again.");
                }
            } else {
                // Computer's turn
                System.out.println("Computer's turn (Player O).");

                // Computer makes a random move
                int row, col;
                do {
                    row = random.nextInt(3);
                    col = random.nextInt(3);
                } while (board.getCell(row, col) != ' '); // Ensure the cell is empty

                board.placeMarker(row, col, computer.getSymbol());

                if (board.checkWin(computer.getSymbol())) {
                    board.displayBoard();
                    System.out.println("Computer wins!");
                    break;
                } else if (board.isFull()) {
                    board.displayBoard();
                    System.out.println("It's a draw!");
                    break;
                }
                currentPlayer = user; // Switch back to user
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        game.startGame();
    }
}
