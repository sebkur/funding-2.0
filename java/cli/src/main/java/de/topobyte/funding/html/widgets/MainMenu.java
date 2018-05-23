package de.topobyte.funding.html.widgets;

import de.topobyte.funding.html.Site;
import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.components.A;
import de.topobyte.jsoup.components.bootstrap3.Menu;
import de.topobyte.webpaths.WebPath;

public class MainMenu extends Menu
{

	public MainMenu(WebPath path)
	{
		A brand = HTML.a(path.relativize(Site.PATH_INDEX).toString());
		brand.appendText("Funding 2.0");

		A tags = HTML.a(path.relativize(Site.PATH_TAGS).toString());
		tags.appendText("Tags");

		A about = HTML.a(path.relativize(Site.PATH_ABOUT).toString());
		about.appendText("About");

		addBrand(brand);
		addMain(tags, false);
		addMain(about, false);
	}

}
