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

import java.util.List;

import de.topobyte.funding.Entry;
import de.topobyte.funding.Util;
import de.topobyte.system.utils.SystemPaths;

public class TagGermany
{

	public static void main(String[] args) throws Exception
	{
		System.out.println("Tagging funding sources: germany");
		System.setProperty("repo",
				SystemPaths.CWD.getParent().getParent().toString());

		List<Entry> entries = Util.readFundingSources();

		for (Entry entry : entries) {
			String funder = entry.getFunder();
			if (funder.toLowerCase().contains("stiftung")) {
				addTag(entry, "germany");
			}
		}

		Util.writeFundingSources(entries);
	}

	private static void addTag(Entry entry, String tag)
	{
		if (!entry.getTags().contains(tag)) {
			entry.getTags().add(tag);
		}
	}

}
