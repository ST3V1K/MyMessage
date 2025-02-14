import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecimalToIntegerParser {

    public static List<Integer> parseDecimalsToIntegers(String text) {
        List<Integer> numbers = new ArrayList<>();
        Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            double decimal = Double.parseDouble(matcher.group());
            numbers.add((int) Math.round(decimal));
        }
        return numbers;
    }

    public static void main(String[] args) {
        String input = "Tengo 3.5 manzanas, 10.99 peras y 42 plátanos.";
        List<Integer> result = parseDecimalsToIntegers(input);

        System.out.println("Números extraídos: " + result);
    }
}
