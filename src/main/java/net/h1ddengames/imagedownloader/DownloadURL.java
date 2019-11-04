package net.h1ddengames.imagedownloader;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Data
public class DownloadURL {
    @Id String id;
    String url;
}
