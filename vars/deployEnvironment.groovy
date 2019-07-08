@Grab(group='org.jenkins-ci.plugins', module='plugin', version='1.411', type='pom')
import org.jenkinsci.plugins.configfiles.buildwrapper.ManagedFile

def call(String env, String[][] configs, String[] sshAgentIds) {
    env = isUndefined(env) ? "integration" : env

    int buildNumber = Integer.parseInt("${BUILD_NUMBER}")
    String buildTag = "${TAG_NAME}"

    if(buildNumber == 1 && !isUndefined(buildTag)){
        currentBuild.result = 'ABORTED'
        error('Aborting first tag build due to undefined stage')
    }

    configFileProvider(prepareConfigProviderArguments(configs)) {
        sshagent(prepareSshAgentArguments(sshAgentIds)) {
            echo "Deploy ${env}"
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
