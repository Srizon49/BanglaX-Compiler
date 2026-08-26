package ast;

public class AssignmentStatement extends Statement {

    private final String name;
    private final Expression expression;
    private final String declaredType;

    // =========================================
    // Normal Assignment
    // =========================================

    public AssignmentStatement(
            String name,
            Expression expression) {

        super();

        this.name = name;
        this.expression = expression;
        this.declaredType = null;
    }

    // =========================================
    // Normal Assignment With Line
    // =========================================

    public AssignmentStatement(
            String name,
            Expression expression,
            int line) {

        super(line);

        this.name = name;
        this.expression = expression;
        this.declaredType = null;
    }

    // =========================================
    // Typed Declaration
    // =========================================

    public AssignmentStatement(
            String name,
            Expression expression,
            String declaredType) {

        super();

        this.name = name;
        this.expression = expression;
        this.declaredType = declaredType;
    }

    // =========================================
    // Typed Declaration With Line
    // =========================================

    public AssignmentStatement(
            String name,
            Expression expression,
            String declaredType,
            int line) {

        super(line);

        this.name = name;
        this.expression = expression;
        this.declaredType = declaredType;
    }

    // =========================================
    // Get Name
    // =========================================

    public String getName() {
        return name;
    }

    // =========================================
    // Get Expression
    // =========================================

    public Expression getExpression() {
        return expression;
    }

    // =========================================
    // Get Declared Type
    // =========================================

    public String getDeclaredType() {
        return declaredType;
    }

    // =========================================
    // Check Typed Declaration
    // =========================================

    public boolean hasDeclaredType() {
        return declaredType != null;
    }

    // =========================================
    // Print
    // =========================================

    @Override
    public String print() {

        if (declaredType != null) {

            return declaredType
                    + " "
                    + name
                    + " = "
                    + expression.print();
        }

        return name
                + " = "
                + expression.print();
    }
}