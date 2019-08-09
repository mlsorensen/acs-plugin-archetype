#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package}.api.responses;

import com.cloud.serializer.Param;
import com.google.gson.annotations.SerializedName;

import org.apache.cloudstack.api.BaseResponse;

public class GetAgentTimeOfDayCmdResponse extends BaseResponse {

    @SerializedName("agenttime") @Param(description="The time of day from CloudStack agent")
    private String agentTime;

    public GetAgentTimeOfDayCmdResponse(String time) {
        this.agentTime = time;
    }

}
