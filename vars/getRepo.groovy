import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Clones repo to the current directory
 * @param repoName - full name of the repository(eg. bornfight/ansible
 * @return
 */
def call(String repoName, String branch = 'master') {
    String fullPath = "https://github.com/" + repoName

    Pattern pattern = Pattern.compile("PR-[\\d]+")
    Matcher matcher = pattern.matcher(branch)
    boolean matches = matcher.matches()
    if(matches){
        branch = "${CHANGE_BRANCH}"
    }
    git branch: "$branch", credentialsId: 'github', url: fullPath
}