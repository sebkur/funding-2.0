package de.topobyte.funding.html;

import java.io.IOException;
import java.nio.file.Path;

import de.topobyte.funding.Entry;
import de.topobyte.jsoup.Bootstrap;
import de.topobyte.jsoup.ElementUtil;
import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.HtmlBuilder;
import de.topobyte.jsoup.components.A;
import de.topobyte.jsoup.components.bootstrap3.Container;
import de.topobyte.jsoup.nodes.Element;
import de.topobyte.webpaths.WebPath;

public class EntryGenerator extends BaseGenerator
{

	public void create(Path path, WebPath webPath, Entry entry)
			throws IOException
	{
		HtmlBuilder htmlBuilder = new HtmlBuilder();
		setupHeader(htmlBuilder);

		Element body = htmlBuilder.getBody();
		addMenu(webPath, body);

		Container content = body.ac(Bootstrap.container());

		content.ac(HTML.h1(entry.getFunder()));
		content.appendText(entry.getInfo());
		content.ac(HTML.br());
		ElementUtil.appendFragmentBody(content, entry.getContact());
		SiteFragments.appendTags(webPath, content, entry.getTags());

		A linkFeedback = HTML
				.a("mailto:fundingsources@topobyte.de?subject=Funding feedback&body=About this site: "
						+ "https://sebkur.github.io/funding-2.0/" + webPath
						+ "%0A%0APlease type your message:");

		linkFeedback.attr("class", "btn btn-primary");
		linkFeedback.attr("role", "button");

		linkFeedback.appendText("Feedback via email");
		content.ac(linkFeedback);

		htmlBuilder.write(path);
	}

}
