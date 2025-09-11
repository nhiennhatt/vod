package com.hiennhatt.vod.utils.ffmpeg;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class FFmpegUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final int[] validResolutions = {1080, 720, 480, 360, 240};

    public static MultimediaInform getStreamInform(String selectedStream, String filePath) {
        ProcessBuilder processBuilder = new ProcessBuilder(
            "ffprobe",
            "-v", "quiet",
            "-print_format", "json",
            "-select_streams", selectedStream,
            "-show_entries", "stream=index,codec_type,width,height",
            filePath
        );
        processBuilder.redirectErrorStream(true);
        try {
            Process process = processBuilder.start();
            process.waitFor(1200, TimeUnit.SECONDS);
            InputStream inputStream = process.getInputStream();
            MultimediaInform multimediaInform = objectMapper.readValue(inputStream, MultimediaInform.class);
            inputStream.close();
            process.destroy();
            return multimediaInform;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void generateMpd(String outputFilePath, String inputFilePath, StreamInform videoStreamInform, StreamInform audioStreamInform) {
        ArrayList<String> command = new ArrayList<>(List.of("ffmpeg", "-v", "error", "-hide_banner", "-i", inputFilePath));
        int closestResolution = getClosestResolution(videoStreamInform.getHeight());
        List<Integer> resolutionForVideo = Arrays.stream(validResolutions).filter(i -> i <= closestResolution).boxed().toList();
        resolutionForVideo.forEach(i -> command.addAll(List.of("-map", "0:v:0", "-map", "0:a:0")));
        command.addAll(List.of(
            "-c:v", "libx264",
            "-c:a", "aac",
            "-b:a", "128k"
        ));
        for (int i = 0; i < resolutionForVideo.size(); i++) {
            int resolution = resolutionForVideo.get(i);
            command.addAll(List.of(
                "-s:v:" + i, getResolutionString(resolution),
                "-b:v:" + i, getVideoBitrate(resolution) + "k"
            ));
        }
        command.addAll(List.of(
            "-f", "dash",
            "-seg_duration", "4",
            "-use_template", "1",
            "-use_timeline", "1",
            "-preset", "medium",
            "-crf", "22",
            "-init_seg_name", "init-$RepresentationID$.m4s",
            "-media_seg_name", "chunk-$RepresentationID$-$Number%06d$.m4s",
            "-adaptation_sets", "id=0,streams=v id=1,streams=a",
            outputFilePath
        ));
        System.out.println(String.join(" ", command));
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        try {
            Process process = processBuilder.start();
            process.waitFor(20, TimeUnit.MINUTES);
            InputStream inputStream = process.getInputStream();
            String output = new String(inputStream.readAllBytes());
            System.out.println(output);
            inputStream.close();
            process.destroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getClosestResolution(int resolution) {
        return Arrays.stream(validResolutions).boxed()
            .map(i -> List.of(i, Math.abs(i - resolution)))
            .min(Comparator.comparing(o -> o.get(1)))
            .orElse(List.of(240))
            .get(0);
    }

    private static String getResolutionString(int resolution) {
        return resolution * Math.round((float) 16 / 9) + "x" + resolution;
    }

    private static int getVideoBitrate(int resolution) {
        return switch (resolution) {
            case 1080 -> 4500;
            case 720 -> 1500;
            case 480 -> 1000;
            default -> 800;
        };
    }
}
