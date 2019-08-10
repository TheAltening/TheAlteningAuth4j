package com.thealtening.auth;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

public final class SSLController {

    private final SSLSocketFactory allTrustingFactory;
    private final SSLSocketFactory originalFactory;

    private final HostnameVerifier originalHostVerifier;

    public SSLController() {
        SSLContext sc = null;
        try {
            sc = SSLContext.getInstance("SSL");
            sc.init(null, ALL_TRUSTING_TRUST_MANAGER, new SecureRandom());
        }catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

        this.allTrustingFactory = sc.getSocketFactory();
        this.originalFactory = HttpsURLConnection.getDefaultSSLSocketFactory();
        this.originalHostVerifier = HttpsURLConnection.getDefaultHostnameVerifier();
    }

    public void enableCertificateValidation() {
        updateCertificateValidation(this.originalFactory, this.originalHostVerifier);
    }

    public void disableCertificateValidation() {
        updateCertificateValidation(this.allTrustingFactory, ALL_TRUSTING_HOSTNAME_VERIFIER);
    }

    private void updateCertificateValidation(SSLSocketFactory factory, HostnameVerifier hostnameVerifier) {
        HttpsURLConnection.setDefaultSSLSocketFactory(factory);
        HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
    }

    private static final TrustManager[] ALL_TRUSTING_TRUST_MANAGER = new TrustManager[] {
            new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {}
                public void checkServerTrusted(X509Certificate[] certs, String authType) {}
            }
    };

    private static final HostnameVerifier ALL_TRUSTING_HOSTNAME_VERIFIER  = new HostnameVerifier() {
        public boolean verify(String hostname, SSLSession session) {
            return hostname.equals("authserver.thealtening.com") || hostname.equals("sessionserver.thealtening.com");
        }
    };
}
