FROM centos:7

RUN yum install -y \
        java-1.8.0-openjdk-devel \
        git \
        maven \
        rpm-build \
        rpm-sign && yum clean all

WORKDIR "/src"
