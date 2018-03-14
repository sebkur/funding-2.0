
package de.topobyte.funding;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.cli.Options;

import de.topobyte.utilities.apache.commons.cli.commands.args.CommonsCliArguments;
import de.topobyte.utilities.apache.commons.cli.commands.options.CommonsCliExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptionsFactory;

public class RunFindStrangeCharacters
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
		System.out.println("Finding strange characters");

		List<Entry> entries = Util.readFundingSources();

		for (Entry entry : entries) {
			specialChars(entry);
		}

		Util.writeFundingSources(entries);
	}

	private static void specialChars(Entry entry)
	{
		Pattern pattern = Pattern
				.compile("([^\\x20-\\x7Eéöäüß€„“’—–…\\xa0\\x0a])");
		Matcher matcher = pattern.matcher(entry.getInfo());
		while (matcher.find()) {
			String chars = matcher.group(1);
			System.out.println("'" + chars + "'");
			for (int i = 0; i < chars.length(); i++) {
				char c = chars.charAt(i);
				System.out.println(Integer.toHexString(c));
			}
		}
	}

}
