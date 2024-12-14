import java.awt.*;

public class Cell {
    Player content;
    int row, col;
    private final int cellSize;

    public Cell(int row, int col, int cellSize) {
        this.row = row;
        this.col = col;
        this.cellSize = cellSize;
        clear();
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int x1 = col * cellSize + cellSize / 6;
        int y1 = row * cellSize + cellSize / 6;
        if (content == Player.Cross) {
            g2d.setColor(Color.MAGENTA);
            int x2 = (col + 1) * cellSize - cellSize / 6;
            int y2 = (row + 1) * cellSize - cellSize / 6;
            g2d.drawLine(x1, y1, x2, y2);
            g2d.drawLine(x2, y1, x1, y2);
        } else if (content == Player.Nought) {
            g2d.setColor(Color.ORANGE);
            g2d.drawOval(x1, y1, cellSize - cellSize / 3, cellSize - cellSize / 3);
        }
    }

    public void clear() {
        content = Player.Empty;
    }
}
