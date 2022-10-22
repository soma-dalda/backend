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

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.s3.dirOriginal}")
    private String dirOriginal;

    @Value("${cloud.aws.s3.cloudFront}")
    private String cloudFront;

    private final AmazonS3Client s3Client;

    public String upload(InputStream inputStream, String originFileName, Long fileSize) {
        String s3FileName = UUID.randomUUID() + "-" + originFileName;

        ObjectMetadata objMeta = new ObjectMetadata();
        objMeta.setContentLength(fileSize);

        s3Client.putObject(bucket, dirOriginal + s3FileName, inputStream, objMeta);

        return cloudFront + dirOriginal + s3FileName;
    }
}
