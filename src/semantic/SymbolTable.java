package semantic;

import java.util.LinkedHashMap;
import java.util.Map;

public class SymbolTable {

    // =========================================
    // Symbol Storage
    // =========================================

    private final Map<String, Symbol> symbols;

    // =========================================
    // Constructor
    // =========================================

    public SymbolTable() {

        symbols = new LinkedHashMap<>();
    }

    // =========================================
    // Declare Variable
    // =========================================

    public void declare(String name, String type) {

        symbols.put(
                name,
                new Symbol(name, type)
        );
    }

    // =========================================
    // Check Variable Exists
    // =========================================

    public boolean contains(String name) {

        return symbols.containsKey(name);
    }

    // =========================================
    // Get Symbol
    // =========================================

    public Symbol get(String name) {

        return symbols.get(name);
    }

    // =========================================
    // Get Variable Type
    // =========================================

    public String getType(String name) {

        Symbol symbol = symbols.get(name);

        if (symbol == null) {
            return null;
        }

        return symbol.getType();
    }

    // =========================================
    // Print Symbol Table
    // =========================================

    public void printTable() {

        System.out.println("=================================");
        System.out.println("        BanglaX Symbol Table");
        System.out.println("=================================");

        if (symbols.isEmpty()) {

            System.out.println("Symbol Table is empty.");

        } else {

            for (Symbol symbol : symbols.values()) {

                System.out.println(
                        symbol.getName()
                                + " -> "
                                + symbol.getType()
                );
            }
        }

        System.out.println("=================================");
    }
}