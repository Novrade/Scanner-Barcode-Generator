package com.scannertool.demo.database;

import jakarta.persistence.*;

@Entity
@Table(name = "scan")
public class Data {
    @Id
    @Column(name = "station", nullable = false, unique = true)
    private String station;
    @Column(name = "mac", nullable = false, unique = true)
    private String mac;

    public Data(String station, String mac) {
        this.station = station;
        this.mac = mac;
    }

    public Data() {
    }


    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    @Override
    public String toString() {
        return "Data{" +
                "stationId='" + station + '\'' +
                ", mac='" + mac + '\'' +
                '}';
    }
}
