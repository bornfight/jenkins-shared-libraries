@Grab(group='org.jenkins-ci.plugins', module='plugin', version='1.411', type='pom')
import org.jenkinsci.plugins.configfiles.buildwrapper.ManagedFile

def call(String environment, String[][] configs, String[] sshAgentIds) {
    echo "Deploy ${environment}"
    if(configs == null){
        sshagent(prepareSshAgentArguments(sshAgentIds)) {
            sh "vendor/bin/dep -vvv -p deploy ${environment}"
        }
    } else {
        configFileProvider(prepareConfigProviderArguments(configs)) {
            sshagent(prepareSshAgentArguments(sshAgentIds)) {
                sh "vendor/bin/dep -vvv -p deploy ${environment}"
            }
        }
    }
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
