import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameMain extends JPanel implements MouseListener {
    private static final long serialVersionUID = 1L;

    // Constants for the game
    public static final int ROWS = 3; // Number of rows
    public static final int COLS = 3; // Number of columns
    public static final String TITLE = "Tic Tac Toe - Modern Style";

    // Dimensions
    public static final int CELL_SIZE = 100; // Width and height of each cell
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS; // Total canvas width
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS; // Total canvas height
    public static final int CELL_PADDING = CELL_SIZE / 6; // Padding within a cell
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2; // Symbol size
    public static final int SYMBOL_STROKE_WIDTH = 8; // Line thickness for X and O

    private Board board; // The game board
    private GameState currentState; // Current state of the game
    private Player currentPlayer; // Current player (X or O)
    private JLabel statusBar; // Status bar for displaying game messages

    /** Constructor to set up the UI and initialize the game */
    public GameMain() {
        // Add mouse listener to capture mouse clicks
        this.addMouseListener(this);

        // Initialize the status bar
        statusBar = new JLabel("         ");
        statusBar.setFont(new Font("Arial", Font.BOLD, 16));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
        statusBar.setOpaque(true);
        statusBar.setBackground(Color.BLACK);
        statusBar.setForeground(Color.CYAN);

        // Set layout and add the status bar at the bottom
        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));

        // Initialize the game board with parameters
        board = new Board(ROWS, COLS, CELL_SIZE);
        initGame(); // Start the game
    }

    /** Main method to launch the game window */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(TITLE);
            frame.add(new GameMain());
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null); // Center the window
            frame.setVisible(true);
        });
    }

    /** Paint the game board and update the status bar */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Paint the background
        setBackground(Color.BLACK); // Modern black background
        board.paint(g); // Ask the board to paint itself

        // Update the status bar based on game state
        if (currentState == GameState.Playing) {
            statusBar.setForeground(Color.CYAN);
            statusBar.setText(currentPlayer == Player.Cross ? "X's Turn" : "O's Turn");
        } else if (currentState == GameState.Draw) {
            statusBar.setForeground(Color.RED);
            statusBar.setText("It's a Draw! Click to play again.");
        } else if (currentState == GameState.Cross_won) {
            statusBar.setForeground(Color.GREEN);
            statusBar.setText("'X' Won! Click to play again.");
        } else if (currentState == GameState.Nought_won) {
            statusBar.setForeground(Color.GREEN);
            statusBar.setText("'O' Won! Click to play again.");
        }
    }

    /** Initialize the game state and clear the board */
    public void initGame() {
        // Clear all cells and reset the game state
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board.cells[row][col].content = Player.Empty;
            }
        }
        currentState = GameState.Playing; // Set game state to Playing
        currentPlayer = Player.Cross; // X starts first
        repaint(); // Refresh the UI
    }

    /** Update the game state after a player's move */
    public void updateGame(Player thePlayer, int row, int col) {
        // Check if the player has won
        if (board.hasWon(thePlayer, row, col)) {
            currentState = (thePlayer == Player.Cross) ? GameState.Cross_won : GameState.Nought_won;
        } else if (board.isDraw()) {
            currentState = GameState.Draw; // Check for a draw
        }
        repaint(); // Refresh the UI after changes
    }

    /** Handle mouse click events for player moves */
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        int rowSelected = mouseY / CELL_SIZE;
        int colSelected = mouseX / CELL_SIZE;

        // Process the click if the game is still ongoing
        if (currentState == GameState.Playing) {
            if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS &&
                board.cells[rowSelected][colSelected].content == Player.Empty) {
                board.cells[rowSelected][colSelected].content = currentPlayer;
                updateGame(currentPlayer, rowSelected, colSelected); // Update game state
                currentPlayer = (currentPlayer == Player.Cross) ? Player.Nought : Player.Cross; // Switch players
            }
        } else {
            initGame(); // Restart the game if it ended
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
