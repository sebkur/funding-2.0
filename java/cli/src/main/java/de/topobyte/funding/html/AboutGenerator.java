package de.topobyte.funding.html;

import java.io.IOException;
import java.nio.file.Path;

import de.topobyte.funding.markdown.Markdown;
import de.topobyte.jsoup.Bootstrap;
import de.topobyte.jsoup.HtmlBuilder;
import de.topobyte.jsoup.components.bootstrap3.Container;
import de.topobyte.jsoup.nodes.Element;
import de.topobyte.webpaths.WebPath;

public class AboutGenerator extends BaseGenerator
{

	public void create(Path path, WebPath webPath) throws IOException
	{
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		setupHeader(webPath, htmlBuilder);

		Element body = htmlBuilder.getBody();
		addMenu(webPath, body);

		Container content = body.ac(Bootstrap.container());

		Markdown.renderResource(content, "about.md");

		addFooter(webPath, body);

		htmlBuilder.write(path);
	}

}
