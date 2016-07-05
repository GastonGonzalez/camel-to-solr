package com.gastongonzalez.blog.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.component.solr.SolrConstants;
import org.apache.camel.core.osgi.OsgiDefaultCamelContext;
import org.apache.camel.dataformat.zipfile.ZipSplitter;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Activator implements BundleActivator {

    private static final Logger LOG = LoggerFactory.getLogger(Activator.class);
    private CamelContext camelContext = null;

    public void start(BundleContext bundleContext) throws Exception {

        LOG.info("Starting the BestBuy importer");
        camelContext = new OsgiDefaultCamelContext(bundleContext);

        PropertiesComponent propertiesComponent = camelContext.getComponent("properties", PropertiesComponent.class);
        propertiesComponent.setLocation("classpath:movies.properties");
        propertiesComponent.setSystemPropertiesMode(PropertiesComponent.SYSTEM_PROPERTIES_MODE_OVERRIDE);

        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {

                from("timer://foo?repeatCount=1&delay=1000")
                        .to("http4://api.bestbuy.com/v1/subsets/productsMovie.json.zip?apiKey={{bestbuy.api.key}}")
                        .setHeader(Exchange.FILE_NAME, constant("productsMovie.json.zip"))
                        .to("file:data/zip?doneFileName=${file:name}.done");

                from("file:data/zip?noop=false&doneFileName=productsMovie.json.zip.done")
                        .split(new ZipSplitter())
                        .streaming().to("file:data/json?doneFileName=${file:name}.done");

                from("file:data/json?noop=false&doneFileName=${file:name}.done")
                        .process(new JsonToProductProcessor())
                        .split().body()
                        .setHeader(SolrConstants.OPERATION, constant(SolrConstants.OPERATION_ADD_BEAN))
                        .to("solrCloud://{{solr.host}}:{{solr.port}}/solr/{{solr.collection}}?zkHost={{solr.zkhost}}&collection={{solr.collection}}");
            }
        });

        camelContext.start();
    }

    public void stop(BundleContext bundleContext) throws Exception {
        LOG.info("Stopping the BestBuy importer");

        if (camelContext != null)
        {
            camelContext.stop();
        }
    }
}
