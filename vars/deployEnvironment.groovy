@Grab(group='org.jenkins-ci.plugins', module='plugin', version='1.411', type='pom')
import org.jenkinsci.plugins.configfiles.buildwrapper.ManagedFile

def call(String environment, String[][] configs, String[] sshAgentIds) {
    echo "Deploy ${environment}"
    environment = isUndefined(environment) ? "integration" : environment

    int buildNumber = Integer.parseInt("${BUILD_NUMBER}")
    echo "build number: ${buildNumber}"
    String buildTag = "${TAG_NAME}"
    echo "build tag: ${buildTag}"

    if(buildNumber == 1 && !isUndefined(buildTag)){
        environment = "staging"
    }

    configFileProvider(prepareConfigProviderArguments(configs)) {
        sshagent(prepareSshAgentArguments(sshAgentIds)) {
            sh "vendor/bin/dep -vvv -p deploy ${environment}"
        }
    }
}

static boolean isUndefined(String var) {
    return var == null || var.trim().isEmpty() || var.trim() == "null"
}

static List<ManagedFile> prepareConfigProviderArguments(String[][] configs) {
    List<ManagedFile> configFileProviderArgs = new ArrayList<>(configs.length)

    for (int i = 0; i < configs.length; i++) {
        configFileProviderArgs.add(new ManagedFile(configs[i][0], configs[i][1], null))
    }

    return configFileProviderArgs
}

static List<String> prepareSshAgentArguments(String[] sshAgentIds) {
    return Arrays.asList(sshAgentIds)
}
