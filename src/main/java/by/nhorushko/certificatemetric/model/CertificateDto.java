package by.nhorushko.certificatemetric.model;

import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.time.Instant;

@ToString
@RequiredArgsConstructor
public class CertificateDto {
    @ToString.Exclude
    private final X509Certificate certificate;
    private final String alias;

    public CertificateDto(String alias, Certificate certificate) {
        this.certificate = (X509Certificate) certificate;
        this.alias = alias;
    }

    public String getAlias() {
        return alias;
    }

    public String getName() {
        return certificate.getSubjectX500Principal().getName();
    }

    @ToString.Include
    public Instant getExpiration() {
        return certificate.getNotBefore().toInstant();
    }
}
