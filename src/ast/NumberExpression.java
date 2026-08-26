package ast;

public class NumberExpression extends Expression {

    private final String value;

    public NumberExpression(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String print() {
        return value;
    }
}