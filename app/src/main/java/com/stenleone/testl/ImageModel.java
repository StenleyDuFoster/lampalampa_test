package com.stenleone.testl;

public class ImageModel {

    private String image_drawable;
    private  String text1;
    private  String text2;
    private  String text3;

    public void setImage_drawable(String local_img)
    {
        image_drawable = local_img;
    }

    public void setText(String local1,String local2,String local3) {
        text1 = local1;
        text2 = local2;
        text3 = local3;
    }

    public String getImage_drawable() {
        return image_drawable;
    }

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }

    public String getText3() {
        return text3;
    }
}