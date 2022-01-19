package wcs.cerebook.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wcs.cerebook.services.hosting_services.HostingServiceProxy;
import wcs.cerebook.services.hosting_services.IHostingService;

import java.io.InputStream;
import java.util.List;

@Service
public class HostingService {
    @Autowired
    private HostingServiceProxy hostingServiceProxy;

    private IHostingService hostingService;

    private IHostingService getHostingService() {
        if(hostingService == null) {
            hostingService = hostingServiceProxy.getHostingService();
        }
        return hostingService;
    }

    public void uploadPictureImage(String objectKey, InputStream inputStream, Long size) {
        getHostingService().uploadPictureImage(objectKey, inputStream, size);
    }

    public List<String> listObjects() {
        return getHostingService().listObjects();
    }

    public void deleteObject(String key) {
        getHostingService().deleteObject(key);
    }

    public String getUrlPrefix() {
        return getHostingService().getUrlPrefix();
    }

    public boolean isAmazon() {
        return getHostingService().isAmazon();
    }
}
