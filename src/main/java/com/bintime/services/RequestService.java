package com.bintime.services;

import com.bintime.entity.Request;
import com.bintime.repositories.RequestRepository;
import com.bintime.repositories.UploadedFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RequestService {
    private RequestRepository requestRepo;
    private UploadedFileRepository uploadedFileRepo;

    @Autowired
    public RequestService(RequestRepository requestRepo, UploadedFileRepository uploadedFileRepo) {
        this.requestRepo = requestRepo;
        this.uploadedFileRepo = uploadedFileRepo;
    }

    @Transactional
    public Request save(Request request) {
        uploadedFileRepo.save(request.getUploadedFiles());
        Request savedRequest = requestRepo.save(request);

        return savedRequest;
    }

    @Transactional
    public List<Request> findAll() {
        return requestRepo.findAll();
    }

    @Transactional
    public void printAllInDB() {
        List<Request> requests = requestRepo.findAll();
        System.out.printf("Requests in DB (%d): \n", requests.size());
        requests.forEach(request -> {
            System.out.printf("\t%s\n---------------------------------\n", request);
        });

    }
}
