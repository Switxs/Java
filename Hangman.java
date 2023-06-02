import java.util.Scanner;

public class Main {
    private static final int INITIAL_LIVES = 3;
    private static final int EXTRA_LIFE_THRESHOLD = 4;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String word;
        boolean isPlayerOne = true;

        System.out.println("Welcome to Hangman!");

        if (isPlayerOne) {
            System.out.print("Player 1, enter a word: ");
            word = scanner.nextLine().toLowerCase();
            isPlayerOne = false;
        } else {
            System.out.println("Player 1 has not entered a word yet. Please wait for Player 1.");
            return;
        }

        int numLives = INITIAL_LIVES + word.length() / EXTRA_LIFE_THRESHOLD;

        char[] guessedLetters = new char[word.length()];
        boolean isGameOver = false;

        System.out.println("Player 2, start guessing!");
        System.out.println("The word has " + word.length() + " letters.");

        while (!isGameOver) {
            displayHangman(numLives);
            displayWordProgress(guessedLetters);

            System.out.print("Enter a letter: ");
            char guess = scanner.nextLine().charAt(0);
            guess = Character.toLowerCase(guess);

            boolean isCorrectGuess = updateGuessedLetters(guess, word, guessedLetters);
            if (isCorrectGuess) {
                System.out.println("Correct guess!");

                if (isWordGuessed(guessedLetters)) {
                    isGameOver = true;
                    System.out.println("Congratulations! You won!");
                }
            } else {
                numLives--;
                System.out.println("Wrong guess! Remaining lives: " + numLives);

                if (numLives == 0) {
                    isGameOver = true;
                    System.out.println("Game Over! You ran out of lives.");
                    System.out.println("The word was: " + word);
                }
            }
        }

        scanner.close();
    }

    private static void displayHangman(int numLives) {
        String[] hangman = {
                "  +---+",
                "  |   |",
                "      |",
                "      |",
                "      |",
                "      |",
                "======="
        };

        switch (numLives) {
            case 6:
                break;
            case 5:
                hangman[2] = "  |   O";
                break;
            case 4:
                hangman[3] = "  |  /|\\";
                break;
            case 3:
                hangman[3] = "  |  /|\\";
                hangman[4] = "  |   |";
                break;
            case 2:
                hangman[3] = "  |  /|\\";
                hangman[4] = "  |  /";
                break;
            case 1:
                hangman[3] = "  |  /|\\";
                hangman[4] = "  |  / \\";
                break;
            case 0:
                hangman[2] = "  |   O";
                hangman[3] = "  |  /|\\";
                hangman[4] = "  |  / \\";
                break;
        }

        for (String line : hangman) {
            System.out.println(line);
        }
    }

    private static void displayWordProgress(char[] guessedLetters) {
        System.out.print("Word: ");
        for (char letter : guessedLetters) {
            System.out.print(letter != '\u0000' ? letter : "_");
        }
        System.out.println();
    }

    private static boolean updateGuessedLetters(char guess, String word, char[] guessedLetters) {
        boolean isCorrectGuess = false;
        for (int i = 0; i < word.length(); i++) {
            if (Character.toLowerCase(word.charAt(i)) == guess) {
                guessedLetters[i] = guess;
                isCorrectGuess = true;
            }
        }
        return isCorrectGuess;
    }

    private static boolean isWordGuessed(char[] guessedLetters) {
        for (char letter : guessedLetters) {
            if (letter == '\u0000') {
                return false;
            }
        }
        return true;
    }
}
