package shop.dalda.s3;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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

    @Operation(summary = "이미지 업로드", description = "이미지 등록을 요청하면 S3에 이미지를 등록 후 그에 해당하는 url 반환하는 메서드")
    @PostMapping("/upload")
    public ResponseEntity<S3ResponseDto> uploadFile(@Parameter(description = "업로드할 이미지 파일") @RequestParam("image") MultipartFile multipartFile) throws IOException {
        String uploadUrl = s3UploadService.upload(multipartFile.getInputStream(), multipartFile.getOriginalFilename(), multipartFile.getSize());
        S3ResponseDto s3ResponseDto = S3ResponseDto.builder().url(uploadUrl).build();

        return ResponseEntity.ok(s3ResponseDto);
    }
}
