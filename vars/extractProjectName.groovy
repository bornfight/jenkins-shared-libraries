def call(String url) {
    return url.replaceAll('https://github.com/degordian/', '').replaceAll('.git', '')
}
