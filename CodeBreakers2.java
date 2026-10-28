import java.util.Scanner;

/** 
 * Code Breakers — Iteration 2 starter. 
 * Copy your four completed Iteration 1 method bodies into this file.
 * Complete reverse and repeatingThreeKeyShift. Keep the driver and checks unchanged.
*/
public class CodeBreakers {
    
    public static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    
    public static boolean isLetter(String ch) {

        return (ALPHABET.contains(ch) && !(ch == "" || ch.length() >= 2));
    }

    
    public static String shiftLetter(String ch, int shift) {
        if (ch == null || ch.length() != 1) {
            return ch;
        }
        char letter = ch.charAt(0);
        if (letter < 'A' || letter > 'Z'){
            return ch;
        }
        int position = letter - 'A';
        int modShift = shift % 26;
        int shifted = (position + modShift + 26) % 26;
        return String.valueOf((char) ('A' + shifted));
    }

    
    public static String encrypt(String message, int shift) {
        String encrypted = "";
        for (int i = 0; i < message.length(); i++){
            String character = message.substring(i, i + 1);
            encrypted += shiftLetter(character, shift);
        }
        return encrypted;
    }

   
    public static String decrypt(String message, int shift) {
        String decrypted = "";
        int reverseShift = -(shift % 26);
        for (int i = 0; i < message.length(); i ++){
            String character = message.substring(i, i+1);
            decrypted += shiftLetter(character, reverseShift);
        }
        return decrypted;
    }

    /** Returns a new String with all characters reversed. text is not null. */
    public static String reverse(String text) {
        String reversed = "";
        for (int i = text.length() - 1; i >= 0; i--) {
            reversed += text.charAt(i);
        }
        return reversed;
    }

    /**
     * Applies a repeating three-digit signed key to capital A-Z only.
     * Nonletters do not advance the key. Each call starts at the first digit.
     * text is not null; key is -999 through 999, with leading zeros allowed.
     */
    public static String repeatingThreeKeyShift(String text, int key) {
        if (key < -999 || key > 999) {  
            throw new IllegalArgumentException("K: key must be -999 through 999.");
        }
        for (int i = 0; i <= 3 && i < text.length(); i++) {
            char letter = text.charAt(i);
            if (letter >= 'A' && letter <= 'Z') {
                int position = letter - 'A';
                int modShift = key % 26;
                int shifted = (position + modShift + 26) % 26;
                text = text.substring(0, i) + (char) ('A' + shifted) + text.substring(i + 1);
            }
        }
        return text;
    }

    public static String applyTransformations(String text, String sequence) {
        String[] operations = sequence.split(",", -1);
        char[] kinds = new char[operations.length];
        int[] values = new int[operations.length];
        for (int i = 0; i < operations.length; i++) {
            String operation = operations[i].trim().toUpperCase(java.util.Locale.ROOT);
            if (operation.equals("R")) {
                kinds[i] = 'R';
            }
            else if (operation.startsWith("C:") || operation.startsWith("K:")) {
                kinds[i] = operation.charAt(0);
                try {
                    values[i] = Integer.parseInt(operation.substring(2).trim());
                }
                catch (NumberFormatException ex) {
                    throw new IllegalArgumentException("Step " + (i + 1) + " needs a whole-number value within the int range.");
                }
                if (kinds[i] == 'K' && (values [i] < -999 || values[i] > 999)){
                    throw new IllegalArgumentException("Step " + (i + 1) + " has a K: key outside -999 through 999");

                }
            }
            else {
                throw new IllegalArgumentException("Step " + (i + 1) + " must be C:n, K:n, or R; empty steps are not allowed.");
            }
        }
        String result = text;
        for (int i = 0; i < operations.length; i++){
            if (kinds[i] == 'C') result = encrypt(result, values[i]);
            else if (kinds[i] == 'K') result = repeatingThreeKeyShift(result, values[i]);
            else result = reverse(result);
        }
        return result;
    }
    
    private static int passedChecks;

    private static int totalChecks;

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Code Breakers — Iteration 2");
        System.out.println("Enter transformations in order, such as C:1, K:123, R.");
        String text = null;
        while (true) {
            if (text == null) {
                System.out.print("Message (or file:filename): ");
                if (!input.hasNextLine()) break;
                String source = input.nextLine();
                if (source.startsWith("file:")) {
                    try {
                        text = new String(java.nio.file.Files.readAllBytes(
                            java.nio.file.Paths.get(source.substring(5).trim())),
                            java.nio.charset.StandardCharsets.UTF_8);
                    } catch (java.io.IOException | IllegalArgumentException ex) {
                        System.out.println("Cannot read that file. Check its name and run from the launch folder.");
                        continue;
                    }
                } else text = source;
                System.out.println("Current text: " + text);
            }
            System.out.print("Transformation (C:n, K:n, R; new, tests, quit): ");
            if (!input.hasNextLine()) break;
            String sequence = input.nextLine().trim();
            if (sequence.equalsIgnoreCase("quit")) break;
            if (sequence.equalsIgnoreCase("new")) { text = null; continue; }
            if (sequence.equalsIgnoreCase("tests")) { runChecks(); continue; }
            try {
                text = applyTransformations(text, sequence);
                System.out.println("Result: " + text);
            }
            catch (IllegalArgumentException ex) {
                System.out.println("Invalid sequence. Use C:n, K:n or R, separated by commas.");
            }
        input.close();
        System.out.println("Goodbye.");
    }

    public static void runChecks() {
        passedChecks = 0;
        totalChecks = 0;
        check("letter A", "true", "" + isLetter("A"));
        check("letter Z", "true", "" + isLetter("Z"));
        check("lowercase", "false", "" + isLetter("a"));
        check("two letters", "false", "" + isLetter("AB"));
        check("empty letter", "false", "" + isLetter(""));
        check("digit", "false", "" + isLetter("7"));
        check("forward wrap", "A", shiftLetter("Z", 1));
        check("backward wrap", "Z", shiftLetter("A", -1));
        check("large positive shift", "B", shiftLetter("A", 53));
        check("large negative shift", "Z", shiftLetter("D", -30));
        check("punctuation", "!", shiftLetter("!", 4));
        check("space", " ", shiftLetter(" ", -3));
        check("zero shift", "A", shiftLetter("A", 0));
        check("full cycle", "Z", shiftLetter("Z", 26));
        check("encrypt example", "BA!", encrypt("AZ!", 1));
        check("mixed characters", "BCD xyz 7!", encrypt("ABC xyz 7!", 1));
        check("empty message", "", encrypt("", 5));
        check("decrypt example", "AZ!", decrypt("BA!", 1));
        check("decrypt negative shift", "GO, TEAM!", decrypt("FN, SDZL!", -1));
        check("round trip", "THE MESSAGE, 7!", decrypt(encrypt("THE MESSAGE, 7!", 19), 19));
        check("maximum int shift", "X", shiftLetter("A", Integer.MAX_VALUE));
        check("minimum int shift", "C", shiftLetter("A", Integer.MIN_VALUE));
        check("minimum int decryption", "A", decrypt("C", Integer.MIN_VALUE));
        check("reverse message", "!C BA", reverse("AB C!"));
        check("reverse empty", "", reverse(""));
        check("reverse one character", "Z", reverse("Z"));
        check("reverse mixed text", "7!b A", reverse("A b!7"));
        check("reverse twice", "AB C!", reverse(reverse("AB C!")));
        check("repeating key example", "DCG DCG!", repeatingThreeKeyShift("ABC ABC!", 314));
        check("key preserves mixed characters", "D bC!G7G", repeatingThreeKeyShift("A bB!C7D", 314));
        check("key forward wrap", "YAC", repeatingThreeKeyShift("XYZ", 123));
        check("key backward wrap", "WWW", repeatingThreeKeyShift("XYZ", -123));
        check("leading zero key", "ABJDEM", repeatingThreeKeyShift("ABCDEF", 7));
        check("negative leading zero key", "ABCDEF", repeatingThreeKeyShift("ABJDEM", -7));
        check("zero key", "ABC xyz 7!", repeatingThreeKeyShift("ABC xyz 7!", 0));
        check("largest key", "JKL", repeatingThreeKeyShift("ABC", 999));
        check("smallest key", "RST", repeatingThreeKeyShift("ABC", -999));
        check("key empty message", "", repeatingThreeKeyShift("", 314));
        check("key nonletters only", "abc 7!", repeatingThreeKeyShift("abc 7!", 314));
        check("key one letter", "D", repeatingThreeKeyShift("A", 314));
        check("key call resets position", "DD", repeatingThreeKeyShift("A", 314) + repeatingThreeKeyShift("A", 314));
        check("key decrypt example", "ABC ABC!", repeatingThreeKeyShift("DCG DCG!", -314));
        check("key round trip", "ZEBRA, abc 7!", repeatingThreeKeyShift(repeatingThreeKeyShift("ZEBRA, abc 7!", 907), -907));
        check("sequence encryption", "!GEC", applyTransformations("ABC!", "C:1, K:123, R"));
        check("sequence decryption", "ABC!", applyTransformations("!GEC", "R, K:-123, C:-1"));
        check("sequence order", "!EEE", applyTransformations("ABC!", "C:1, R, K:123"));
        check("sequence repeats", "ABC!", applyTransformations("ABC!", "R,R,C:1,C:-1"));
        check("sequence whitespace and case", "!GEC", applyTransformations("ABC!", " c: 1 , k:123 , r "));
        check("sequence leading zero key", "ABJDEM", applyTransformations("ABCDEF", "K:007"));
        check("sequence single transformation", "BA!", applyTransformations("AZ!", "C:1"));
        check("sequence empty message", "", applyTransformations("", "C:1,K:314,R"));
        check("sequence max int shift", "X", applyTransformations("A", "C:2147483647"));
        check("sequence min int shift", "C", applyTransformations("A", "C:-2147483648"));
        System.out.println("Checks: " + passedChecks + "/" + totalChecks + " passed.");
        if (passedChecks != totalChecks) throw new AssertionError("Some supplied checks failed.");
        }

    public static void check(String label, String expected, String actual) {
        totalChecks ++
        if (expected.equals(actual)) passedChecks ++
        System.out.println((expected.equals(actual) ? "PASS " : "FAIL ") + label
            + ": expected [" + expected + "]; actual [" + actual + "]");
    }
}
