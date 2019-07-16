#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
%global debug_package %{nil}
%define __jar_repack %{nil}
%define _ver %(mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version | egrep -v "^\\[|Downloading")
%define _commit %(git rev-parse --short HEAD)
%define _csver 4.11.2.0
%define _pluginname ${rootArtifactId}

Name:           %{_pluginname}
Version:        %{_ver}
Release:        1_%{_commit}%{?dist}
Summary:        CloudStack plugin
License:        TBD
URL:            TBD
Source0:    %{name}-%{version}.tar.gz
BuildRoot:  %{_tmppath}/%{name}-%{version}-%{release}-build

%package server
Summary: CloudStack Server plugin - %{name}
Requires: cloudstack-management >= %{_csver}, cloudstack-common >= %{_csver}
Provides: %{name}-server

%package agent
Summary: CloudStack KVM agent plugin - %{name}
Requires: cloudstack-agent >= %{_csver}, cloudstack-common >= %{_csver}
Requires: vmfs-tools
Requires: util-linux
Requires: coreutils
Provides: %{name}-agent

%description
CloudStack plugin %{name} v%{version}

%description server
CloudStack plugin %{name} v%{version} for server

%description agent
CloudStack plugin %{name} v%{version} for KVM agent

%prep
%setup -q
mvn clean

%build
export JAVA_HOME=%{_javahome}
mvn -TC1.5

%install
#server
mkdir -p %{buildroot}/usr/share/%{name}
mkdir -p %{buildroot}/usr/share/cloudstack-management/lib/
cp -rf server/scripts %{buildroot}/usr/share/%{name}
cp -f server/target/%{name}-server-%{_ver}.jar %{buildroot}/usr/share/cloudstack-management/lib/
mkdir -p %{buildroot}/usr/share/cloudstack-management/webapp/plugins
cp -rf server/plugin-ui/* %{buildroot}/usr/share/cloudstack-management/webapp/plugins
mkdir -p %{buildroot}%{_sysconfdir}/cloudstack/management/META-INF/cloudstack/core
cp -f server/config/spring-event-bus-context.xml %{buildroot}%{_sysconfdir}/cloudstack/management/META-INF/cloudstack/core/spring-event-bus-context.xml
#agent
mkdir -p %{buildroot}/usr/share/cloudstack-agent/lib/scripts
mkdir -p %{buildroot}/etc/cloudstack/agent
cp -f agent/target/%{name}-agent-%{version}.jar %{buildroot}/usr/share/cloudstack-agent/lib
cp -f agent/scripts/* %{buildroot}/usr/share/cloudstack-agent/lib/scripts
#common
cp -f common/target/%{name}-common-%{version}.jar %{buildroot}/usr/share/cloudstack-management/lib/
cp -f common/target/%{name}-common-%{version}.jar %{buildroot}/usr/share/cloudstack-agent/lib

%clean
rm -rf %{buildroot}

%files server
%defattr(640,cloud,cloud,750)
%attr(750, cloud, cloud) /usr/share/%{name}/scripts/*
/usr/share/cloudstack-management/lib/*
%config(noreplace) %{_sysconfdir}/cloudstack/management/META-INF
/usr/share/cloudstack-management/webapp/plugins/*
%doc

%files agent
%defattr(640,root,root,-)
/usr/share/cloudstack-agent/lib/*.jar
%attr(750, root, root)/usr/share/cloudstack-agent/lib/scripts/*
/etc/cloudstack/agent/*
%doc

%changelog
