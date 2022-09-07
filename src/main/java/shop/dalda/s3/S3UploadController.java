package shop.dalda.s3;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/api/img")
@RestController
public class S3UploadController {

    private final S3UploadService s3UploadService;

    @PostMapping("/upload")
    public ResponseEntity<S3ResponseDto> uploadFile(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        String uploadUrl = s3UploadService.upload(multipartFile.getInputStream(), multipartFile.getOriginalFilename(), multipartFile.getSize());
        S3ResponseDto s3ResponseDto = S3ResponseDto.builder().url(uploadUrl).build();

        return ResponseEntity.ok(s3ResponseDto);
    }
}
