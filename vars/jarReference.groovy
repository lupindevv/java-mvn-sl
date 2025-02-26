// File: vars/jarReference.groovy

/**
 * Creates or updates a reference to a JAR file with the current version
 * @param jarBaseName Base name of the JAR file (without version and extension)
 * @param version Current version to use
 * @param targetPath Path where the reference file should be created/updated
 * @param artifactPath Path to the directory containing the JAR files
 * @return Path to the referenced JAR file
 */
def call(Map params = [:]) {
    def jarBaseName = params.jarBaseName ?: 'application'
    def version = params.version
    def targetPath = params.targetPath ?: '.'
    def artifactPath = params.artifactPath ?: 'build/libs'
    
    if (!version) {
        error "Version parameter is required"
    }
    
    def jarFileName = "${jarBaseName}-${version}.jar"
    def jarPath = "${artifactPath}/${jarFileName}"
    def referenceFilePath = "${targetPath}/${jarBaseName}.ref"
    
    // Verify the JAR exists
    if (!fileExists(jarPath)) {
        error "JAR file not found: ${jarPath}"
    }
    
    // Create or update the reference file
    writeFile(file: referenceFilePath, text: jarPath)
    echo "Created reference file at ${referenceFilePath} pointing to ${jarPath}"
    
    return jarPath
}