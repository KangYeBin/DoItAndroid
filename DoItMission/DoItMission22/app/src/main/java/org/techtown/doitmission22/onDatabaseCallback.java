package org.techtown.doitmission22;

import java.util.ArrayList;

public interface onDatabaseCallback {
    public void insert(String title, String author, String contents);
    public ArrayList<BookInfo> selectAll();
}
