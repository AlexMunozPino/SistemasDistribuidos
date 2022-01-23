package com.example.KafkaConsumer.listener;


import com.example.KafkaConsumer.model.Earthquake;
import com.example.KafkaConsumer.repository.EarthquakeRepository;
import com.example.KafkaConsumer.service.EarthquakeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.kafka.annotation.KafkaListener;

@Service

public class KafkaConsumer {

/*
    @KafkaListener(topics = "earthquakes", groupId = "group_json",
            containerFactory = "earthquakeKafkaListenerFactory")
    public void consumeJson(Earthquake earthquake) {
        System.out.println("Consumed JSON Message: " + earthquake);
        earthquakeService.insertEarthquake(earthquake.getId(), earthquake.getLongitude(), earthquake.getLatitude(), earthquake.getDepth());
    }*/
}