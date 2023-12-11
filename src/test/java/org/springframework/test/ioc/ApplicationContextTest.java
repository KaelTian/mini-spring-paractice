package org.springframework.test.ioc;

import cn.hutool.crypto.asymmetric.RSA;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.ioc.bean.Car;
import org.springframework.test.ioc.bean.Person;
import org.springframework.test.ioc.common.RSA_Util;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationContextTest {

    @Test
    public void testApplicationContext() throws Exception {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");

        Person person = applicationContext.getBean("person", Person.class);
        System.out.println(person);
        //name属性在CustomBeanFactoryPostProcessor中被修改为ivy
        assertThat(person.getName()).isEqualTo("kael");

        Car car = applicationContext.getBean("car", Car.class);
        System.out.println(car);
        //brand属性在CustomerBeanPostProcessor中被修改为lamborghini
        assertThat(car.getBrand()).isEqualTo("lamborghini");
    }

    @Test
    public void testRSA() throws Exception {
        try {
            String publicKey = "<RSAKeyValue><Modulus>nrsp7k+qQVm7zBuexOJlBu7aS32P2n8mJ0+11A3bhMJX7qgNUWFMIYGnSBLh//6k/8tB8In9u7BLLQz+qpAKvx+l4vXEz6e2XpwnAySh9Rk2GQNzVYFNiAc+4Cg7qtKrmEKqSuBKIFVJLsS9Eyzl2DO8skxgfIbPprHfBQxgSD2JRv+WsH5A15j27qA6Czy+xrQt5BQSTm4mK1k7ApbUDooUdvK1NINLjv3GIIB4p7X6dMQtWxPzTR76bncCHu7WArKBscTyRZyo2RjgkfGCKYtd4Nf/VQna92+clb+QqwuaMaBMGTKk9EY/sswVJckncCgPAUfV8RLiJAQ6ZH6K8Q==</Modulus><Exponent>AQAB</Exponent></RSAKeyValue>";
            RSA_Util rsa = new RSA_Util(publicKey);
            String testStr = "加密测试字符串";
            String en = rsa.Encrypt("OAEP+SHA256", testStr);
            String algorithm=rsa.RSAPadding_Enc("OAEP+SHA256");
            S(algorithm);
            S(en);
//            String privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCZk7F9vU0lnOPim5fbqLGHwY2b9J97UTDNTrzvwyxYGr9K0+X6o3DRZqBR6y1lQQ0w5qgn2LH5rHgAubtL/EzFgWI3bTMLCw03xAxYlZ5oBdtgsrMGt3WVOpOMoLF2vrwS9xW3YWrwyFKhZeWLZK3JdqYkdP4cMabxePkAP68qnbBD5ouVln3/DnE1BXQAyybIzNikAW/6u8jvXzZU5B40kI7aunPNXqnFM9awE+uxAkpZ1CDqrJ11T1kjhoWJcAsyaMKMK2s6xjsbCSJ7+xO9Y7zVfScViArHXg6RmVn6nk4rGcxGcBHoGrLbv5RAEf/fW8VoFS2S2rXQYEC4RvDhAgMBAAECggEBAI3pYzasiTA7dHy+iCnLDRlEKmW1OvWJxc3Bd2cac8yKEr/DZJ0hECv/aB+qcI1C+jx4t2PyoirzSwRonAEQB6Tt9OMAvgzzTmh8eWgWAYVf/CKzniiTuEyIDzYAe1wdzc68kU2d8GLEyU4oyV7wToJ+Z/ICRZmp6/jwby7iqit2klggWkuvLyMonmVfWXaQdYWqHXM+pCFPbW3FwEQ9n7Zgy6tCptuyS16ejCqUDpFrILCIMGyiMv+szvADJJcamdqfFFb3IhHIl6nxO0SeaXOt8Nl3yq0gXnWbxQtFIuZ9DoQn6GfmwCIoFOYUxwuZBb0olLDKfWr3B9oarweezwECgYEAzsoxH3OytB8SV2UH67bTvIq3mNZdRaxNw2d1xGQqzrHof6kT7aPr/ipB2WBC9RL/+xopiWKKHOR7AaEfXmu2vQouIs2V2WN4SRoPw6WfvBvLb3UuhRU8gRWWAFumIfvICXMtIm1OOm/bxkhCJdEK29Umk/fV5JT7LZpjH48mg3ECgYEAvh+6X/7mB6Api8abW9F7FPGiVqm0auZCO3dJBQDXzEY+A3ecU84Xa4DLr98lQ2DhK+OQF12Uj2DwfAeWXWF08AknjyKiMkmzTQ2QOoV5LIeLdSC54waK9RJeoxMHGVLp4PdkHq/Uu07VnSP9AYmNxinx4xAlyzG9csHB5F3nrHECgYEArsAxaQnmD89eOZq4adrz7hYv9ynYYqOsh6Ar5o5bmpbACvoATEfFi+F8Ck4N53tp9XJzfZVXb/kpdgQKNUnDgNdUi7a+lpo3sVcj0UtqxJWcgpE9gdujaNasD5tjtr5Gmq3Ji5lhjYKv8E1qEPkqOwJS+qJrECNSbIRWlfnsKxECgYEAnzC9wwcQ4C2w28JN7xF1d1HjVaD+2cs2hcgZMcFE71uZLRoHoPwdC/xpkDbNYp9Jqo/95DKM44iNk6xesI2bdiiUIPskx+qB4PCMZRPBJBHSKR5fxsGBtDXPW8fg18le3ukFwdQD0Vnu7oZln9XnCZexWyf4HSzmraGikW43P2ECgYBQt8pijKU5/S/HOz0yx3nRPa45rUvK6H50phgmitamcGTwGYCtqL8bepygOdb0XkKZWijfCjBihGW5e7Z4t2uy6y4e2wo0xy3atowTLdUfsHzFVl9HNCDxtVgYT70eyszB+Ub04wiyvUuwT75ME6YODOIXDEpgywmekp209uMLDg==";
//            String xmlPrivateKey=getXmlPrivateKey(privateKey);
//            RSA_Util deRsa = new RSA_Util(xmlPrivateKey);
//            String value = rsa.Decrypt("OAEP+SHA256", en);
//            S(value);
//            String deStr = rsa.Decrypt("OAEP+SHA256", en);
//            S(deStr);
        } catch (Exception ex) {
            S(ex.toString());
        }
    }

    private String getXmlPrivateKey(String privateKeyStr) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // 替换这里的 privateKeyBytes 为你的 RSA 私钥字节数组
        byte[] privateKeyBytes = privateKeyStr.getBytes()/* 你的 RSA 私钥字节数组 */;

        // 创建 PKCS8EncodedKeySpec 对象
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");

        // 从 PKCS#8 格式的密钥规范中生成私钥
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        // 获取私钥的组成部分
        RSAPrivateCrtKey rsaPrivateKey = (RSAPrivateCrtKey) privateKey;
        String modulus = Base64.getEncoder().encodeToString(rsaPrivateKey.getModulus().toByteArray());
        String exponent = Base64.getEncoder().encodeToString(rsaPrivateKey.getPrivateExponent().toByteArray());

        // 将组成部分转换为 XML 格式
        String xmlPrivateKey = "<RSAKeyValue>"
                + "<Modulus>" + modulus + "</Modulus>"
                + "<Exponent>" + exponent + "</Exponent>"
                + "</RSAKeyValue>";

        System.out.println(xmlPrivateKey);
        return xmlPrivateKey;
    }

    static private void S(String s) {
        System.out.println(s);
    }
}
