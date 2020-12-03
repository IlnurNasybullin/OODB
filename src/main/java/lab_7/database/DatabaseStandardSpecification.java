package lab_7.database;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseStandardSpecification {
    public static String getStandardName(String name) {
        Pattern pattern = Pattern.compile("([A-Z]++)");
        Matcher matcher = pattern.matcher(name);

        String standardName = matcher.replaceAll(matchResult -> "_" + matchResult.group().toLowerCase(Locale.ROOT));
        if (standardName.startsWith("_")) {
            standardName = standardName.replaceFirst("_", "");
        }

        return standardName;
    }
}
