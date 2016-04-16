package com.gastongonzalez.blog.camel;

import org.apache.solr.client.solrj.beans.Field;

public class Movie {

    @Field
    private String sku;

    @Field
    private String name;


    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Movie{");
        sb.append("sku='").append(sku).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
