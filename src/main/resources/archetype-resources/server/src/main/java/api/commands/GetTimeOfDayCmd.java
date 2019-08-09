#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
// from https://cwiki.apache.org/confluence/display/CLOUDSTACK/CloudStack+API+Development
package ${package}.api.commands;

import org.apache.log4j.Logger;

import org.apache.cloudstack.acl.RoleType;
import org.apache.cloudstack.api.BaseCmd;
import org.apache.cloudstack.api.APICommand;
import org.apache.cloudstack.api.Parameter;

import ${package}.api.responses.GetTimeOfDayCmdResponse;

@APICommand(name = "getTimeOfDay", description = "Get Cloudstack's time of day", responseObject = GetTimeOfDayCmdResponse.class, since = "4.11.0", includeInApiDoc = true,
            authorized = {RoleType.User, RoleType.Admin, RoleType.DomainAdmin, RoleType.ResourceAdmin})
public class GetTimeOfDayCmd extends BaseCmd {
    public static final Logger s_logger = Logger.getLogger(GetTimeOfDayCmd.class.getName());
    private static final String s_name = "gettimeofdayresponse";

    @Parameter(name="example", type=CommandType.STRING, required=false, description="Just an example string that will be uppercased")
    private String example;

    public String getExample() {
        return this.example;
    }

    @Override
    public void execute()
    {
        GetTimeOfDayCmdResponse response = new GetTimeOfDayCmdResponse();
        if ( this.example != null ) {
            response.setExampleEcho(example);
        }

        response.setObjectName("timeofday"); // the inner part of the json structure
        response.setResponseName(getCommandName()); // the outer part of the json structure

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
}
