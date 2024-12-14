import java.awt.*;

public class Board {
    public static final int GRID_WIDTH = 8;
    public static final int GRID_WIDTH_HALF = GRID_WIDTH / 2;

    private final int rows;
    private final int cols;
    private final int cellSize;

    Cell[][] cells;

    /** Constructor para inicializar el tablero */
    public Board(int rows, int cols, int cellSize) {
        this.rows = rows;
        this.cols = cols;
        this.cellSize = cellSize;

        // Inicializa el array `cells`
        cells = new Cell[rows][cols];
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                cells[row][col] = new Cell(row, col, cellSize);
            }
        }
    }

    public boolean isDraw() {
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                if (cells[row][col].content == Player.Empty) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean hasWon(Player thePlayer, int playerRow, int playerCol) {
        return (cells[playerRow][0].content == thePlayer && cells[playerRow][1].content == thePlayer && cells[playerRow][2].content == thePlayer) ||
               (cells[0][playerCol].content == thePlayer && cells[1][playerCol].content == thePlayer && cells[2][playerCol].content == thePlayer) ||
               (cells[0][0].content == thePlayer && cells[1][1].content == thePlayer && cells[2][2].content == thePlayer) ||
               (cells[0][2].content == thePlayer && cells[1][1].content == thePlayer && cells[2][0].content == thePlayer);
    }

    public void paint(Graphics g) {
        g.setColor(Color.CYAN);
        for (int row = 1; row < rows; ++row) {
            g.fillRoundRect(0, cellSize * row - GRID_WIDTH_HALF, cellSize * cols - 1, GRID_WIDTH, GRID_WIDTH, GRID_WIDTH);
        }
        for (int col = 1; col < cols; ++col) {
            g.fillRoundRect(cellSize * col - GRID_WIDTH_HALF, 0, GRID_WIDTH, cellSize * rows - 1, GRID_WIDTH, GRID_WIDTH);
        }
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                cells[row][col].paint(g);
            }
        }
    }
}
