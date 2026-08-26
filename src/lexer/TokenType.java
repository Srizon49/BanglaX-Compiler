package lexer;

public enum TokenType {

    // ==============================
    // Identifiers and Literals
    // ==============================

    IDENTIFIER,
    INTEGER,
    DECIMAL,

    // ==============================
    // Arithmetic Operators
    // ==============================

    PLUS,           // +
    MINUS,          // -
    MULTIPLY,       // *
    DIVIDE,         // /

    // ==============================
    // Assignment Operator
    // ==============================

    ASSIGN,         // =

    // ==============================
    // Comparison Operators
    // ==============================

    GREATER,        // >
    LESS,           // <
    GREATER_EQUAL,  // >=
    LESS_EQUAL,     // <=
    EQUAL_EQUAL,    // ==
    NOT_EQUAL,      // !=

    // ==============================
    // Parentheses
    // ==============================

    LPAREN,         // (
    RPAREN,         // )

    // ==============================
    // BanglaX Keywords
    // ==============================

    NUMBER_TYPE,    // সংখ্যা
    DECIMAL_TYPE,   // দশমিক
    PRINT,          // দেখাও
    IF,             // যদি
    ELSE,           // নাহলে
    WHILE,          // যতক্ষণ
    END,            // শেষ

    // ==============================
    // Special Tokens
    // ==============================

    NEWLINE,
    EOF
}