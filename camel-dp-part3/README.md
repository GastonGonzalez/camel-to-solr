Solr Document Processing with Apache Camel - Part III
=====================================================

This project contains the code described in [Solr Document Processing with Apache Camel - Part III](http://www.gastongonzalez.com/).

Prerequisites 
-------------

1. Register for a Best Buy API key at [https://developer.bestbuy.com/](https://developer.bestbuy.com/).
2. Edit `src/main/resources/movies.properties` and update the `bestbuy.api.key` property with your API key.

Apache Solr Setup
-----------------

Install, download and start Solr using the Getting Started example.

    $ curl -O https://archive.apache.org/dist/lucene/solr/5.4.1/solr-5.4.1.tgz
    $ tar -xzf solr-5.4.1.tgz
    $ cd solr-5.4.1
    $ bin/solr start -e cloud -noprompt


Indexing Content with Camel
---------------------------

Move into the project root and run:

    $ mvn clean compile exec:java -Dexec.mainClass=com.gastongonzalez.blog.camel.App

or

    $ ./run.sh
