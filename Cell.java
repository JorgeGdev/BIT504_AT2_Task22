import java.awt.*;

public class Cell {
    Player content; // Content of the cell (Empty, Cross, Nought)
    int row, col; // Position of the cell in the grid
    private final int cellSize; // Custom size of the cell

    /**
     * Constructor to initialize a cell with its position and size.
     * @param row The row of the cell.
     * @param col The column of the cell.
     * @param cellSize The size of the cell.
     */
    public Cell(int row, int col, int cellSize) {
        this.row = row;
        this.col = col;
        this.cellSize = cellSize;
        clear();
    }

    /**
     * Draws the cell's content (Cross or Nought) on the board.
     * @param g The Graphics object for drawing.
     */
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND)); // Pen stroke size
        
        // Calculate the position and padding
        int x1 = col * cellSize + cellSize / 6;
        int y1 = row * cellSize + cellSize / 6;

        if (content == Player.Cross) {
            g2d.setColor(Color.MAGENTA); // Modern color for Cross
            int x2 = (col + 1) * cellSize - cellSize / 6;
            int y2 = (row + 1) * cellSize - cellSize / 6;
            g2d.drawLine(x1, y1, x2, y2);
            g2d.drawLine(x2, y1, x1, y2);
        } else if (content == Player.Nought) {
            g2d.setColor(Color.ORANGE); // Modern color for Nought
            g2d.drawOval(x1, y1, cellSize - cellSize / 3, cellSize - cellSize / 3);
        }
    }

    /**
     * Clears the cell content, setting it to Empty.
     */
    public void clear() {
        content = Player.Empty;
    }
}

