// Copyright 2018 Sebastian Kuerten
//
// This file is part of funding-2.0.
//
// funding-2.0 is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// funding-2.0 is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with funding-2.0. If not, see <http://www.gnu.org/licenses/>.

package de.topobyte.funding.tagging;

import java.net.URL;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.common.base.Splitter;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import de.topobyte.collections.util.ListUtil;
import de.topobyte.funding.Entry;
import de.topobyte.funding.TestUtil;
import de.topobyte.funding.Util;

public class TagCountriesByTLD
{

	public static void main(String[] args) throws Exception
	{
		System.out.println("Tagging funding sources by TLD");
		TestUtil.prepare();

		List<Entry> entries = Util.readFundingSources();

		Multiset<String> tlds = HashMultiset.create();
		Multiset<String> nonIdentifiedTlds = HashMultiset.create();
		for (Entry entry : entries) {
			String contact = entry.getContact();
			Document doc = Jsoup.parse(contact);
			Elements links = doc.getElementsByTag("a");
			if (links.size() < 1) {
				continue;
			}
			Element link = links.get(0);
			String href = link.attributes().get("href");
			URL url = new URL(href);
			String host = url.getHost();
			List<String> parts = Splitter.on(".").splitToList(host);
			String tld = ListUtil.last(parts);
			tlds.add(tld);

			String country = getCountry(tld);
			if (country != null) {
				addTag(entry, country);
			} else {
				nonIdentifiedTlds.add(tld);
			}
		}

		System.out.println(tlds);
		System.out.println(nonIdentifiedTlds);

		Util.writeFundingSources(entries);
	}

	private static String getCountry(String tld)
	{
		switch (tld) {
		case "de":
			return "germany";
		case "no":
			return "norway";
		case "fi":
			return "finland";
		case "ch":
			return "switzerland";
		case "is":
			return "iceland";
		case "ly":
			return "lybia";
		case "vc":
			return "saint-vincent-and-the-grenadines";
		case "se":
			return "sweden";
		case "at":
			return "austria";
		case "uk":
			return "united-kingdom";
		case "nl":
			return "netherlands";
		case "ca":
			return "canada";
		case "gov":
			return "united-states";
		case "eu":
			return "eu";
		}
		return null;
	}

	private static void addTag(Entry entry, String tag)
	{
		if (!entry.getTags().contains(tag)) {
			entry.getTags().add(tag);
		}
	}

}
