package src;

import java.util.HashMap;
import java.util.Map;

import src.types.RacketExpression;
import src.types.RacketPrimitiveFunction;
import src.types.RacketSymbol;


public class Frame {

    private final Frame parentFrame;
    private final Map<RacketSymbol, RacketExpression> table;

    /**
     * Constructor for the "global" frame
     */
    public Frame() {
        this(null);
    }

    /**
     * Constructor for a frame that points to another frame.  
     */
    public Frame(Frame parent) {
        parentFrame = parent;
        table = new HashMap<RacketSymbol, RacketExpression>();
    }

    /**
     * Look up the value of a variable, given its symbol.  Throw an
     * InterpreterException if not found (never return null).
     */
    public RacketExpression lookupVariableValue(RacketSymbol var) {
        if (table.containsKey(var)) {
            // check if variable in curr framee
            return table.get(var);
        } else if (parentFrame != null) {
            // otherwise check the parent framee
            return parentFrame.lookupVariableValue(var);
        } else {
            // variable doesn't exist yet
            throw new InterpreterException("Variable not defined:" + var);
        }
    }

    /**
     * Define a variable to have a certain value.
     */
    public void defineVariable(RacketSymbol var, RacketExpression value) {
        table.put(var, value);
    }

    /**
     * Set a variable to a new value (var must already exist).
     */
    public void setVariable(RacketSymbol var, RacketExpression value) {
		// WRITE ME -- OPTIONAL
    }

    /**
     * Search for a value in a frame and return a corresponding variable
     * as a symbol.  This is only here to let functions find their names to
     * enable pretty-printing.
     */
    public RacketSymbol searchForValue(RacketExpression value) {
        for (Map.Entry<RacketSymbol, RacketExpression> e : table.entrySet()) {
            if (e.getValue().equals(value))
                return e.getKey();
        }
        if (parentFrame != null)
            return parentFrame.searchForValue(value);

        return null;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("jracket.Frame:{");

        for (Map.Entry<RacketSymbol, RacketExpression> entry : table.entrySet()) {
            if (!(entry.getValue() instanceof RacketPrimitiveFunction)) {
                sb.append(entry.getKey());
                sb.append("=");
                sb.append(entry.getValue());
                sb.append(", ");
            }
        }

        sb.delete(sb.length() - 2, sb.length());
        sb.append("}");

        return sb.toString();
    }
}
