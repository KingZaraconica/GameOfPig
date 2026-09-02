import java.util.Scanner;

public class GameOfPig
{
    static Scanner input = new Scanner(System.in);
    public static void menu()
    {
        System.out.println("""
                Welcome to the Game of Pig! Press 1 if you already
                know the rules and are ready to play, or press 2 if
                you've never played before!
                """);
    }
    public static void rules()
    {
        System.out.println("""
                To play, you need a partner. Take turns inputting "Roll"
                or "Bank". "Roll" will roll a 6 sided die, adding the roll to the
                round total. Be careful, if you roll a 1, your turn ends and
                the total resets! Choose "Bank" to permanently secure your points,
                first to 100 wins!
                """);
    }
    public static int roll()
    {
        boolean rolling = true;
        int total = 0;

        while (rolling)
        {    
            int x = (int)(Math.random() * 6) + 1;
            System.out.println("You rolled a " + x);
            if (x != 1)
            {    
                total = total + x;
                System.out.println("\"Continue\" or \"Bank\" (Case sensitive)");
                String userInput = input.next();
                if (userInput.equals("Continue"))
                {
                    continue;
                }
                else if (userInput.equals("Bank"))
                {
                    rolling = false;
                    return total;
                }
                else
                {
                    System.out.println("Invalid Input. Assuming you want to continue rolling.");
                    rolling = false;
                    return 0;
                }
            }
            else
            {
                total = 0;
                System.out.println("You rolled a 1! Your turn is over and your total resets to 0.");
                rolling = false;
                return 0;
            }
        }

        return total;
    }
    public static void main()
    {
        boolean run = true;
        while (run)
        {
            menu();
            int menuInput = input.nextInt();
            if (menuInput == 1)
            {
                boolean game = true;
                int playerOneTotal = 0;
                int playerTwoTotal = 0;
                int player = 1;
                while (game)
                {
                    System.out.println("Player " + player + "'s turn!");
                    
                    if (player == 1)
                    {
                        playerOneTotal += roll();
                        System.out.println("Player 1's total is " + playerOneTotal);
                        if (playerOneTotal >= 100)
                        {
                            System.out.println("Player 1 wins!");
                            game = false;
                        }
                    }
                    else
                    {
                        playerTwoTotal += roll();
                        System.out.println("Player 2's total is " + playerTwoTotal);
                        if (playerTwoTotal >= 100)
                        {
                            System.out.println("Player 2 wins!");
                            game = false;
                        }
                    }
                    if (game)
                    {
                        if (player == 1)
                        {
                            player = 2;
                        }
                        else
                        {
                            player = 1;
                        }
                    }
                }
            }
            else if (menuInput == 2)
            {
                rules();
                boolean game = true;
                int playerOneTotal = 0;
                int playerTwoTotal = 0;
                int player = 1;
                while (game)
                {
                    System.out.println("Player " + player + "'s turn!");
                    
                    if (player == 1)
                    {
                        playerOneTotal += roll();
                        if (playerOneTotal >= 100)
                        {
                            System.out.println("Player 1 wins!");
                            game = false;
                        }
                    }
                    else
                    {
                        playerTwoTotal += roll();
                        if (playerTwoTotal >= 100)
                        {
                            System.out.println("Player 2 wins!");
                            game = false;
                        }
                    }
                }
            }
            else
            {
                System.out.println("Invalid Input");
            }
        }
    }
}
