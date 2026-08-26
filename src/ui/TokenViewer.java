package ui;

import lexer.Lexer;
import lexer.Token;
import lexer.TokenType;

import java.util.List;

public class TokenViewer {

    private TokenViewer() {
    }

    public static String generate(String source) {

        StringBuilder result = new StringBuilder();

        result.append("BanglaX Token Stream\n");
        result.append("============================================================\n\n");

        result.append(
                String.format(
                        "%-6s %-22s %-25s%n",
                        "Line",
                        "Token Type",
                        "Lexeme"
                )
        );

        result.append(
                "------------------------------------------------------------\n"
        );

        try {

            Lexer lexer =
                    new Lexer(source);

            List<Token> tokens =
                    lexer.tokenize();

            int tokenCount = 0;

            for (Token token : tokens) {

                /*
                 * NEWLINE and EOF are internal compiler tokens.
                 * They are hidden here to keep the UI clean
                 * and presentation-friendly.
                 */

                if (token.getType() == TokenType.NEWLINE ||
                        token.getType() == TokenType.EOF) {

                    continue;
                }

                String lexeme =
                        token.getLexeme()
                                .replace("\n", "\\n")
                                .replace("\r", "\\r")
                                .replace("\t", "\\t");

                result.append(
                        String.format(
                                "%-6d %-22s %-25s%n",
                                token.getLine(),
                                token.getType(),
                                lexeme
                        )
                );

                tokenCount++;
            }

            result.append("\n");
            result.append(
                    "============================================================\n"
            );

            result.append(
                    "Total Tokens: "
            );

            result.append(tokenCount);

            result.append("\n\n");

            result.append(
                    "Lexical Analysis: SUCCESS"
            );

        } catch (Exception ex) {

            result.append("\n");
            result.append(
                    "============================================================\n"
            );

            result.append(
                    "LEXICAL ERROR\n\n"
            );

            result.append(
                    ex.getMessage()
            );
        }

        return result.toString();
    }
}