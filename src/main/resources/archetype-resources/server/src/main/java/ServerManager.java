#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import com.cloud.utils.component.PluggableService;

import org.apache.cloudstack.framework.config.ConfigKey;

public interface ServerManager extends PluggableService {

    static final ConfigKey<String> exampleConfig = new ConfigKey<String>(
        String.class, "${artifactId}.example.plugin.config", "Advanced", "foo", "Example global config provided by plugin", true
    );

    static final ConfigKey<String> exampleClusterConfig = new ConfigKey<String>(
        "Storage", String.class, "${artifactId}.example.plugin.cluster.config", "bar", "Example cluster config provided by plugin", true, ConfigKey.Scope.Cluster
    );

    static final ConfigKey<Boolean> zoneReportEnabled = new ConfigKey<Boolean>(
        Boolean.class, "${artifactId}.example.plugin.zonereport.enable", "Advanced", "false", "Example zone report executor enabled", false
    );

    static final ConfigKey<Long> zoneReportInterval = new ConfigKey<Long>(
        Long.class, "${artifactId}.example.plugin.zonereport.interval", "Advanced", "60", "Example zone report executor interval (seconds)", false
    );
}
