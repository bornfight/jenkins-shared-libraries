def call(String url) {
    return url.replaceAll('https://github.com/', '').replaceAll('.git', '')
}
