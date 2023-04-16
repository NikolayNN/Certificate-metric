package by.nhorushko.certificatemetric.store;

import java.security.cert.Certificate;
import java.security.cert.TrustAnchor;
import java.util.Set;

public interface KeyStoreStorage {

    Set<TrustAnchor> getTrustAnchors();

    String getAlias(Certificate certificate);
}
