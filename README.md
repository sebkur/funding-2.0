This is a project for collecting and processing information about funding
sources. A rendered version of the data set is available here:
<https://sebkur.github.io/funding-2.0/>.

The initial data has been imported from a repository that the
[Renewable Freedom Foundation](https://renewablefreedom.org) has
provided in a [repository](https://github.com/renewablefreedom/funding-sources)
which contains the data visible on their [list of alternative funding
sources](https://renewablefreedom.org/grants/alternative-funding-sources).

# Data

The data in this repository is located in a CSV file here:
[data/funding-sources.csv](data/funding-sources.csv)

It's a simple CSV file with `;` as field delimiter and optional `"` as quote
characters. The file currently has the following columns:

    Funder;Info;Contact;Tags

The `Tags` column contains a comma-separated list of tags/keywords.

The `Contact` column is interpreted as HTML, so links can be encoded here.
In a future version, we're probably going to move away from CSV as a means
of storage and support multiple and different types of contact options.

# Processing

There's a Java project contained in this repository, that can be used
for various tasks. It ships with a command line interface (CLI)
for generating the static website and running a number of data manipulation
tasks such as sorting the entries alphabetically or cleaning up whitespace.

More information on this is available in the accompanying
[README file](java/README.md).

# Research

* <http://www.forschen-foerdern.org/>
