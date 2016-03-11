# BootSecurity
Spring Boot Web with Spring Security Using Login Form and Mysql 

# Environment
Spring Boot 1.4.0 , Spring Security 4.0.3<br>
Maven<br>
mysql , mybatis 3.3.1 , mybatis-spring 1.2.4<br>
JSTL<br>
Logback<br>
External Tomcat 8<br>
Test URL : http://localhost:8080/BootSecurity/login.do

# How to add support for extra login fields in Spring Security Login Form

1. UsernamePasswordAuthenticationFilter + HTTP Session<br>
WebSecurityConfigAnotherParam , AuthenticationFilterAnotherParam

2. AuthenticationDetailsSource + SavedRequestAwareAuthenticationSuccessHandler<br>
WebSecurityConfigDetailsSource , CustomWebAuthenticationDetailsSource , CustomWebAuthenticationDetails

3. UsernamePasswordAuthenticationFilter + UserDetailsService().loadUserByUsername<br>
WebSecurityConfigObtainUsername , AuthenticationFilterObtainUsername , LoginService

# enabled  HTTPS for my login page, switching back to HTTP afterwards.Lost Security Session

WebSecurityConfigSwicthHttps , HttpsLoginSuccessHandler<br>
1. Disable Session Fixation Attack Protection<br>
http.sessionManagement().sessionFixation().none()<br><br>
2. Re-Create Session Cookie

# Tomcat Datasource JNDI
```
<GlobalNamingResources>
<Resource auth="Container" driverClassName="com.mysql.jdbc.Driver" 
loginTimeout="10" maxActive="200" maxIdle="8" maxWait="5000" 
name="jdbc/sim" username="dbuser" password="1234" 
type="javax.sql.DataSource"
url="jdbc:mysql://db.example.com:3306/exampledb?zeroDateTimeBehavior=convertToNull"/>      
</GlobalNamingResources>

<Context docBase="BootSecurity" path="/BootSecurity" reloadable="true">
<ResourceLink global="jdbc/sim" name="jdbc/sim" type="javax.sql.DataSource"/>
</Context>
```
