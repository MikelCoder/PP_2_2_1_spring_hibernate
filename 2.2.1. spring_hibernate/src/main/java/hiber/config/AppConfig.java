package hiber.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("hiber")
@EnableTransactionManagement
public class AppConfig {

   @Bean
   public DataSource dataSource() {
      DriverManagerDataSource dataSource = new DriverManagerDataSource();
      dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
      dataSource.setUrl("jdbc:mysql://localhost:3306/your_database");
      dataSource.setUsername("your_username");
      dataSource.setPassword("your_password");
      return dataSource;
   }

   @Bean
   public LocalSessionFactoryBean sessionFactory() {
      LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
      sessionFactory.setDataSource(dataSource());
      sessionFactory.setPackagesToScan("hiber.model");
      sessionFactory.setHibernateProperties(hibernateProperties());
      return sessionFactory;
   }

   private Properties hibernateProperties() {
      Properties properties = new Properties();
      properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
      properties.setProperty("hibernate.show_sql", "true");
      properties.setProperty("hibernate.hbm2ddl.auto", "update");
      properties.setProperty("hibernate.format_sql", "true");
      return properties;
   }

   @Bean
   public HibernateTransactionManager transactionManager() {
      HibernateTransactionManager transactionManager = new HibernateTransactionManager();
      transactionManager.setSessionFactory(sessionFactory().getObject());
      return transactionManager;
   }
}
