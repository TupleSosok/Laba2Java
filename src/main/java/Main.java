/**
 * Main класс
 */
public class Main {
    /**
     * Точка входа
     * @param args аргумента коммандой строки
     */
    public static void main(String[] args) {

        String t = "27*0.4-15/0.7+16*(18-0.5*12)";
        System.out.println("Infix: " + t);
        System.out.println("Postfix: " + CalculateExpression.infixToPostfix(t));
        System.out.printf("Result: %.5f\n", CalculateExpression.calcPostfix(CalculateExpression.infixToPostfix(t))); // Верное 181,37143
    }
}
