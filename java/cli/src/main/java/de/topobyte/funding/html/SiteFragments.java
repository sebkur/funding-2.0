package de.topobyte.funding.html;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import de.topobyte.jsoup.Bootstrap;
import de.topobyte.jsoup.HTML;
import de.topobyte.jsoup.components.A;
import de.topobyte.jsoup.components.P;
import de.topobyte.jsoup.components.bootstrap3.Container;
import de.topobyte.jsoup.components.bootstrap3.Label;
import de.topobyte.jsoup.components.bootstrap3.Label.Type;
import de.topobyte.webpaths.WebPath;

public class SiteFragments
{

	public static void appendTags(WebPath webPath, Container content,
			List<String> tags)
	{
		if (!tags.isEmpty()) {
			appendTags(webPath, content, tags, Type.PRIMARY);
		} else {
			appendTags(webPath, content, Arrays.asList(Site.TAG_UNTAGGED),
					Type.WARNING);
		}
	}

	private static void appendTags(WebPath webPath, Container content,
			List<String> tags, Type type)
	{
		P p = content.ac(HTML.p());
		p.attr("style", "padding-top:0.5em");
		Iterator<String> iterator = tags.iterator();
		while (iterator.hasNext()) {
			String tag = iterator.next();
			WebPath path = Site.pathTag(tag);
			WebPath relative = webPath.relativize(path);
			A link = HTML.a(relative.toString());
			Label label = link.ac(Bootstrap.label(type));
			label.appendText(tag);
			p.ac(link);
			if (iterator.hasNext()) {
				p.appendText(" ");
			}
		}
	}

}
