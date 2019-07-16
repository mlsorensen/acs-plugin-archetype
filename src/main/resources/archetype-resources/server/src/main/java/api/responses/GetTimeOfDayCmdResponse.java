#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.api.responses;

import com.cloud.serializer.Param;
import com.google.gson.annotations.SerializedName;

import org.apache.cloudstack.api.BaseResponse;
import org.apache.cloudstack.api.ApiConstants;

import java.util.Date;
import java.text.SimpleDateFormat;

@SuppressWarnings("unused")
public class GetTimeOfDayCmdResponse extends BaseResponse {
    @SerializedName(ApiConstants.IS_ASYNC) @Param(description="true if api is asynchronous")
    private Boolean isAsync;
    @SerializedName("timeOfDay") @Param(description="The time of day from CloudStack")
    private String  timeOfDay;
    @SerializedName("exampleEcho") @Param(description="An upper cased string")
    private String  exampleEcho;

    public GetTimeOfDayCmdResponse(){
        this.isAsync   = false;

        SimpleDateFormat dateformatYYYYMMDD = new SimpleDateFormat("yyyyMMdd hh:mm:ss");
        this.setTimeOfDay( (new StringBuilder( dateformatYYYYMMDD.format( new Date() ) )).toString() );
    }

    public void setAsync(Boolean isAsync) {
        this.isAsync = isAsync;
    }

    public boolean getAsync() {
        return isAsync;
    }

    public void setTimeOfDay(String timeOfDay) {
        this.timeOfDay = timeOfDay;
    }

    public void setExampleEcho(String exampleEcho) {
        this.exampleEcho = exampleEcho.toUpperCase();
    }
}
