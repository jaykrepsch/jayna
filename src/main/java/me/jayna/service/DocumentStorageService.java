package me.jayna.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.File;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class DocumentStorageService {

    private final Path root;

    public DocumentStorageService(@Value("${storage.local.path:#{systemProperties['user.home']}/volumes/jayna/documents}") String rootPath) throws IOException {
        this.root = Paths.get(rootPath).toAbsolutePath().normalize();
        Files.createDirectories(this.root);
    }

    public Path getRoot() {
        return this.root;
    }

    public StoredFile store(Long documentId, MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename() == null ? "upload" : file.getOriginalFilename());
        String safe = filename.replace("..", "_");
        Path targetDir = this.root.resolve(String.valueOf(documentId));
        Files.createDirectories(targetDir);
        Path target = targetDir.resolve(safe);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return new StoredFile(safe, target.toString(), file.getContentType(), file.getSize(), LocalDateTime.now());
    }

    public record StoredFile(String fileName, String filePath, String mimeType, long size, LocalDateTime uploadedAt) {}

    public void deleteAllFor(Long documentId) throws IOException {
        Path targetDir = this.root.resolve(String.valueOf(documentId));
        if (Files.exists(targetDir)) {
            Files.walk(targetDir)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(File::delete);
        }
    }

    public void delete(Long documentId, String fileName) throws IOException {
        if (fileName == null || fileName.isBlank()) return;
        Path target = this.root.resolve(String.valueOf(documentId)).resolve(fileName);
        if (Files.exists(target)) {
            Files.delete(target);
        }
    }

    public List<StoredFile> list(Long documentId) throws IOException {
        List<StoredFile> results = new ArrayList<>();
        Path targetDir = this.root.resolve(String.valueOf(documentId));
        if (!Files.exists(targetDir)) return results;
        try (var stream = Files.list(targetDir)) {
            stream.filter(Files::isRegularFile).forEach(p -> {
                try {
                    String name = p.getFileName().toString();
                    String mime = Files.probeContentType(p);
                    long size = Files.size(p);
                    LocalDateTime lm = LocalDateTime.ofInstant(Files.getLastModifiedTime(p).toInstant(), ZoneId.systemDefault());
                    results.add(new StoredFile(name, p.toString(), mime, size, lm));
                } catch (IOException ignored) {}
            });
        }
        return results;
    }
}


