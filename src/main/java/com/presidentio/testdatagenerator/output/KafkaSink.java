package com.presidentio.testdatagenerator.output;

import com.presidentio.testdatagenerator.cons.PropConst;
import com.presidentio.testdatagenerator.model.Template;
import com.presidentio.testdatagenerator.output.formatter.DefaultFormatterFactory;
import com.presidentio.testdatagenerator.output.formatter.Formatter;
import com.presidentio.testdatagenerator.output.formatter.FormatterFactory;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by presidentio on 24.04.15.
 */
public class KafkaSink implements Sink {

    private FormatterFactory formatterFactory = new DefaultFormatterFactory();

    private Producer<String, String> producer;

    private Formatter formatter;

    @Override
    public void init(Map<String, String> props) {
        String brokerList = props.get(PropConst.BROKER_LIST);
        if (brokerList == null) {
            throw new IllegalArgumentException("Broker list does not specified");
        }
        Map<String, Object> configs = new HashMap<>();
        configs.put("bootstrap.servers", brokerList);
        producer = new KafkaProducer<>(configs);
        formatter = formatterFactory.buildFormatter(props);
    }

    @Override
    public void process(Template template, Map<String, Object> map) {
        String message = formatter.format(map, template);
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(template.getName(),
                message);
        producer.send(producerRecord);
    }

    @Override
    public void close() {
        producer.close();
    }

}
