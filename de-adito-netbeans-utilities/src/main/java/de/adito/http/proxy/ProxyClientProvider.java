package de.adito.http.proxy;

import lombok.NonNull;
import org.apache.http.HttpHost;
import org.apache.http.auth.*;
import org.apache.http.client.*;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.config.*;
import org.apache.http.conn.ssl.*;
import org.apache.http.impl.auth.BasicSchemeFactory;
import org.apache.http.impl.client.*;
import org.apache.http.ssl.SSLContexts;
import org.jetbrains.annotations.*;
import org.netbeans.api.keyring.Keyring;
import org.openide.util.NbPreferences;

import javax.net.ssl.SSLContext;
import java.security.*;
import java.util.prefs.*;

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
  @NonNull
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

    int proxyType = PREFERENCES_NODE.getInt("proxyType", 1);
    if (proxyType == 1)
    {
      setSystemProxySettings(httpClientBuilder);
    }
    else if (proxyType == 2)
    {
      setManualProxySettings(httpClientBuilder);
    }
    httpClientBuilder.useSystemProperties();
    // if the proxy requires authentication provide a credentialsProvider and pre-load it with the proxy authentication info stored in netbeans
    boolean isUseProxyAuth = PREFERENCES_NODE.getBoolean("useProxyAuthentication", false);
    if (isUseProxyAuth)
    {
      CredentialsProvider credentialsProvider = new SystemDefaultCredentialsProvider();
      char[] authenticationPassword = Keyring.read("proxyAuthenticationPassword");
      String authenticationUser = PREFERENCES_NODE.get("proxyAuthenticationUsername", null);
      if (authenticationUser != null && authenticationPassword != null)
      {
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(authenticationUser, new String(authenticationPassword)));
      }
      httpClientBuilder.setProxyAuthenticationStrategy(new ProxyAuthenticationStrategy());
      Lookup<AuthSchemeProvider> authProviders = RegistryBuilder.<AuthSchemeProvider>create()
          .register(AuthSchemes.BASIC, new BasicSchemeFactory())
          .build();
      httpClientBuilder.setDefaultAuthSchemeRegistry(authProviders);
      httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
    }
    return httpClientBuilder.build();
  }

  private static void setSystemProxySettings(HttpClientBuilder httpClientBuilder)
  {
    // load system proxy options
    String systemProxyHttpHost = PREFERENCES_NODE.get("systemProxyHttpHost", null);
    String systemProxyHttpPort = PREFERENCES_NODE.get("systemProxyHttpPort", null);
    String systemProxyHttpsHost = PREFERENCES_NODE.get("systemProxyHttpsHost", null);
    String systemProxyHttpsPort = PREFERENCES_NODE.get("systemProxyHttpsPort", null);
    if (isNonEmpty(systemProxyHttpHost) && isNonEmpty(systemProxyHttpPort))
    {
      httpClientBuilder.setProxy(new HttpHost(systemProxyHttpHost, Integer.parseInt(systemProxyHttpPort)));
    }
    else if (isNonEmpty(systemProxyHttpsHost) && isNonEmpty(systemProxyHttpsPort))
    {
      httpClientBuilder.setProxy(new HttpHost(systemProxyHttpsHost, Integer.parseInt(systemProxyHttpsPort)));
    }
  }

  private static void setManualProxySettings(HttpClientBuilder httpClientBuilder)
  {
    // load manually set proxy options
    String proxyHttpHost = PREFERENCES_NODE.get("proxyHttpHost", null);
    String proxyHttpPort = PREFERENCES_NODE.get("proxyHttpPort", null);
    String proxyHttpsHost = PREFERENCES_NODE.get("proxyHttpsHost", null);
    String proxyHttpsPort = PREFERENCES_NODE.get("proxyHttpsPort", null);
    if (isNonEmpty(proxyHttpsHost) && isNonEmpty(proxyHttpsPort))
    {
      httpClientBuilder.setProxy(new HttpHost(proxyHttpsHost, Integer.parseInt(proxyHttpsPort)));
    }
    else if (isNonEmpty(proxyHttpHost) && isNonEmpty(proxyHttpPort))
    {
      httpClientBuilder.setProxy(new HttpHost(proxyHttpHost, Integer.parseInt(proxyHttpPort)));
    }
  }

  public static void addProxySettingsListener(@NonNull PreferenceChangeListener pPreferenceChangeListener)
  {
    PREFERENCES_NODE.addPreferenceChangeListener(pPreferenceChangeListener);
  }

  public static void removeProxySettingsListener(@NonNull PreferenceChangeListener pPreferenceChangeListener)
  {
    PREFERENCES_NODE.removePreferenceChangeListener(pPreferenceChangeListener);
  }

  private static boolean isNonEmpty(@Nullable String pToCheck)
  {
    return pToCheck != null && !pToCheck.isEmpty();
  }

}
