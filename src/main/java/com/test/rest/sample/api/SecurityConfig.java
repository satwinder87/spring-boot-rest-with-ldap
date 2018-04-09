package com.test.rest.sample.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${api.login.password}")
    private String password;

    @Value("${ldap.url}")
    private String ldap_url;

    @Value("${ldap.password}")
    private String ldap_password;


//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/health", "/info").permitAll()
//                .anyRequest().authenticated()
//                .and().csrf().disable()
//                .httpBasic();
//    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin").password(password).roles("USER");
//    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/health", "/info").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin();
    }

    @Bean
    public LdapContextSource contextSource() {
        LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrl(ldap_url);
        ldapContextSource.setUserDn("CN=comhem_se-LDAPuser,OU=Service Accounts,OU=Special Accounts,OU=Com Hem AB,DC=ad,DC=comhem,DC=com");
        ldapContextSource.setPassword(ldap_password);
        return ldapContextSource;
    }


    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.ldapAuthentication()
                .userSearchFilter("(sAMAccountName={0})")
                .userSearchBase("DC=ad,DC=comhem,DC=com")
                .groupSearchFilter("member={0}")
                .groupSearchBase("OU=Com Hem AB,DC=ad,DC=comhem,DC=com")
                .groupRoleAttribute("cn")
                .contextSource(contextSource());
    }
}
