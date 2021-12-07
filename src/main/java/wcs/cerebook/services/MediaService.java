package wcs.cerebook.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import wcs.cerebook.entity.CerebookEvent;
import wcs.cerebook.entity.CerebookPicture;
import wcs.cerebook.entity.CerebookUser;
import wcs.cerebook.entity.CerebookVideo;
import wcs.cerebook.model.SimpleHostedMedia;
import wcs.cerebook.repository.EventRepository;
import wcs.cerebook.repository.PictureRepository;
import wcs.cerebook.repository.VideoRepository;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MediaService {

    @Autowired
    PictureRepository pictureRepository;
    @Autowired
    VideoRepository videoRepository;
    @Autowired
    EventRepository eventRepository;
    @Autowired
    HostingService hostingService;

    public void uploadPicture(String filename, InputStream inputStream, long size, CerebookUser user) {
        hostingService.uploadPictureImage(filename, inputStream, size);

        CerebookPicture cerebookPicture = new CerebookPicture();
        cerebookPicture.setUser(user);
        cerebookPicture.setMediaType(CerebookPicture.Type.SimpleMedia);
        cerebookPicture.setPicturePath(filename);
        cerebookPicture.setAmazonS3Hosted(hostingService.isAmazon());

        pictureRepository.save(cerebookPicture);
    }

    public void uploadVideo(String filename, InputStream inputStream, long size, CerebookUser user) {
        hostingService.uploadPictureImage(filename, inputStream, size);

        CerebookVideo cerebookVideo = new CerebookVideo();
        cerebookVideo.setUser(user);
        cerebookVideo.setMediaType(CerebookVideo.Type.SimpleMedia);
        cerebookVideo.setVideoPath(filename);
        cerebookVideo.setAmazonS3Hosted(hostingService.isAmazon());

        videoRepository.save(cerebookVideo);
    }

    public void uploadEventImage(String filename, InputStream inputStream, long size, CerebookUser user, CerebookEvent cerebookEvent) {
        hostingService.uploadPictureImage(filename, inputStream, size);

        cerebookEvent.setCerebookUser(user);
        cerebookEvent.setMediaType(CerebookEvent.Type.SimpleMedia);
        cerebookEvent.setImage(filename);
        cerebookEvent.setAmazonS3Hosted(hostingService.isAmazon());

        eventRepository.save(cerebookEvent);
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
