package petStore.service.fileStorage;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Storage configs
 */

@ConfigurationProperties("fileStorage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "images";

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

}