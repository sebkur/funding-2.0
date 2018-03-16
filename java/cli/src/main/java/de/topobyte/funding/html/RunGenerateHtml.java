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

package de.topobyte.funding.html;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Options;

import de.topobyte.cssutils.CssFileWriter;
import de.topobyte.cssutils.css.CssEntry;
import de.topobyte.cssutils.css.CssFile;
import de.topobyte.cssutils.css.Property;
import de.topobyte.funding.Entry;
import de.topobyte.funding.Util;
import de.topobyte.jsoup.Bootstrap;
import de.topobyte.jsoup.Bootstrap3;
import de.topobyte.jsoup.ElementUtil;
import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.HtmlBuilder;
import de.topobyte.jsoup.components.A;
import de.topobyte.jsoup.components.P;
import de.topobyte.jsoup.components.bootstrap3.Container;
import de.topobyte.jsoup.components.bootstrap3.Label;
import de.topobyte.jsoup.components.bootstrap3.Label.Type;
import de.topobyte.jsoup.components.bootstrap3.Menu;
import de.topobyte.jsoup.nodes.Element;
import de.topobyte.melon.paths.PathUtil;
import de.topobyte.utilities.apache.commons.cli.OptionHelper;
import de.topobyte.utilities.apache.commons.cli.commands.args.CommonsCliArguments;
import de.topobyte.utilities.apache.commons.cli.commands.options.CommonsCliExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptionsFactory;
import de.topobyte.webpaths.NioPaths;
import de.topobyte.webpaths.WebPath;
import de.topobyte.webpaths.WebPaths;

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

	private static final WebPath PATH_INDEX = WebPaths.get("index.html");
	private static final WebPath PATH_ABOUT = WebPaths.get("about.html");
	private static final WebPath PATH_STYLES = WebPaths.get("style.css");
	private static final WebPath PATH_TAGS = WebPaths.get("tags/");

	private static WebPath pathTag(String tag)
	{
		return PATH_TAGS.resolve(String.format("%s.html", tag));
	}

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

		List<Entry> entries = Util.readFundingSources();

		Path pathIndex = NioPaths.resolve(pathOutput, PATH_INDEX);
		createIndex(pathIndex, PATH_INDEX, entries);

		Path pathAbout = NioPaths.resolve(pathOutput, PATH_ABOUT);
		createAbout(pathAbout, PATH_ABOUT);

		Path pathStyles = NioPaths.resolve(pathOutput, PATH_STYLES);
		createCSS(pathStyles);

		Path pathTags = NioPaths.resolve(pathOutput, PATH_TAGS);
		Files.createDirectories(pathTags);

		Set<String> tags = collectTags(entries);
		for (String tag : tags) {
			WebPath webPath = pathTag(tag);
			List<Entry> withTag = filter(entries, tag);
			Path path = NioPaths.resolve(pathOutput, webPath);
			createIndex(path, webPath, withTag);
		}
	}

	private static Set<String> collectTags(List<Entry> entries)
	{
		Set<String> allTags = new HashSet<>();
		for (Entry entry : entries) {
			allTags.addAll(entry.getTags());
		}
		return allTags;
	}

	private static List<Entry> filter(List<Entry> entries, String tag)
	{
		List<Entry> filtered = new ArrayList<>();
		for (Entry entry : entries) {
			if (entry.getTags().contains(tag)) {
				filtered.add(entry);
			}
		}
		return filtered;
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
				"<link rel=\"stylesheet\" href=\"" + PATH_STYLES + "\">");
	}

	private static void addMenu(WebPath webPath, Element body)
	{
		Menu menu = new Menu();
		body.ac(menu);

		A brand = HTML.a(webPath.relativize(PATH_INDEX).toString());
		brand.appendText("Funding 2.0");

		A link = HTML.a(webPath.relativize(PATH_ABOUT).toString());
		link.appendText("About");

		menu.addBrand(brand);
		menu.addMain(link, false);
	}

	private static void createIndex(Path path, WebPath webPath,
			List<Entry> entries) throws IOException
	{
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		setupHeader(htmlBuilder);

		Element body = htmlBuilder.getBody();
		addMenu(webPath, body);

		Container content = body.ac(Bootstrap.container());

		for (Entry entry : entries) {
			content.ac(HTML.h1(entry.getFunder()));
			content.appendText(entry.getInfo());
			content.ac(HTML.br());
			ElementUtil.appendFragmentBody(content, entry.getContact());
			List<String> tags = entry.getTags();
			if (!tags.isEmpty()) {
				appendTags(webPath, content, tags);
			}
		}

		htmlBuilder.write(path);
	}

	private static void appendTags(WebPath webPath, Container content,
			List<String> tags)
	{
		P p = content.ac(HTML.p());
		p.attr("style", "padding-top:0.5em");
		for (String tag : tags) {
			WebPath path = pathTag(tag);
			WebPath relative = webPath.relativize(path);
			A link = HTML.a(relative.toString());
			Label label = link.ac(Bootstrap.label(Type.PRIMARY));
			label.appendText(tag);
			p.ac(link);
		}
	}

	private static void createAbout(Path path, WebPath webPath)
			throws IOException
	{
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		setupHeader(htmlBuilder);

		Element body = htmlBuilder.getBody();
		addMenu(webPath, body);

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
