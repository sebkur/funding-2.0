package de.topobyte.funding.html;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import de.topobyte.funding.Entry;
import de.topobyte.jsoup.Bootstrap;
import de.topobyte.jsoup.ElementUtil;
import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.HtmlBuilder;
import de.topobyte.jsoup.components.A;
import de.topobyte.jsoup.components.bootstrap3.Container;
import de.topobyte.jsoup.nodes.Element;
import de.topobyte.webpaths.WebPath;

public class IndexGenerator extends BaseGenerator
{

	public void create(Path path, WebPath webPath, List<Entry> entries)
			throws IOException
	{
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		setupHeader(webPath, htmlBuilder);

		Element body = htmlBuilder.getBody();
		addMenu(webPath, body);

		Container content = body.ac(Bootstrap.container());

		for (Entry entry : entries) {
			A link = content.ac(HTML
					.a(webPath.relativize(Site.pathEntry(entry)).toString()));
			link.ac(HTML.h1(entry.getFunder()));
			content.appendText(entry.getInfo());
			content.ac(HTML.br());
			ElementUtil.appendFragmentBody(content, entry.getContact());
			SiteFragments.appendTags(webPath, content, entry.getTags());
		}

		htmlBuilder.write(path);
	}

}
