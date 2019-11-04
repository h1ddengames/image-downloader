package net.h1ddengames.imagedownloader;

import org.springframework.data.jpa.repository.JpaRepository;

public interface URLRepository extends JpaRepository<DownloadURL, String> {
}
