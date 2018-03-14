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

public class Entry
{

	private String funder;
	private String info;
	private String contact;

	public Entry(String funder, String info, String contact)
	{
		this.funder = funder;
		this.info = info;
		this.contact = contact;
	}

	public String getFunder()
	{
		return funder;
	}

	public void setFunder(String funder)
	{
		this.funder = funder;
	}

	public String getInfo()
	{
		return info;
	}

	public void setInfo(String info)
	{
		this.info = info;
	}

	public String getContact()
	{
		return contact;
	}

	public void setContact(String contact)
	{
		this.contact = contact;
	}

}
