#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.commands;

import com.cloud.agent.AgentManager;
import com.cloud.agent.api.Answer;
import com.cloud.host.HostVO;
import com.cloud.host.Status;
import com.cloud.hypervisor.Hypervisor.HypervisorType;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import org.apache.cloudstack.acl.RoleType;
import org.apache.cloudstack.api.BaseCmd;
import org.apache.cloudstack.api.APICommand;
import org.apache.cloudstack.api.ApiConstants;
import org.apache.cloudstack.api.ApiErrorCode;
import org.apache.cloudstack.api.Parameter;
import org.apache.cloudstack.api.ServerApiException;
import org.apache.cloudstack.api.response.HostResponse;

import ${package}.agent.commands.GetAgentTimeOfDayCommand;

import ${package}.api.responses.GetAgentTimeOfDayCmdResponse;

@APICommand(name = "getAgentTimeOfDay", description = "Get a CloudStack agent's time of day", responseObject = GetAgentTimeOfDayCmdResponse.class, since = "4.11.0", includeInApiDoc = true, authorized = {RoleType.User, RoleType.Admin, RoleType.DomainAdmin, RoleType.ResourceAdmin})
public class GetAgentTimeOfDayCmd extends BaseCmd {
    public static final Logger s_logger = Logger.getLogger(GetAgentTimeOfDayCmd.class.getName());
    private static final String s_name = "getagenttimeofdayresponse";

    @Inject
    AgentManager _agentMgr;

    @Parameter(name=ApiConstants.HOST_ID, type=CommandType.UUID, entityType=HostResponse.class, required=true, description="The ID of the host")
    public Long hostId;

    public Long getHostId() {
        return hostId;
    }

    @Override
    public void execute()
    {
        GetAgentTimeOfDayCmdResponse response = new GetAgentTimeOfDayCmdResponse(getTimeFromAgent());
        response.setObjectName("agenttimeofday");
        response.setResponseName(getCommandName());
        this.setResponseObject(response);
    }

    @Override
    public String getCommandName() {
        return s_name;
    }

    @Override
    public long getEntityOwnerId() {
        return 0;
    }

    private String getTimeFromAgent() {
        HostVO host = _entityMgr.findById(HostVO.class, getHostId());
        if (host == null) {
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Host not found");
        }

        if (host.getState() != Status.Up) {
            throw new ServerApiException(ApiErrorCode.UNSUPPORTED_ACTION_ERROR, "Host is not in a good state to run command: State=" + host.getState());
        }

        if (host.getHypervisorType() != HypervisorType.KVM) {
            throw new ServerApiException(ApiErrorCode.UNSUPPORTED_ACTION_ERROR, "Command only works for hosts of type KVM");
        }

        Answer answer = _agentMgr.easySend(host.getId(), new GetAgentTimeOfDayCommand());

        if (answer == null || !answer.getResult()) {
            throw new ServerApiException(ApiErrorCode.INTERNAL_ERROR, "Unable to retrieve answer from host " + host.getUuid() + "," + host.getName());
        }

        return answer.getDetails();
    }
}
