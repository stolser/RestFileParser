package com.bintime.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import java.util.List;

@Entity
@Embeddable
public class UploadedFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String originalName;
    private String contentType;
    private long size;
    @ElementCollection
    private List<String> lines;

    public UploadedFile() {}

    public UploadedFile(String originalName, String contentType, long size, List<String> lines) {
        this.originalName = originalName;
        this.contentType = contentType;
        this.size = size;
        this.lines = lines;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public List<String> getLines() {
        return lines;
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UploadedFile)) return false;

        UploadedFile that = (UploadedFile) o;

        return id == that.id;

    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return String.format("UploadedFile{id = %d, originalName = '%s', contentType = '%s', size = %d, lines = %s}",
                id, originalName, contentType, size, lines);
    }
}
