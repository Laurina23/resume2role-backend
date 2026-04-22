package com.resume2role.service;

import com.google.firebase.cloud.StorageClient;
import com.google.cloud.storage.Blob;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FirebaseStorageService {

    public String uploadFile(byte[] fileBytes, String originalFilename) {

        String fileName = "resumes/" + UUID.randomUUID() + "_" + originalFilename;

        Blob blob = StorageClient.getInstance().bucket()
                .create(fileName, fileBytes, "application/pdf");

        return String.format(
                "https://storage.googleapis.com/%s/%s",
                blob.getBucket(),
                blob.getName()
        );
    }
    public void deleteFile(String fileUrl) {

        try {
            String bucketName = StorageClient.getInstance().bucket().getName();

            String filePath = fileUrl.split(bucketName + "/")[1];

            StorageClient.getInstance()
                    .bucket()
                    .get(filePath)
                    .delete();

        } catch (Exception e) {
            throw new RuntimeException("Failed to delete file from Firebase", e);
        }
    }
}