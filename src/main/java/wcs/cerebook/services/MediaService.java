package wcs.cerebook.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wcs.cerebook.entity.CerebookPicture;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.model.SimpleHostedMedia;
import wcs.cerebook.repository.PictureRepository;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MediaService {

    @Autowired
    PictureRepository pictureRepository;

    @Autowired
    HostingService hostingService;

    public void uploadMedia(String filename, InputStream inputStream, long size, CerebookUser user) {
        hostingService.uploadPictureImage(filename, inputStream, size);

        CerebookPicture cerebookPicture = new CerebookPicture();
        cerebookPicture.setUser(user);
        cerebookPicture.setMediaType(CerebookPicture.Type.SimpleMedia);
        cerebookPicture.setPicturePath(filename);
        cerebookPicture.setAmazonS3Hosted(hostingService.isAmazon());

        pictureRepository.save(cerebookPicture);
    }

    public List<SimpleHostedMedia> getMediaList() {
        return pictureRepository
                .findAll()
                .stream()
                .map(cm -> new SimpleHostedMedia(
                        hostingService.getUrlPrefix(),
                        cm
                ))
                .collect(Collectors.toList());
    }
}
