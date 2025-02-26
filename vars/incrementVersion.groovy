#!/usr/bin/env groovy

/**
 * Increments the version number of a project according to semantic versioning
 * @param versionFile Path to the file containing the version (relative to workspace)
 * @param versionPattern Regex pattern to extract the version (should include capture groups)
 * @param incrementType Type of increment: 'major', 'minor', 'patch' (default: 'patch')
 * @return The new version string
 */
def call(Map params = [:]) {
    def versionFile = params.versionFile ?: 'version.txt'
    def versionPattern = params.versionPattern ?: /version\s*=\s*['"]?(\d+)\.(\d+)\.(\d+)['"]?/
    def incrementType = params.incrementType ?: 'patch'
    
    echo "Reading version from file: ${versionFile}"
    
    if (!fileExists(versionFile)) {
        error "Version file not found: ${versionFile}"
    }
    
    def fileContent = readFile(file: versionFile)
    def matcher = fileContent =~ versionPattern
    
    if (!matcher.find()) {
        error "Could not find version pattern in file: ${versionFile}"
    }
    
    def major = matcher.group(1).toInteger()
    def minor = matcher.group(2).toInteger()
    def patch = matcher.group(3).toInteger()
    
    echo "Current version: ${major}.${minor}.${patch}"
    
    // Increment version according to type
    switch (incrementType.toLowerCase()) {
        case 'major':
            major++
            minor = 0
            patch = 0
            break
        case 'minor':
            minor++
            patch = 0
            break
        case 'patch':
        default:
            patch++
            break
    }
    
    def newVersion = "${major}.${minor}.${patch}"
    echo "New version: ${newVersion}"
    
    // Replace version in file
    def newContent = fileContent.replaceFirst(versionPattern, "version=${newVersion}")
    writeFile(file: versionFile, text: newContent)
    
    return newVersion
}