#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package};

import com.cloud.utils.ReflectUtil;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;

import java.lang.reflect.Field;

import org.apache.cloudstack.api.APICommand;
import org.apache.cloudstack.framework.config.ConfigDepot;
import org.apache.cloudstack.framework.config.ConfigKey;
import org.apache.cloudstack.framework.config.Configurable;
import org.apache.log4j.Logger;

import org.springframework.stereotype.Component;

import ${package}.api.commands.*;
import ${package}.api.responses.*;

@Component
public class ServerManagerImpl implements ServerManager, Configurable {
    private static final Logger s_logger = Logger.getLogger(ServerManagerImpl.class);

    @Inject private ConfigDepot _configDepot;

    public ServerManagerImpl() {
        super();
    }

    @Override
    public List<Class<?>> getCommands() {
        Set<Class<?>> cmdClasses = ReflectUtil.getClassesWithAnnotation(APICommand.class, new String[] {"${package}.api"});
        List<Class<?>> cmdList = new ArrayList<Class<?>>(cmdClasses);
        return cmdList;
    }

    @Override
    public String getConfigComponentName() {
        return ServerManager.class.getSimpleName();
    }

    @Override
    public ConfigKey<?>[] getConfigKeys() {
        List<ConfigKey<?>> configList = new ArrayList<ConfigKey<?>>();
        List<Field> fields = new ArrayList<Field>();
        Collections.addAll(fields, ServerManager.class.getDeclaredFields());
        for(Field field : fields) {
            if (field.getType().equals(ConfigKey.class)) {
                try {
                    ConfigKey<?> key = (ConfigKey<?>)field.get(ServerManager.class);
                    s_logger.debug("Found configuration key:" + key);
                    _configDepot.createOrUpdateConfigObject(getConfigComponentName(), key, null);
                    configList.add(key);
                } catch (IllegalAccessException ex) {
                    s_logger.info("Got illegal access while looking for config keys", ex);
                }
            }
        }
        ConfigKey<?>[] configKeys = new ConfigKey<?>[configList.size()];
        configKeys = configList.toArray(configKeys);
        return configKeys;
    }

}
