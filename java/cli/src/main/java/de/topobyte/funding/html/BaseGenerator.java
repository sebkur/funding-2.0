package de.topobyte.funding.html;

import de.topobyte.jsoup.Bootstrap3;
import de.topobyte.jsoup.ElementUtil;
import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.HtmlBuilder;
import de.topobyte.jsoup.components.A;
import de.topobyte.jsoup.components.bootstrap3.Menu;
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
		Menu menu = new Menu();
		body.ac(menu);

		A brand = HTML.a(webPath.relativize(Site.PATH_INDEX).toString());
		brand.appendText("Funding 2.0");

		A tags = HTML.a(webPath.relativize(Site.PATH_TAGS).toString());
		tags.appendText("Tags");

		A about = HTML.a(webPath.relativize(Site.PATH_ABOUT).toString());
		about.appendText("About");

		menu.addBrand(brand);
		menu.addMain(tags, false);
		menu.addMain(about, false);
	}

}
