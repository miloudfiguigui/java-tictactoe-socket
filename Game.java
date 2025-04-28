import java.io.Serializable;

public class Game implements Serializable {
    private char[][] board;
    private boolean player1Turn;
    private boolean gameWon;
    private boolean gameDraw;

    public Game() {
        board = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
        player1Turn = true;
        gameWon = false;
        gameDraw = false;
    }

    public char[][] getBoard() {
        return board;
    }

    public boolean isPlayer1Turn() {
        return player1Turn;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public boolean isGameDraw() {
        return gameDraw;
    }

    public boolean makeMove(int row, int col, boolean isPlayer1) {
        if (board[row][col] == ' ' && (player1Turn == isPlayer1)) {
            board[row][col] = isPlayer1 ? 'X' : 'O';
            checkGameStatus();
            player1Turn = !player1Turn;
            return true;
        }
        return false;
    }

    private void checkGameStatus() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] != ' ' && board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                gameWon = true;
                return;
            }
            if (board[0][i] != ' ' && board[0][i] == board[1][i] && board[1][i] == board[2][i]) {
                gameWon = true;
                return;
            }
        }
        if (board[0][0] != ' ' && board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            gameWon = true;
            return;
        }
        if (board[0][2] != ' ' && board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            gameWon = true;
            return;
        }
        gameDraw = true;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    gameDraw = false;
                    return;
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                sb.append(board[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
