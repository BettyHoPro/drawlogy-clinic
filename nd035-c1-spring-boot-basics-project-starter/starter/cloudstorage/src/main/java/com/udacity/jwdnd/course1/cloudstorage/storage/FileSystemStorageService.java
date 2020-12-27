package com.udacity.jwdnd.course1.cloudstorage.storage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
@Service
public class FileSystemStorageService implements StorageService {

    FileMapper fileMapper;
    private final Path rootLocation;

    @Autowired
    public FileSystemStorageService(StorageProperties properties,FileMapper fileMapper) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.fileMapper=fileMapper;
    }

    @Override
    public void store(MultipartFile mfile,int userId) {

        try {
            File file=new File(mfile);
            file.setFk_userid(userId);
            fileMapper.insert(file);
        }
       catch (Exception exception)
       {
            System.out.println(exception.getCause());
       }

        /* try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = this.rootLocation.resolve(
                    Paths.get(file.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                // This is a security check
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }*/
    }

    @Override
    public List<File> loadAllFile()
    {
        return fileMapper.getAllfiles();
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public void deleteById(int fileid)
    {
        fileMapper.deleteById(fileid);
    }
    @Override
    public File loadSingleFile(int fileid) {
 //       try {
            return  fileMapper.getfileById(fileid);

/*            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file " );

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }*/
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}