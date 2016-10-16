package com.bintime.controllers;

import com.bintime.config.H2RootConfig;
import com.bintime.services.RequestService;
import com.bintime.services.UploadedFileService;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.FileInputStream;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {H2RootConfig.class})
@WebAppConfiguration
public class FileManagerControllerTest {
    @Autowired
    private FileManager fileController;
    @Autowired
    private RequestService requestService;
    @Autowired
    private UploadedFileService uploadedFileService;
    private InputStream fileNames;
    private InputStream fileNumbers;
    private InputStream fileMix;
    private String userNameAttrKey;
    private String uploadedFilesAttrKey;
    private String userNameModelAttrValue;
    private MockMultipartFile mockFileNames;
    private MockMultipartFile mockFileNumbers;
    private MockMultipartFile mockFileMix;

    @Before
    public void setUp() throws Exception {
        fileNames = new FileInputStream("D:\\Programs\\IntelliJ IDEA\\IdeaProjects\\" +
                "RestFileParser\\src\\main\\resources\\filesForUpload\\names.txt");
        fileNumbers = new FileInputStream("D:\\Programs\\IntelliJ IDEA\\IdeaProjects\\" +
                "RestFileParser\\src\\main\\resources\\filesForUpload\\numbers.txt");
        fileMix = new FileInputStream("D:\\Programs\\IntelliJ IDEA\\IdeaProjects\\" +
                "RestFileParser\\src\\main\\resources\\filesForUpload\\mix.txt");
        mockFileNames = new MockMultipartFile("file", "names.txt", "multipart/form-data", fileNames);
        mockFileNumbers = new MockMultipartFile("file", "numbers.txt", "multipart/form-data", fileNumbers);
        mockFileMix = new MockMultipartFile("file", "mix.txt", "multipart/form-data", fileMix);

        userNameAttrKey = "userName";
        uploadedFilesAttrKey = "uploadedFiles";
        userNameModelAttrValue = "Oleh Stoliarov";
    }

    @Test
    public void uploadFilesEndpointShouldWorkProperly() throws Exception {
        uploading3filesShouldPersist1requestAnd3files(mockFileNames, mockFileNumbers, mockFileMix);
        uploading1MoreFileShouldPersist2requestsAnd4files(mockFileNames);
    }

    private void uploading3filesShouldPersist1requestAnd3files(MockMultipartFile mockFile1, MockMultipartFile mockFile2, MockMultipartFile mockFile3) throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();

        mockMvc.perform(
                fileUpload("/upload-files")
                .file(mockFile1).file(mockFile2).file(mockFile3)
                .param(userNameAttrKey, userNameModelAttrValue))
                .andExpect(status().isOk())
                .andExpect(view().name("thanksForUploading"))
                .andExpect(model().attributeExists(userNameAttrKey, uploadedFilesAttrKey))
                .andExpect(model().attribute(userNameAttrKey, userNameModelAttrValue));

        assertEquals(1, requestService.findAll().size());
        assertEquals(3, uploadedFileService.findAll().size());
    }

    private void uploading1MoreFileShouldPersist2requestsAnd4files(MockMultipartFile mockFile1) throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();
        mockMvc.perform(
                fileUpload("/upload-files")
                .file(mockFile1)
                .param(userNameAttrKey, userNameModelAttrValue))
                .andExpect(status().isOk())
                .andExpect(view().name("thanksForUploading"));

        assertEquals(2, requestService.findAll().size());
        assertEquals(4, uploadedFileService.findAll().size());
    }

    @Test
    public void uploadNamesFile_getResultsShouldReturnCorrectJson() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(fileController).build();
        mockMvc.perform(
                fileUpload("/upload-files")
                .file(mockFileNames));

        mockMvc.perform(get("/get-results"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=utf8"))
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[0].value", is("Bruce")))
                .andExpect(jsonPath("$[0].count", is(2)))
                .andExpect(jsonPath("$[1].value", is("Brian")))
                .andExpect(jsonPath("$[1].count", is(1)))
                .andExpect(jsonPath("$[2].value", is("Bob")))
                .andExpect(jsonPath("$[2].count", is(1)))
                .andExpect(jsonPath("$[3].value", is("Joshua")))
                .andExpect(jsonPath("$[3].count", is(3)));

    }

}