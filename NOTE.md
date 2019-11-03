# SpringBoot 配置支持 http 与 https

## JDK https 证书生成

```java
keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize 4096 
-keystore /home/james/Data/ssl/keystore.p12 -validity 365
```

```bash
root@james-cd:/home/james/Data/ssl# keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize 4096 
-keystore /home/james/Data/ssl/keystore.p12 -validity 365
Enter keystore password:  
Re-enter new password: 
What is your first and last name?
  [Unknown]:  chengdu sun
What is the name of your organizational unit?
  [Unknown]:  cn
What is the name of your organization?
  [Unknown]:  cn
What is the name of your City or Locality?
  [Unknown]:  cn
What is the name of your State or Province?
  [Unknown]:  cn
What is the two-letter country code for this unit?
  [Unknown]:  cn
Is CN=chengdu sun, OU=cn, O=cn, L=cn, ST=cn, C=cn correct?
  [no]:  y 

root@james-cd:/home/james/Data/ssl# ls -lt
total 16
-rw-r--r-- 1 root root 4207 Nov  3 11:32 keystore.p12
```

## 项目配置 SSL

#https端口号.
server.port=443
#证书的路径.
server.ssl.key-store=classpath:keystore.p12
#证书密码，请修改为您自己证书的密码.
server.ssl.key-store-password=123456（改为之前设置的密码）
#秘钥库类型
server.ssl.keyStoreType=PKCS12
#证书别名
server.ssl.keyAlias=tomcat

## 增加 http 可访问配置

```java
@Configuration
public class TomcatConfig {

@Value("${server.http.port}")
private int httpPort;

@Bean
public EmbeddedServletContainerCustomizer containerCustomizer() {
    return new EmbeddedServletContainerCustomizer() {
        @Override
        public void customize(ConfigurableEmbeddedServletContainer container) {
            if (container instanceof TomcatEmbeddedServletContainerFactory) {
                TomcatEmbeddedServletContainerFactory containerFactory =
                        (TomcatEmbeddedServletContainerFactory) container;

                Connector connector = new Connector(TomcatEmbeddedServletContainerFactory.DEFAULT_PROTOCOL);
                connector.setPort(httpPort);
                containerFactory.addAdditionalTomcatConnectors(connector);
            }
        }
    };
}
}
```