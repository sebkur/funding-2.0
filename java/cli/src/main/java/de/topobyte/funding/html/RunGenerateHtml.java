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
import de.topobyte.melon.paths.PathUtil;
import de.topobyte.utilities.apache.commons.cli.OptionHelper;
import de.topobyte.utilities.apache.commons.cli.commands.args.CommonsCliArguments;
import de.topobyte.utilities.apache.commons.cli.commands.options.CommonsCliExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptionsFactory;
import de.topobyte.webpaths.NioPaths;
import de.topobyte.webpaths.WebPath;

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

		IndexGenerator indexGenerator = new IndexGenerator();
		Path pathIndex = NioPaths.resolve(pathOutput, Site.PATH_INDEX);
		indexGenerator.create(pathIndex, Site.PATH_INDEX, entries);

		AboutGenerator aboutGenerator = new AboutGenerator();
		Path pathAbout = NioPaths.resolve(pathOutput, Site.PATH_ABOUT);
		aboutGenerator.create(pathAbout, Site.PATH_ABOUT);

		Path pathStyles = NioPaths.resolve(pathOutput, Site.PATH_STYLES);
		createCSS(pathStyles);

		Path pathTags = NioPaths.resolve(pathOutput, Site.PATH_TAGS);
		Files.createDirectories(pathTags);

		Set<String> tags = collectTags(entries);
		for (String tag : tags) {
			WebPath webPath = Site.pathTag(tag);
			List<Entry> withTag = filter(entries, tag);
			Path path = NioPaths.resolve(pathOutput, webPath);

			IndexGenerator tagIndexGernator = new IndexGenerator();
			tagIndexGernator.create(path, webPath, withTag);
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

}
