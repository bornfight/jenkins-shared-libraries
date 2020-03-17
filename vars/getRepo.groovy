import com.bornfight.util.PullRequestBuildMatcher

/**
 * Clones repo to the current directory
 * @param repoName - full name of the repository(eg. bornfight/ansible
 * @return
 */
def call(String repoName, String branch = 'master', String credentialsId = 'github', String protocol = 'http') {
    String fullPath = "https://github.com/" + repoName
    if(protocol == 'git'){
        fullPath = "git@github.com:" + repoName
    }
    String fullPath = "https://github.com/" + repoName
    PullRequestBuildMatcher matcher = new PullRequestBuildMatcher()

    if(matcher.isPR(branch)){
        branch = "${CHANGE_BRANCH}"
    }
    git branch: "$branch", credentialsId: "$credentialsId", url: fullPath
}
