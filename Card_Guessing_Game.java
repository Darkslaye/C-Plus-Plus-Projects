import java.util.Scanner;
import java.util.Random;

public class CardGame {

    public static int[] loadDeck() { // Method to load the deck.
        int[] CardArray = new int[51]; // Ranging it to 52.
        for (int i = 0; i < CardArray.length; i++) { // Loading in values using the length of the array.
            CardArray[i] = i;
        }
    return CardArray;
    }

    // Validates user's inputs.
    public static void userAnswerHandler(Scanner scanner, int userInput, String userInputString) { 
        boolean isAnswerValid = false;
        if (!userInputString.isEmpty()) { // This determines if the user wants to continue playing or not. Since the user response is emptied each load of the game, this seemed appropriate.
            while (!isAnswerValid) {
                if (userInputString.charAt(0) == 'y' || userInputString.charAt(0) == 'n') {
                    isAnswerValid = true;
                } else {
                    System.out.println("Your response is not valid. \nType either 'yes' or 'no.'");
                }
            }
        } else { // Whether or not the user's input is within range of the cards.
            while (!isAnswerValid) {
                if (userInput >= 0 && userInput <= 51) {
                    isAnswerValid = true;
                } else {
                    System.out.println("Your response is not valid. \nSelect a card between 0 and 51.");
                    userInput = scanner.nextInt();
                }
            }
        }
    }

    // Calculates the card's number.
    public static int cardNumberMethod(int inputValue) { 
        int cardNumber = inputValue % 13; 
        return cardNumber;
    }

    // Calculates the rank of the card.
    private static int cardRankIntMethod(int inputValue) {
        int cardRankInt = (int) Math.floor(inputValue / 13); 
        return cardRankInt;
    }

    // Converts the Rank Int to a readable string.
    public static String cardRankStringMethod(int inputValue) {
        String[] cardRanks = {"Spades", "Hearts", "Diamonds", "Clubs"}; // I found it easier to use an array to determine the card rank instead of a switch case I previously used. 14 lines less!
        int cardIndex = cardRankIntMethod(inputValue) % cardRanks.length; // "Normalizes" the value, received errors without adding the %.
        String cardRankString = cardRanks[cardIndex];
        return cardRankString;
    }

    public static String cardNumberToStringMethod(int inputValue) {
        String[] cardNumberStringArray = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        return cardNumberStringArray[inputValue];
    }

    public static void main (String args[]) {
        Scanner scanner = new Scanner(System.in);
        Random randGen = new Random();
        int PlayerScore = 0; int BotScore = 0;  int userInput;
        int[] cardDeck = loadDeck(); // Loading the deck.
        String userInputString;

        System.out.println("Welcome to the card game.");
        do {
            userInputString = ""; // Resetting the string so the userAnswerHandler won't throw a fit.

            System.out.println("Please select a card between 0 and " + cardDeck.length + ".");
            userInput = scanner.nextInt();
            userAnswerHandler(scanner, userInput, userInputString); // I noticed I had to fill in the 3 variables or else this wouldn't work.

            // Placed the calls here because I started to get confused reading my own code. I feel like there is a better way to call these values.
            int userCardNumber = cardNumberMethod(userInput);
            int userCardRankInt = cardRankIntMethod(userInput);
            String userCardRankString = cardRankStringMethod(userInput);
            String userCardNumberString = cardNumberToStringMethod(userCardNumber);


            // Just to make sure it doesn't end up on a tie.
            int RNGInt = randGen.nextInt(52); 
            if (RNGInt == userInput) {
                do { // While loop for extra verification.
                    RNGInt = randGen.nextInt(52);
                } while (RNGInt == userInput);
            }
            
            // Doing the RNG side now.
            int RNGCardNumber = cardNumberMethod(RNGInt);
            int RNGCardRankInt = cardRankIntMethod(RNGInt);
            String RNGCardRankString = cardRankStringMethod(RNGInt);
            String RNGCardNumberString = cardNumberToStringMethod(RNGCardNumber);

            System.out.println("Your card is a " + userCardNumberString + " of " + userCardRankString + ".\nThe bot's card is a " + RNGCardNumberString + " of " + RNGCardRankString + ".");

            // A condition that sees if the userCardNumber is greater than RNGCardNumber. If not, we check if they are the same and if the card rank is higher than the bot's card rank.
            if ((userCardNumber == 0 && RNGCardNumber == 12) || (userCardNumber > RNGCardNumber) || (userCardNumber == RNGCardNumber && userCardRankInt > RNGCardRankInt)) {
                System.out.println("You win this round!");
                PlayerScore++;
            } else {
                System.out.println("Bot wins this round!");
                BotScore++;
            }
            System.out.println("Score: " + PlayerScore + " to " + BotScore);
            
            System.out.println("Would you like to play again? Type either 'yes' or 'no.'");
            userInputString = scanner.next().toLowerCase(); // Making it not case sensitive.
            userAnswerHandler(scanner, userInput, userInputString);
        } while (userInputString.charAt(0) == 'y'); // Grabbing the first character of the string and using that as the detection.

        System.out.println("Final Score\n~~~~~~~~~~~\nYour Score: " + PlayerScore + "\nBot's Score: " + BotScore);
        if (PlayerScore > BotScore) {
            System.out.println("You won!");
        } else {
            System.out.println("The bot won!");
        }
        System.out.println("Thanks for playing.");
    }
}
