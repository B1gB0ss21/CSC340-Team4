package com.backend_api.gameimage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/games")
public class ImageUploadController {
    private static final Logger log = LoggerFactory.getLogger(ImageUploadController.class);

    @Value("${upload.dir:uploads}")
    private String uploadDir;

    @PostMapping(path = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadCover(@PathVariable String id,
                                         @RequestParam("file") MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "No file uploaded"));
        }

        String ct = file.getContentType() == null ? "" : file.getContentType().toLowerCase();
        if (!(ct.startsWith("image/"))) {
            return ResponseEntity.badRequest().body(Map.of("error", "File must be an image"));
        }

        if (file.getSize() > 5 * 1024 * 1024) { // 5MB limit
            return ResponseEntity.status(413).body(Map.of("error", "File too large"));
        }

        Path base = Paths.get(uploadDir).toAbsolutePath().normalize();
        Path gameDir = base.resolve("games");
        Files.createDirectories(gameDir);

        String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
        ext = (ext == null || ext.isBlank()) ? "jpg" : ext.replaceAll("[^A-Za-z0-9]", "");
        String filename = String.format("cover-%s-%d.%s", id, System.currentTimeMillis(), ext);
        Path target = gameDir.resolve(filename);

        try {
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            log.error("Failed to save uploaded file", ex);
            throw ex;
        }

        String publicUrl = "/uploads/games/" + filename;
        Map<String,String> resp = new HashMap<>();
        resp.put("imageUrl", publicUrl);
        return ResponseEntity.ok(resp);
    }
}