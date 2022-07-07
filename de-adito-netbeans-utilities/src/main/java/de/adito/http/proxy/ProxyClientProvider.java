package de.adito.http.proxy;

import org.apache.http.HttpHost;
import org.apache.http.auth.*;
import org.apache.http.client.*;
import org.apache.http.conn.ssl.*;
import org.apache.http.impl.client.*;
import org.apache.http.ssl.SSLContexts;
import org.jetbrains.annotations.NotNull;
import org.netbeans.api.keyring.Keyring;
import org.openide.util.NbPreferences;

import javax.net.ssl.SSLContext;
import java.security.*;
import java.util.prefs.Preferences;

/**
 * Utility class for allowing easy setting of proxy options
 *
 * @author m.kaspera, 07.07.2022
 */
public class ProxyClientProvider
{

  private static final Preferences PREFERENCES_NODE = NbPreferences.root().node("org/netbeans/core");

  private ProxyClientProvider()
  {
  }

  /**
   * Builds an HttpClient with pre-determined proxy for setting in e.g. Unirest if a proxy is required
   *
   * @param pIgnoreCertificates only set this to true if all certificate errors should be ignored
   * @return HttpClient that includes the proxy determined by the Netbeans proxy settings
   * @throws NoSuchAlgorithmException only relevant if certificates should be ignored, thrown if the no Self-Signed Strategy algorithm can be found
   * @throws KeyStoreException        only relevant if certificates should be ignored, thrown if the keystore cannot be initalised
   * @throws KeyManagementException   only relevant if certificates should be ignored, thrown if the SSL Context cannot be initalised
   */
  @NotNull
  public static HttpClient getClient(boolean pIgnoreCertificates) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException
  {
    HttpClientBuilder httpClientBuilder = HttpClients.custom();
    if (pIgnoreCertificates)
    {
      SSLContext sslcontext = SSLContexts.custom()
          .loadTrustMaterial(null, new TrustSelfSignedStrategy())
          .build();
      SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, NoopHostnameVerifier.INSTANCE);
      httpClientBuilder.setSSLSocketFactory(sslsf);
    }

    // load manually set proxy options
    String proxyHttpHost = PREFERENCES_NODE.get("proxyHttpHost", null);
    String proxyHttpPort = PREFERENCES_NODE.get("proxyHttpPort", null);
    String proxyHttpsHost = PREFERENCES_NODE.get("proxyHttpsHost", null);
    String proxyHttpsPort = PREFERENCES_NODE.get("proxyHttpsPort", null);
    // load system proxy options
    String systemProxyHttpHost = PREFERENCES_NODE.get("systemProxyHttpHost", null);
    String systemProxyHttpPort = PREFERENCES_NODE.get("systemProxyHttpPort", null);
    String systemProxyHttpsHost = PREFERENCES_NODE.get("systemProxyHttpsHost", null);
    String systemProxyHttpsPort = PREFERENCES_NODE.get("systemProxyHttpsPort", null);
    if (proxyHttpsHost != null && proxyHttpsPort != null)
    {
      httpClientBuilder.setProxy(new HttpHost(proxyHttpsHost, Integer.parseInt(proxyHttpsPort)));
    }
    else if (proxyHttpHost != null && proxyHttpPort != null)
    {
      httpClientBuilder.setProxy(new HttpHost(proxyHttpHost, Integer.parseInt(proxyHttpPort)));
    }
    else if (systemProxyHttpHost != null && systemProxyHttpPort != null)
    {
      httpClientBuilder.setProxy(new HttpHost(systemProxyHttpHost, Integer.parseInt(systemProxyHttpPort)));
    }
    else if (systemProxyHttpsHost != null && systemProxyHttpsPort != null)
    {
      httpClientBuilder.setProxy(new HttpHost(systemProxyHttpsHost, Integer.parseInt(systemProxyHttpsPort)));
    }
    // if the proxy requires authentication provide a credentialsProvider and pre-load it with the proxy authentication info stored in netbeans
    boolean isUseProxyAuth = Boolean.parseBoolean(PREFERENCES_NODE.get("useProxyAuthentication", null));
    if (isUseProxyAuth)
    {
      CredentialsProvider credentialsProvider = new SystemDefaultCredentialsProvider();
      char[] authenticationPassword = Keyring.read("proxyAuthenticationPassword");
      String authenticationUser = PREFERENCES_NODE.get("proxyAuthenticationUsername", null);
      if (authenticationUser != null && authenticationPassword != null)
      {
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(authenticationUser, new String(authenticationPassword)));
      }
      httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
    }
    return httpClientBuilder.build();
  }

}
