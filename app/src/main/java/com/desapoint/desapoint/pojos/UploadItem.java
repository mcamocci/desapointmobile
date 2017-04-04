package com.desapoint.desapoint.pojos;

/**
 * Created by root on 4/4/17.
 */

public class UploadItem {

    public static final String ARTICLE_TYPE="ARTICLE";
    public static final String NOTES_TYPE="NOTEs";
    public static final String BOOK_TYPE="BOOK";
    public static final String PAST_TYPE="PASTPAPER";
    public static final String NONE_TYPE="NONE";
    public static final String HOLDING_NAME="UPLOAD_ITEM_HOLDER";


    private String type=NONE_TYPE;
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    private String subject;


    public  void setType(String type){
        this.type=type;
    }

    public String getType(){
        return this.type;
    }


}
