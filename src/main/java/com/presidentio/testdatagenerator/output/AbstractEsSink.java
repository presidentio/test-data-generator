package com.presidentio.testdatagenerator.output;

import com.presidentio.testdatagenerator.cons.PropConst;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.util.Map;

/**
 * Created by Vitalii_Gergel on 2/19/2015.
 */
public abstract class AbstractEsSink extends AbstractBufferedSink {

    private Client client;

    @Override
    public void init(Map<String, String> props) {
        String host = props.get(PropConst.HOST);
        if (host == null) {
            throw new IllegalArgumentException("Host does not specified");
        }
        String port = props.get(PropConst.PORT);
        if (port == null) {
            throw new IllegalArgumentException("Port does not specified");
        }
        String clusterName = props.get(PropConst.CLUSTER_NAME);
        if (clusterName == null) {
            throw new IllegalArgumentException("Cluster name does not specified");
        }
        client = (new TransportClient(ImmutableSettings.settingsBuilder().put("cluster.name", clusterName).build()))
                .addTransportAddress(new InetSocketTransportAddress(host, Integer.valueOf(port)));
    }

    @Override
    public void write(String query) {
        try {
            byte[] bytes = query.getBytes();
            client.prepareBulk().add(bytes, 0, bytes.length, false).execute().actionGet();
        } catch (Exception e) {
            throw new IllegalStateException("Failed to save: " + query, e);
        }
    }

    @Override
    public void close() {
        super.close();
        client.close();
    }
}
