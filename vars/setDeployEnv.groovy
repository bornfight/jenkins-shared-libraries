def call(String environment) {
    int buildNumber = Integer.parseInt("${BUILD_NUMBER}")
    String buildTag

    try {
        buildTag = "${TAG_NAME}"
    } catch (MissingPropertyException ignored) {
        buildTag = ""
    }

    environment = isUndefined(environment) || isUndefined(buildTag) ? "integration" : environment

    if (buildNumber == 1 && !isUndefined(buildTag)) {
        environment = "staging"
    }

    return environment
}

static boolean isUndefined(String var) {
    return var == null || var.trim().isEmpty() || var.trim() == "null"
}
