import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TicTacToeServer {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        System.out.println("TicTacToe Server started...");
        Game game = new Game();

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                Socket player1Socket = serverSocket.accept();
                System.out.println("Player 1 connected.");
                Socket player2Socket = serverSocket.accept();
                System.out.println("Player 2 connected.");

                PlayerHandler player1Handler = new PlayerHandler(player1Socket, game, true);
                PlayerHandler player2Handler = new PlayerHandler(player2Socket, game, false);

                Thread player1Thread = new Thread(player1Handler);
                Thread player2Thread = new Thread(player2Handler);

                player1Thread.start();
                player2Thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
