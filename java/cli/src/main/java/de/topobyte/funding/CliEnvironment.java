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

import java.nio.file.Path;
import java.nio.file.Paths;

public class CliEnvironment
{

	private Path pathRepo;
	private Path pathFundingSources;

	public CliEnvironment()
	{
		String repo = System.getProperty("repo");
		pathRepo = Paths.get(repo);
		pathFundingSources = pathRepo.resolve("data/funding-sources.csv");
	}

	public Path getPathRepo()
	{
		return pathRepo;
	}

	public Path getPathFundingSources()
	{
		return pathFundingSources;
	}

}
