package com.hiennhatt.vod.utils.ffmpeg;

import lombok.Data;

@Data
public class StreamInform {
    private Integer index;
    private String codec_type;
    private Integer width;
    private Integer height;
}
