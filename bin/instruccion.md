## SERVER

#se genera el keystore del servidor

keytool -genkeypair -alias server-keypair -keyalg RSA -keysize 2048 -validity 3650 -dname "CN=server,O=codenotfound.com" -keypass server-key-p455w0rd -keystore server-keystore.jks -storepass server-keystore-p455w0rd -ext san=dns:localhost

#se exporta el certificado publico del servidor y se comparte con el cliente

keytool -exportcert -alias server-keypair -file server-public-key.cer -keystore server-keystore.jks -storepass server-keystore-p455w0rd






#se importa el certificado del cliente en el servidor (Teniendo el certificado publico del cliente)
keytool -importcert -keystore server-truststore.jks -alias client-public-key -file client-public-key.cer -storepass server-truststore-p455w0rd -noprompt


#Properties server
'''
    debug=true

    spring.application.name=server
    spring.profiles.active=default

    server.ssl.key-store=classpath:server-keystore.jks
    server.ssl.key-store-password=server-keystore-p455w0rd
    server.ssl.key-password=server-key-p455w0rd
    server.ssl.key-alias=server-keypair
    server.ssl.trust-store=classpath:server-truststore.jks
    server.ssl.trust-store-password=server-truststore-p455w0rd
    # Mutual TLS/SSL
    server.ssl.client-auth=need
    server.port=8111
'''


##CLIENTE



#se genera el keystore del cliente
keytool -genkeypair -alias client-keypair -keyalg RSA -keysize 2048 -validity 3650 -dname "CN=client,O=codenotfound.com" -keypass client-key-p455w0rd -keystore client-keystore.jks -storepass client-keystore-p455w0rd


#se exporta el certificado publico del cliente y se comparte con el servidor
keytool -exportcert -alias client-keypair -file client-public-key.cer -keystore client-keystore.jks -storepass client-keystore-p455w0rd


#Se agrega el certificado publico del servidor en el trustore del cliente (Teniendo el certificado publico del servidor)
keytool -importcert -keystore client-truststore.jks -alias server-public-key -file server-public-key.cer -storepass client-truststore-p455w0rd -noprompt



#Properties Cliente

'''
    #debug=true

    spring.application.name=client
    spring.profiles.active=default

    server.ssl.key-store=classpath:client-keystore.jks
    server.ssl.key-store-password=client-keystore-p455w0rd
    server.ssl.key-password=client-key-p455w0rd
    server.ssl.trust-store=classpath:client-truststore.jks
    server.ssl.trust-store-password=client-truststore-p455w0rd
    # Mutual TLS/SSL
    server.ssl.client-auth=need
    server.port=8222

'''
