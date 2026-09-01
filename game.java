import java.util.Scanner;

public class gameOfPig
{
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
    public static void roll()
    {
        int x = Math.random(1, 7);
        
    }
    public static void main()
    {
        userInput input = new Scanner(System.in);

    }
}
