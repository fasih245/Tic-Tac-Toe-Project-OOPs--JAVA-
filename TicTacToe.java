// Importing utilities
import java.util.Random; // Used for generating random moves by the computer
import java.util.Scanner; // For user input

// Class representing the game board
class Board { 
    // Encapsulation: Grid is private to prevent direct access
    private char[][] grid = new char[3][3]; // 2D Grid for the Tic-Tac-Toe board
    
    // Constructor: Initializes the board
    public Board() { 
        // Initialize the grid with empty spaces
        for (int i = 0; i < 3; i++) { // Nested loop to initialize the board
             for (int j = 0; j < 3; j++) {
                grid[i][j] = ' '; // Empty spaces indicate unoccupied cells
            }
        }
    }

    // Method to display the board (Abstraction: hides details of board representation)
    public void displayBoard() {
        System.out.println("Current Board:");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(" " + grid[i][j]);
                if (j < 2) System.out.print(" |"); // Adds separator between columns
            }
            System.out.println();
            if (i < 2) System.out.println("---+---+---"); // Adds separator between rows
        }
    }

    // Encapsulation: Method to safely place a marker on the board
    public boolean placeMarker(int row, int col, char symbol) {
        if (row >= 0 && row < 3 && col >= 0 && col < 3 && grid[row][col] == ' ') {
            grid[row][col] = symbol; // Place marker
            return true; // Valid move
        }
        return false; // Invalid move
    }

    // Method to check if a player has won (Abstraction of win condition logic)
    public boolean checkWin(char symbol) {
        // Check rows, columns, and diagonals for a win
        for (int i = 0; i < 3; i++) {
            if (grid[i][0] == symbol && grid[i][1] == symbol && grid[i][2] == symbol) return true; // Row
            if (grid[0][i] == symbol && grid[1][i] == symbol && grid[2][i] == symbol) return true; // Column
        }
        if (grid[0][0] == symbol && grid[1][1] == symbol && grid[2][2] == symbol) return true; // Main diagonal
        if (grid[0][2] == symbol && grid[1][1] == symbol && grid[2][0] == symbol) return true; // Anti-diagonal
        return false; // No win
    }

    // Encapsulation: Checks if the board is full (no empty cells)
    public boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[i][j] == ' ') return false; // Found an empty cell
            }
        }
        return true; // Board is full
    }

    // Encapsulation: Safely retrieves the value of a specific cell
    public char getCell(int row, int col) {
        return grid[row][col];
    }
}

// Class representing a Player (Encapsulation: symbol is private)
class Player {
    private char symbol; // Each player has a unique symbol (e.g., 'X' or 'O')

    // Constructor to initialize a player with a specific symbol
    public Player(char symbol) {
        this.symbol = symbol;
    }

    // Getter to access the player's symbol
    public char getSymbol() {
        return symbol;
    }
}

// Main class implementing the game
public class TicTacToe {
    private Board board; // Composition: Board is part of the game
    private Player user; // User player
    private Player computer; // Computer player
    private Player currentPlayer; // Tracks whose turn it is
    private Random random; // For generating computer moves

    // Constructor initializes the game components
    public TicTacToe() { 
        board = new Board(); // Create a new game board
        user = new Player('X'); // User plays as 'X'
        computer = new Player('O'); // Computer plays as 'O'
        currentPlayer = user; // User starts first
        random = new Random(); // Initialize random generator
    }

    // Polymorphism: Method Overloading
    public void startGame() { 
        Scanner scanner = new Scanner(System.in);

        while (true) {
            board.displayBoard(); // Show current board

            if (currentPlayer == user) {
                // User's turn
                System.out.println("Your turn (Player X). Enter row and column (1-3): ");
                int row = scanner.nextInt() - 1; // Input adjusted to 0-based indexing
                int col = scanner.nextInt() - 1;

                if (board.placeMarker(row, col, user.getSymbol())) { // Encapsulation: placeMarker
                    if (board.checkWin(user.getSymbol())) { // Check win condition
                        board.displayBoard();
                        System.out.println("You win!");
                        break;
                    } else if (board.isFull()) { // Check for draw
                        board.displayBoard();
                        System.out.println("It's a draw!");
                        break;
                    }
                    currentPlayer = computer; // Switch to computer
                } else {
                    System.out.println("Invalid move. Try again."); // Invalid move
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

                board.placeMarker(row, col, computer.getSymbol()); // Encapsulation

                if (board.checkWin(computer.getSymbol())) { // Check win condition
                    board.displayBoard();
                    System.out.println("Computer wins!");
                    break;
                } else if (board.isFull()) { // Check for draw
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
        // Create and start a new game
        TicTacToe game = new TicTacToe(); // Object creation (Classes and Objects)
        game.startGame(); // Start the game
    }
}
