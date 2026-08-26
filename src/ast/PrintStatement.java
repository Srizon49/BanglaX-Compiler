package ast;

public class PrintStatement extends Statement {

    private final Expression expression;

    public PrintStatement(
            Expression expression) {

        super();

        this.expression = expression;
    }

    public PrintStatement(
            Expression expression,
            int line) {

        super(line);

        this.expression = expression;
    }

    public Expression getExpression() {
        return expression;
    }

    @Override
    public String print() {
        return "দেখাও " + expression.print();
    }
}