package wcs.cerebook.services.hosting_services;

import java.io.InputStream;
import java.util.List;

public interface IHostingService {
    public void uploadPictureImage(String objectKey, InputStream inputStream, Long size);
    public List<String> listObjects();
    public void deleteObject(String key);
    public String getUrlPrefix();
    public boolean isAmazon();
}
