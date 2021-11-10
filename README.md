# Maven Archetype for generating CloudStack Plugins

This archetype can be used to bootstrap plugin development. 

It's recommended that you first choose a target CloudStack version (e.g. 4.11.3.0) and build the jars locally so they are in your maven cache. You'll use this version string as input when generating the plugin, which will in turn set up your plugin's RPM requires and pom.xml dependency to that version.

```
#in cloudstack source code dir
git checkout 4.14
mvn clean install -P developer
# alternatively 'mvn clean install -P developer -Dnoredist' for including VMware and other special license dependencies
```

### Installation
1. Check out the source code (this repo)
2. Run 'mvn install' to install archetype to development system

### Use
1. Run the archetype, which will create a directory and build a Maven project. Substitute "org.foo" values with your own.
```
mvn archetype:generate -DarchetypeArtifactId=acs-plugin -DarchetypeGroupId=org.apache.cloudstack.archetype -DgroupId=org.foo -Dpackage=org.foo.plugin -DartifactId=foo-plugin -DcloudstackVersion=4.11.3.0 -Dversion=1.0 -DinteractiveMode=false
```
2. Change directory into your new plugin project, or open your plugin project and modify. Short proof of concept builds an EL7 RPM using Docker:
```
cd foo-plugin
make docker-builder # image used for building RPMs
# before proceeding, check version of cloudstack referenced in pom.xml, must match installed cloudstack jars from installation step
git init
git add .
git config user.email 'you@example.com'
git config user.name 'Your Name'
git commit -a -m 'initial commit'
make rpm
```

 3. There is also support for injecting your plugin jar into a CloudStack Docker image. See your plugin's README.md for more information.
```
make server
```
