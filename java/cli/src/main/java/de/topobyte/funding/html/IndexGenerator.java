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
import de.topobyte.jsoup.components.P;
import de.topobyte.jsoup.components.bootstrap3.Container;
import de.topobyte.jsoup.components.bootstrap3.Label;
import de.topobyte.jsoup.components.bootstrap3.Label.Type;
import de.topobyte.jsoup.nodes.Element;
import de.topobyte.webpaths.WebPath;

public class IndexGenerator extends BaseGenerator
{

	public void create(Path path, WebPath webPath, List<Entry> entries)
			throws IOException
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

	private void appendTags(WebPath webPath, Container content,
			List<String> tags)
	{
		P p = content.ac(HTML.p());
		p.attr("style", "padding-top:0.5em");
		for (String tag : tags) {
			WebPath path = Site.pathTag(tag);
			WebPath relative = webPath.relativize(path);
			A link = HTML.a(relative.toString());
			Label label = link.ac(Bootstrap.label(Type.PRIMARY));
			label.appendText(tag);
			p.ac(link);
		}
	}

}
