import java.util.Scanner;

public class Hangman {
  private static final String[] words = { "apple", "banana", "cherry", "orange", "strawberry" };
    private static final int EXTRA_LIFE_THRESHOLD = 5;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String word = getRandomWord();
        int numLives = calculateInitialLives(word);

        char[] guessedLetters = new char[word.length()];
        int numLettersGuessed = 0;
        boolean isGameOver = false;

        System.out.println("Welcome to Hangman!");
        System.out.println("Guess the word:");

        while (!isGameOver) {
            displayHangman(numLives);
            displayWordProgress(guessedLetters);

            System.out.print("Enter a letter: ");
            char guess = scanner.nextLine().charAt(0);

            if (isLetterGuessed(guess, guessedLetters)) {
                System.out.println("You already guessed that letter!");
                continue;
            }

            boolean isCorrectGuess = updateGuessedLetters(guess, word, guessedLetters);
            if (isCorrectGuess) {
                numLettersGuessed++;
                System.out.println("Correct guess!");
            } else {
                numLives--;
                System.out.println("Wrong guess!");

                if (numLives == 0) {
                    isGameOver = true;
                    System.out.println("Game Over! You ran out of lives.");
                }
            }

            if (numLettersGuessed == word.length()) {
                isGameOver = true;
                System.out.println("Congratulations! You won!");
            }
        }

        scanner.close();
    }

    private static String getRandomWord() {
        int index = (int) (Math.random() * words.length);
        return words[index];
    }

    private static int calculateInitialLives(String word) {
        int wordLength = word.length();
        int numLives = wordLength / EXTRA_LIFE_THRESHOLD;
        return numLives > 0 ? numLives : 1;
    }

    private static void displayHangman(int numLives) {
        System.out.println("Remaining lives: " + numLives);
        // Add your Hangman ASCII art here
    }

    private static void displayWordProgress(char[] guessedLetters) {
        System.out.print("Word: ");
        for (char letter : guessedLetters) {
            System.out.print(letter != '\u0000' ? letter : "_");
        }
        System.out.println();
    }

    private static boolean isLetterGuessed(char guess, char[] guessedLetters) {
        for (char letter : guessedLetters) {
            if (letter == guess) {
                return true;
            }
        }
        return false;
    }

    private static boolean updateGuessedLetters(char guess, String word, char[] guessedLetters) {
        boolean isCorrectGuess = false;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess) {
                guessedLetters[i] = guess;
                isCorrectGuess = true;
            }
        }
        return isCorrectGuess;
    }
}
