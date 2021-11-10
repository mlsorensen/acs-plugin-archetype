FROM centos:7

ARG uid="1000"
ARG gid="1000"

RUN yum install -y \
        java-11-openjdk-devel \
        git \
        maven \
        rpm-build \
        rpm-sign && \
    yum clean all

ENV JAVA_HOME=/usr/lib/jvm/java-11

# we add user's local user into docker image so we can link in .m2 repo
RUN groupadd -g $gid builder || echo "gid exists" \
&& useradd -m -u $uid -g $gid builder || echo "uid exists"
USER builder

WORKDIR "/src"
