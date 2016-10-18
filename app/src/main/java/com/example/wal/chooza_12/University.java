package com.example.wal.chooza_12;

/**
 * Created by wal on 9/17/2016.
 */
public class University {
    //private int University_ID;
    private String Name;



    private int Thumbnail;
    //private String City;
    //private String Introduction;
    //private String ImageData;
    //private String Address;
    //private String Website;
    //private String Sector;



    public University(String Name, int Thumbnail)
    {

        this.Name= Name;
        this.Thumbnail= Thumbnail;
    }


   /* public int getUniversity_ID() {
        return University_ID;
    }

    public void setUniversity_ID(int university_ID) {
        University_ID = university_ID;
    }*/

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
    /*public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getIntroduction() {
        return Introduction;
    }

    public void setIntroduction(String introduction) {
        Introduction = introduction;
    }

    public String getImageData() {
        return ImageData;
    }

    public void setImageData(String imageData) {
        ImageData = imageData;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
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
*/
}
