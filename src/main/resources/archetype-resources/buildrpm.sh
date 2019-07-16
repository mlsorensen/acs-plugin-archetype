#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
#!/bin/bash
set -e -o pipefail

PROJECT=${rootArtifactId}
JAVA_HOME=/usr/lib/jvm/jre-1.8.0
CWD=`pwd`
RPMDIR=$CWD/rpmbuild

function package() {
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
    (rpmbuild ${DISTRO+"$DISTRO"} --define "_topdir $RPMDIR" --define "_javahome $JAVA_HOME" -bb $RPMDIR/SPECS/$PROJECT.spec)
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

function checkGitClean() {
    if git status | grep Untracked; then
        echo "untracked files found, clean up before building rpm. Do you need to 'git clean -fxd', or perhaps git add?"
        exit 1;
    fi
}

if [ "$1" == "el7" ]; then
    DISTRO="-Ddist .el7"
fi

package
