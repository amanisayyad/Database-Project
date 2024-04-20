import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.util.StringConverter;

public class MyDateConverter extends StringConverter<LocalDate> {
	private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Override
	public String toString(LocalDate date) {
		if (date != null) {
			return dateFormatter.format(date);
		} else {
			return "";
		}
	}

	@Override
	public LocalDate fromString(String string) {
		if (string != null && !string.isEmpty()) {
			return LocalDate.parse(string, dateFormatter);
		} else {
			return null;
		}
	}
}
