Solr Document Processing with Apache Camel - Part II
====================================================

This project contains the code described in [Solr Document Processing with Apache Camel - Part II](http://www.gastongonzalez.com/).

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
