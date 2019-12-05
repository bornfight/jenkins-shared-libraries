/**
 * Clones repo to the current directory
 * @param repoName - full name of the repository(eg. bornfight/ansible
 * @return
 */
def call(String repoName) {
    String fullPath = "https://github.com/" + repoName
    git credentialsId: 'github', url: fullPath
}