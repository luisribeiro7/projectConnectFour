package academy.mindswap;

import java.util.Scanner;

public class Game implements Runnable {

    private final PlayerHandler player1;
    private final PlayerHandler player2;
    private String player1Name;
    private String player2Name;
    private final Server server;

    public Game(PlayerHandler[] array, Server server) {
        this.server = server;
        player1 = array[0];
        player2 = array[1];
    }

    private void gameProcess(){
        char[][] board = createBoard();

        char playerChar = 0;
        boolean someoneWon = false;
        int counter = 1;
        Scanner scanner = new Scanner(System.in);

        while (counter < 43 && !someoneWon) {

            boolean validation;
            String move;

            do {

                if (counter % 2 == 0) {
                    playerChar = 'Y';
                } else {
                    playerChar = 'R';
                }


                do {
                    printBoard(board);
                    System.out.println("It's " + playerChar + " turn to play");
                    System.out.println("Choose between 0 and 6 please.");
                    move = scanner.nextLine();
                } while (!move.matches("^[0-6]$"));

                validation = playValidation(board, Integer.parseInt(move));

            } while (validation == false);

            play(board, Integer.parseInt(move), playerChar);
            counter++;
            someoneWon = checkWinner(playerChar, board);

        }

        printBoard(board);

        if (someoneWon) {
            if (playerChar == 'R') {
                System.out.println("Red won");
            } else {
                System.out.println("Yellow won");
            }
        } else {
            System.out.println("Draw");
        }
    }

    private char[][] createBoard() {

        char[][] array = new char[6][7];

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                array[i][j] = ' ';
            }
        }
        return array;
    }

    private void printBoard(char[][] board) {

        for (int i = 0; i < board.length; i++) {
            System.out.print("|");
            for (int j = 0; j < board[0].length; j++) {
                System.out.print(board[i][j]);
                System.out.print("|");
            }
            System.out.println();
        }
        System.out.println("---------------");
        System.out.println(" 0 1 2 3 4 5 6");
        System.out.println();
    }

    private boolean playValidation(char[][] board, int move) {
        if (board[0][move] != ' ') {
            System.out.println("That column is full, pick another one.");
            return false;
        }
        return true;
    }

    private void play(char[][] board, int move, char playerChar) {

        for (int i = 5; i >= 0; i--) {
            if (board[i][move] == ' ') {
                board[i][move] = playerChar;
                break;
            }
        }
    }

    private boolean checkWinner(char player, char[][] board) {

        //check row
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length - 3; j++) {
                if (board[i][j] == player && board[i][j + 1] == player &&
                        board[i][j + 2] == player && board[i][j + 3] == player) {
                    return true;
                }
            }
        }

        //check column
        for (int i = 0; i < board.length - 3; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == player && board[i + 1][j] == player &&
                        board[i + 2][j] == player && board[i + 3][j] == player) {
                    return true;
                }
            }
        }

        // diagonal down - up
        for (int i = 3; i < board.length; i++) {
            for (int j = 0; j < board[0].length - 3; j++) {
                if (board[i][j] == player && board[i - 1][j + 1] == player &&
                        board[i - 2][j + 2] == player && board[i - 3][j + 3] == player) {
                    return true;
                }
            }
        }

        // diagonal up - down
        for (int i = 0; i < board.length - 3; i++) {
            for (int j = 0; j < board[0].length - 3; j++) {
                if (board[i][j] == player && board[i + 1][j + 1] == player &&
                        board[i + 2][j + 2] == player && board[i + 3][j + 3] == player) {
                    return true;
                }
            }
        }
        return false;
    }

    private void welcome() {
        player1.sendMessage("Let's start the game!");
        player2.sendMessage("Let's start the game!");
        player1Name = player1.getPlayerName();
        player2Name = player2.getPlayerName();
    }

    @Override
    public void run() {
        welcome();
        gameProcess();

    }
}
