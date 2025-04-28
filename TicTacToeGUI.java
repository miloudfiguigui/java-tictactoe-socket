import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class TicTacToeGUI implements Runnable {
    private JFrame frame;
    private JButton[][] buttons;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Game game;
    private boolean isPlayer1;

    public TicTacToeGUI(ObjectOutputStream out, ObjectInputStream in, boolean isPlayer1) {
        this.out = out;
        this.in = in;
        this.isPlayer1 = isPlayer1;
    }

    @Override
    public void run() {
        frame = new JFrame("Tic-Tac-Toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLayout(new GridLayout(3, 3));

        buttons = new JButton[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 60));
                buttons[i][j].setFocusPainted(false);
                buttons[i][j].addActionListener(new ButtonListener(i, j));
                frame.add(buttons[i][j]);
            }
        }

        frame.setVisible(true);

        new Thread(new GameUpdater()).start();
    }

    private void updateBoard(char[][] board) {
        SwingUtilities.invokeLater(() -> {
            System.out.println("Updating board...");
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.print(board[i][j] + " ");
                    buttons[i][j].setText(Character.toString(board[i][j]));
                    buttons[i][j].setEnabled(board[i][j] == ' ');
                }
                System.out.println();
            }
            System.out.println("Board updated.");
        });
    }

    private class ButtonListener implements ActionListener {
        private int row, col;

        public ButtonListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                out.writeInt(row);
                out.writeInt(col);
                out.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private class GameUpdater implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    Object receivedObject = in.readObject();
                    if (receivedObject instanceof Game) {
                        Game receivedGame = (Game) receivedObject;
                        System.out.println("Received game state: ");
                        System.out.println(receivedGame.toString());
                        game = receivedGame; // Update the local game state
                        updateBoard(game.getBoard());

                        if (game.isGameWon() || game.isGameDraw()) {
                            String message = game.isGameWon() ? (!game.isPlayer1Turn() ? "Player 1 wins!" : "Player 2 wins!") : "It's a draw!";
                            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, message));
                            break;
                        }
                    } else {
                        System.err.println("Received object is not of type Game.");
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
