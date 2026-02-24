# Amazing Numbers 🧮

A robust Java-based command-line application that performs deep number theory analysis. This project allows users to explore various mathematical properties of numbers using a flexible query system and advanced filtering capabilities.

## 🚀 Features
- **Comprehensive Analysis**: Detects 12 distinct mathematical properties:
  - `Even`, `Odd`, `Buzz`, `Duck`, `Palindromic`, `Gapful`, `Spy`, `Square`, `Sunny`, `Jumping`, `Happy`, and `Sad`.
- **Advanced Query Engine**: 
  - Search for a specific number's properties.
  - Generate a list of consecutive numbers with their properties.
  - Search for numbers matching multiple properties simultaneously.
- **Negative Filtering (Stage 8)**: Exclude specific properties using the `-` prefix (e.g., `100 5 -EVEN -GAPFUL`).
- **Conflict Validation**: Intelligent error handling for mutually exclusive properties (e.g., `HAPPY -HAPPY` or `ODD EVEN`).
- **Interactive CLI**: Dynamic user instructions and formatted output for better readability.

## 🛠️ Technical Highlights
- **Cycle Detection**: Implemented using `java.util.HashSet` to identify "Happy" numbers and prevent infinite loops in sequence generation.
- **State Management**: Developed a robust validation logic to handle complex search queries and property exclusions.
- **Clean Code**: Modular design using a central `checkProperty` logic and switch expressions for maintainability.

## 📋 How to Run

### Prerequisites
- **JDK 17** or higher installed on your system.

### Installation
1. Clone the repository:
   ```bash
   git clone [https://github.com/albajoseph/Amazing-Numbers.git](https://github.com/albajoseph/AmazingNumbers.git)
   ```
### Execution
Compile and run the application:
   ```bash
   javac numbers/Main.java
   java numbers.Main
   ```
###🎮 Example Requests

| Request | Description |
| :--- | :--- |
| `13` | Shows all properties for the number 13 (Happy, Odd, etc.) |
| `1 10` | Lists properties for numbers 1 through 10. |
| `10 5 JUMPING` | Finds the first 5 Jumping numbers starting from 10. |
| `1 5 HAPPY -EVEN` | Finds the first 5 Happy Odd numbers. |
| `0` | Exits the program. |
