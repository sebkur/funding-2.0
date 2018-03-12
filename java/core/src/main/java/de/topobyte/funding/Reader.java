package de.topobyte.funding;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.supercsv.io.CsvMapReader;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.prefs.CsvPreference;

public class Reader
{

	private static final String keyFunder = "Funder";
	private static final String keyInfo = "Info";
	private static final String keyContact = "Contact";

	public static List<Entry> read(Path path) throws IOException
	{
		List<Entry> entries = new ArrayList<>();

		ICsvMapReader csvReader = null;
		try {
			BufferedReader reader = Files.newBufferedReader(path);
			csvReader = new CsvMapReader(reader,
					CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);

			final String[] header = csvReader.getHeader(true);

			Map<String, String> map;
			while ((map = csvReader.read(header)) != null) {
				String funder = map.get(keyFunder);
				String info = map.get(keyInfo);
				String contact = map.get(keyContact);
				entries.add(new Entry(funder, info, contact));
			}
		} finally {
			if (csvReader != null) {
				csvReader.close();
			}
		}

		return entries;
	}

}
