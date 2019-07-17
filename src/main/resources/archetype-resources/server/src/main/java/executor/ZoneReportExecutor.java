#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.executor;

import com.cloud.dc.dao.ClusterDao;
import com.cloud.dc.dao.DataCenterDao;
import com.cloud.dc.DataCenterVO;
import com.cloud.utils.component.ComponentMethodInterceptable;
import com.cloud.utils.component.ManagerBase;
import com.cloud.utils.concurrency.NamedThreadFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.cloudstack.framework.config.ConfigDepot;
import org.apache.cloudstack.framework.config.ConfigKey;
import org.apache.cloudstack.managed.context.ManagedContextRunnable;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import ${package}.ServerManager;

@Component
public class ZoneReportExecutor extends ManagerBase implements ComponentMethodInterceptable {
    public static final Logger s_logger = Logger.getLogger(ZoneReportExecutor.class.getName());

    private ScheduledExecutorService _executor;

    @Inject private ConfigDepot _configDepot;
    @Inject private ClusterDao _clusterDao;
    @Inject private DataCenterDao _dcDao;

    @Override
    public boolean start() {
        if (isEnabled()) {
            s_logger.info("Zone report executor is enabled");
            _executor = Executors.newScheduledThreadPool(2, new NamedThreadFactory(ZoneReportExecutor.class.getName()));
            _executor.scheduleAtFixedRate(new ZoneReportRunner(), 10L, reportIntervalSeconds(), TimeUnit.SECONDS);
        } else {
            s_logger.info("Zone report executor is disabled");
        }
        return true;
    }

    private boolean isEnabled() {
        ConfigKey<Boolean> enableZoneReport = (ConfigKey<Boolean>)_configDepot.get(ServerManager.zoneReportEnabled.key());
        return enableZoneReport != null ? Boolean.valueOf(enableZoneReport.value()) : false;
    }

    private long reportIntervalSeconds() {
        ConfigKey<Long> zoneReportInterval = (ConfigKey<Long>)_configDepot.get(ServerManager.zoneReportInterval.key());
        return zoneReportInterval != null ? Long.valueOf(zoneReportInterval.value()) : 60L;
    }

    class ZoneReportRunner extends ManagedContextRunnable {
        @Override
        protected void runInContext() {
            List<DataCenterVO> zones = _dcDao.listAllZones();
            String zoneNames = zones.stream().map(z -> z.getName() + "-" + z.getAllocationState()).collect(Collectors.joining(", ", "[" , "]"));
            s_logger.info("Zone report: zone count - " + zones.size() + ", zone detail - " + zoneNames);
        }
    }

}
