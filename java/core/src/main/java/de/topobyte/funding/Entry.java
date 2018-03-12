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
