package com.hiennhatt.vod.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.file.Path;

@Getter
@AllArgsConstructor
public class PreSaveFileDTO {
    String uid;
    Path imagePath;
    Path tempVideoPath;
}
