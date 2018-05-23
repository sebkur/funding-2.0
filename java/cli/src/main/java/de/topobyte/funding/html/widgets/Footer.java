package de.topobyte.funding.html.widgets;

import static de.topobyte.jsoup.HTML.p;
import static de.topobyte.jsoup.HTML.span;

import de.topobyte.funding.html.Site;
import de.topobyte.jsoup.Bootstrap;
import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.components.UnorderedList;
import de.topobyte.jsoup.nodes.Element;
import de.topobyte.webpaths.WebPath;

public class Footer extends Element
{

	private WebPath path;

	public Footer(WebPath path)
	{
		super("footer");
		this.path = path;

		attr("class", "footer");

		Element container = ac(Bootstrap.container());

		addLinks(container);

		Element p = container.ac(p().addClass("text-muted"));

		Element heart = span().addClass("glyphicon glyphicon-heart");
		p.appendText("Made with ");
		p.appendChild(heart);
		p.appendText(" in Berlin");
	}

	private void addLinks(Element container)
	{
		UnorderedList links = container.ac(HTML.ul());

		String linkImpressum = path.relativize(Site.PATH_IMPRINT).toString();
		links.addItem(HTML.a(linkImpressum, "Impressum"));

		String linkPrivacyPolicy = path.relativize(Site.PATH_PRIVACY_POLICY)
				.toString();
		links.addItem(HTML.a(linkPrivacyPolicy, "Privacy policy"));
	}

}
