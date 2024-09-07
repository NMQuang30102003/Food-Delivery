package com.myproject.fooddelivery.service;

import com.myproject.fooddelivery.Interface.FileStorageInterface;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.desktop.SystemEventListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
@Service
public class FileStorageImp implements FileStorageInterface {

    @Value("${fileupload.rootpath}")
    private String rootPath ;
    private Path root;

    @Override
    public void init() {
        try
        {
            root = Paths.get(rootPath);
            if(Files.notExists(root))
            {
                Files.createDirectories(root);
            }
        }catch (Exception e) {
            System.out.println("Error create Directories: " + e.getLocalizedMessage());
        }
    }

    @Override
    public boolean save(MultipartFile filename) {
        init();
        try{
            Files.copy(filename.getInputStream(), this.root.resolve(filename.getOriginalFilename()));
            return true;
        }catch(Exception e)
        {
            System.out.println("Error save file: " + e.getLocalizedMessage());
            return false;
        }
    }
    @Override
    public Resource load(String filename) {
        init();
        try{
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable())
            {
                return resource;
            }
        }catch (Exception e)
        {
            System.out.println("Error load file : " + e.getLocalizedMessage());
        }
        return null;
    }

    @Override
    public void deleteAll() {

    }

    @Override
    public Stream<Path> loadAll() {
        return null;
    }
}
