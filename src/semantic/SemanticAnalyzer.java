package semantic;

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
import error.CompilerError;

import java.util.List;

public class SemanticAnalyzer {

    // =========================================
    // Symbol Table
    // =========================================

    private final SymbolTable symbolTable;

    // =========================================
    // Constructor
    // =========================================

    public SemanticAnalyzer() {

        symbolTable = new SymbolTable();
    }

    // =========================================
    // Analyze Program
    // =========================================

    public void analyze(ProgramNode program) {

        for (Statement statement :
                program.getStatements()) {

            analyzeStatement(statement);
        }
    }

    // =========================================
    // Analyze Statement
    // =========================================

    private void analyzeStatement(
            Statement statement) {

        // -----------------------------------------
        // Assignment / Declaration
        // -----------------------------------------

        if (statement instanceof AssignmentStatement) {

            analyzeAssignment(
                    (AssignmentStatement) statement
            );

            return;
        }

        // -----------------------------------------
        // Print
        // -----------------------------------------

        if (statement instanceof PrintStatement) {

            PrintStatement print =
                    (PrintStatement) statement;

            analyzeExpression(
                    print.getExpression()
            );

            return;
        }

        // -----------------------------------------
        // IF
        // -----------------------------------------

        if (statement instanceof IfStatement) {

            analyzeIf(
                    (IfStatement) statement
            );

            return;
        }

        // -----------------------------------------
        // WHILE
        // -----------------------------------------

        if (statement instanceof WhileStatement) {

            analyzeWhile(
                    (WhileStatement) statement
            );
        }
    }

    // =========================================
    // Analyze Assignment / Declaration
    // =========================================

    private void analyzeAssignment(
            AssignmentStatement statement) {

        String name =
                statement.getName();

        Expression expression =
                statement.getExpression();

        // -----------------------------------------
        // First analyze expression
        // -----------------------------------------

        analyzeExpression(expression);

        // -----------------------------------------
        // Determine actual expression type
        // -----------------------------------------

        String actualType =
                getExpressionType(expression);

        if (actualType == null) {

            actualType = "UNKNOWN";
        }

        // =========================================
        // TYPED DECLARATION
        // =========================================

        if (statement.hasDeclaredType()) {

            String declaredType =
                    statement.getDeclaredType();

            // -----------------------------------------
            // Check type compatibility
            // -----------------------------------------

            if (!isTypeCompatible(
                    declaredType,
                    actualType)) {

                throw new CompilerError(
                        "Semantic Error: Variable '"
                                + name
                                + "' declared as "
                                + declaredType
                                + " but assigned "
                                + actualType
                                + " value.",
                        statement.getLine()
                );
            }

            // -----------------------------------------
            // Check duplicate declaration
            // -----------------------------------------

            if (symbolTable.contains(name)) {

                throw new CompilerError(
                        "Semantic Error: Variable '"
                                + name
                                + "' is already declared.",
                        statement.getLine()
                );
            }

            // -----------------------------------------
            // Add typed variable
            // -----------------------------------------

            symbolTable.declare(
                    name,
                    declaredType
            );

            return;
        }

        // =========================================
        // NORMAL ASSIGNMENT
        // =========================================

        if (symbolTable.contains(name)) {

            String existingType =
                    symbolTable.getType(name);

            // -----------------------------------------
            // Check assignment type
            // -----------------------------------------

            if (!isTypeCompatible(
                    existingType,
                    actualType)) {

                throw new CompilerError(
                        "Semantic Error: Variable '"
                                + name
                                + "' is "
                                + existingType
                                + " but assigned "
                                + actualType
                                + " value.",
                        statement.getLine()
                );
            }

            return;
        }

        // =========================================
        // AUTO DECLARATION
        // =========================================
        //
        // Normal assignment to a new variable
        // automatically declares the variable.
        // =========================================

        symbolTable.declare(
                name,
                actualType
        );
    }

    // =========================================
    // Type Compatibility
    // =========================================

    private boolean isTypeCompatible(
            String declaredType,
            String actualType) {

        if (declaredType == null
                || actualType == null) {

            return false;
        }

        // -----------------------------------------
        // UNKNOWN is not accepted
        // -----------------------------------------

        if ("UNKNOWN".equals(actualType)) {

            return false;
        }

        // -----------------------------------------
        // Exact match
        // -----------------------------------------

        if (declaredType.equals(actualType)) {

            return true;
        }

        // -----------------------------------------
        // NUMBER cannot receive DECIMAL
        // -----------------------------------------

        if ("NUMBER".equals(declaredType)
                && "DECIMAL".equals(actualType)) {

            return false;
        }

        // -----------------------------------------
        // DECIMAL can receive NUMBER
        // -----------------------------------------

        if ("DECIMAL".equals(declaredType)
                && "NUMBER".equals(actualType)) {

            return true;
        }

        return false;
    }

    // =========================================
    // Analyze IF
    // =========================================

    private void analyzeIf(
            IfStatement statement) {

        // Check condition

        analyzeExpression(
                statement.getCondition()
        );

        // THEN branch

        analyzeStatements(
                statement.getThenBranch()
        );

        // ELSE branch

        analyzeStatements(
                statement.getElseBranch()
        );
    }

    // =========================================
    // Analyze WHILE
    // =========================================

    private void analyzeWhile(
            WhileStatement statement) {

        // Check condition

        analyzeExpression(
                statement.getCondition()
        );

        // Check body

        analyzeStatements(
                statement.getBody()
        );
    }

    // =========================================
    // Analyze Statement List
    // =========================================

    private void analyzeStatements(
            List<Statement> statements) {

        for (Statement statement :
                statements) {

            analyzeStatement(statement);
        }
    }

    // =========================================
    // Analyze Expression
    // =========================================

    private void analyzeExpression(
            Expression expression) {

        // -----------------------------------------
        // Number
        // -----------------------------------------

        if (expression instanceof NumberExpression) {

            return;
        }

        // -----------------------------------------
        // Identifier
        // -----------------------------------------

        if (expression instanceof IdentifierExpression) {

            IdentifierExpression identifier =
                    (IdentifierExpression) expression;

            String name =
                    identifier.getName();

            if (!symbolTable.contains(name)) {

                throw new CompilerError(
                        "Semantic Error: Variable '"
                                + name
                                + "' is not declared.",
                        identifier.getLine()
                );
            }

            return;
        }

        // -----------------------------------------
        // Binary Expression
        // -----------------------------------------

        if (expression instanceof BinaryExpression) {

            BinaryExpression binary =
                    (BinaryExpression) expression;

            analyzeExpression(
                    binary.getLeft()
            );

            analyzeExpression(
                    binary.getRight()
            );
        }
    }

    // =========================================
    // Get Expression Type
    // =========================================

    private String getExpressionType(
            Expression expression) {

        // -----------------------------------------
        // Number / Decimal
        // -----------------------------------------

        if (expression instanceof NumberExpression) {

            NumberExpression number =
                    (NumberExpression) expression;

            String value =
                    number.getValue();

            if (value.contains(".")) {

                return "DECIMAL";
            }

            return "NUMBER";
        }

        // -----------------------------------------
        // Identifier
        // -----------------------------------------

        if (expression instanceof IdentifierExpression) {

            IdentifierExpression identifier =
                    (IdentifierExpression) expression;

            return symbolTable.getType(
                    identifier.getName()
            );
        }

        // -----------------------------------------
        // Binary Expression
        // -----------------------------------------

        if (expression instanceof BinaryExpression) {

            BinaryExpression binary =
                    (BinaryExpression) expression;

            String leftType =
                    getExpressionType(
                            binary.getLeft()
                    );

            String rightType =
                    getExpressionType(
                            binary.getRight()
                    );

            // -----------------------------------------
            // DECIMAL has priority
            // -----------------------------------------

            if ("DECIMAL".equals(leftType)
                    || "DECIMAL".equals(rightType)) {

                return "DECIMAL";
            }

            // -----------------------------------------
            // Both NUMBER
            // -----------------------------------------

            if ("NUMBER".equals(leftType)
                    && "NUMBER".equals(rightType)) {

                return "NUMBER";
            }

            return "UNKNOWN";
        }

        return "UNKNOWN";
    }

    // =========================================
    // Get Symbol Table
    // =========================================

    public SymbolTable getSymbolTable() {

        return symbolTable;
    }
}