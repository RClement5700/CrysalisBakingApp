package crysalis.example.crysalisbakingapp;

import java.io.Serializable;

public class Step implements Serializable {
    int id;
    String shortDescription, description, videoUrl, thumbnailUrl;

    public Step(int id, String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }
}
