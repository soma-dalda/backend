package shop.dalda.s3;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class S3UploadService {
    private final AmazonS3Client amazonS3Client;

    @Value("${s3.bucket.name}")
    private String bucket;

    @Value("${cloud.aws.s3.dirOriginal}")
    private String dirOriginal;

    @Value("${s3.cloudfront}")
    private String cloudFront;

    public String upload(InputStream inputStream, String originFileName, Long fileSize) {

        String s3FileName = UUID.randomUUID() + "-" + originFileName;

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(fileSize);

        amazonS3Client.putObject(bucket, dirOriginal + s3FileName, inputStream, objMeta);

        return cloudFront + dirOriginal + s3FileName;
    }
}
