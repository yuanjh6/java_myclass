package com.yuan.demo.video;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class VideoLastThumbTaker extends VideoThumbTaker
{
    public VideoLastThumbTaker(String ffmpegApp)
    {
        super(ffmpegApp);
    }

    public void getThumb(String videoFilename, String thumbFilename, int width,
            int height) throws IOException, InterruptedException
    {
        VideoInfo videoInfo = new VideoInfo(ffmpegApp);
        videoInfo.getInfo(videoFilename);
        super.getThumb(videoFilename, thumbFilename, width, height,
                videoInfo.getHours(), videoInfo.getMinutes(),
                videoInfo.getSeconds() - 0.2f);
    }


}