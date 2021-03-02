package org.techtown.doitmission18;

public class ImageInfo {
    String path;
    String addedDate;

    public ImageInfo(String path, String addedDate) {
        this.path = path;
        this.addedDate = addedDate;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }
}
