package com.hiennhatt.vod.utils.ffmpeg;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties({"programs"})
public class MultimediaInform {
    private List<StreamInform> streams;
}
