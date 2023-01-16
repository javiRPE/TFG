package com.jetty.tfg;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.HttpConfiguration;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.SecureRequestCustomizer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.SslConnectionFactory;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.eclipse.jetty.webapp.WebAppContext;

public class MainEmbeddedJetty {

	public static void main(String[] args) throws Exception {

		Server server = new Server();
		
		//Ruta donde está localizado el certificado SSL
		Path keystorePath = Paths.get("/Users/Javier/eclipse-workspace/jetty-embedded-ssl/src/main/resources/keystore").toAbsolutePath();
		
		//Desarrollo de la aplicación web o handler
		WebAppContext webAppContext = new WebAppContext();
		webAppContext.setContextPath("/WebAppJetty");
		webAppContext.setResourceBase("src/main/webapp");
		webAppContext.setDescriptor("src/main/webapp/WEB-INF/web.xml");
		webAppContext.setParentLoaderPriority(true);
		server.setHandler(webAppContext);

		// Configuración HTTP
		HttpConfiguration http = new HttpConfiguration();
		http.addCustomizer(new SecureRequestCustomizer());

		// Configuración HTTPS
		http.setSecurePort(8443);
		http.setSecureScheme("https");
		ServerConnector connector = new ServerConnector(server);
		connector.addConnectionFactory(new HttpConnectionFactory(http));
		//Añadir puerto de HTTP
		connector.setPort(8080);

		//Configuración HTTPS
		HttpConfiguration https = new HttpConfiguration();
		https.addCustomizer(new SecureRequestCustomizer());
	
		//Señalar la ruta del certificado así como sus claves
		SslContextFactory.Server sslContextFactory = new SslContextFactory.Server();
        sslContextFactory.setKeyStorePath(keystorePath.toString());
        sslContextFactory.setKeyStorePassword("jetty16");
        sslContextFactory.setKeyManagerPassword("jetty16");

        //Crear el conector SSL y el puerto para HTTPS
		ServerConnector sslConnector = new ServerConnector(server, new SslConnectionFactory(sslContextFactory, "http/1.1"), new HttpConnectionFactory(https));
		sslConnector.setPort(8443);

		//Añadir conector HTTP y HTTPS y ejecutar el servidor
		server.setConnectors(new Connector[]{connector, sslConnector});
		
		server.start();

		server.join();
	}
}
