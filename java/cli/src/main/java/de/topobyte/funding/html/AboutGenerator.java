package de.topobyte.funding.html;

import java.io.IOException;
import java.nio.file.Path;

import de.topobyte.jsoup.Bootstrap;
import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.HtmlBuilder;
import de.topobyte.jsoup.components.P;
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
