package parser;

import ast.AssignmentStatement;
import ast.BinaryExpression;
import ast.Expression;
import ast.IdentifierExpression;
import ast.IfStatement;
import ast.NumberExpression;
import ast.PrintStatement;
import ast.ProgramNode;
import ast.Statement;
import ast.WhileStatement;

import lexer.Token;
import lexer.TokenType;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    private final List<Token> tokens;
    private int current;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.current = 0;
    }

    // =========================================
    // TOKEN HELPERS
    // =========================================

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }

    private boolean isAtEnd() {
        return peek().getType() == TokenType.EOF;
    }

    private Token advance() {

        if (!isAtEnd()) {
            current++;
        }

        return previous();
    }

    private boolean check(TokenType type) {

        if (isAtEnd()) {
            return type == TokenType.EOF;
        }

        return peek().getType() == type;
    }

    private boolean match(TokenType... types) {

        for (TokenType type : types) {

            if (check(type)) {
                advance();
                return true;
            }
        }

        return false;
    }

    // =========================================
    // MAIN PARSER
    // =========================================

    public ProgramNode parse() {

        ProgramNode program =
                new ProgramNode();

        while (!isAtEnd()) {

            if (match(TokenType.NEWLINE)) {
                continue;
            }

            Statement statement =
                    statement();

            if (statement != null) {
                program.addStatement(statement);
            }
        }

        return program;
    }

    // =========================================
    // STATEMENT
    // =========================================

    private Statement statement() {

        // -----------------------------------------
        // NUMBER declaration
        // -----------------------------------------

        if (match(TokenType.NUMBER_TYPE)) {

            return declaration("NUMBER");
        }

        // -----------------------------------------
        // DECIMAL declaration
        // -----------------------------------------

        if (match(TokenType.DECIMAL_TYPE)) {

            return declaration("DECIMAL");
        }

        // -----------------------------------------
        // PRINT
        // -----------------------------------------

        if (match(TokenType.PRINT)) {

            return printStatement();
        }

        // -----------------------------------------
        // IF
        // -----------------------------------------

        if (match(TokenType.IF)) {

            return ifStatement();
        }

        // -----------------------------------------
        // WHILE
        // -----------------------------------------

        if (match(TokenType.WHILE)) {

            return whileStatement();
        }

        // -----------------------------------------
        // END
        // -----------------------------------------

        if (match(TokenType.END)) {

            return null;
        }

        // -----------------------------------------
        // ASSIGNMENT
        // -----------------------------------------

        if (check(TokenType.IDENTIFIER)) {

            return assignment();
        }

        // -----------------------------------------
        // Unexpected token
        // -----------------------------------------

        Token token = advance();

        System.out.println(
                "Parser Error: Unexpected token '"
                        + token.getLexeme()
                        + "' at line "
                        + token.getLine()
        );

        return null;
    }

    // =========================================
    // DECLARATION
    // =========================================

    private Statement declaration(
            String declaredType) {

        Token name =
                consume(
                        TokenType.IDENTIFIER,
                        "Expected variable name."
                );

        consume(
                TokenType.ASSIGN,
                "Expected '=' after variable name."
        );

        Expression expression =
                expression();

        match(TokenType.NEWLINE);

        // Preserve declaration line number
        return new AssignmentStatement(
                name.getLexeme(),
                expression,
                declaredType,
                name.getLine()
        );
    }

    // =========================================
    // ASSIGNMENT
    // =========================================

    private Statement assignment() {

        Token name =
                consume(
                        TokenType.IDENTIFIER,
                        "Expected variable name."
                );

        consume(
                TokenType.ASSIGN,
                "Expected '=' after variable name."
        );

        Expression expression =
                expression();

        match(TokenType.NEWLINE);

        return new AssignmentStatement(
                name.getLexeme(),
                expression,
                name.getLine()
        );
    }

    // =========================================
    // PRINT STATEMENT
    // =========================================

    private Statement printStatement() {

        Expression expression =
                expression();

        match(TokenType.NEWLINE);

        return new PrintStatement(
                expression
        );
    }

    // =========================================
    // IF STATEMENT
    // =========================================

    private Statement ifStatement() {

        Expression condition =
                expression();

        match(TokenType.NEWLINE);

        List<Statement> thenBranch =
                new ArrayList<>();

        List<Statement> elseBranch =
                new ArrayList<>();

        // -----------------------------------------
        // THEN
        // -----------------------------------------

        while (!isAtEnd()
                && !check(TokenType.ELSE)
                && !check(TokenType.END)) {

            if (match(TokenType.NEWLINE)) {
                continue;
            }

            Statement statement =
                    statement();

            if (statement != null) {
                thenBranch.add(statement);
            }
        }

        // -----------------------------------------
        // ELSE
        // -----------------------------------------

        if (match(TokenType.ELSE)) {

            match(TokenType.NEWLINE);

            while (!isAtEnd()
                    && !check(TokenType.END)) {

                if (match(TokenType.NEWLINE)) {
                    continue;
                }

                Statement statement =
                        statement();

                if (statement != null) {
                    elseBranch.add(statement);
                }
            }
        }

        // -----------------------------------------
        // END
        // -----------------------------------------

        if (match(TokenType.END)) {
            match(TokenType.NEWLINE);
        }

        return new IfStatement(
                condition,
                thenBranch,
                elseBranch
        );
    }

    // =========================================
    // WHILE STATEMENT
    // =========================================

    private Statement whileStatement() {

        Expression condition =
                expression();

        match(TokenType.NEWLINE);

        List<Statement> body =
                new ArrayList<>();

        while (!isAtEnd()
                && !check(TokenType.END)) {

            if (match(TokenType.NEWLINE)) {
                continue;
            }

            Statement statement =
                    statement();

            if (statement != null) {
                body.add(statement);
            }
        }

        // -----------------------------------------
        // END WHILE
        // -----------------------------------------

        if (match(TokenType.END)) {
            match(TokenType.NEWLINE);
        }

        return new WhileStatement(
                condition,
                body
        );
    }

    // =========================================
    // EXPRESSION
    // =========================================

    private Expression expression() {

        return comparison();
    }

    // =========================================
    // COMPARISON
    // =========================================

    private Expression comparison() {

        Expression expression =
                addition();

        while (match(
                TokenType.GREATER,
                TokenType.GREATER_EQUAL,
                TokenType.LESS,
                TokenType.LESS_EQUAL,
                TokenType.EQUAL_EQUAL,
                TokenType.NOT_EQUAL
        )) {

            Token operator =
                    previous();

            Expression right =
                    addition();

            expression =
                    new BinaryExpression(
                            expression,
                            operator.getLexeme(),
                            right
                    );
        }

        return expression;
    }

    // =========================================
    // ADDITION / SUBTRACTION
    // =========================================

    private Expression addition() {

        Expression expression =
                multiplication();

        while (match(
                TokenType.PLUS,
                TokenType.MINUS
        )) {

            Token operator =
                    previous();

            Expression right =
                    multiplication();

            expression =
                    new BinaryExpression(
                            expression,
                            operator.getLexeme(),
                            right
                    );
        }

        return expression;
    }

    // =========================================
    // MULTIPLICATION / DIVISION
    // =========================================

    private Expression multiplication() {

        Expression expression =
                primary();

        while (match(
                TokenType.MULTIPLY,
                TokenType.DIVIDE
        )) {

            Token operator =
                    previous();

            Expression right =
                    primary();

            expression =
                    new BinaryExpression(
                            expression,
                            operator.getLexeme(),
                            right
                    );
        }

        return expression;
    }

    // =========================================
    // PRIMARY
    // =========================================

    private Expression primary() {

        // -----------------------------------------
        // INTEGER / DECIMAL
        // -----------------------------------------

        if (match(
                TokenType.INTEGER,
                TokenType.DECIMAL
        )) {

            return new NumberExpression(
                    previous().getLexeme()
            );
        }

        // -----------------------------------------
        // IDENTIFIER
        // IMPORTANT:
        // Preserve actual source line number
        // -----------------------------------------

        if (match(TokenType.IDENTIFIER)) {

            Token identifierToken =
                    previous();

            return new IdentifierExpression(
                    identifierToken.getLexeme(),
                    identifierToken.getLine()
            );
        }

        // -----------------------------------------
        // PARENTHESES
        // -----------------------------------------

        if (match(TokenType.LPAREN)) {

            Expression expression =
                    expression();

            consume(
                    TokenType.RPAREN,
                    "Expected ')'."
            );

            return expression;
        }

        // -----------------------------------------
        // Unexpected expression
        // -----------------------------------------

        Token token =
                peek();

        throw new RuntimeException(
                "Parser Error: Unexpected token '"
                        + token.getLexeme()
                        + "' at line "
                        + token.getLine()
        );
    }

    // =========================================
    // CONSUME
    // =========================================

    private Token consume(
            TokenType type,
            String message) {

        if (check(type)) {
            return advance();
        }

        Token token =
                peek();

        throw new RuntimeException(
                "Parser Error: "
                        + message
                        + " Found '"
                        + token.getLexeme()
                        + "' at line "
                        + token.getLine()
        );
    }
}