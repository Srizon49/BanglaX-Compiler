package ast;

public class IdentifierExpression extends Expression {

    private final String name;
    private final int line;

    public IdentifierExpression(String name) {
        this(name, 0);
    }

    public IdentifierExpression(
            String name,
            int line) {

        this.name = name;
        this.line = line;
    }

    public String getName() {
        return name;
    }

    public int getLine() {
        return line;
    }

    @Override
    public String print() {
        return name;
    }
}