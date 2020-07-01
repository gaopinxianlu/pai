package cn.sc.pai.test.app;

import android.util.Base64;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SignUtil {
	private static final String JKS = "JKS";
	private static final String P12 = "P12";
	private static final String PKCS12 = "PKCS12";
	private static final String JCEKS = "JCEKS";
	private static final String JCK = "JCK";
	private static final String PFX = "PFX";

	public static final String DEFAULT_ENCODING = "UTF-8";

	public static final String SHA1WithRSA = "SHA1WithRSA";
	public static final String SHA256WithRSA = "SHA256WithRSA";

	public static void main(String[] args) {
		test();
	}

	public static void test() {
		try {
			String algorithm = SHA256WithRSA;
			PublicKey pubkey = getPublicKey("d:/test/dkey/test.cer");
			for (int i = 0; i < 1; i++) {

				String sign = "emIytHHrOetkc1ORyQVkSiRi+u7QzfiBG+n+FUOeftC2M+Uz8nz1TdFrgIp+40/kNsn3STVBr3GV2KiuPvsV0tHYB2TtBtM+jgClyaDN3e2+rrhMknbjf+OSY9ahiR8nkTJkcoaHbjb6T4w2g7TUesmsazssMOS6a836XVlFyeA=";
				String data = "eyJyZXNwQ29kZSI6IjAwMDAwMDAwMDAiLCJyZXNwTXNnIjoi6LCD55So5oiQ5YqfIiwicmVjb3JkQ291bnQiOm51bGwsInJlcVNlcU5vIjoiT1BFTjE5MDMxNTE4MzY1NDAwMDAiLCJib2R5Ijp7ImZ1bmN0aW9uIjp7InJlc3BvbnNlaGVhZGVyIjp7ImRlYWxjb2RlIjoiVVBCMDA1IiwiZGVhbG1zZyI6IuS4muWKoemHjeWPkSIsImZpbGVjeWNsZWNvdW50IjoiMCIsImN5Y2xlbGV2ZWwxIjpbXSwiY3ljbGVsZXZlbDIiOltdfSwicmVzcG9uc2Vib2R5Ijp7InBsdGZEdCI6IjIwMTkwMzE1IiwiaW5pdEJhbmtJZCI6IjMxNDY1NjAwMDAwOSIsImluaXRCYW5rTm0iOiLmlIDmnp3oirHlhpzmnZHllYbkuJrpk7booYzogqHku73mnInpmZDlhazlj7gifX19LCJoZWFkZXIiOnsic29haGVhZGVyIjp7ImVycmNvZGUiOiJzb2FFcnJfMDAwIiwiZXJybXNnIjoiU1VDRVNTIiwiZXJyZGV0YWlsIjoiU1VDRVNTIiwiZGF0YXNpZ24iOiJiNWEzYzE0YjQ1ZWIzZGFlYWYzNGE3NDc0MTg3MThmMyIsInJlc3RpbWUiOiIyMDE5MDMxNTE4MzY1NyIsImdsZXJyY29kZSI6InNvYUVycl8wMDAiLCJnbGVycm1zZyI6IlNVQ0VTUyIsImJ6dHJhbnNpZCI6IjIwMDAxMTAwMDAwMDIwMTkwMzE1MDAwMDAwMDAwMDA2IiwidHJhbnNpZCI6IjMzNjAwMDAwMDIwMTkwMzE1MDAwMDAwMDAwMDAwMDA2Iiwic3J2c3lzY2QiOiIwNTMiLCJzZXJ2aWNlY29kZSI6InVwYnMuaGFpLjAwMDEuMDEiLCJjaGFubmVsaWQiOiIwMzciLCJyZXNwb25zZXRpbWUiOiIyMDE5LTAzLTE1IDE4OjM2OjU3LjgyOCIsInNydmlwIjoiMTAuMTYuNDQuMTc2In19fQ==";
//				System.out.println(data);
//				String sign = signBase64(DEFAULT_ENCODING, data, privateKey,algorithm);
//				String signUrl = signBase64UrlEncode(DEFAULT_ENCODING, data, privateKey,algorithm);
//
//				System.out.println(sign);
//				System.out.println(signUrl);
				Boolean b = verifyBase64(data, DEFAULT_ENCODING, sign, pubkey,algorithm);

				byte[] orgData = SignUtil.base64Decode(data);

				System.out.println(new String(orgData,DEFAULT_ENCODING));

				System.out.println(b);

				Thread.sleep(1000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 将参数map组装为消息摘要key1=value1&key2=value2&keyn=valuen
	 *
	 * @return 消息摘要
	 */
	public static String signMessageMap(Map<String, List<String>> paramMap) {
		List<String> keys = new ArrayList<String>(paramMap.keySet());
		// 对HashMap的key按照字母顺序排序
		Collections.sort(keys);

		StringBuilder strbd = new StringBuilder();
		for (int keyIndex = 0; keyIndex < keys.size(); keyIndex++) {
			String key = keys.get(keyIndex);
			List<String> valueList = paramMap.get(key);
			Collections.sort(valueList);
			for (int valueIndex = 0; valueIndex < valueList.size(); valueIndex++) {
				strbd.append(key).append("=").append(valueList.get(valueIndex)).append("&");
			}
		}

		int length = strbd.length();
		if (length == 0) {
			length = 1;
		}
		return strbd.substring(0, length - 1);
	}

	/**
	 * 将参数map组装为消息摘要key1=value1&key2=value2&keyn=valuen
	 * 对key进行了排序
	 * @return 消息摘要
	 */
	public static String signMessageMap2(Map<String, String> paramMap) {
		List<String> keys = new ArrayList<String>(paramMap.keySet());
		// 对HashMap的key按照字母顺序排序
		Collections.sort(keys);

		StringBuilder strbd = new StringBuilder();
		for (int keyIndex = 0; keyIndex < keys.size(); keyIndex++) {
			String key = keys.get(keyIndex);
			String value = paramMap.get(key);
			strbd.append(key).append("=").append(value).append("&");
		}

		int length = strbd.length();
		if (length == 0) {
			length = 1;
		}
		return strbd.substring(0, length - 1);
	}

	/**
	 * 将参数map组装为消息摘要key1=value1&key2=value2&keyn=valuen
	 *
	 * 不进行排序
	 *
	 * @return 消息摘要
	 */
	public static String messageMap(Map<String, String> paramMap) {
		StringBuilder strbd = new StringBuilder();
		Iterator<Map.Entry<String, String>> it = paramMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, String> entry = it.next();
			strbd.append(entry.getKey()).append("=").append(entry.getValue()).append("&");

		}
		int length = strbd.length();
		if (length == 0) {
			length = 1;
		}
		return strbd.substring(0, length - 1);
	}

	/**
	 * 使用私钥签名
	 *
	 * @param priKey
	 *            私钥
	 * @param bytes
	 *            需要签名的byte 数组
	 * @return 返回签名后的byte
	 * @throws Exception
	 */
	public static byte[] sign(PrivateKey priKey,
                              byte[] bytes, String algorithm) throws Exception {
		Signature sig = Signature.getInstance(algorithm);
		sig.initSign(priKey);
		sig.update(bytes);
		return sig.sign();
	}

	/**
	 * 签名
	 *
	 * @param encoding
	 *            编码方式
	 * @param data
	 *            参数
	 * @param primaryKeyPath
	 *            私钥路径
	 * @param primaryKeyPwd
	 *            私钥密码
	 * @return 签名数据
	 * @throws Exception
	 */
	public static String signBase64(String encoding,
                                    String data,
                                    String primaryKeyPath,
                                    String primaryKeyPwd,
                                    String algorithm) throws Exception {
		PrivateKey privateKey = getPrivateKey(primaryKeyPath, primaryKeyPwd);
		return signBase64(encoding, data, privateKey,algorithm);
	}

	/**
	 * 签名
	 *
	 * @param encoding
	 *            编码方式
	 * @param data
	 *            参数字符串
	 * @param privateKey
	 *            私钥
	 * @return 签名数据
	 * @throws Exception
	 */
	public static String signBase64(String encoding,
                                    String data,
                                    PrivateKey privateKey,
                                    String algorithm) throws Exception {
		byte[] bytes = data.getBytes(encoding);
		byte[] sigBytes = sign(privateKey, bytes,algorithm);
		String signature = base64Encode(sigBytes);
		return signature;
	}

	/**
	 * 签名
	 *
	 * @param encoding
	 *            编码方式
	 * @param data
	 *            参数MAP
	 * @param primaryKeyPath
	 *            私钥路径
	 * @param primaryKeyPwd
	 *            私钥密码
	 * @return 签名数据url格式
	 * @throws Exception
	 */
	public static String signBase64UrlEncode(String encoding,
                                             String data,
                                             String primaryKeyPath,
                                             String primaryKeyPwd,
                                             String algorithm) throws Exception {
		PrivateKey privateKey = getPrivateKey(primaryKeyPath, primaryKeyPwd);
		return signBase64UrlEncode(encoding, data, privateKey,algorithm);
	}

	/**
	 * 签名
	 *
	 * @param encoding
	 *            编码方式
	 * @param data
	 *            参数字符串
	 * @param privateKey
	 *            私钥
	 * @return 签名数据url格式
	 * @throws Exception
	 */
	public static String signBase64UrlEncode(String encoding,
                                             String data,
                                             PrivateKey privateKey,
                                             String algorithm) throws Exception {
		String sign = signBase64(encoding, data, privateKey,algorithm);
		return URLEncoder.encode(sign, encoding);
	}

	/**
	 * 通过密钥文件或者证书文件获取私钥
	 *
	 * @param keyPath
	 *            密钥文件或者证书的路径
	 * @param passwd
	 *            密钥文件或者证书的密码
	 * @return 返回私钥
	 * @throws Exception
	 */
	public static PrivateKey getPrivateKey(String keyPath,
                                           String passwd) throws Exception {
		String keySuffix = keyPath.substring(keyPath.indexOf(".") + 1);
		String keyType = JKS;

		if (keySuffix == null || keySuffix.trim().equals("")) {
			keyType = JKS;
		} else {
			keySuffix = keySuffix.trim().toUpperCase();
		}

		if (keySuffix.equals(P12)) {
			keyType = PKCS12;
		} else if (keySuffix.equals(PFX)) {
			keyType = PKCS12;
		} else if (keySuffix.equals(JCK)) {
			keyType = JCEKS;
		} else {
			keyType = JKS;
		}
		return getPrivateKey(keyPath, passwd, keyType);
	}

	public static PrivateKey getPrivateKey(String keyPath, InputStream keyStream,
                                           String passwd) throws Exception {
		String keySuffix = keyPath.substring(keyPath.indexOf(".") + 1);
		String keyType = JKS;

		if (keySuffix == null || keySuffix.trim().equals("")) {
			keyType = JKS;
		} else {
			keySuffix = keySuffix.trim().toUpperCase();
		}

		if (keySuffix.equals(P12)) {
			keyType = PKCS12;
		} else if (keySuffix.equals(PFX)) {
			keyType = PKCS12;
		} else if (keySuffix.equals(JCK)) {
			keyType = JCEKS;
		} else {
			keyType = JKS;
		}
		return getPrivateKey(keyPath,keyStream, passwd, keyType);
	}

	@SuppressWarnings({ "rawtypes", "unused" })
	public static PrivateKey getPrivateKey(String keyPath, InputStream keyStream,
                                           String passwd,
                                           String keyType) throws Exception {
		KeyStore ks = KeyStore.getInstance(keyType);
		char[] cPasswd = passwd.toCharArray();
		try {
			ks.load(keyStream, cPasswd);
			keyStream.close();
		} finally {
			if (keyStream != null) {
				keyStream.close();
				keyStream = null;
			}
		}
		Enumeration aliasenum = ks.aliases();
		String keyAlias = null;
		PrivateKey key = null;
		while (aliasenum.hasMoreElements()) {
			keyAlias = (String) aliasenum.nextElement();
			key = (PrivateKey) ks.getKey(keyAlias, cPasswd);
			X509Certificate cert = (X509Certificate) ks.getCertificate(keyAlias);
			if (key != null) {
				break;
			}
		}
		return key;
	}

	/**
	 * 通过证书或者密钥文件获取私钥
	 *
	 * @param keyPath
	 *            证书或者密钥文件
	 * @param passwd
	 *            密钥保存密码
	 * @param keyType
	 *            密钥保存类型
	 * @return 返回私钥
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	public static PrivateKey getPrivateKey(String keyPath,
                                           String passwd,
                                           String keyType) throws Exception {

		KeyStore ks = KeyStore.getInstance(keyType);
		char[] cPasswd = passwd.toCharArray();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(keyPath);
			ks.load(fis, cPasswd);
			fis.close();
		} finally {
			if (fis != null) {
				fis.close();
				fis = null;
			}
		}
		Enumeration aliasenum = ks.aliases();
		String keyAlias = null;
		PrivateKey key = null;
		while (aliasenum.hasMoreElements()) {
			keyAlias = (String) aliasenum.nextElement();
			key = (PrivateKey) ks.getKey(keyAlias, cPasswd);
			X509Certificate cert = (X509Certificate) ks.getCertificate(keyAlias);
			if (key != null) {
				break;
			}
		}
		return key;
	}

	/**
	 * BASE64编码
	 *
	 * @param txt
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static String base64Encode(byte[] txt) {
		return Base64.encodeToString(txt, Base64.DEFAULT);
	}

	/**
	 * BASE64解码
	 *
	 * @param txt
	 * @return
	 */
	@SuppressWarnings("restriction")
	public static byte[] base64Decode(String txt) {
		return Base64.decode(txt, Base64.DEFAULT);
	}

	/**
	 * 通过证书获取公钥
	 *
	 * @param certPath
	 *            证书的路径
	 * @return 返回公钥
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(String certPath) throws Exception {
		InputStream streamCert = new FileInputStream(certPath);
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		Certificate cert = (Certificate) factory.generateCertificate(streamCert);
		return cert.getPublicKey();
	}

	/**
	 * 通过证书获取公钥
	 *
	 * @param streamCert
	 *            证书的路径
	 * @return 返回公钥
	 * @throws Exception
	 */
	public static PublicKey getPublicKey(InputStream streamCert) throws Exception {
		CertificateFactory factory = CertificateFactory.getInstance("X.509");
		Certificate cert = factory.generateCertificate(streamCert);
		streamCert.close();
		return cert.getPublicKey();
	}

	/**
	 * 签名验证
	 *
	 * @param data
	 *            参数MAP
	 * @param encoding
	 *            编码方式
	 * @param sign
	 *            签名数据
	 * @param certPath
	 *            公钥路径
	 * @return 验签是否成功
	 */
	public static Boolean verifyBase64(String data,
                                       String encoding,
                                       String sign,
                                       String certPath,
                                       String algorithm) throws Exception {
		PublicKey pubkey = getPublicKey(certPath);
		return verifyBase64(data, encoding, sign, pubkey,algorithm);
	}

	public static Boolean verifyBase64(String data,
                                       String encoding,
                                       String sign,
                                       PublicKey pubkey,
                                       String algorithm) throws Exception {
		Boolean flag = false;
		byte[] byteSign = base64Decode(sign);
		flag = verify(pubkey, data.getBytes(encoding), byteSign,algorithm);
		return flag;
	}

	/**
	 * 使用公钥验证
	 *
	 * @param pubKey
	 *            公钥
	 * @param orgByte
	 *            原始数据byte 数组
	 * @param signByte
	 *            签名后的数据byte 数组
	 * @return 是否验证结果
	 * @throws Exception
	 */
	public static boolean verify(PublicKey pubKey,
                                 byte[] orgByte,
                                 byte[] signByte,
                                 String algorithm) throws Exception {
		Signature sig = Signature.getInstance(algorithm);
		sig.initVerify(pubKey);
		sig.update(orgByte);
		return sig.verify(signByte);
	}
}
