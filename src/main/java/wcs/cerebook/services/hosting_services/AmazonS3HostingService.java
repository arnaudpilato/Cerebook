package wcs.cerebook.services.hosting_services;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@Service
class AmazonS3HostingService implements IHostingService {
    // <editor-fold desc="Fields">
    @Value("${amazon.aws.s3.accesskey}")
    private String accessKey;

    @Value("${amazon.aws.s3.secretkey}")
    private String accessSecret;

    @Value("${amazon.aws.s3.bucket}")
    private String bucketName;

    @Value("https://${amazon.aws.s3.bucket}.s3.${amazon.aws.s3.region}.amazonaws.com")
    private String urlPrefix;

    private AmazonS3 amazonS3;
    // </editor-fold>

    // <editor-fold desc="Public methods">
    private AmazonS3 getAmazonS3() {
        if(amazonS3 == null) {
            initAmazonS3Client();
        }
        return amazonS3;
    }

    public void uploadPictureImage(String objectKey, InputStream inputStream, Long size) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(size);
        getAmazonS3().putObject(
                new PutObjectRequest(bucketName, objectKey, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public List<String> listObjects() {
        return getAmazonS3()
                .listObjects(bucketName)
                .getObjectSummaries()
                .stream()
                .map(S3ObjectSummary::getKey)
                .collect(Collectors.toList());
    }

    public void deleteObject(String key) {
        getAmazonS3().deleteObject(new DeleteObjectRequest(bucketName, key));
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }

    @Override
    public boolean isAmazon() {
        return true;
    }
    // </editor-fold>

    // <editor-fold desc="Private methods">
    private void initAmazonS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(
                accessKey,
                accessSecret
        );

        amazonS3 = AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_WEST_3)
                .build();
    }


    // </editor-fold>
}
