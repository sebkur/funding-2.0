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

import org.apache.commons.cli.Options;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import de.topobyte.utilities.apache.commons.cli.commands.args.CommonsCliArguments;
import de.topobyte.utilities.apache.commons.cli.commands.options.CommonsCliExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptionsFactory;

public class RunCheckLinks
{

	public static ExeOptionsFactory OPTIONS_FACTORY = new ExeOptionsFactory() {

		@Override
		public ExeOptions createOptions()
		{
			Options options = new Options();
			return new CommonsCliExeOptions(options, "[options]");
		}

	};

	public static void main(String name, CommonsCliArguments arguments)
			throws Exception
	{
		System.out.println("Checking links");

		List<Entry> entries = Util.readFundingSources();
		for (Entry entry : entries) {
			check(entry);
		}
	}

	private static void check(Entry entry)
	{
		String link = entry.getContact();
		System.out.println(link);

		try {
			checkLink(link);
		} catch (IOException e) {
			System.out.println(String.format("Exception: '%s': '%s'",
					e.getClass().getSimpleName(), e.getMessage()));
		}
	}

	private static void checkLink(String link)
			throws ClientProtocolException, IOException
	{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(link);
		CloseableHttpResponse response = httpclient.execute(httpGet);

		try {
			StatusLine statusLine = response.getStatusLine();
			interpret(statusLine);

			HttpEntity entity = response.getEntity();
			EntityUtils.consume(entity);
		} finally {
			response.close();
		}
	}

	private static void interpret(StatusLine statusLine)
	{
		int statusCode = statusLine.getStatusCode();

		if (statusCode == HttpStatus.SC_OK) {
			return;
		}

		if (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT) {
			System.out.println(statusLine);
		} else if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY) {
			System.out.println(statusLine);
		} else if (statusCode == HttpStatus.SC_FORBIDDEN) {
			System.out.println(statusLine);
		} else if (statusCode == HttpStatus.SC_NOT_FOUND) {
			System.out.println(statusLine);
		} else {
			System.out.println(statusLine);
		}
	}

}
