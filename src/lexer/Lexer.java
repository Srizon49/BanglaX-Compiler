package lexer;

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    private final String source;
    private int pos;
    private int line;

    private final List<Token> tokens;

    public Lexer(String source) {
        this.source = source;
        this.pos = 0;
        this.line = 1;
        this.tokens = new ArrayList<>();
    }

    // =========================================================
    // CURRENT CHARACTER
    // =========================================================

    private char currentChar() {

        if (pos >= source.length()) {
            return '\0';
        }

        return source.charAt(pos);
    }

    // =========================================================
    // ADVANCE
    // =========================================================

    private char advance() {

        char ch = currentChar();

        if (ch != '\0') {

            pos++;

            if (ch == '\n') {
                line++;
            }
        }

        return ch;
    }

    // =========================================================
    // PEEK
    // =========================================================

    private char peek() {

        if (pos + 1 >= source.length()) {
            return '\0';
        }

        return source.charAt(pos + 1);
    }

    // =========================================================
    // READ NUMBER
    // =========================================================

    private void readNumber() {

        int start = pos;

        while (Character.isDigit(currentChar())) {
            advance();
        }

        // Decimal number
        if (currentChar() == '.'
                && Character.isDigit(peek())) {

            advance();

            while (Character.isDigit(currentChar())) {
                advance();
            }

            String lexeme =
                    source.substring(start, pos);

            tokens.add(
                    new Token(
                            TokenType.DECIMAL,
                            lexeme,
                            line
                    )
            );

        } else {

            String lexeme =
                    source.substring(start, pos);

            tokens.add(
                    new Token(
                            TokenType.INTEGER,
                            lexeme,
                            line
                    )
            );
        }
    }

    // =========================================================
    // READ IDENTIFIER / KEYWORD
    // =========================================================

    private void readIdentifier() {

        int start = pos;

        while (isIdentifierPart(currentChar())) {
            advance();
        }

        String lexeme =
                source.substring(start, pos);

        TokenType type =
                getKeywordType(lexeme);

        tokens.add(
                new Token(
                        type,
                        lexeme,
                        line
                )
        );
    }

    // =========================================================
    // IDENTIFIER CHARACTER
    // =========================================================

    private boolean isIdentifierPart(char ch) {

        return Character.isLetterOrDigit(ch)
                || ch == '_'
                || isBanglaCharacter(ch);
    }

    // =========================================================
    // BANGLA UNICODE RANGE
    // =========================================================

    private boolean isBanglaCharacter(char ch) {

        return ch >= '\u0980'
                && ch <= '\u09FF';
    }

    // =========================================================
    // BANGLAX KEYWORDS
    // =========================================================

    private TokenType getKeywordType(String word) {

        switch (word) {

            // সংখ্যা
            case "\u09B8\u0982\u0996\u09CD\u09AF\u09BE":
                return TokenType.NUMBER_TYPE;

            // দশমিক
            case "\u09A6\u09B6\u09AE\u09BF\u0995":
                return TokenType.DECIMAL_TYPE;

            // দেখাও
            case "\u09A6\u09C7\u0996\u09BE\u0993":
                return TokenType.PRINT;

            // যদি
            case "\u09AF\u09A6\u09BF":
                return TokenType.IF;

            // নাহলে
            case "\u09A8\u09BE\u09B9\u09B2\u09C7":
                return TokenType.ELSE;

            // যতক্ষণ
            case "\u09AF\u09A4\u0995\u09CD\u09B7\u09A3":
                return TokenType.WHILE;

            // শেষ
            case "\u09B6\u09C7\u09B7":
                return TokenType.END;

            default:
                return TokenType.IDENTIFIER;
        }
    }

    // =========================================================
    // ADD SINGLE CHARACTER TOKEN
    // =========================================================

    private void addToken(TokenType type) {

        String lexeme =
                String.valueOf(currentChar());

        tokens.add(
                new Token(
                        type,
                        lexeme,
                        line
                )
        );

        advance();
    }

    // =========================================================
    // OPERATOR
    // =========================================================

    private void readOperator() {

        char ch = currentChar();

        switch (ch) {

            case '+':

                addToken(TokenType.PLUS);
                break;

            case '-':

                addToken(TokenType.MINUS);
                break;

            case '*':

                addToken(TokenType.MULTIPLY);
                break;

            case '/':

                addToken(TokenType.DIVIDE);
                break;

            case '=':

                if (peek() == '=') {

                    int start = pos;

                    advance();
                    advance();

                    String lexeme =
                            source.substring(
                                    start,
                                    pos
                            );

                    tokens.add(
                            new Token(
                                    TokenType.EQUAL_EQUAL,
                                    lexeme,
                                    line
                            )
                    );

                } else {

                    addToken(TokenType.ASSIGN);
                }

                break;

            case '>':

                if (peek() == '=') {

                    int start = pos;

                    advance();
                    advance();

                    String lexeme =
                            source.substring(
                                    start,
                                    pos
                            );

                    tokens.add(
                            new Token(
                                    TokenType.GREATER_EQUAL,
                                    lexeme,
                                    line
                            )
                    );

                } else {

                    addToken(TokenType.GREATER);
                }

                break;

            case '<':

                if (peek() == '=') {

                    int start = pos;

                    advance();
                    advance();

                    String lexeme =
                            source.substring(
                                    start,
                                    pos
                            );

                    tokens.add(
                            new Token(
                                    TokenType.LESS_EQUAL,
                                    lexeme,
                                    line
                            )
                    );

                } else {

                    addToken(TokenType.LESS);
                }

                break;

            case '!':

                if (peek() == '=') {

                    int start = pos;

                    advance();
                    advance();

                    String lexeme =
                            source.substring(
                                    start,
                                    pos
                            );

                    tokens.add(
                            new Token(
                                    TokenType.NOT_EQUAL,
                                    lexeme,
                                    line
                            )
                    );

                } else {

                    // Ignore standalone !
                    advance();
                }

                break;

            default:

                advance();
                break;
        }
    }

    // =========================================================
    // TOKENIZE
    // =========================================================

    public List<Token> tokenize() {

        while (currentChar() != '\0') {

            char ch = currentChar();

            // -------------------------------------------------
            // Spaces / Tabs / Carriage Return
            // -------------------------------------------------

            if (ch == ' '
                    || ch == '\t'
                    || ch == '\r') {

                advance();
                continue;
            }

            // -------------------------------------------------
            // Newline
            // -------------------------------------------------

            if (ch == '\n') {

                tokens.add(
                        new Token(
                                TokenType.NEWLINE,
                                "\\n",
                                line
                        )
                );

                advance();
                continue;
            }

            // -------------------------------------------------
            // Number
            // -------------------------------------------------

            if (Character.isDigit(ch)) {

                readNumber();
                continue;
            }

            // -------------------------------------------------
            // Identifier / Bangla Keyword
            // -------------------------------------------------

            if (Character.isLetter(ch)
                    || isBanglaCharacter(ch)) {

                readIdentifier();
                continue;
            }

            // -------------------------------------------------
            // Left Parenthesis
            // -------------------------------------------------

            if (ch == '(') {

                addToken(TokenType.LPAREN);
                continue;
            }

            // -------------------------------------------------
            // Right Parenthesis
            // -------------------------------------------------

            if (ch == ')') {

                addToken(TokenType.RPAREN);
                continue;
            }

            // -------------------------------------------------
            // Operators
            // -------------------------------------------------

            if (ch == '+'
                    || ch == '-'
                    || ch == '*'
                    || ch == '/'
                    || ch == '='
                    || ch == '>'
                    || ch == '<'
                    || ch == '!') {

                readOperator();
                continue;
            }

            // -------------------------------------------------
            // Unknown Character
            // -------------------------------------------------

            advance();
        }

        // -----------------------------------------------------
        // EOF
        // -----------------------------------------------------

        tokens.add(
                new Token(
                        TokenType.EOF,
                        "",
                        line
                )
        );

        return tokens;
    }
}