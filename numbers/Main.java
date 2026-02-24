package numbers;

import java.util.*;

public class Main {
    // All 12 supported properties
    private static final List<String> ALL_PROPS = Arrays.asList(
            "EVEN", "ODD", "BUZZ", "DUCK", "PALINDROMIC", "GAPFUL",
            "SPY", "SQUARE", "SUNNY", "JUMPING", "HAPPY", "SAD"
    );

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Amazing Numbers!");
        printInstructions();

        while (true) {
            System.out.print("\nEnter a request: ");
            String input = scanner.nextLine().toUpperCase();
            if (input.isEmpty()) {
                printInstructions();
                continue;
            }

            String[] parts = input.split("\\s+");
            try {
                long start = Long.parseLong(parts[0]);
                if (start == 0) {
                    System.out.println("Goodbye!");
                    break;
                }
                if (start < 0) {
                    System.out.println("The first parameter should be a natural number or zero.");
                    continue;
                }

                if (parts.length == 1) {
                    printSingleCard(start);
                    continue;
                }

                long count = Long.parseLong(parts[1]);
                if (count <= 0) {
                    System.out.println("The second parameter should be a natural number.");
                    continue;
                }

                List<String> queryProps = new ArrayList<>();
                for (int i = 2; i < parts.length; i++) {
                    queryProps.add(parts[i]);
                }

                if (validateRequest(queryProps)) {
                    printRange(start, count, queryProps);
                }

            } catch (NumberFormatException e) {
                System.out.println("The first parameter should be a natural number or zero.");
            }
        }
    }

    private static boolean validateRequest(List<String> props) {
        // 1. Unknown properties
        List<String> wrong = new ArrayList<>();
        for (String p : props) {
            String clean = p.startsWith("-") ? p.substring(1) : p;
            if (!ALL_PROPS.contains(clean)) wrong.add(p);
        }
        if (!wrong.isEmpty()) {
            System.out.printf("The propert%s %s %s wrong.%n",
                    wrong.size() > 1 ? "ies" : "y", wrong, wrong.size() > 1 ? "are" : "is");
            System.out.println("Available properties: " + ALL_PROPS);
            return false;
        }

        // 2. Mutually exclusive
        String[][] pairs = {
                {"EVEN", "ODD"}, {"-EVEN", "-ODD"},
                {"DUCK", "SPY"}, {"SQUARE", "SUNNY"},
                {"HAPPY", "SAD"}, {"-HAPPY", "-SAD"}
        };

        for (String[] pair : pairs) {
            if (props.contains(pair[0]) && props.contains(pair[1])) {
                System.out.printf("The request contains mutually exclusive properties: [%s, %s]%n", pair[0], pair[1]);
                return false;
            }
        }

        // 3. Direct opposites (Property and -Property)
        for (String p : props) {
            if (p.startsWith("-")) {
                if (props.contains(p.substring(1))) {
                    System.out.printf("The request contains mutually exclusive properties: [%s, %s]%n", p, p.substring(1));
                    return false;
                }
            }
        }
        return true;
    }

    private static void printRange(long start, long count, List<String> filters) {
        long found = 0;
        long current = start;
        while (found < count) {
            boolean match = true;
            for (String f : filters) {
                boolean isNegative = f.startsWith("-");
                String prop = isNegative ? f.substring(1) : f;
                boolean hasProp = checkProperty(current, prop);

                if ((!isNegative && !hasProp) || (isNegative && hasProp)) {
                    match = false;
                    break;
                }
            }

            if (match) {
                List<String> active = new ArrayList<>();
                for (String p : ALL_PROPS) {
                    if (checkProperty(current, p)) active.add(p.toLowerCase());
                }
                System.out.printf("%,d is %s%n", current, String.join(", ", active));
                found++;
            }
            current++;
        }
    }

    private static void printSingleCard(long n) {
        System.out.printf("Properties of %,d%n", n);
        for (String p : ALL_PROPS) {
            System.out.printf("%12s: %b%n", p.toLowerCase(), checkProperty(n, p));
        }
    }

    private static boolean checkProperty(long n, String prop) {
        switch (prop) {
            case "EVEN": return n % 2 == 0;
            case "ODD": return n % 2 != 0;
            case "BUZZ": return n % 7 == 0 || n % 10 == 7;
            case "DUCK": return String.valueOf(n).contains("0");
            case "PALINDROMIC":
                String s = String.valueOf(n);
                return s.equals(new StringBuilder(s).reverse().toString());
            case "GAPFUL":
                String gs = String.valueOf(n);
                if (gs.length() < 3) return false;
                return n % Long.parseLong("" + gs.charAt(0) + gs.charAt(gs.length()-1)) == 0;
            case "SPY":
                long sum = 0, prod = 1, t = n;
                while (t > 0) { sum += t % 10; prod *= t % 10; t /= 10; }
                return sum == prod;
            case "SQUARE":
                long sqrt = (long) Math.sqrt(n);
                return sqrt * sqrt == n;
            case "SUNNY":
                long sqrtS = (long) Math.sqrt(n + 1);
                return sqrtS * sqrtS == n + 1;
            case "JUMPING":
                String js = String.valueOf(n);
                for (int i = 0; i < js.length() - 1; i++) {
                    if (Math.abs(js.charAt(i) - js.charAt(i+1)) != 1) return false;
                }
                return true;
            case "HAPPY": return isHappy(n);
            case "SAD": return !isHappy(n);
            default: return false;
        }
    }

    private static boolean isHappy(long n) {
        Set<Long> seen = new HashSet<>();
        while (n != 1 && !seen.contains(n)) {
            seen.add(n);
            long sum = 0;
            while (n > 0) {
                sum += Math.pow(n % 10, 2);
                n /= 10;
            }
            n = sum;
        }
        return n == 1;
    }

    private static void printInstructions() {
        System.out.println("Supported requests:");
        System.out.println("- enter a natural number to know its properties;");
        System.out.println("- enter two natural numbers to obtain the properties of the list;");
        System.out.println("- two natural numbers and properties to search for;");
        System.out.println("- a property preceded by minus must not be present in numbers;");
        System.out.println("- separate the parameters with one space;");
        System.out.println("- enter 0 to exit.");
    }
}