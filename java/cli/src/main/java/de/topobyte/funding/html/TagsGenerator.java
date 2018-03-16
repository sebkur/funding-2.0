package de.topobyte.funding.html;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.topobyte.funding.Entry;
import de.topobyte.jsoup.Bootstrap;
import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.HtmlBuilder;
import de.topobyte.jsoup.components.A;
import de.topobyte.jsoup.components.bootstrap3.Container;
import de.topobyte.jsoup.components.bootstrap3.Label;
import de.topobyte.jsoup.components.bootstrap3.Label.Type;
import de.topobyte.jsoup.nodes.Element;
import de.topobyte.webpaths.WebPath;

public class TagsGenerator extends BaseGenerator
{

	public void create(Path path, WebPath webPath, List<Entry> entries)
			throws IOException
	{
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		setupHeader(htmlBuilder);

		Element body = htmlBuilder.getBody();
		addMenu(webPath, body);

		Container content = body.ac(Bootstrap.container());

		Set<String> allTags = new HashSet<>();

		for (Entry entry : entries) {
			allTags.addAll(entry.getTags());
		}

		List<String> list = new ArrayList<>(allTags);
		Collections.sort(list);

		content.ac(HTML.h1("Tags"));

		for (String tag : list) {
			addTag(webPath, content, tag, Type.PRIMARY);
		}

		addTag(webPath, content, Site.TAG_UNTAGGED, Type.WARNING);

		htmlBuilder.write(path);
	}

	private void addTag(WebPath webPath, Container content, String tag,
			Type type)
	{
		WebPath path = Site.pathTag(tag);
		WebPath relative = webPath.relativize(path);
		A link = HTML.a(relative.toString());
		Label label = link.ac(Bootstrap.label(type));
		label.appendText(tag);
		content.ac(link);
	}

}
