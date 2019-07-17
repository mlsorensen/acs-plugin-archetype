##### CloudStack configuration XML data

XML configuration files can be placed in this directory.
For example, if your plugin requires the CloudStack in-memory event bus, creating an xml with the following contents would turn on the memory event bus within cloudstack:

spring-event-bus-context.xml

```
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
                      http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
                      http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
                      http://www.springframework.org/schema/context
                      http://www.springframework.org/schema/context/spring-context-3.0.xsd"
                      >
    <bean id="eventNotificationBus" class="org.apache.cloudstack.mom.inmemory.InMemoryEventBus">
        <property name="name" value="eventNotificationBus"/>
    </bean>

</beans>
``` 

The packaging system would need to be modified to include this new file for installation into cloudstack's META-INF location.
For example, with RPM a line would need to be added to the %install section like so:

```
cp -f server/config/spring-event-bus-context.xml %{buildroot}%{_sysconfdir}/cloudstack/management/META-INF/cloudstack/core/spring-event-bus-context.xml
```

And also in "%files server" section we need to define the packaging environment's META-INF for inclusion in the RPM:

```
%config(noreplace) %{_sysconfdir}/cloudstack/management/META-INF
```
