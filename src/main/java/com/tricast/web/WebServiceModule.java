package com.tricast.web;

import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import com.tricast.database.PostGreSQLDataSourceProvider;
import com.tricast.database.WorkspaceImpl;
import com.tricast.guice.JacksonJsonProviderProvider;
import com.tricast.guice.JdbcTransactionInterceptor;
import com.tricast.guice.ObjectMapperProvider;
import com.tricast.guice.WorkspaceProvider;
import com.tricast.web.annotations.JdbcTransaction;
import com.tricast.web.dao.AccountDao;
import com.tricast.web.dao.AccountDaoImpl;
import com.tricast.web.manager.AccountManager;
import com.tricast.web.manager.AccountManagerImpl;
import com.tricast.web.server.AccountService;

public class WebServiceModule extends JerseyServletModule {

    @Override
    protected void configureServlets() {
        final ResourceConfig rc = new PackagesResourceConfig("com.tricast.web.server");

        Names.bindProperties(binder(), loadProperties());

        // Force the Injector to bind a Provider that provides DataSources
        requireBinding(DataSource.class);

        // Bind the DataSource class to be provided by OracleDataSourceProvider
        // in Singleton
        bind(DataSource.class).toProvider(PostGreSQLDataSourceProvider.class).in(Singleton.class);
        bind(WorkspaceImpl.class).toProvider(WorkspaceProvider.class);

        // Binding certain Interfaces to their Implementations. This can be
        // easily modified for testing, to use Test Implementations
        bind(AccountDao.class).to(AccountDaoImpl.class);

        bind(AccountManager.class).to(AccountManagerImpl.class);

        // Binding the REST endpoints
        bind(AccountService.class);

        // Injections to use our own ObjectMappers. Only needed to pretty print
        // the JSON output, not necessary at all.
        bind(ObjectMapper.class).toProvider(ObjectMapperProvider.class).in(Singleton.class);
        bind(JacksonJsonProvider.class).toProvider(JacksonJsonProviderProvider.class).in(Singleton.class);

        // Creating the JdbcUnitOfWorkInterceptor and also stating that it is
        // required to bind this class.
        JdbcTransactionInterceptor jdbcTransactionInterceptor = new JdbcTransactionInterceptor();
        requestInjection(jdbcTransactionInterceptor);

        // Bind to intercept any calls annotated with JdbcUnitOfWork by
        bindInterceptor(any(), annotatedWith(JdbcTransaction.class), jdbcTransactionInterceptor);

        for (Class<?> resource : rc.getClasses()) {
            System.out.println("Binding resource: " + resource.getName());
            bind(resource);
        }

        // Bind to serve all /services calls by this Servlet
        serve("/services/*").with(GuiceContainer.class);
    }

    private Properties loadProperties() {
        InputStream is = null;
        Properties props = new Properties();
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            is = classLoader.getResourceAsStream("setup.xml");
            // Try loading properties from the file (if found)
            props.loadFromXML(is);
        } catch (Exception e) {
            // Skip load use default
        }
        if (is == null) {
            props.put("datasourceName", "PostGreSQL Portable DB");
            props.put("serverName", "localhost");
            props.put("portNumber", "5432");
            props.put("databaseName", "postgres");
            props.put("user", "postgres");
            props.put("password", "password");
        }
        String dbUrl = System.getProperty("db.url");
        if (dbUrl != null) {
            props.put("serverName", dbUrl);
        }
        String dbPassword = System.getProperty("db.password");
        if (dbPassword != null) {
            props.put("password", dbPassword);
        }

        return props;
    }

}
