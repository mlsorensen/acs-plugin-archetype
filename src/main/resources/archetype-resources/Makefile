REGISTRY ?= acs-plugin
IMAGE ?= ${REGISTRY}/cloudstack-plugins-builder
SRCDIR ?= $(shell pwd)
UID ?= $(shell id -u)
GID ?= $(shell id -g)

# we use local users uid/gid so we can link in .m2 repo
docker-builder:
	docker build --build-arg uid=${UID} --build-arg gid=${GID} -t ${IMAGE} .

# this is for native centos builds
rpm:
	./buildrpm.sh

# this is to build from Mac or Linux using docker
docker-rpm:
	docker run -it -v ${SRCDIR}:/src -v ${HOME}/.m2:/home/builder/.m2 ${IMAGE} /src/buildrpm.sh
