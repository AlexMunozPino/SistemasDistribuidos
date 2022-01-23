package com.example.KafkaConsumer.repository;

import com.example.KafkaConsumer.model.Earthquake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EarthquakeRepository extends JpaRepository<Earthquake, String> {

    List<Earthquake> getEarthquakeByTimestampBetween(Long t1, Long t2);
    /*Modifying
    @Query(value="INSERT INTO earthquake (id, longitude, latitude, magnitude, depth, timestamp) " +
            "VALUES(:id, :longitude, :latitude, :magnitude, :depth, :timestamp) " +
            "ON CONFLICT (id) " +
            "DO NOTHING", nativeQuery = true)
    void insertEarthquake(@Param("id") String id, @Param("longitude") Float longitude, @Param("latitude") Float latitude, @Param("magnitude") Float magnitude, @Param("depth") Float depth, @Param("timestamp") Date timestamp);*/

    @Query(value="SELECT COUNT(id) " +
            "FROM earthquake " +
            "WHERE id = ':id'", nativeQuery = true)
    int checkIfIdExists(@Param("id") String id);


}
