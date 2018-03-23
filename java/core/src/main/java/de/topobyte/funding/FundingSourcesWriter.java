// Copyright 2018 Sebastian Kuerten
//
// This file is part of funding-2.0.
//
// funding-2.0 is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// funding-2.0 is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with funding-2.0. If not, see <http://www.gnu.org/licenses/>.

package de.topobyte.funding;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

public class FundingSourcesWriter
{

	private static final String keyFunder = "Funder";
	private static final String keyInfo = "Info";
	private static final String keyContact = "Contact";

	public static void write(Path path, List<Entry> entries) throws IOException
	{
		String header[] = new String[] { keyFunder, keyInfo, keyContact };
		Map<String, String> map = new HashMap<>();

		ICsvMapWriter mapWriter = null;
		try {
			BufferedWriter writer = Files.newBufferedWriter(path);
			mapWriter = new CsvMapWriter(writer,
					CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE);

			mapWriter.writeHeader(header);

			for (Entry entry : entries) {
				map.put(keyFunder, entry.getFunder());
				map.put(keyInfo, entry.getInfo());
				map.put(keyContact, link(entry.getContact()));
				mapWriter.write(map, header);
			}
		} finally {
			if (mapWriter != null) {
				mapWriter.close();
			}
		}
	}

	private static String link(String contact)
	{
		return String.format("<a href=\"%s\">%s</a>", contact, contact);
	}

}
