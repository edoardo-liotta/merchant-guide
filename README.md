#Merchant's Guide to the Galaxy

##Introduction
This Spring Boot project is a solution to the common Merchant's Guide problem. The proposed approach looks for an input file from command-line arguments and generates an output "-result.txt" file next to it. 

##Usage
From the project's home folder, run './mvnw spring-boot:run -Dspring-boot.run.arguments="-f path-to-file.txt"' (on Windows, use 'mvnw' command). File path can be either absolute or relative, and can make use of ~ as user home. At this stage, the application cannot be run in interactive mode.
The Guide currently interprets four commands:
- Roman assignment: an Intergalactic keyword is assigned a Roman numeral, e.g. 'glob is I' or 'pish means V'

- Material assignment: a material is assigned its Credits value, starting from an Intergalactic number and its Credits value, e.g. "glob glob units of Silver are worth 30 Credits" will assign a unit value of 15 Credits to Silver, given that 'glob' is a keyword for I
    - Every Intergalactic number used in a Material assignment command should be assigned a Roman in advance.

- Numeral query: converts Intergalactic to numeral, e.g. "how much is glob glob glob ?" returns "glob glob glob is 3", given that 'glob' is a keyword for I
    - Every Intergalactic number used in a Numeral query command should be assigned a Roman in advance.

- Credit query: converts an Intergalactic value for a given material to Credit numeral, e.g. "how many Credits is glob glob glob Silver ?", given that the unit value for Silver is 50 Credits and 'glob' is a keyword for I
    - Every Intergalactic number used in a Numeral query command should be assigned a Roman in advance, and Credits value for the given material should be assigned in advance.

##Notes and assumptions
- Lines are read one by one using java.nio.Files
- Valid assignment commands (either roman or credit) do not produce any output
- Query commands end with a question mark, separated by the last word by one space at least
- Roman numerals should be written in Capital, e.g. 'glob is I'
- Commands are case-sensitive, and so are Intergalactic keywords and Materials
- Material names can contain spaces, Intergalactic numbers may not
- Valid Intergalactic numbers may not exceed 3999 (MMMCMXCIX)

##Technology breakdown
This project makes use of Java 8 and Spring Boot 2. Annotation configuration was used and JUnit test classes are provided. Integration tests make use of Spring Boot context. Unit tests cover 91% of code lines, according to IntelliJ Code Coverage plugin.

##External dependencies (other to the ones provided by Spring Boot)
- Apache Commons Lang3 for String Utilities
- Apache Commons I/O for File name utilities, in order to remove the extension from the input file
- Apache Commons CLI for tooling the command-line options
- [Peter Reutemann's RomanNumerals4J](https://github.com/fracpete/romannumerals4j), as this Formatter covers the Roman-Numeral conversion (test cases provided in source GitHub)

##Known limitations
- Size of the input file should be fair, since CommandFileManager and GuideApplication currently ".collect"s the interpreter outputs before flushing them to file. This might be perfected later, should the need be raised.

