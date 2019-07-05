@Grab(group='org.jenkins-ci.plugins', module='plugin', version='1.411', type='pom')
import org.jenkinsci.plugins.configfiles.buildwrapper.ManagedFile

def call(String env, String[][] configs, String[] sshAgentIds) {
    env = isUndefined(env) ? "integration" : env
    sh 'printenv'

    configFileProvider(prepareConfigProviderArguments(configs)) {
        sshagent([prepareSshAgentArguments(sshAgentIds)]) {
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

static String prepareSshAgentArguments(String[] sshAgentIds) {
    StringBuilder arguments = new StringBuilder()

    for (int i = 0; i < sshAgentIds.length; i++) {
        arguments.append("'" + sshAgentIds[i] + "'")
        if (i < sshAgentIds.length - 1) {
            arguments.append(", ")
        }
    }

    return arguments.toString()
}
