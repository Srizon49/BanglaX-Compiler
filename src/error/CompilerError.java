package error;

public class CompilerError extends RuntimeException {

    private final int line;

    public CompilerError(String message, int line) {
        super(message);
        this.line = line;
    }

    public int getLine() {
        return line;
    }

    @Override
    public String getMessage() {
        return super.getMessage()
                + " at line "
                + line;
    }
}