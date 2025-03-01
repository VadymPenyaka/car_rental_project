package nulp.cs.carrentalrestservice.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CaesarCipherServiceImpl implements CaesarCipherService {
//    @Value("${encryption.secret}")
    private int shift = 7;

    @Override
    public String encrypt(String text) {
        StringBuilder sb = new StringBuilder();
        for (char c:text.toCharArray()) {
            sb.append(c+shift);
        }
        return sb.toString();
    }

    @Override
    public String decrypt(String encryptedText) {
        StringBuilder sb = new StringBuilder();
        for (char c:encryptedText.toCharArray()) {
            sb.append(c-shift);
        }
        return sb.toString();
    }

}
