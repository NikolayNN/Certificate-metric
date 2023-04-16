package by.nhorushko.certificatemetric.store;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.util.Set;

@Component
public class KeyStoreStorageDefault implements KeyStoreStorage {

    private final KeyStore keyStore;

    public KeyStoreStorageDefault(@Value("${app.keystore.password}") String keystorePassword) {
        keyStore = load(keystorePassword);
    }

    @SneakyThrows
    public KeyStore load(String password) {
        String path = "/lib/security/cacerts".replace("/", File.separator);
        String filename = System.getProperty("java.home") + path;
        FileInputStream is = new FileInputStream(filename);
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(is, password.toCharArray());
        return keystore;
    }

    @Override
    @SneakyThrows
    public Set<TrustAnchor> getTrustAnchors() {
        return new PKIXParameters(keyStore).getTrustAnchors();
    }

    @Override
    @SneakyThrows
    public String getAlias(Certificate certificate) {
        return keyStore.getCertificateAlias(certificate);
    }
}
