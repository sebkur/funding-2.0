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
import java.util.List;

public class Util
{

	private static CliEnvironment ce = new CliEnvironment();

	public static List<Entry> readFundingSources() throws IOException
	{
		List<Entry> entries = Reader.read(ce.getPathFundingSources());
		return entries;
	}

	public static void writeFundingSources(List<Entry> entries) throws IOException
	{
		Writer.write(ce.getPathFundingSources(), entries);
	}

}
