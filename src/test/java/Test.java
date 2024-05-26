import org.junit.jupiter.api.Assertions;

public class Test {
    @org.junit.jupiter.api.Test
    void divineByZero() {
        String t = "56/0";
        CalculateExpression.infixToPostfix(t);
        try {
            CalculateExpression.calcPostfix(CalculateExpression.infixToPostfix(t));
        }
        catch (RuntimeException e){
            Assertions.assertEquals("Division by zero", e.getMessage());
        }
    }

    @org.junit.jupiter.api.Test
    void unarMinus() {
        String t = "-56/1";
        CalculateExpression.infixToPostfix(t);
        double result = CalculateExpression.calcPostfix(CalculateExpression.infixToPostfix(t));
        Assertions.assertEquals(-56, result);
    }

    @org.junit.jupiter.api.Test
    void spaces() {
        String t = "  -56                 /         1       ";
        CalculateExpression.infixToPostfix(t);
        double result = CalculateExpression.calcPostfix(CalculateExpression.infixToPostfix(t));
        Assertions.assertEquals(-56, result);
    }

    @org.junit.jupiter.api.Test
    void unexpectedСontrolСharacters() {
        String t = "-56 \t/1 \n";
        CalculateExpression.infixToPostfix(t);
        double result = CalculateExpression.calcPostfix(CalculateExpression.infixToPostfix(t));
        Assertions.assertEquals(-56, result);
    }

    @org.junit.jupiter.api.Test
    void tripleMinus() {
        String t = "-(-(-56))";
        CalculateExpression.infixToPostfix(t);
        double result = CalculateExpression.calcPostfix(CalculateExpression.infixToPostfix(t));
        Assertions.assertEquals(-56, result);
    }

    @org.junit.jupiter.api.Test
    void illegalCharaters() {
        String t = "-(-(-56))!#";
        CalculateExpression.infixToPostfix(t);
        try {
            CalculateExpression.calcPostfix(CalculateExpression.infixToPostfix(t));
        }
        catch (RuntimeException e){
            Assertions.assertEquals("Illegal expression", e.getMessage());
        }
    }
}
