package cn.pai.connect.http.ssl;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * description：ssl
 * author：pany
 * on 2018/3/13 10:42
 */
public class SSLSocketFactory {
    /**
     * Android证书一般放在asset下
     *
     * @param certificateInputs 证书流
     * @return
     */
    private javax.net.ssl.SSLSocketFactory getSSLSocketFactory(InputStream... certificateInputs) {

        try {
            /**
             * CertificateFactory提供用于解析和管理证书、证书撤消列表 (CRL) 和证书路径的类和接口。
             */
            // 这里生成一个实现指定证书类型的 CertificateFactory 对象
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            /**
             * 密钥和证书的存储设施
             */
            KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
            // 从指定的输入流中加载此 KeyStore
            keystore.load(null, null);
            int index = 0;
            for (InputStream certificateInput : certificateInputs) {
                // 证书别名
                String certificateAlias = "Certificate" + (++index);
                // 生成一个证书对象并使用从输入流 inStream 中读取的数据对它进行初始化
                Certificate certificate = certificateFactory.generateCertificate(certificateInput);
                // 将给定可信证书分配给给定别名
                keystore.setCertificateEntry(certificateAlias, certificate);
                try {
                    if (certificateInput != null)
                        certificateInput.close();
                } catch (IOException e) {
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
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            /**
             * 初始化此上下文。前两个参数都可以为 null，在这种情况下将搜索装入的安全提供程序来寻找适当工厂的最高优先级实现。
             * 同样，安全的 random 参数也可以为 null，在这种情况下将使用默认的实现。
             * 只有数组中的第一个特定密钥和/或信任管理器实现类型的实例被使用。（例如，只有数组中的第一个 javax.net.ssl.X509KeyManager 被使用。）
             * 第一个：身份验证密钥源 第二个同位体身份验证信任决策源 第三个此生成器的随机源
             */
            sslContext.init(null, trustManagerFactory.getTrustManagers(), null);
            return sslContext.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
