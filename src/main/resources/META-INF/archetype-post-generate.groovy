
def executablePaths = ["buildrpm.sh"]

executablePaths.each {
    def file = new File( request.getOutputDirectory(), request.getArtifactId() + File.separator + "${it}" );
    println "Setting " + file.getAbsolutePath() + "to executable"
    file.setExecutable(true, false);
}
