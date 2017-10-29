package com.example.waati.Bean;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by 江婷婷 on 2017/10/29.
 */

public class ThingListItem extends DataSupport{

    private int id;

    private int mListType;//0日期 1事情

    private Date mDate;

    private String mContent;
    private int mUrgentType = 0;//0 1 2 --->重要
    private int mDoStatus = 0;//0待办 1已办
    private int mEditStatus = 0;//0未编辑 1正在编辑

    //事情构造
    public ThingListItem(Date mDate, String mContent, int mUrgentType) {
        this.mDate = mDate;
        this.mContent = mContent;
        this.mUrgentType = mUrgentType;
        this.mDoStatus = 0;
        this.mEditStatus = 0;
        this.mListType = 1;
    }

    //日期构造
    public ThingListItem(Date mDate) {
        this.mDate = mDate;
        this.mListType = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getmListType() {
        return mListType;
    }

    public void setmListType(int mListType) {
        this.mListType = mListType;
    }

    public Date getmDate() {
        return mDate;
    }

    public void setmDate(Date mDate) {
        this.mDate = mDate;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public int getmUrgentType() {
        return mUrgentType;
    }

    public void setmUrgentType(int mUrgentType) {
        this.mUrgentType = mUrgentType;
    }

    public int getmDoStatus() {
        return mDoStatus;
    }

    public void setmDoStatus(int mDoStatus) {
        this.mDoStatus = mDoStatus;
    }

    public int getmEditStatus() {
        return mEditStatus;
    }

    public void setmEditStatus(int mEditStatus) {
        this.mEditStatus = mEditStatus;
    }
}
