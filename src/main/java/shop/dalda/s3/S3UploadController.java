package shop.dalda.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

@RequiredArgsConstructor
@RequestMapping("/api/img")
@RestController
public class S3UploadController {

    private final S3UploadService s3UploadService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("images") MultipartFile multipartFile,
                                             @RequestParam String fileSize) throws IOException {
        String uploadUrl = s3UploadService.upload(multipartFile.getInputStream(), multipartFile.getOriginalFilename(), fileSize);

        return ResponseEntity.created(URI.create(uploadUrl)).build();
    }
}
