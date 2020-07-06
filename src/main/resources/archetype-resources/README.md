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
