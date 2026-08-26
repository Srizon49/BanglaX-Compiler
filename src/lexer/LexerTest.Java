package lexer;

import java.util.List;

public class LexerTest {

    public static void main(String[] args) {

        String source =
                "সংখ্যা x = 25\n" +
                "দশমিক price = 99.50\n" +
                "দেখাও x + price\n" +
                "যদি x >= 20\n" +
                "দেখাও (x * 2)\n" +
                "নাহলে\n" +
                "যতক্ষণ x < 100\n" +
                "x = x + 1\n" +
                "শেষ\n";

        Lexer lexer = new Lexer(source);

        List<Token> tokens = lexer.tokenize();

        System.out.println("=================================");
        System.out.println("     BanglaX Comprehensive Test");
        System.out.println("=================================");

        for (Token token : tokens) {

            System.out.println(
                    token.getType()
                    + " | "
                    + token.getLexeme()
                    + " | line "
                    + token.getLine()
            );
        }

        System.out.println("=================================");
        System.out.println("Total Tokens: " + tokens.size());
        System.out.println("=================================");
    }
}