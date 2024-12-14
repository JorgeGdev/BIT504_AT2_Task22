import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GameMain extends JPanel implements MouseListener {
    private static final long serialVersionUID = 1L;

    // Constants for game
    public static final int ROWS = 3; // Number of rows
    public static final int COLS = 3; // Number of columns
    public static final String TITLE = "Tic Tac Toe - Modern Style";

    // Dimensions
    public static final int CELL_SIZE = 100; // Cell width and height
    public static final int CANVAS_WIDTH = CELL_SIZE * COLS; // Width of the drawing canvas
    public static final int CANVAS_HEIGHT = CELL_SIZE * ROWS; // Height of the drawing canvas
    public static final int CELL_PADDING = CELL_SIZE / 6; // Padding within a cell
    public static final int SYMBOL_SIZE = CELL_SIZE - CELL_PADDING * 2; // Size of X or O
    public static final int SYMBOL_STROKE_WIDTH = 8; // Stroke width for X and O

    private Board board; // The game board
    private GameState currentState; // Current state of the game (Playing, Draw, etc.)
    private Player currentPlayer; // Current player (Cross or Nought)
    private JLabel statusBar; // For displaying status messages

    /** Constructor to setup the UI and game components */
    public GameMain() {
        // Add mouse listener to capture clicks
        this.addMouseListener(this);

        // Initialize the status bar
        statusBar = new JLabel("         ");
        statusBar.setFont(new Font("Arial", Font.BOLD, 16));
        statusBar.setBorder(BorderFactory.createEmptyBorder(2, 5, 4, 5));
        statusBar.setOpaque(true);
        statusBar.setBackground(Color.BLACK);
        statusBar.setForeground(Color.CYAN);

        // Set layout and add the status bar
        setLayout(new BorderLayout());
        add(statusBar, BorderLayout.SOUTH);
        setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT + 30));

        // Initialize the game board
        board = new Board(ROWS, COLS, CELL_SIZE);
        initGame();
    }

    /** Main method to launch the application */
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

    /** Custom painting codes for the JPanel */
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // Paint the background
        setBackground(Color.BLACK); // Set background color
        board.paint(g); // Ask the board to paint itself

        // Display the game status
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

    /** Initialize the game */
    public void initGame() {
        // Clear the board and reset the game state
        for (int row = 0; row < ROWS; ++row) {
            for (int col = 0; col < COLS; ++col) {
                board.cells[row][col].content = Player.Empty;
            }
        }
        currentState = GameState.Playing; // Set the game state to Playing
        currentPlayer = Player.Cross; // X starts first
        repaint(); // Refresh the UI
    }

    /** Update the game state after a player's move */
    public void updateGame(Player thePlayer, int row, int col) {
        if (board.hasWon(thePlayer, row, col)) {
            currentState = (thePlayer == Player.Cross) ? GameState.Cross_won : GameState.Nought_won;
        } else if (board.isDraw()) {
            currentState = GameState.Draw;
        }
        repaint(); // Refresh the UI after state change
    }

    /** Handle mouse click events */
    public void mouseClicked(MouseEvent e) {
        // Get the coordinates of the click
        int mouseX = e.getX();
        int mouseY = e.getY();
        // Determine which cell was clicked
        int rowSelected = mouseY / CELL_SIZE;
        int colSelected = mouseX / CELL_SIZE;

        if (currentState == GameState.Playing) {
            // Ensure valid click and cell is empty
            if (rowSelected >= 0 && rowSelected < ROWS && colSelected >= 0 && colSelected < COLS &&
                board.cells[rowSelected][colSelected].content == Player.Empty) {
                // Place the current player's move
                board.cells[rowSelected][colSelected].content = currentPlayer;

                // Update the game state after the move
                updateGame(currentPlayer, rowSelected, colSelected);

                // Switch to the next player
                currentPlayer = (currentPlayer == Player.Cross) ? Player.Nought : Player.Cross;
            }
        } else {
            // Reset the game if it has ended
            initGame();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
