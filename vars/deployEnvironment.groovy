def call(String env, String branch, String tag, String[][] configs, String sshAgentId) {
    env = isUndefined(env) ? "integration" : env

    if(env == "integration" && branch != "master"){
        currentBuild.result = "ABORTED"
        return
    }

    if(!isUndefined(tag) && env == "integration"){
        currentBuild.result = "ABORTED"
        error('Aborting first tag build due to undefined stage')
    }


    echo env
    echo branch
    echo tag
    echo configs[0][0]
    echo sshAgentId
    currentBuild.result = "ABORTED"
    error('Aborting first tag build due to undefined stage')
}

static boolean isUndefined(String var){
    return var == null || var.trim().isEmpty() || var.trim() == "null"
}
