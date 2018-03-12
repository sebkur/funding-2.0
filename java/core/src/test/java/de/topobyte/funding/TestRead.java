package de.topobyte.funding;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import de.topobyte.system.utils.SystemPaths;

public class TestRead
{

	public static void main(String[] args) throws IOException
	{
		Path path = SystemPaths.CWD.getParent().getParent()
				.resolve("data/funding-sources.csv");
		List<Entry> entries = Reader.read(path);
		for (Entry entry : entries) {
			System.out.println(String.format("funder: %s: %s",
					entry.getFunder(), entry.getContact()));
		}
	}

}
