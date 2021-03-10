package org.techtown.doitmission25;

public class PictureInfo {
    String image;
    String name;
    String date;

    public PictureInfo(String image, String name, String date) {
        this.image = image;
        this.name = name;
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
