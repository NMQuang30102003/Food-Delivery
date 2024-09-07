package com.myproject.fooddelivery.Interface;

import org.apache.tomcat.util.http.fileupload.MultipartStream;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStorageInterface {
    public void init();
    public boolean save(MultipartFile filename);
    public Resource load(String filename);
    public void deleteAll();
    public Stream<Path> loadAll();
}
