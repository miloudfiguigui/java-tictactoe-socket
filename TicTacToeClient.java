import java.io.*;
import java.net.Socket;

public class TicTacToeClient {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            Game initialGame = (Game) in.readObject(); // Receive initial game state
            boolean isPlayer1 = initialGame.isPlayer1Turn();

            System.out.println("Connected as " + (isPlayer1 ? "Player 1 (X)" : "Player 2 (O)"));

            TicTacToeGUI gui = new TicTacToeGUI(out, in, isPlayer1);
            javax.swing.SwingUtilities.invokeLater(gui);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
