package wcs.cerebook.model;

import wcs.cerebook.entity.CerebookPicture;

public class SimpleHostedMedia {
    private final String urlPrefix;
    private final CerebookPicture cerebookPicture;

    public SimpleHostedMedia(String urlPrefix, CerebookPicture cerebookPicture) {
        this.urlPrefix = urlPrefix;
        this.cerebookPicture = cerebookPicture;
    }

    public String getUrl() {
        return urlPrefix + "/" + cerebookPicture.getPicturePath();
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }

    public CerebookPicture getCerebookPicture() {
        return cerebookPicture;
    }
}
