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

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import de.topobyte.system.utils.SystemPaths;

public class TestRead
{

	public static void main(String[] args) throws IOException
	{
		Path path = SystemPaths.CWD.getParent().getParent()
				.resolve("data/funding-sources.csv");
		List<Entry> entries = Reader.read(path);
		for (Entry entry : entries) {
			System.out.println(String.format("funder: %s: %s",
					entry.getFunder(), entry.getContact()));
		}
	}

}
