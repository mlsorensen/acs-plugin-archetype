#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package com.cloud.hypervisor.kvm.resource.wrapper; // using this package ensures agent reflection will find our command wrapper

import org.apache.log4j.Logger;

import com.cloud.agent.api.Answer;
import com.cloud.hypervisor.kvm.resource.LibvirtComputingResource;
import com.cloud.resource.CommandWrapper;
import com.cloud.resource.ResourceWrapper;

import java.util.Date;
import java.text.SimpleDateFormat;

import ${package}.agent.commands.GetAgentTimeOfDayCommand;

@ResourceWrapper(handles = GetAgentTimeOfDayCommand.class)
public final class GetAgentTimeOfDayCommandWrapper extends CommandWrapper<GetAgentTimeOfDayCommand, Answer, LibvirtComputingResource> {

    private static final Logger s_logger = Logger.getLogger(GetAgentTimeOfDayCommandWrapper.class);

    @Override
    public Answer execute(final GetAgentTimeOfDayCommand command, final LibvirtComputingResource libvirtComputingResource) {
        return new Answer(command, true, getTime());
    }

    private String getTime() {
        SimpleDateFormat dateformatYYYYMMDD = new SimpleDateFormat("yyyyMMdd hh:mm:ss");
        return new StringBuilder( dateformatYYYYMMDD.format( new Date() ) ).toString();
    }
}
