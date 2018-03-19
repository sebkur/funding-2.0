package de.topobyte.funding.html;

import de.topobyte.funding.Entry;
import de.topobyte.webpaths.WebPath;
import de.topobyte.webpaths.WebPaths;

public class Site
{

	public static final WebPath PATH_INDEX = WebPaths.get("index.html");
	public static final WebPath PATH_ABOUT = WebPaths.get("about.html");
	public static final WebPath PATH_TAGS = WebPaths.get("tags.html");
	public static final WebPath PATH_STYLES = WebPaths.get("style.css");
	public static final WebPath DIR_TAGS = WebPaths.get("tags/");
	public static final WebPath DIR_ENTRIES = WebPaths.get("entries/");

	public static final String TAG_UNTAGGED = "untagged";

	public static WebPath pathTag(String tag)
	{
		return DIR_TAGS.resolve(String.format("%s.html", tag));
	}

	public static WebPath pathEntry(Entry entry)
	{
		String funder = entry.getFunder();
		funder = funder.toLowerCase();
		funder = funder.replaceAll(" ", "-");
		funder = funder.replaceAll("/", "-");
		return DIR_ENTRIES.resolve(String.format("%s.html", funder));
	}

}
