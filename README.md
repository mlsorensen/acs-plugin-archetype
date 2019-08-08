##### Maven Archetype for generating CloudStack Plugins


### Installation
1. Check out the source code (this repo)
2. Run 'mvn install' to install archetype to development system
3. Ensure you have local cloudstack jars in your ~/.m2 directory for the target cloudstack version. Example:
```
#in cloudstack source code dir
git checkout 4.11.3.0
mvn clean install -P developer
# alternatively 'mvn clean install -P developer -Dnoredist' for including VMware and other special license dependencies
```

### Use
1. Run the archetype, which will create a directory and build a Maven project. Substitute "org.foo" values with your own.
```
mvn archetype:generate -DarchetypeArtifactId=acs-plugin -DarchetypeGroupId=org.apache.cloudstack.archetype -DgroupId=org.foo -Dpackage=org.foo.plugin -DartifactId=foo-plugin -Dversion=1.0 -DinteractiveMode=false
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
