package com.example.dongkyoo.webe.vos;

public class Setting {

    private int iconResId;
    private String content;
    private String note;

    public Setting(int iconResId, String content, String note) {
        this.iconResId = iconResId;
        this.content = content;
        this.note = note;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
