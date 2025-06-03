package nulp.cs.carrentalrestservice.shared.event.listener;

import lombok.RequiredArgsConstructor;
import nulp.cs.carrentalrestservice.shared.event.SaveCarPicturesEvent;
import nulp.cs.carrentalrestservice.modules.file.S3Service;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class FIleListener {
    private final S3Service s3Service;

    @EventListener
    public void saveCarPictures (SaveCarPicturesEvent event) {
        int fileNumber = 0;

        for (MultipartFile file: event.getMultipartFiles()) {
            String fileName = event.getCarId() + "_" + fileNumber;
            s3Service.savePictureToServer(file, fileName);
            fileNumber++;
        }
    }
}
