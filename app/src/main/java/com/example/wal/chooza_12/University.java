package com.example.wal.chooza_12;

/**
 * Created by wal on 9/17/2016.
 */
public class University {

    private String Name;
    private int Thumbnail;
    private String City;
    private String Website;
    private String Sector;
    public String HSSC_Criteria;
    public String SSC_Criteria;
    public String Duration;

    public University(String name, int thumbnail, String city, String website, String sector, String HSSC_Criteria, String SSC_Criteria, String duration) {
        Name = name;
        Thumbnail = thumbnail;
        City = city;
        Website = website;
        Sector = sector;
        this.HSSC_Criteria = HSSC_Criteria;
        this.SSC_Criteria = SSC_Criteria;
        Duration = duration;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
    public int getThumbnail() {
        return Thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getWebsite() {
        return Website;
    }

    public void setWebsite(String website) {
        Website = website;
    }

    public String getSector() {
        return Sector;
    }

    public void setSector(String sector) {
        Sector = sector;
    }

    public String getHSSC_Criteria() {
        return HSSC_Criteria;
    }

    public void setHSSC_Criteria(String HSSC_Criteria) {
        this.HSSC_Criteria = HSSC_Criteria;
    }

    public String getSSC_Criteria() {
        return SSC_Criteria;
    }

    public void setSSC_Criteria(String SSC_Criteria) {
        this.SSC_Criteria = SSC_Criteria;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }
}
