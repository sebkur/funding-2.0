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

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.cli.Options;

import de.topobyte.utilities.apache.commons.cli.commands.args.CommonsCliArguments;
import de.topobyte.utilities.apache.commons.cli.commands.options.CommonsCliExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptionsFactory;

public class RunCleanupWhitespace
{

	public static ExeOptionsFactory OPTIONS_FACTORY = new ExeOptionsFactory() {

		@Override
		public ExeOptions createOptions()
		{
			Options options = new Options();
			return new CommonsCliExeOptions(options, "[options]");
		}

	};

	public static void main(String name, CommonsCliArguments arguments)
			throws Exception
	{
		String repo = System.getProperty("repo");
		Path pathRepo = Paths.get(repo);

		System.out.println("Cleaning up whitespace");

		Path path = pathRepo.resolve("data/funding-sources.csv");
		List<Entry> entries = Reader.read(path);

		for (Entry entry : entries) {
			trimFunder(entry);
			trimInfo(entry);
			trimContact(entry);
		}

		Writer.write(path, entries);
	}

	private static void trimFunder(Entry entry)
	{
		String value = entry.getFunder();
		String trimmed = value.trim();
		if (!value.equals(trimmed)) {
			entry.setFunder(trimmed);
		}
	}

	private static void trimInfo(Entry entry)
	{
		String value = entry.getInfo();
		String trimmed = value.trim();
		if (!value.equals(trimmed)) {
			entry.setInfo(trimmed);
		}
	}

	private static void trimContact(Entry entry)
	{
		String value = entry.getContact();
		String trimmed = value.trim();
		if (!value.equals(trimmed)) {
			entry.setContact(trimmed);
		}
	}

}
