package com.bintime.services;

import com.bintime.entity.UploadedFile;
import com.bintime.repositories.UploadedFileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UploadedFileService {
    private UploadedFileRepository uploadedFileRepo;

    public UploadedFileService(UploadedFileRepository uploadedFileRepo) {
        this.uploadedFileRepo = uploadedFileRepo;
    }

    @Transactional
    public List<UploadedFile> findAll() {
        return uploadedFileRepo.findAll();
    }
}
