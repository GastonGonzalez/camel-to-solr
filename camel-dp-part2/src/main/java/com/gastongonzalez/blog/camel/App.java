package com.gastongonzalez.blog.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.gson.GsonDataFormat;
import org.apache.camel.component.solr.SolrConstants;
import org.apache.camel.impl.DefaultCamelContext;

public class App
{
    public static void main( String[] args ) throws Exception
    {
        CamelContext context = new DefaultCamelContext();

        final GsonDataFormat gsonDataFormat = new GsonDataFormat();
        gsonDataFormat.setUnmarshalType(Products.class);

        context.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception
            {
                from("file:data/solr?noop=true")
                    .unmarshal(gsonDataFormat)
                        .setBody().simple("${body.products}")
                            .split().body()
                                .setHeader(SolrConstants.OPERATION, constant(SolrConstants.OPERATION_ADD_BEAN))
                                .to("solrCloud://localhost:8983/solr/gettingstarted?zkHost=localhost:9983&collection=gettingstarted");
            }
        });

        context.start();
        Thread.sleep(10000);
        context.stop();
    }
}
