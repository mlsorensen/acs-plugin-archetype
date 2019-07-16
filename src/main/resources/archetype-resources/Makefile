REGISTRY ?= acs-plugin
IMAGE ?= ${REGISTRY}/cloudstack-plugins-builder
SRCDIR ?= $(shell pwd)

docker-builder:
	docker build -t ${IMAGE} .

rpm:
	docker run -it -v ${SRCDIR}:/src -v ${HOME}/.m2:/root/.m2 ${IMAGE} /src/buildrpm.sh
