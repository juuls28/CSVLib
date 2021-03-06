package helper;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 *CSVWriter class.
 * Helps writing entries in CSV files.
 * @author Julius
 */
public class CSVWriter {
    private static final char DEFAULT_SEPARATOR = ';';

    /**
     *wirteLine Method.
     * Wirtes lines in a CSV file.
     * @param w {@link Writer Writer}
     * @param values List of {@link String}
     * @throws IOException
     */
    public static void writeLine(Writer w, List<String> values) throws IOException {
        writeLine(w, values, DEFAULT_SEPARATOR, ' ');
    }

    /**
     *wirteLine Method.
     * Wirtes lines in a CSV file.
     * @param w {@link Writer Writer}
     * @param values List of {@link String}
     * @param separators {@link Character Character} separates the values
     * @throws IOException
     */
    public static void writeLine(Writer w, List<String> values, char separators) throws IOException {
        writeLine(w, values, separators, ' ');
    }

    private static String followCVSformat(String value) {

        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;

    }

    /**
     wirteLine Method.
     * Wirtes lines in a CSV file.
     * @param w {@link Writer Writer}
     * @param values List of {@link String}
     * @param separators {@link Character Character} separates the values
     * @param customQuote {@link Character Character} separates the entires
     * @throws IOException
     */
    public static void writeLine(Writer w, List<String> values, char separators, char customQuote) throws IOException {

        boolean first = true;

        //default customQuote is empty

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuilder sb = new StringBuilder();
        for (String value : values) {
            if (!first) {
                sb.append(separators);
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value));
            } else {
                sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
            }

            first = false;
        }
        sb.append("\n");
        w.append(sb.toString());

    }
}