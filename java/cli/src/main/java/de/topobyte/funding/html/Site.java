package de.topobyte.funding.html;

import de.topobyte.webpaths.WebPath;
import de.topobyte.webpaths.WebPaths;

public class Site
{

	public static final WebPath PATH_INDEX = WebPaths.get("index.html");
	public static final WebPath PATH_ABOUT = WebPaths.get("about.html");
	public static final WebPath PATH_STYLES = WebPaths.get("style.css");
	public static final WebPath PATH_TAGS = WebPaths.get("tags/");

	public static final String TAG_UNTAGGED = "untagged";

	public static WebPath pathTag(String tag)
	{
		return PATH_TAGS.resolve(String.format("%s.html", tag));
	}

}
