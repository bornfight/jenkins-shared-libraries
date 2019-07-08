def call(String environment) {
    int buildNumber = Integer.parseInt("${BUILD_NUMBER}")
    String buildTag
    try {
        buildTag = "${TAG_NAME}"
    } catch (MissingPropertyException ignored) {
        echo "b tag catch: ${buildTag}"
        buildTag = ""
    }

    environment = isUndefined(environment) || isUndefined(buildTag) ? "integration" : environment
    echo "b tag: ${buildTag}"

    if (buildNumber == 1 && !isUndefined(buildTag)) {
        environment = "staging"
    }
    echo "end env: ${environment}"
    return "st"
}

static boolean isUndefined(String var) {
    return var == null || var.trim().isEmpty() || var.trim() == "null"
}
