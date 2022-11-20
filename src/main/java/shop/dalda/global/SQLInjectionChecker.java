package shop.dalda.global;

import lombok.extern.slf4j.Slf4j;
import shop.dalda.exception.SQLInjectionException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SQLInjectionChecker {
    private static final Pattern SpecialChars = Pattern.compile("['\"\\-#()@;=*/+]");
    private static final String regex = "(UNION|SELECT|FROM|WHERE)";
    private static final Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

    public static String checkSQLInjection(String input) {
        String tmpInput = SpecialChars.matcher(input).replaceAll("");
        Matcher matcher = pattern.matcher(tmpInput);

        if (matcher.find()) {
            log.warn("Input : " + input + " = SQL Injection 의심 입력값");
            throw new SQLInjectionException();
        }

        return tmpInput;
    }
}