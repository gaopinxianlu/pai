package cn.pai.connect.http.ssl;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;


/**
 * description：ssl 工具类
 * author：pany
 * on 2018/3/13 10:42
 */
public class SSLSocketHelper {
    /**
     * Android证书一般放在asset下
     * <p>
     * 单向证书
     *
     * @param certificateInputs 证书流
     * @return
     */
    public static SSLSocketFactory getSingleSSLFactory(InputStream... certificateInputs) {

        try {
            /**
             * CertificateFactory提供用于解析和管理证书、证书撤消列表 (CRL) 和证书路径的类和接口。
             */
            // 这里生成一个实现指定证书类型的 CertificateFactory 对象
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            //密钥和证书的存储设施
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            // 从指定的输入流中加载此 KeyStore
            keystore.load(null, null);// By convention, ‘null‘ creates an empty key store.
            for (int i = 0; i < certificateInputs.length; i++) {
                // 生成一个证书对象并使用从输入流 inStream 中读取的数据对它进行初始化
                InputStream certificateInput = null;
                try {
                    certificateInput = certificateInputs[i];
                    Certificate certificate = certificateFactory.generateCertificate(certificateInput);
                    // 将给定可信证书分配给给定别名
                    keystore.setCertificateEntry(Integer.toString(i++), certificate);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (certificateInput != null)
                        certificateInput.close();
                }
            }
            // TrustManagerFactory此类充当基于信任材料源的信任管理器的工厂，每个信任管理器管理特定类型的由安全套接字使用的信任材料。
            // 信任材料是基于 KeyStore 和/或提供程序特定的源
            // 这里是获取默认的 TrustManagerFactory 算法名称
            String defaultAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            //生成实现指定的信任管理算法的 TrustManagerFactory 对象
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(defaultAlgorithm);
            //用证书授权源和相关的信任材料初始化此工厂
            trustManagerFactory.init(keystore);

            // Create an SSLContext that uses our TrustManager
            SSLContext sslContext = SSLContext.getInstance("TLS");
            /**
             * 初始化此上下文。前两个参数都可以为 null，在这种情况下将搜索装入的安全提供程序来寻找适当工厂的最高优先级实现。
             * 同样，安全的 random 参数也可以为 null，在这种情况下将使用默认的实现。
             * 只有数组中的第一个特定密钥和/或信任管理器实现类型的实例被使用。（例如，只有数组中的第一个 javax.net.ssl.X509KeyManager 被使用。）
             * 第一个：身份验证密钥源 第二个同位体身份验证信任决策源 第三个此生成器的随机源
             */
            sslContext.init(null, trustManagerFactory.getTrustManagers(), new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @param priKey    客户端私钥证书
     * @param priKeyPwd 客户端私钥密码
     * @param pubKey    服务端公钥证书
     * @param pubKeyPwd 服务端公钥密码
     * @return
     */
    public static SSLSocketFactory getMutualSSLFactory(InputStream priKey, String priKeyPwd, InputStream pubKey, String pubKeyPwd) {
        SSLSocketFactory sslSocketFactory = null;
        try {
            // 服务器端需要验证的客户端证书，其实就是客户端的keystore
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            // 客户端信任的服务器端证书
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            //加载证书
            keyStore.load(priKey, priKeyPwd.toCharArray());
            trustStore.load(pubKey, pubKeyPwd.toCharArray());
            priKey.close();
            pubKey.close();
            //初始化SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLS");
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("X509");
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("X509");
            trustManagerFactory.init(trustStore);
            keyManagerFactory.init(keyStore, priKeyPwd.toCharArray());
            sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return sslSocketFactory;
    }

    /**
     * 客户端不验服务器证书
     *
     * @return
     */
    public static SSLSocketFactory getNoValidSSLFactory() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) {
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[]{};
                    }
                }
        };
        try {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static X509TrustManager getX509TrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }
}
