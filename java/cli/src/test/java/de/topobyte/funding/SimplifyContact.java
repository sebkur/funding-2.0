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

package de.topobyte.funding;

import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SimplifyContact
{

	public static void main(String[] args) throws Exception
	{
		System.out.println("Simplifying contact info");
		TestUtil.prepare();

		List<Entry> entries = Util.readFundingSources();

		for (Entry entry : entries) {
			simplify(entry);
		}

		Util.writeFundingSources(entries);
	}

	private static void simplify(Entry entry)
	{
		String contact = entry.getContact();
		Document document = Jsoup.parse(contact);
		Elements links = document.getElementsByTag("a");
		if (links.size() != 1) {
			System.out.println("not exactly one link: " + contact);
			return;
		}
		Element link = links.get(0);
		String href = link.attr("href");
		String text = link.ownText();
		if (!href.equals(text)) {
			System.out.println(href);
			System.out.println(text);
		}
		entry.setContact(href);
	}

}
