package by.nhorushko.certificatemetric;

import by.nhorushko.certificatemetric.model.CertificateDto;
import by.nhorushko.certificatemetric.store.KeyStoreStorage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.security.cert.Certificate;
import java.security.cert.TrustAnchor;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class CertificateLoader {

    private final KeyStoreStorage keyStoreStorage;

    @SneakyThrows
    public List<CertificateDto> getCertificates() {
        return keyStoreStorage.getTrustAnchors().stream()
                .map(TrustAnchor::getTrustedCert)
                .map(this::create)
                .collect(Collectors.toList());
    }

    private CertificateDto create(Certificate certificate) {
        String alias = keyStoreStorage.getAlias(certificate);
        return new CertificateDto(alias, certificate);
    }

}