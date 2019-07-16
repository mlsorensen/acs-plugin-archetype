#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import com.cloud.utils.component.PluggableService;

import org.apache.cloudstack.framework.config.ConfigKey;

public interface ServerManager extends PluggableService {

    static final ConfigKey<String> exampleConfig = new ConfigKey<String>(
        String.class, "example.plugin.config", "Advanced", "foo", "Example global config provided by plugin", true
    );

    static final ConfigKey<String> exampleClusterConfig = new ConfigKey<String>(
        "Storage", String.class, "example.plugin.cluster.config", "bar", "Example cluster config provided by plugin", true, ConfigKey.Scope.Cluster
    );
}
