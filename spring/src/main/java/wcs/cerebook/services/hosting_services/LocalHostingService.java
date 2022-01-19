package wcs.cerebook.services.hosting_services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
class LocalHostingService implements IHostingService {
    @Value("${local.storage.main_path}")
    private String mainPath;

    @Value("${local.storage.sub_path}")
    private String subPath;

    @Value("${local.storage.main_path}/${local.storage.sub_path}")
    private String rootPath;

    @Override
    public void uploadPictureImage(String objectKey, InputStream inputStream, Long size) {
        Path fullPath = getPath(objectKey);
        try {
            Files.createDirectories(fullPath);
            Files.copy(inputStream, fullPath, StandardCopyOption.REPLACE_EXISTING);
        }
        catch(IOException io) {
            System.err.println("Cannot create directories - " + io);
        }
    }

    @Override
    public List<String> listObjects() {
        createRootDir();
        String path = getRootPath();
        try {
            return Files.walk(Paths.get(path))
                    .filter(Files::isRegularFile)
                    .map(Path::toString)
                    .map(s -> s.substring(rootPath.length() + 1))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getRootPath() {
        return rootPath;
    }

    @Override
    public void deleteObject(String key) {
        getFile(key).delete();
    }

    @Override
    public String getUrlPrefix() {
        return "/" + subPath;
    }

    @Override
    public boolean isAmazon() {
        return false;
    }

    private File getFile(String objectKey) {
        return new File(String.format("%s/%s", rootPath, objectKey));
    }

    private Path getPath(String objectKey) {
        return getFile(objectKey).toPath();
    }

    private void createRootDir() {
        String path = getRootPath();
        try {
            Files.createDirectories(Path.of(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
