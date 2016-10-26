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
import com.tricast.web.dao.BetDao;
import com.tricast.web.dao.BetDaoImpl;
import com.tricast.web.dao.BetDataDao;
import com.tricast.web.dao.BetDataDaoImpl;
import com.tricast.web.dao.CountryDao;
import com.tricast.web.dao.CountryDaoImpl;
import com.tricast.web.dao.EventDao;
import com.tricast.web.dao.EventDaoImpl;
import com.tricast.web.dao.LeagueDao;
import com.tricast.web.dao.LeagueDaoImpl;
import com.tricast.web.dao.MarketDao;
import com.tricast.web.dao.MarketDaoImpl;
import com.tricast.web.dao.OutcomeDao;
import com.tricast.web.dao.OutcomeDaoImpl;
import com.tricast.web.dao.PeriodDao;
import com.tricast.web.dao.PeriodDaoIpml;
import com.tricast.web.dao.TeamDao;
import com.tricast.web.dao.TeamDaoImpl;
import com.tricast.web.dao.TransactionDao;
import com.tricast.web.dao.TransactionDaoImpl;
import com.tricast.web.manager.AccountManager;
import com.tricast.web.manager.AccountManagerImpl;
import com.tricast.web.manager.BetDataManager;
import com.tricast.web.manager.BetDataManagerImpl;
import com.tricast.web.manager.BetManager;
import com.tricast.web.manager.BetManagerImpl;
import com.tricast.web.manager.CountryManager;
import com.tricast.web.manager.CountryManagerImpl;
import com.tricast.web.manager.EventManager;
import com.tricast.web.manager.EventManagerImpl;
import com.tricast.web.manager.LeagueManager;
import com.tricast.web.manager.LeagueManagerImpl;
import com.tricast.web.manager.MarketManager;
import com.tricast.web.manager.MarketManagerImpl;
import com.tricast.web.manager.OutcomeManager;
import com.tricast.web.manager.OutcomeManagerImpl;
import com.tricast.web.manager.PeriodManager;
import com.tricast.web.manager.PeriodManagerImpl;
import com.tricast.web.manager.TeamManager;
import com.tricast.web.manager.TeamManagerImpl;
import com.tricast.web.manager.TransactionManager;
import com.tricast.web.manager.TransactionManagerImpl;
import com.tricast.web.server.AccountService;
import com.tricast.web.server.BetDataService;
import com.tricast.web.server.BetService;
import com.tricast.web.server.CountryService;
import com.tricast.web.server.EventService;
import com.tricast.web.server.LeagueService;
import com.tricast.web.server.MarketService;
import com.tricast.web.server.OutcomeService;
import com.tricast.web.server.PeriodService;
import com.tricast.web.server.TeamService;
import com.tricast.web.server.TransactionService;

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
        bind(OutcomeDao.class).to(OutcomeDaoImpl.class);
        bind(BetDao.class).to(BetDaoImpl.class);
        bind(BetDataDao.class).to(BetDataDaoImpl.class);
        bind(TransactionDao.class).to(TransactionDaoImpl.class);
        bind(MarketDao.class).to(MarketDaoImpl.class);
        bind(CountryDao.class).to(CountryDaoImpl.class);
        bind(EventDao.class).to(EventDaoImpl.class);
        bind(LeagueDao.class).to(LeagueDaoImpl.class);
        bind(PeriodDao.class).to(PeriodDaoIpml.class);
        bind(TeamDao.class).to(TeamDaoImpl.class);
        
        bind(AccountManager.class).to(AccountManagerImpl.class);
        bind(BetManager.class).to(BetManagerImpl.class);
        bind(BetDataManager.class).to(BetDataManagerImpl.class);
        bind(OutcomeManager.class).to(OutcomeManagerImpl.class);
        bind(TransactionManager.class).to(TransactionManagerImpl.class);
        bind(MarketManager.class).to(MarketManagerImpl.class);
        bind(CountryManager.class).to(CountryManagerImpl.class);
        bind(EventManager.class).to(EventManagerImpl.class);
        bind(LeagueManager.class).to(LeagueManagerImpl.class);
        bind(PeriodManager.class).to(PeriodManagerImpl.class);
        bind(TeamManager.class).to(TeamManagerImpl.class);
        
        // Binding the REST endpoints
        bind(AccountService.class);
        bind(BetService.class);
        bind(BetDataService.class);
        bind(TransactionService.class);
        bind(MarketService.class);
        bind(OutcomeService.class);
        bind(PeriodService.class);
        bind(CountryService.class);
        bind(EventService.class);
        bind(LeagueService.class);
        bind(PeriodService.class);
        bind(TeamService.class);

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
