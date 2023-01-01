package com.scannertool.demo.Repository;

import com.scannertool.demo.database.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataRepository extends JpaRepository<Data, Long> {
    public Data findByStation(String station);
    public Data findByMac(String mac);

}
