import java.io.*;
import java.net.Socket;

public class PlayerHandler implements Runnable {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Game game;
    private boolean isPlayer1;
    private static final Object gameLock = new Object();
    private static PlayerHandler player1Handler = null;
    private static PlayerHandler player2Handler = null;

    public PlayerHandler(Socket socket, Game game, boolean isPlayer1) {
        this.socket = socket;
        this.game = game;
        this.isPlayer1 = isPlayer1;
    }

    @Override
    public void run() {
        try {
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            if (isPlayer1) {
                player1Handler = this;
            } else {
                player2Handler = this;
            }

            out.writeObject(game); // Send initial game state
            out.flush();

            while (true) {
                int row = in.readInt();
                int col = in.readInt();

                synchronized (gameLock) {
                    if (game.isPlayer1Turn() == isPlayer1 && game.makeMove(row, col, isPlayer1)) {
                        broadcastGameState();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcastGameState() {
        synchronized (gameLock) {
            try {
                if (player1Handler != null) {
                    player1Handler.out.reset(); // Ensure ObjectOutputStream sends updated object
                    player1Handler.out.writeObject(game);
                    player1Handler.out.flush();
                }
                if (player2Handler != null) {
                    player2Handler.out.reset(); // Ensure ObjectOutputStream sends updated object
                    player2Handler.out.writeObject(game);
                    player2Handler.out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
