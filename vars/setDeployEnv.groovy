def call(String environment) {
    int buildNumber = Integer.parseInt("${BUILD_NUMBER}")

    String buildTag
    try {
        buildTag = "${TAG_NAME}"
    } catch (MissingPropertyException ignored) {
        buildTag = ""
    }

    environment = isUndefined(environment) || isUndefined(buildTag) ? "dev" : environment
    environment = buildNumber == 1 && !isUndefined(buildTag) ? "staging" : environment

    return environment
}

static boolean isUndefined(String var) {
    return var == null || var.trim().isEmpty() || var.trim() == "null"
}
