package by.nhorushko.certificatemetric;

import by.nhorushko.certificatemetric.model.CertificateDto;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.function.ToDoubleFunction;

import static java.time.Duration.between;
import static java.util.concurrent.TimeUnit.SECONDS;

@Component
public class CertificateMetric implements MeterBinder {
    private final CertificateLoader loader;

    private final ToDoubleFunction<CertificateDto> daysRemaining = c -> {
        long secondsRemaining = between(Instant.now(), c.getExpiration()).getSeconds();
        return (double) SECONDS.toDays(secondsRemaining);
    };

    public CertificateMetric(CertificateLoader loader) {
        this.loader = loader;
    }

    @Override
    public void bindTo(MeterRegistry registry) {
        loader.getCertificates().forEach(certificate ->
                Gauge.builder("certificate." + certificate.getAlias(), certificate, daysRemaining)
                        .description("certificate will expire in days")
                        .baseUnit("days")
                        .register(registry)
        );
    }
}
