#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound}${symbol_pound} Apache cloudstack Plugin - ${rootArtifactId}

${symbol_pound}${symbol_pound}${symbol_pound} Prerequisites 

Ensure you have local cloudstack jars in your ~/.m2 directory for the target cloudstack version. Example:
```
#in cloudstack source code dir
git checkout 4.14
mvn clean install -P developer
# alternatively 'mvn clean install -P developer -Dnoredist' for including VMware and other special license dependencies
```

Alternatively, configure your ~/.m2/settings.xml to pull jars from a Maven repository. For CloudStack's repo it would look like this:
```
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                      http://maven.apache.org/xsd/settings-1.0.0.xsd">

  <activeProfiles>
    <activeProfile>github</activeProfile>
  </activeProfiles>

  <profiles>
    <profile>
      <id>github</id>
      <repositories>
        <repository>
          <id>central</id>
          <url>https://repo1.maven.org/maven2</url>
        </repository>
        <repository>
          <id>github</id>
          <url>https://maven.pkg.github.com/apache/cloudstack</url>
          <snapshots>
            <enabled>true</enabled>
          </snapshots>
        </repository>
      </repositories>
    </profile>
  </profiles>

  <servers>
    <server>
      <id>github</id>
      <username>USERNAME</username>
      <password>PERSONAL_ACCESS_TOKEN</password>
    </server>
  </servers>
</settings>

```

Ensure git repo is initialized
```
git init
git add .
git config user.email 'you@example.com'
git config user.name 'Your Name'
git commit -a -m 'initial commit'
```

${symbol_pound}${symbol_pound}${symbol_pound} Building

```
# before proceeding, ensure pom.xml references version of cloudstack jars installed as prerequisite

# build using docker
make docker-builder
make docker-rpm

# build natively on CentOS
make rpm
```

${symbol_pound}${symbol_pound}${symbol_pound} Docker

Basic support for running your plugin with Docker is provided. This starts with a CloudStack Docker image and simply
adds your plugin jars. See the [CloudStack README](http://github.com/apache/cloudstack/tree/main/tools/docker/README.md)
for more info about how to build CloudStack Docker images.

Example:

```
# build with default args
make server

# build from specified CloudStack server image, specified output image name, specified plugin install dir
SERVER_IMAGE=my-local-cloudstack SERVER_TAG=4.16-SNAPSHOT PLUGIN_IMAGE=${rootArtifactId}-server:1.0 SERVER_LIB=/usr/share/cloudstack-management/lib/ make server

# run (this is ultimately dependent on how the CloudStack image you're starting from is supposed to be run.
docker run -p 8080:8080 --network cloudstack -it ${rootArtifactId}-server:1.0

```
