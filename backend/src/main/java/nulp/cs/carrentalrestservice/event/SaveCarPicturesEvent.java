package nulp.cs.carrentalrestservice.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Getter
public class SaveCarPicturesEvent extends ApplicationEvent {
    private final UUID carId;
    private final MultipartFile[] multipartFiles;

    public SaveCarPicturesEvent(Object source, UUID carId, MultipartFile[] multipartFiles) {
        super(source);
        this.carId = carId;
        this.multipartFiles = multipartFiles;
    }
}
