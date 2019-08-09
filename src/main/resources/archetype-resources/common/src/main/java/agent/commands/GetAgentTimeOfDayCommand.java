#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.agent.commands;

import com.cloud.agent.api.Command;

public class GetAgentTimeOfDayCommand extends Command {
    boolean executeInSequence = false;

    @Override
    public boolean executeInSequence() {
        return executeInSequence;
    }
}
