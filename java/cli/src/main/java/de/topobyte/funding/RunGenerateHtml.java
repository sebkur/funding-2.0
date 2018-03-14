package de.topobyte.funding;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import de.topobyte.cssutils.CssFileWriter;
import de.topobyte.cssutils.css.CssEntry;
import de.topobyte.cssutils.css.CssFile;
import de.topobyte.cssutils.css.Property;
import de.topobyte.jsoup.Bootstrap;
import de.topobyte.jsoup.Bootstrap3;
import de.topobyte.jsoup.ElementUtil;
import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.HtmlBuilder;
import de.topobyte.jsoup.components.A;
import de.topobyte.jsoup.components.P;
import de.topobyte.jsoup.components.bootstrap3.Container;
import de.topobyte.jsoup.components.bootstrap3.Menu;
import de.topobyte.jsoup.nodes.Element;
import de.topobyte.melon.paths.PathUtil;
import de.topobyte.utilities.apache.commons.cli.OptionHelper;
import de.topobyte.utilities.apache.commons.cli.commands.args.CommonsCliArguments;
import de.topobyte.utilities.apache.commons.cli.commands.options.CommonsCliExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptionsFactory;

public class RunGenerateHtml
{

	private static final String OPTION_OUTPUT = "output";
	private static final String OPTION_FORCE = "force";

	public static ExeOptionsFactory OPTIONS_FACTORY = new ExeOptionsFactory() {

		@Override
		public ExeOptions createOptions()
		{
			Options options = new Options();
			// @formatter:off
			OptionHelper.addL(options, OPTION_OUTPUT, true, true, "file", "an output directory");
			OptionHelper.add(options, "f", OPTION_FORCE, false, false, "file", "overwrite existing files");
			// @formatter:on
			return new CommonsCliExeOptions(options, "[options]");
		}

	};

	private static final String FILENAME_STYLES = "style.css";

	public static void main(String name, CommonsCliArguments arguments)
			throws Exception
	{
		CommandLine line = arguments.getLine();

		String argOutput = line.getOptionValue(OPTION_OUTPUT);
		Path pathOutput = Paths.get(argOutput);

		boolean force = line.hasOption(OPTION_FORCE);

		System.out.println("Generating HTML");
		System.out.println("Output: " + pathOutput);

		if (Files.exists(pathOutput) && !Files.isDirectory(pathOutput)) {
			System.out.println("Specified output path is not a directory");
			System.exit(1);
		}
		if (Files.exists(pathOutput) && !PathUtil.list(pathOutput).isEmpty()) {
			if (!force) {
				System.out.println(
						"Specified output path exists, but is not empty");
				System.exit(1);
			}
		}
		if (!Files.exists(pathOutput)) {
			Files.createDirectories(pathOutput);
		}
		if (!Files.exists(pathOutput)) {
			System.out.println("Unable to create output directory");
			System.exit(1);
		}

		String repo = System.getProperty("repo");
		Path pathRepo = Paths.get(repo);
		Path path = pathRepo.resolve("data/funding-sources.csv");
		List<Entry> entries = Reader.read(path);

		Path pathIndex = pathOutput.resolve("index.html");
		createIndex(pathIndex, entries);

		Path pathAbout = pathOutput.resolve("about.html");
		createAbout(pathAbout);

		Path pathStyles = pathOutput.resolve(FILENAME_STYLES);
		createCSS(pathStyles);
	}

	private static void createCSS(Path path) throws IOException
	{
		CssFile cssFile = new CssFile();
		CssEntry a = new CssEntry("body");
		a.addProperty(new Property("overflow-wrap", "break-word"));
		cssFile.addEntry(a);

		CssFileWriter.write(cssFile, path);
	}

	private static void setupHeader(HtmlBuilder htmlBuilder)
	{
		Element head = htmlBuilder.getHead();
		htmlBuilder.getTitle().appendText("Funding 2.0");

		Bootstrap3.addCdnHeaders(head);

		ElementUtil.appendFragmentHead(head,
				"<link rel=\"stylesheet\" href=\"" + FILENAME_STYLES + "\">");
	}

	private static void addMenu(Element body)
	{
		Menu menu = new Menu();
		body.ac(menu);

		A brand = HTML.a("index.html");
		brand.appendText("Funding 2.0");

		A link = HTML.a("about.html");
		link.appendText("About");

		menu.addBrand(brand);
		menu.addMain(link, false);
	}

	private static void createIndex(Path path, List<Entry> entries)
			throws IOException
	{
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		setupHeader(htmlBuilder);

		Element body = htmlBuilder.getBody();
		addMenu(body);

		Container content = body.ac(Bootstrap.container());

		for (Entry entry : entries) {
			content.ac(HTML.h1(entry.getFunder()));
			content.appendText(entry.getInfo());
			content.ac(HTML.br());
			ElementUtil.appendFragmentBody(content, entry.getContact());
		}

		htmlBuilder.write(path);
	}

	private static void createAbout(Path path) throws IOException
	{
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		setupHeader(htmlBuilder);

		Element body = htmlBuilder.getBody();
		addMenu(body);

		Container content = body.ac(Bootstrap.container());

		content.ac(HTML.h1("Funding 2.0"));

		P p = content.ac(HTML.p());
		p.appendText(
				"A crowd-sourced database of alternative funding sources.");

		p = content.ac(HTML.p());
		p.appendText("Contribute to this collection on GitHub: ");
		p.ac(HTML.a("https://github.com/sebkur/funding-2.0",
				"sebkur/funding-2.0"));
		p.appendText(".");

		content.ac(HTML.h2("Credits"));
		p = content.ac(HTML.p());

		p.appendText("This project is based on a data set provided by the ");
		p.ac(HTML.a("https://renewablefreedom.org/",
				"Renewable Freedom Foundation"));
		p.appendText(" that is available on GitHub: ");
		p.ac(HTML.a("https://github.com/renewablefreedom/funding-sources",
				"renewablefreedom/funding-sources"));
		p.appendText(".");

		htmlBuilder.write(path);
	}

}
