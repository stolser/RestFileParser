package com.bintime.controllers;

import com.bintime.entity.LineCounter;
import com.bintime.entity.Request;
import com.bintime.entity.ResultItem;
import com.bintime.entity.UploadedFile;
import com.bintime.services.RequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class FileManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileManager.class);
    private Map<String, Integer> lineFrequency;
    private RequestService requestService;

    @RequestMapping(value = "upload-files", method = RequestMethod.POST)
    public String uploadFiles(@RequestParam("file") MultipartFile[] files,
                              @RequestParam String userName,
                              Model model) {

        List<UploadedFile> uploadedFiles = countLineFrequencyAndGetFiles(files);
        requestService.printAllInDB();

        model.addAttribute("userName", userName);
        model.addAttribute("uploadedFiles", uploadedFiles);

        return "thanksForUploading";
    }

    @RequestMapping(value = "get-results", method = RequestMethod.GET,
                    produces = "application/json;charset=utf8")
    @ResponseBody
    public String returnResults() {
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        List<ResultItem> results = new ArrayList<>();

        lineFrequency.entrySet().forEach(
                entry -> results.add(new ResultItem(entry.getKey(), entry.getValue())));

        try {
            json = mapper.writeValueAsString(results);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }

    private List<UploadedFile> countLineFrequencyAndGetFiles(MultipartFile[] files) {
        List<UploadedFile> uploadedFiles = new ArrayList<>();

        for(MultipartFile file: files) {
            try {
                String originalName = file.getOriginalFilename();
                String contentType = file.getContentType();
                long size = file.getSize();
                List<String> lines = new BufferedReader(new InputStreamReader(file.getInputStream()))
                        .lines().collect(Collectors.toList());

                UploadedFile uploadedFile = new UploadedFile(originalName, contentType,
                        size, lines);

                uploadedFiles.add(uploadedFile);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        Request savedRequest = requestService.save(new Request(uploadedFiles));
        LOGGER.debug("Saved request from the DB: {}", savedRequest);

        lineFrequency = new LineCounter().count(uploadedFiles);

        return uploadedFiles;
    }

    @Autowired
    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }
}
