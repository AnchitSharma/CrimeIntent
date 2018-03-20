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

    @Override
    public boolean equals(Object obj) {
        if (obj == this){
            return true;
        }
        if (!(obj instanceof Note)){
            return false;
        }
        return note.equalsIgnoreCase(((Note)obj).getNote());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash +(this.note != null?this.note.hashCode():0);
        return hash;
    }
}
