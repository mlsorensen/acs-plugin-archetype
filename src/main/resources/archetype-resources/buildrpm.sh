#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
#!/bin/bash
set -e -o pipefail

PROJECT=${rootArtifactId}
CWD=`pwd`
RPMDIR="$CWD/rpmbuild"
VERSION=$(mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate -Dexpression=project.version | egrep -v "^\\[|Download")

function package() {
    checkVersion
    checkGitClean

    # create rpm build environment
    mkdir -p $RPMDIR/SPECS
    mkdir -p $RPMDIR/BUILD
    mkdir -p $RPMDIR/RPMS
    mkdir -p $RPMDIR/SRPMS
    mkdir -p $RPMDIR/SOURCES/$PROJECT-$VERSION

    echo !!!!!!!!!!!!!!!!
    echo BUILDING TARBALL
    echo !!!!!!!!!!!!!!!!
    mvn clean assembly:assembly -Dmaven.test.skip=true
    cp target/${PROJECT}* $RPMDIR/SOURCES
    cp $PROJECT.spec $RPMDIR/SPECS

    echo !!!!!!!!!!!!!
    echo BUILDING RPMs
    echo !!!!!!!!!!!!!
    (rpmbuild --define "_topdir $RPMDIR" -bb $RPMDIR/SPECS/$PROJECT.spec)
    cp $RPMDIR/RPMS/x86_64/*.rpm .

    echo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    echo RPM BUILD COMPLETE, check current directory for RPMs
    echo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    cleanup
}

function cleanup() {
    if [ -d $RPMDIR ]; then
        rm -rf $RPMDIR
    fi
}

function checkVersion() {
    if [[ $VERSION == *"-"* ]]; then
        echo "Unable to use artifact version $VERSION for RPM version as it contains a hyphen"
       exit 1;
    fi
}

function checkGitClean() {
    if ! git status; then
echo "
please set up git repo first, e.g. :
git init
git add .
git commit -a -m 'initial commit'
git config user.email 'you@example.com'
git config user.name 'Your Name'
"
        exit 1;
    fi

    if git status | grep Untracked; then
        echo "untracked files found, clean up before building rpm. Do you need to 'git clean -fxd', or perhaps git add?"
        exit 1;
    fi
}

package
