package me.jayna.web.rest;

import me.jayna.domain.Document;
import me.jayna.repository.DocumentRepository;
import me.jayna.repository.GroupTypeRepository;
import me.jayna.domain.GroupType;
import me.jayna.domain.enumeration.DocumentStatus;
import me.jayna.domain.enumeration.EntityState;
import me.jayna.service.DocumentStorageService;
import me.jayna.service.DocumentStorageService.StoredFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.core.io.InputStreamResource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/documents")
@Transactional
public class DocumentResource {

    private final DocumentRepository documentRepository;
    private final DocumentStorageService storageService;
    private final GroupTypeRepository groupTypeRepository;

    public DocumentResource(DocumentRepository documentRepository, DocumentStorageService storageService, GroupTypeRepository groupTypeRepository) {
        this.documentRepository = documentRepository;
        this.storageService = storageService;
        this.groupTypeRepository = groupTypeRepository;
    }

    @GetMapping
    public ResponseEntity<List<Document>> getAll() {
        return ResponseEntity.ok(documentRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> getOne(@PathVariable Long id) {
        return documentRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Document> create(@RequestBody Document document) {
        // Defaults
        if (document.getStatus() == null) {
            document.setStatus(DocumentStatus.DRAFT);
        }
        if (document.getEntityState() == null) {
            document.setEntityState(EntityState.ACTIVE);
        }
        // Ensure managed GroupType reference if provided
        if (document.getGroupType() != null && document.getGroupType().getId() != null) {
            GroupType gt = groupTypeRepository.findById(document.getGroupType().getId()).orElse(null);
            document.setGroupType(gt);
        }
        document.setUploadDate(LocalDateTime.now());
        Document saved = documentRepository.save(document);
        return ResponseEntity.created(URI.create("/api/documents/" + saved.getId())).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Document> update(@PathVariable Long id, @RequestBody Document document) {
        if (document.getId() == null) document.setId(id);
        if (!documentRepository.existsById(id)) return ResponseEntity.notFound().build();
        // Defaults
        if (document.getStatus() == null) {
            document.setStatus(DocumentStatus.DRAFT);
        }
        if (document.getEntityState() == null) {
            document.setEntityState(EntityState.ACTIVE);
        }
        // Ensure managed GroupType reference if provided
        if (document.getGroupType() != null && document.getGroupType().getId() != null) {
            GroupType gt = groupTypeRepository.findById(document.getGroupType().getId()).orElse(null);
            document.setGroupType(gt);
        }
        Document saved = documentRepository.save(document);
        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
        // Erst Dateien löschen, dann DB-Eintrag
        storageService.deleteAllFor(id);
        if (documentRepository.existsById(id)) {
            documentRepository.deleteById(id);
        }
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<Document> upload(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws Exception {
        Document doc = documentRepository.findById(id).orElse(null);
        if (doc == null) return ResponseEntity.notFound().build();
        StoredFile stored = storageService.store(id, file);
        doc.setFileName(stored.fileName());
        doc.setFilePath(stored.filePath());
        doc.setMimeType(stored.mimeType());
        doc.setFileType(stored.mimeType());
        doc.setFileSize(stored.size());
        doc.setLastModified(stored.uploadedAt());
        documentRepository.save(doc);
        return ResponseEntity.ok(doc);
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<InputStreamResource> download(@PathVariable Long id) throws Exception {
        Document doc = documentRepository.findById(id).orElse(null);
        if (doc == null || doc.getFilePath() == null) return ResponseEntity.notFound().build();
        java.nio.file.Path path = java.nio.file.Paths.get(doc.getFilePath());
        if (!java.nio.file.Files.exists(path)) return ResponseEntity.notFound().build();
        java.io.InputStream is = java.nio.file.Files.newInputStream(path);
        String fileName = doc.getFileName() == null ? "document" : doc.getFileName();
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
            .contentType(MediaType.parseMediaType(doc.getMimeType() == null ? MediaType.APPLICATION_OCTET_STREAM_VALUE : doc.getMimeType()))
            .contentLength(java.nio.file.Files.size(path))
            .body(new InputStreamResource(is));
    }

    @DeleteMapping("/{id}/file")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) throws Exception {
        Document doc = documentRepository.findById(id).orElse(null);
        if (doc == null) return ResponseEntity.notFound().build();
        if (doc.getFileName() != null) {
            storageService.delete(id, doc.getFileName());
        }
        doc.setFileName(null);
        doc.setFilePath(null);
        doc.setMimeType(null);
        doc.setFileSize(null);
        doc.setLastModified(null);
        documentRepository.save(doc);
        return ResponseEntity.noContent().build();
    }

    // List all files under this document's storage folder
    @GetMapping("/{id}/files")
    public ResponseEntity<List<StoredFile>> listFiles(@PathVariable Long id) throws Exception {
        if (!documentRepository.existsById(id)) return ResponseEntity.notFound().build();
        List<StoredFile> files = storageService.list(id);
        return ResponseEntity.ok(files);
    }

    // Download a specific file by name
    @GetMapping("/{id}/files/{fileName:.+}")
    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable Long id, @PathVariable String fileName) throws Exception {
        if (!documentRepository.existsById(id)) return ResponseEntity.notFound().build();
        java.nio.file.Path dir = storageService.getRoot().resolve(String.valueOf(id));
        java.nio.file.Path path = dir.resolve(fileName);
        if (!java.nio.file.Files.exists(path) || !java.nio.file.Files.isRegularFile(path)) return ResponseEntity.notFound().build();
        java.io.InputStream is = java.nio.file.Files.newInputStream(path);
        String mime = java.nio.file.Files.probeContentType(path);
        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
            .contentType(MediaType.parseMediaType(mime == null ? MediaType.APPLICATION_OCTET_STREAM_VALUE : mime))
            .contentLength(java.nio.file.Files.size(path))
            .body(new InputStreamResource(is));
    }

    // Delete a specific file by name
    @DeleteMapping("/{id}/files/{fileName:.+}")
    public ResponseEntity<Void> deleteFileByName(@PathVariable Long id, @PathVariable String fileName) throws Exception {
        Document doc = documentRepository.findById(id).orElse(null);
        if (doc == null) return ResponseEntity.notFound().build();
        storageService.delete(id, fileName);
        if (fileName.equals(doc.getFileName())) {
            doc.setFileName(null);
            doc.setFilePath(null);
            doc.setMimeType(null);
            doc.setFileType(null);
            doc.setFileSize(null);
            doc.setLastModified(null);
        }
        documentRepository.save(doc);
        return ResponseEntity.noContent().build();
    }
}


