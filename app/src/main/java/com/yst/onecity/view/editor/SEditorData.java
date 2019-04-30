package com.yst.onecity.view.editor;

/**
 * 富文本数据Bean
 *
 *@author  songbinbin
 * @date    2017/12/14
 * @version 3.0.1
 */
public class SEditorData {

    private String inputStr;

    private String imagePath;

    private String videoPath;


    public String getInputStr() {
        return inputStr;
    }

    public void setInputStr(String inputStr) {
        this.inputStr = inputStr;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    @Override
    public String toString() {
        return "SEditorData{" +
                "inputStr='" + inputStr + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", videoPath='" + videoPath + '\'' +
                '}';
    }
}
