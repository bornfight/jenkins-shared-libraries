@Grab(group='org.jenkins-ci.plugins', module='plugin', version='1.411', type='pom')
import org.jenkinsci.plugins.configfiles.buildwrapper.ManagedFile

def call(String environment, String[][] configs, String[] sshAgentIds) {
    int buildNumber = Integer.parseInt("${BUILD_NUMBER}")
    String buildTag = ""
    try {
        buildTag = "${TAG_NAME}"
    }catch(MissingPropertyException ignored){
        echo "b tag catch: ${buildTag}"
        buildTag = ""
    }

    environment = isUndefined(environment) || isUndefined(buildTag) ? "integration" : environment
    echo "b tag: ${buildTag}"

    if(buildNumber == 1 && !isUndefined(buildTag)){
        environment = "staging"
    }

    echo "Deploy ${environment}"
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
