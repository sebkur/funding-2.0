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

import de.topobyte.funding.html.RunGenerateHtml;
import de.topobyte.utilities.apache.commons.cli.commands.ArgumentParser;
import de.topobyte.utilities.apache.commons.cli.commands.ExeRunner;
import de.topobyte.utilities.apache.commons.cli.commands.ExecutionData;
import de.topobyte.utilities.apache.commons.cli.commands.RunnerException;
import de.topobyte.utilities.apache.commons.cli.commands.options.DelegateExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptionsFactory;

public class FundingCli
{

	public static ExeOptionsFactory OPTIONS_FACTORY = new ExeOptionsFactory() {

		@Override
		public ExeOptions createOptions()
		{
			DelegateExeOptions options = new DelegateExeOptions();
			options.addCommand("list", RunList.OPTIONS_FACTORY, RunList.class);
			options.addCommand("create-html", RunGenerateHtml.OPTIONS_FACTORY,
					RunGenerateHtml.class);
			options.addCommand("clean-up-whitespace",
					RunCleanupWhitespace.OPTIONS_FACTORY,
					RunCleanupWhitespace.class);
			options.addCommand("find-strange-characters",
					RunFindStrangeCharacters.OPTIONS_FACTORY,
					RunFindStrangeCharacters.class);
			options.addCommand("sort", RunSort.OPTIONS_FACTORY, RunSort.class);
			options.addCommand("export-funding-sources",
					RunExportFundingSources.OPTIONS_FACTORY,
					RunExportFundingSources.class);
			return options;
		}

	};

	public static void main(String[] args) throws RunnerException
	{
		String name = "funding-cli";

		ExeOptions options = OPTIONS_FACTORY.createOptions();
		ArgumentParser parser = new ArgumentParser(name, options);

		if (args.length == 0) {
			System.out.println("Funding 2.0 Command Line Interface");
			System.out.println();
			options.usage(name);
			System.exit(1);
		}

		ExecutionData data = parser.parse(args);
		if (data != null) {
			ExeRunner.run(data);
		}
	}

}
