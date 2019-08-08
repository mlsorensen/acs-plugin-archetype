
def executablePaths = ["buildrpm.sh"]
def plugindir = request.getArtifactId() + File.separator

executablePaths.each {
    def file = new File( request.getOutputDirectory(), plugindir + "${it}" );
    println "Setting " + file.getAbsolutePath() + " to executable"
    file.setExecutable(true, false);
}

// we can't store .gitignore in the archetype, so let's rename it when the archetype is run
def gitignorefile = new File(request.getOutputDirectory(), plugindir + 'gitignore')
gitignorefile.renameTo(plugindir + '.gitignore')
println "Updated .gitignore file"
