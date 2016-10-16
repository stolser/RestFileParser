package com.bintime.entity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LineCounter {
    private static final Integer THREAD_NUMBER = 3;
    private Map<String, Integer> lineFrequency;

    public Map<String, Integer> count(List<UploadedFile> uploadedFiles) {
        lineFrequency = new HashMap<>();
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_NUMBER);
        for (UploadedFile file: uploadedFiles) {
            List<String> lines = file.getTextLines();
            executor.submit((Runnable) () -> lines.forEach(this::putLineIntoResultMap));
        }

        return lineFrequency;
    }

    private synchronized void putLineIntoResultMap(String line) {
        Integer count = lineFrequency.get(line);
        Integer newCount = (count != null) ? (count + 1) : 1;
        lineFrequency.put(line, newCount);
    }


}
