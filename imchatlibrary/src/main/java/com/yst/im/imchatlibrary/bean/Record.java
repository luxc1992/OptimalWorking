package com.yst.im.imchatlibrary.bean;

/**
 * 音频 实体类
 *
 * @author Lierpeng
 * @date 2018/1/2.
 * @version 1.0.1
 */
public class Record {

    private String path;
    private int second;
    /**
     * 是否已经播放过
     */
    private String played;
    /**
     * 是否正在播放
     */
    private String playing;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public String isPlayed() {
        return played;
    }

    public void setPlayed(String played) {
        this.played = played;
    }

    public String isPlaying() {
        return playing;
    }

    public void setPlaying(String playing) {
        this.playing = playing;
    }

    @Override
    public String toString() {
        return "Record{" +
                "path='" + path + '\'' +
                ", second=" + second +
                ", played=" + played +
                ", playing=" + playing +
                '}';
    }
}
