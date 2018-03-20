package com.anchitsharma.user.gpstracking.rxjava;

/**
 * Created by user on 3/15/2018.
 */

public class Note {
    int nid;
    String note;

    public Note(int nid, String note) {
        this.nid = nid;
        this.note = note;
    }

    public int getNid() {
        return nid;
    }

    public String getNote() {
        return note;
    }

    public void setNid(int nid) {
        this.nid = nid;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
