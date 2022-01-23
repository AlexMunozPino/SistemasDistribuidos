package com.example.KafkaConsumer.service;

import com.example.KafkaConsumer.model.Earthquake;
import com.example.KafkaConsumer.repository.EarthquakeRepository;
import net.bytebuddy.build.Plugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


@Service
@RestController
@CrossOrigin(origins = "*")
public class EarthquakeService {

    @Autowired
    EarthquakeRepository earthquakeRepository;

    @GetMapping("/earthquakes")
    public List<Earthquake> index() {
        return earthquakeRepository.findAll();
    }

    @GetMapping("/earthquakes/{begin}/{end}")
    public List<Earthquake> indexBetweenDate(@PathVariable String begin, @PathVariable String end) throws ParseException {
        Timestamp t1 = new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(begin).getTime());
        Timestamp t2 = new Timestamp(new SimpleDateFormat("yyyy-MM-dd").parse(end).getTime());

        Long begin_t = t1.getTime()/1000;
        Long end_t = t2.getTime()/1000;
        return earthquakeRepository.getEarthquakeByTimestampBetween(begin_t, end_t);
    }

    @GetMapping("/earthquakes/today")
    public List<Earthquake> indexToday() {
        Date current = Date.from(java.time.LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
        Timestamp t = new Timestamp(current.getTime());
        Long timestamp = t.getTime()/1000;
        System.out.println(java.time.LocalDate.now().atStartOfDay(ZoneId.systemDefault()));
        return earthquakeRepository.getEarthquakeByTimestampBetween(timestamp, timestamp + 24*60*60-1);
    }

    @KafkaListener(topics = "earthquakes", groupId = "group_json",
            containerFactory = "earthquakeKafkaListenerFactory")
    public void consumeJson(Earthquake earthquake) {
        if(earthquakeRepository.checkIfIdExists(earthquake.getId()) >= 1) return;
        earthquakeRepository.save(earthquake);
    }
}
