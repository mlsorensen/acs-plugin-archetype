REGISTRY ?= acs-plugin
IMAGE ?= ${REGISTRY}/cloudstack-plugins-builder
PLUGIN_ARTIFACT ?= $(shell mvn -q -Dexec.executable=echo -Dexec.args='$${project.artifactId}' --non-recursive exec:exec)
PLUGIN_VERSION ?= $(shell mvn -q -Dexec.executable=echo -Dexec.args='$${project.version}' --non-recursive exec:exec)
PLUGIN_IMAGE ?= ${REGISTRY}/cloudstack-server_${PLUGIN_ARTIFACT}:${PLUGIN_VERSION}
SERVER_IMAGE ?= "cloudstack/simulator-jdk"
SERVER_TAG ?= "latest"
SERVER_LIB ?= "/usr/share/cloudstack-management/lib/"
SRCDIR ?= $(shell pwd)
UID ?= $(shell id -u)
GID ?= $(shell id -g)

# we use local users uid/gid so we can link in .m2 repo
docker-builder:
	docker build -f Dockerfile.builder --build-arg uid=${UID} --build-arg gid=${GID} -t ${IMAGE} .

# this is for native centos builds
rpm:
	./buildrpm.sh

# this is to build from Mac or Linux using docker
docker-rpm:
	docker run --rm -v ${SRCDIR}:/src -v ${HOME}/.m2:/home/builder/.m2 ${IMAGE} /src/buildrpm.sh


jar:
	docker run --rm -v ${SRCDIR}:/src -v ${HOME}/.m2:/home/builder/.m2 ${IMAGE} mvn

bash:
	docker run -it -v ${SRCDIR}:/src -v ${HOME}/.m2:/home/builder/.m2 ${IMAGE} bash

.PHONY: server
server:
	docker build -f Dockerfile.server --build-arg "SERVER=${SERVER_IMAGE}" --build-arg "TAG=${SERVER_TAG}" --build-arg "LIBPATH=${SERVER_LIB}" -t ${PLUGIN_IMAGE} .
