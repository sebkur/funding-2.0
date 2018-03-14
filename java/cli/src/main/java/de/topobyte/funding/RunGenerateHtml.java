package de.topobyte.funding;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import de.topobyte.jsoup.Bootstrap;
import de.topobyte.jsoup.Bootstrap3;
import de.topobyte.jsoup.ElementUtil;
import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.HtmlBuilder;
import de.topobyte.jsoup.components.A;
import de.topobyte.jsoup.components.bootstrap3.Container;
import de.topobyte.jsoup.components.bootstrap3.Menu;
import de.topobyte.jsoup.nodes.Element;
import de.topobyte.utilities.apache.commons.cli.OptionHelper;
import de.topobyte.utilities.apache.commons.cli.commands.args.CommonsCliArguments;
import de.topobyte.utilities.apache.commons.cli.commands.options.CommonsCliExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptionsFactory;

public class RunGenerateHtml
{

	private static final String OPTION_OUTPUT = "output";

	public static ExeOptionsFactory OPTIONS_FACTORY = new ExeOptionsFactory() {

		@Override
		public ExeOptions createOptions()
		{
			Options options = new Options();
			// @formatter:off
			OptionHelper.addL(options, OPTION_OUTPUT, true, true, "file", "an output image file");
			// @formatter:on
			return new CommonsCliExeOptions(options, "[options]");
		}

	};

	public static void main(String name, CommonsCliArguments arguments)
			throws Exception
	{
		CommandLine line = arguments.getLine();

		String argOutput = line.getOptionValue(OPTION_OUTPUT);
		Path pathOutput = Paths.get(argOutput);

		System.out.println("Generating HTML");
		System.out.println("Output: " + pathOutput);

		String repo = System.getProperty("repo");
		Path pathRepo = Paths.get(repo);

		HtmlBuilder htmlBuilder = new HtmlBuilder();

		Element head = htmlBuilder.getHead();
		htmlBuilder.getTitle().appendText("Funding 2.0");

		Bootstrap3.addCdnHeaders(head);

		Element body = htmlBuilder.getBody();

		Menu menu = new Menu();
		body.ac(menu);

		A brand = HTML.a("");
		brand.appendText("Funding");

		A link = HTML.a("");
		link.appendText("About");

		menu.addBrand(brand);
		menu.addMain(link, false);

		Container content = body.ac(Bootstrap.container());

		Path path = pathRepo.resolve("data/funding-sources.csv");
		List<Entry> entries = Reader.read(path);
		for (Entry entry : entries) {
			content.ac(HTML.h1(entry.getFunder()));
			content.appendText(entry.getInfo());
			content.ac(HTML.br());
			ElementUtil.appendFragmentBody(content, entry.getContact());
		}

		htmlBuilder.write(pathOutput);
	}

}
