def call(String env, String[][] configs, String[] sshAgentIds) {
    env = isUndefined(env) ? "integration" : env

    String configArgument = prepareConfigProviderArguments()
    echo configArgument

    String sshAgents = prepareSshAgentArguments(sshAgentIds)
    echo sshAgents


    configFileProvider([prepareConfigProviderArguments(configs)]) {
        sshagent([prepareSshAgentArguments(sshAgentIds)]) {
            echo "Deploy ${env}"
        }
    }
}

static boolean isUndefined(String var){
    return var == null || var.trim().isEmpty() || var.trim() == "null"
}

static String prepareConfigProviderArguments(String[][] configs){
    StringBuilder arguments = new StringBuilder();

    for(int i = 0; i < configs.length; i++){
        arguments.append("configFile(fileId: '" + configs[i][0] + "', targetLocation: '" + configs[i][1] + "')")
        if(i < configs.length -1){
          arguments.append(", ")
        }
    }

    return arguments.toString();
}

static String prepareSshAgentArguments(String[] sshAgentIds){
    StringBuilder arguments = new StringBuilder()

    for(int i = 0; i < sshAgentIds.length; i++){
        arguments.append("'" + sshAgentIds[i] + "'")
        if(i < sshAgentIds.length -1){
            arguments.append(", ")
        }
    }

    return arguments.toString()
}
