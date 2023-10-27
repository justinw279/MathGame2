import java.util.Scanner;

public class MathGame {

    private Player player1;
    private Player player2;
    private Player player3;
    private int winningStreak;
    private Player currentPlayer;
    private Player pastWinner = null;
    private Player winner;
    private boolean gameOver;
    private Scanner scanner;

    // create MathGame object
    public MathGame(Player player1, Player player2, Player player3, Scanner scanner) {
        this.player1 = player1;
        this.player2 = player2;
        this.player3 = player3;
        this.scanner = scanner;
        currentPlayer = null; // will get assigned at start of game
        winner = null; // will get assigned when a Player wins
        gameOver = false;
    }

    // ------------ PUBLIC METHODS (to be used by client classes) ------------

    // returns winning Player; will be null if neither Player has won yet
    public String getWinnerName() {
        if (winner == player1) {
            return player1.getName();
        } else if (winner == player2) {
            return player2.getName();
        } else {
            return player3.getName();
        }
    }

    /*
            if (winner == pastWinner) {
            System.out.println(winner + " has won " + winningStreak + " games in a row!");
        }
     */

    public Player getPastWinner() {
        return pastWinner;
    }

    public Player getWinner() {
        return winner;
    }

    public int getWinningStreak() {
        return winningStreak;
    }

    // plays a round of the math game
    int consecutiveIncorrects = 0;
    public void playRound() {
        chooseStartingPlayer();  // this helper method (shown below) sets currentPlayer to either player1 or player2
        while (!gameOver) {
            printGameState();   // this helper method (shown below) prints the state of the Game
            System.out.println("Current player: " + currentPlayer.getName());
            boolean correct = askQuestion();  // this helper method (shown below) asks a question and returns T or F
            if (correct) {
                System.out.println("Correct!");
                currentPlayer.incrementScore();  // this increments the currentPlayer's score
                swapPlayers();  // this helper method (shown below) sets currentPlayer to the other Player
            } else {
                // System.out.println("INCORRECT!");
                // gameOver = true;
                // determineWinner();
                System.out.println("Incorrect! Next player gets one more chance.");
                swapPlayers();
                correct = askQuestion();
                if (!correct) {
                    System.out.println("INCORRECT!");
                    gameOver = true;
                    determineWinner();
                }
            }
        }
    }

    // prints the current scores of the two players
    private void printGameState() {
        System.out.println("--------------------------------------");
        System.out.println("Current Scores:");
        System.out.println(player1.getName() + ": " + player1.getScore());
        System.out.println(player2.getName() + ": " + player2.getScore());
        System.out.println(player3.getName() + ": " + player3.getScore());
        System.out.println("--------------------------------------");
    }

    // resets the game back to its starting state
    public void resetGame() {
        player1.reset(); // this method resets the player
        player2.reset();
        player3.reset();
        gameOver = false;
        currentPlayer = null;
        winner = null;
    }

    // ------------ PRIVATE HELPER METHODS (internal use only) ------------

    // randomly chooses one of the Player objects to be the currentPlayer
    private void chooseStartingPlayer() {
        int randNum = (int) (Math.random() * 3) + 1;
        if (randNum == 1) {
            currentPlayer = player1;
        } else if (randNum == 2) {
            currentPlayer = player2;
        } else {
            currentPlayer = player3;
        }
    }

    // asks a math question and returns true if the player answered correctly, false if not
    private boolean askQuestion() {
        int operation = (int) (Math.random() * 5) + 1;
        int num1 = (int) (Math.random() * 100) + 1;
        int num2;
        int correctAnswer;
        System.out.println("Type in your answer as an integer (/ is int division)");
        if (operation == 1) {
            num2 = (int) (Math.random() * 100) + 1;
            System.out.print(num1 + " + " + num2 + " = ");
            correctAnswer = num1 + num2;
        } else if (operation == 2) {
            num2 = (int) (Math.random() * 100) + 1;
            System.out.print(num1 + " - " + num2 + " = ");
            correctAnswer = num1 - num2;
        } else if (operation == 3) {
            num2 = (int) (Math.random() * 10) + 1;
            System.out.print(num1 + " * " + num2 + " = ");
            correctAnswer = num1 * num2;
        } else if (operation == 4) {  // option == 4
            num2 = (int) (Math.random() * 10) + 1;
            System.out.print(num1 + " / " + num2 + " = ");
            correctAnswer = num1 / num2;
        } else {
            num1 = (int) (Math.random() * 10) + 1;
            num2 = (int) (Math.random() * 10) + 3;
            correctAnswer = (int) (Math.log(num1) / Math.log(num2));
            System.out.print("log base " + num2 + " to " + num1 + " equals: ");
        }

        int playerAnswer = scanner.nextInt(); // get player's answer using Scanner
        scanner.nextLine(); // clear text buffer after numeric scanner input

        if (playerAnswer == correctAnswer) {
            return true;
        } else {
            return false;
        }
    }

    // swaps the currentPlayer to the other player
    private void swapPlayers() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else if (currentPlayer == player2) {
            currentPlayer = player3;
        } else {
            currentPlayer = player1;
        }
    }

    // sets the winner when the game ends based on the player that missed the question
    private void determineWinner() {
        pastWinner = winner;
        if (currentPlayer == player1) {
            if (player2.getScore() > player3.getScore()) {
                winner = player2;
            } else {
                winner = player3;
            }
        } else if (currentPlayer == player2) {
            if (player3.getScore() > player1.getScore()) {
                winner = player3;
            } else {
                winner = player1;
            }
        } else {
            if (player1.getScore() > player2.getScore()) {
                winner = player1;
            } else {
                winner = player2;
            }
        }
        if (pastWinner == null) {
            pastWinner = winner;
        }
        winningStreak();
        if (winner == pastWinner) {
            System.out.println(winner + " has won " + winningStreak + " games in a row!");
        }
    }

    private void winningStreak() {
        if (winner == pastWinner) {
            winningStreak++;
        }
        if (winner != pastWinner) {
            winningStreak = 0;
        }
    }



}