def call(String env, String branch, String tag, String[][] configs, String sshAgentId) {
    env = isUndefined(env) ? "integration" : env

    if(env == "integration" && branch != "master"){
        currentBuild.result = "SKIP"
        return
    }

    if(!isUndefined(tag) && env == "integration"){
        currentBuild.result = "ABORTED"
        return
    }


    echo env
    echo branch
    echo tag
    echo configs[0][0]
    echo sshAgentId
    currentBuild.result = "SKIP"
}

static boolean isUndefined(String var){
    return var == null || var.trim().isEmpty() || var.trim() == "null"
}
