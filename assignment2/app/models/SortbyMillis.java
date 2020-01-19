package models;

import java.util.Comparator;

public class SortbyMillis implements Comparator<Assessment> {
    public int compare(Assessment a,Assessment b){
        return (int)(b.getMillis() - a.getMillis());
    }
}
