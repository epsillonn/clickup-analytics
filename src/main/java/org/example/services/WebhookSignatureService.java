package org.example.services;

import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

import static javax.xml.crypto.dsig.SignatureMethod.HMAC_SHA256;

@Service
public class WebhookSignatureService {
    private static final String hmac_sha256 = "HmacSHA256";
        public boolean verifySignature(String rawPayload, String headerSignature, String secretKey) {
            if(rawPayload == null || secretKey==null || headerSignature==null){
                return false;
            }
          try{
              Mac mac = Mac.getInstance(HMAC_SHA256);
              SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), HMAC_SHA256);
              mac.init(secretKeySpec);

              byte[] hash = mac.doFinal(rawPayload.getBytes(StandardCharsets.UTF_8));
              String calculatedSignature = bytesToHex(hash);

              // Constant-time comparison (Timing attack koruması)
              return MessageDigest.isEqual(
                      calculatedSignature.getBytes(StandardCharsets.UTF_8),
                      headerSignature.getBytes(StandardCharsets.UTF_8)
              );
          } catch (Exception e) {
              return false;
          }
        }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}



