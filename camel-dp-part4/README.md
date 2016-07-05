# Solr Document Processing with Apache Camel - Part IV

This project contains the code described in [Solr Document Processing with Apache Camel - Part IV](http://www.gastongonzalez.com/).

## Prerequisites

1. Register for a Best Buy API key at [https://developer.bestbuy.com/](https://developer.bestbuy.com/).
2. Edit `src/main/resources/movies.properties` and update the `bestbuy.api.key` property with your API key.


## Create a dedicated installation directory (Optional)

While this is not required, it is suggested that you create a top-level installation directory for Apache Solr and
Apache Karaf. In this example, we will create a directory structure under your home directory called `opt/dp-part4`.

    $ mkdir -p ~/opt/dp-part4

## Apache Solr Setup

Install, download and start Solr using the Getting Started example.

    $ cd ~/opt/dp-part4
    $ curl -O https://archive.apache.org/dist/lucene/solr/5.4.1/solr-5.4.1.tgz
    $ tar -xzf solr-5.4.1.tgz
    $ cd solr-5.4.1
    $ bin/solr start -e cloud -noprompt

## Apache Karaf Setup

Begin be creating a new directory to install Apache Karaf.

1. Download, install and start Apache Karaf 4.0.5.

        $ cd ~/opt/dp-part4
        $ curl -O https://archive.apache.org/dist/karaf/4.0.5/apache-karaf-4.0.5.tar.gz
        $ tar -xzf apache-karaf-4.0.5.tar.gz
        $ cd apache-karaf-4.0.5
        $ bin/start

2. Log into Apache Karaf using the Karaf client and install the web console.

        $ bin/client
        karaf@root()> feature:install webconsole

3. In a web browser, visit: http://localhost:8181/system/console/bundles. Log in with `karaf` / `karaf`.

4. Install Apache Camel 2.17.1. (This will install camel-karaf-commands, camel-core, camel-commands-core, camel-catalog, camel-blueprint.)

        karaf@root()> feature:repo-add camel 2.17.1
        karaf@root()> feature:install camel
        karaf@root()> bundle:install -s mvn:org.apache.camel/camel-core-osgi/2.17.1

## Deploy SolrJ OSGi bundle.

1. Begin be creating a workspace for our source code.

2. Then, clone the repository.

        $ git clone https://github.com/GastonGonzalez/camel-to-solr.git

3. Build and install the SolrJ OSGi bundle.

        $ cd camel-to-solr/camel-dp-part4/karaf-solrj-osgi
        $ mvn clean package
        $ cp target/karaf-solrj-osgi-1.0.0.jar ~/opt/dp-part4/apache-karaf-4.0.5/deploy/


## Deploy the remaining OSGi components required by our application.

1. Install the `camel-solr` component

        karaf@root()> bundle:install -s mvn:org.apache.camel/camel-solr/2.17.1

2. Install HttpClient components.

        karaf@root()> bundle:install -s mvn:org.apache.httpcomponents/httpcore-osgi/4.4.5
        karaf@root()> bundle:install -s mvn:org.apache.httpcomponents/httpclient-osgi/4.5.2

3. Install Camel HTTP component bundles.

        karaf@root()> bundle:install -s mvn:org.apache.camel/camel-http-common/2.17.1
        karaf@root()> bundle:install -s mvn:org.apache.camel/camel-http4/2.17.1

4. Verify the list of bundles.

        karaf@root()> bundle:list
        START LEVEL 100 , List Threshold: 50
         ID | State  | Lvl | Version | Name
        -------------------------------------------------------------------
         95 | Active |  50 | 2.17.1  | camel-blueprint
         96 | Active |  50 | 2.17.1  | camel-catalog
         97 | Active |  80 | 2.17.1  | camel-commands-core
         98 | Active |  50 | 2.17.1  | camel-core
         99 | Active |  80 | 2.17.1  | camel-karaf-commands
        103 | Active |  80 | 2.17.1  | camel-core-osgi
        104 | Active |  80 | 1.0.0   | Solr SolrJ OSGi bundle
        105 | Active |  80 | 2.17.1  | camel-solr
        106 | Active |  80 | 4.4.5   | Apache Apache HttpCore OSGi bundle
        107 | Active |  80 | 4.5.2   | Apache Apache HttpClient OSGi bundle
        108 | Active |  80 | 2.17.1  | camel-http-common
        109 | Active |  80 | 2.17.1  | camel-http4

## Deploy our Camel Route

1. Change into the Git workspace again.

2. Change into the directory containing our `karaf-camel-bestbuy` module

        $ cd karaf-camel-bestbuy

3. Edit `/src/main/resources/movies.properties and enter your Best Buy API key on the
   `bestbuy.api.key` property.

4. Build and install our OSGi bundle.

        $ mvn clean package
        $ cp target/camel-dp-part4-1.0.0.jar ~/opt/dp-part4/apache-karaf-4.0.5/deploy/ 

## Verification

1. Check that all the bundles are in the active state.

2. Open a browser and visit the Solr admin GUI. http://localhost:8983/solr/#/gettingstarted_shard1_replica2/query
