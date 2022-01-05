package com.pam.icourse.model;

public class HistoryModel {

    private String mIdBook;
    private String mJudul;
    private String mRiwayat;
    private String mTotal;
    private int mImageResourceId;
    private static final int NO_IMAGE_PROVIDED = -1;

    public HistoryModel(String idBook, String judul, String riwayat, int imageResourceId) {
        mIdBook = idBook;
        mJudul = judul;
        mRiwayat = riwayat;
        mImageResourceId = imageResourceId;
    }

    public String getIdBook() {
        return mIdBook;
    }

    public String getJudul() {
        return mJudul;
    }

    public String getRiwayat() {
        return mRiwayat;
    }

    public String getTotal() {
        return mTotal;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }

}