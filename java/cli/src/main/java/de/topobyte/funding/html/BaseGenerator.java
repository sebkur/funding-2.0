package de.topobyte.funding.html;

import de.topobyte.funding.html.widgets.MainMenu;
import de.topobyte.jsoup.Bootstrap3;
import de.topobyte.jsoup.ElementUtil;
import de.topobyte.jsoup.HtmlBuilder;
import de.topobyte.jsoup.nodes.Element;
import de.topobyte.webpaths.WebPath;

public class BaseGenerator
{

	protected void setupHeader(WebPath webPath, HtmlBuilder htmlBuilder)
	{
		Element head = htmlBuilder.getHead();
		htmlBuilder.getTitle().appendText("Funding 2.0");

		Bootstrap3.addCdnHeaders(head);

		ElementUtil.appendFragmentHead(head, "<link rel=\"stylesheet\" href=\""
				+ webPath.relativize(Site.PATH_STYLES).toString() + "\">");
	}

	protected void addMenu(WebPath webPath, Element body)
	{
		body.ac(new MainMenu(webPath));
	}

}
