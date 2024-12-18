import java.awt.*;

public class Board {
    public static final int GRID_WIDTH = 8; // Width of the grid lines
    public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2; // Half grid line width

    private final int rows; // Number of rows in the grid
    private final int cols; // Number of columns in the grid
    private final int cellSize; // Size of each cell

    Cell[][] cells; // 2D array representing the cells on the board

    /** Constructor to initialize the board */
    public Board(int rows, int cols, int cellSize) {
        this.rows = rows;
        this.cols = cols;
        this.cellSize = cellSize;

        // Initialize the cells array
        cells = new Cell[rows][cols];
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                cells[row][col] = new Cell(row, col, cellSize);
            }
        }
    }

    /** Check if the game is a draw (no empty cells left) */
    public boolean isDraw() {
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                if (cells[row][col].content == Player.Empty) {
                    return false; // If any cell is empty, it's not a draw
                }
            }
        }
        return true; // No empty cells, game is a draw
    }

    /** Check if the player has won */
    public boolean hasWon(Player thePlayer, int playerRow, int playerCol) {
        return (cells[playerRow][0].content == thePlayer && cells[playerRow][1].content == thePlayer && cells[playerRow][2].content == thePlayer) ||
               (cells[0][playerCol].content == thePlayer && cells[1][playerCol].content == thePlayer && cells[2][playerCol].content == thePlayer) ||
               (cells[0][0].content == thePlayer && cells[1][1].content == thePlayer && cells[2][2].content == thePlayer) ||
               (cells[0][2].content == thePlayer && cells[1][1].content == thePlayer && cells[2][0].content == thePlayer);
    }

    /** Draw the grid and ask each cell to paint itself */
    public void paint(Graphics g) {
        g.setColor(Color.CYAN); // Modern cyan grid color
        for (int row = 1; row < rows; ++row) {
            g.fillRoundRect(0, cellSize * row - GRID_WIDTH_HALF, cellSize * cols - 1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
        }
        for (int col = 1; col < cols; ++col) {
            g.fillRoundRect(cellSize * col - GRID_WIDTH_HALF, 0, GRID_WIDTH, cellSize * rows - 1, GRID_WIDTH, GRID_WIDTH);
        }

        // Draw each cell on the board
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                cells[row][col].paint(g);
            }
        }
    }
}

