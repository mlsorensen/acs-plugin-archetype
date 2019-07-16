##### Maven Archetype for generating CloudStack Plugins


### Installation
1. Check out the source code (this repo)
2. Run 'mvn install' to install archetype to development system

### Use
1. Run the archetype, which will create a directory and build a Maven project. Substitute "org.foo" values with your own.
```
mvn archetype:generate -DarchetypeArtifactId=acs-plugin -DarchetypeGroupId=org.apache.cloudstack.archetype -DgroupId=org.foo -Dpackage=org.foo.plugin -DartifactId=foo-plugin -Dversion=1.0 -DinteractiveMode=false
```
2. Change directory into your new plugin project, or open your plugin project and modify. Short proof of concept builds an EL7 RPM using Docker:
```
cd foo-plugin
make docker-builder # image used for building RPMs
make rpm
```
