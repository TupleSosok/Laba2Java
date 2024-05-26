import java.util.Stack;

/**
 * Вычисление выражений
 */
public class CalculateExpression {

    /**
     * Определение приоритета знака
     * @param ch символ
     * @return int приоритет
     */
    private static int getPriority(char ch) {
        return switch (ch) {
            case '+', '-' -> 1;
            case '*', '/' -> 2;
            case '^' -> 3;
            case '~' -> 4;
            default -> 0;
        };
    }

    /**
     * Преобразование выражение в постфиксную форму
     * @param infix инфиксная форма
     * @return поствиксная форма
     */
    public static String infixToPostfix(String infix) {
        StringBuilder postfix = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        infix = infix.replaceAll(" ", "");

        for (int i = 0; i < infix.length(); i++) {
            char ch = infix.charAt(i);

            if (Character.isLetterOrDigit(ch) || ch == '.') {
                postfix.append(ch);

                // Проверяем, является ли следующий символ числом или оператором
                if (i + 1 < infix.length()) {
                    char nextChar = infix.charAt(i + 1);
                    if (!Character.isDigit(nextChar) && nextChar != '.') {
                        // Добавляем пробел после числа,
                        // если следующий символ не является частью числа
                        postfix.append(' ');
                    }
                }

            } else if (ch == '(') {

                stack.push(ch);

            } else if (ch == ')') { // Если символ - закрывающая скобка, выталкиваем элементы из стека до открывающей скобки

                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfix.append(stack.pop()).append(' ');
                }
                stack.pop(); // Удаляем открывающую скобку из стека

            } else { // Если символ - оператор

                //если минус унарный то сделать его ~
                if (ch == '-' && (i == 0 || (i > 1 && infix.charAt(i - 1) == '(')))
                    ch = '~';

                // Выталкиваем элементы из стека, пока приоритет текущего оператора не станет меньше приоритета оператора на вершине стека
                while (!stack.isEmpty() && getPriority(ch) <= getPriority(stack.peek())) {
                    postfix.append(stack.pop()).append(' ');
                }
                stack.push(ch); // Помещаем текущий оператор в стек

            }
        }

        // Выталкиваем оставшиеся элементы из стека в выходную строку
        while (!stack.isEmpty()) {
            postfix.append(stack.pop()).append(' ');
        }

        return postfix.toString().trim();
    }

    /**
     * Вычисление значения постфиксного выражения
     * @param postfix постфиксное выражение
     * @return double число
     */
    public static double calcPostfix(String postfix) {
        Stack<Double> stack = new Stack<>();

        // Разбиваем строку на числа и операторы
        String[] tokens = postfix.split("\\s+");

        for (String token : tokens) {

            if (Character.isDigit(token.charAt(0)) || token.charAt(0) == '.') {
                stack.push(Double.parseDouble(token));
            }
            else if(token.charAt(0) == '~') {
                stack.push(stack.pop() * -1);
            }
            else{
                try {
                double b = stack.pop();
                double a = stack.pop();
                double result = performOperation(token.charAt(0), a, b);
                stack.push(result);
                }
                catch (RuntimeException e){
                    throw new RuntimeException("Illegal expression");
                }
            }
        }

        // В стеке должен остаться только один элемент, который является результатом выражения
        double result = stack.pop();
        if(!stack.empty())
            throw new RuntimeException("Illegal expression");
        return result;
    }

    /**
     * Вычисление значение для одного опреатора
     * @param operator оператор
     * @param a число
     * @param b число
     * @return результиат операции
     */
    private static double performOperation(char operator, double a, double b) {
        return switch (operator) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> {
                if (b == 0) {
                    throw new ArithmeticException("Division by zero");
                }
                yield a / b;
            }
            case '^' -> Math.pow(a, b);
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }
}
