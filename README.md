

## **Pre requisitos**

 - HttpClient 4.5.8   
 - Spring Boot 2.1.6
## Crear keystore y trustore

En la carpeta /bin ejecutar el script gen-non-prod-key.sh

    user/taller/bin $ sh gen-non-prod-key.sh

## Servidor

Modificar el properties

    #src/main/resources/application.properties
    debug=true

	spring.application.name=server
	spring.profiles.active=default

	server.ssl.key-store=classpath:server-nonprod.jks
	server.ssl.key-store-password=changeme
	server.ssl.key-password=changeme
	server.ssl.trust-store=classpath:server-nonprod.jks
	server.ssl.trust-store-password=changeme
	# Mutual TLS/SSL
	server.ssl.client-auth=need
	server.port=8111

## Cliente

Configuracion del cliente para el consumo de un servicio
    
	com.cdc.pdmx.Application.java
	...
		@Value("${server.ssl.trust-store-password}")
	    private String trustStorePassword;
	    @Value("${server.ssl.trust-store}")
	    private Resource trustStore;
	    @Value("${server.ssl.key-store-password}")
	    private String keyStorePassword;
	    @Value("${server.ssl.key-password}")
	    private String keyPassword;
	    @Value("${server.ssl.key-store}")
	    private Resource keyStore;
	...
	...
    @Bean
    public RestTemplate restTemplate() throws Exception {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        restTemplate.setErrorHandler(
                new DefaultResponseErrorHandler() {
                    @Override
                    protected boolean hasError(HttpStatus statusCode) {
                        return false;
                    }
                });

        return restTemplate;
    }

    private ClientHttpRequestFactory clientHttpRequestFactory() throws Exception {
        return new HttpComponentsClientHttpRequestFactory(httpClient());
    }

    private HttpClient httpClient() throws Exception {
        // Load our keystore and truststore containing certificates that we trust.
        SSLContext sslcontext =
                SSLContexts.custom().loadTrustMaterial(trustStore.getFile(), trustStorePassword.toCharArray())
                        .loadKeyMaterial(keyStore.getFile(), keyStorePassword.toCharArray(),
                                keyPassword.toCharArray()).build();
        SSLConnectionSocketFactory sslConnectionSocketFactory =
                new SSLConnectionSocketFactory(sslcontext, new NoopHostnameVerifier());
        return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
    }
	...
	
Modificacion  del properties

	#src/main/resources/application.properties
	
	#debug=true

	spring.application.name=client
	spring.profiles.active=default

	server.ssl.key-store=classpath:client-nonprod.jks
	server.ssl.key-store-password=changeme
	server.ssl.key-password=changeme
	server.ssl.trust-store=classpath:client-nonprod.jks
	server.ssl.trust-store-password=changeme
	# Mutual TLS/SSL
	server.ssl.client-auth=need
	server.port=8222
	
	# Mutual TLS/SSL
	server.ssl.client-auth=need
	server.port=8222

Ejecutar el test ubicado en `src/test/java/com/cdc/pdmx/ApplicationTests.java`






### **Fuentes**

 - https://codenotfound.com/spring-ws-mutual-authentication-example.html
 - https://github.com/joutwate/mtls-springboot
