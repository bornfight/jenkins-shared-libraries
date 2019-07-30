def call(String url) {
    return url.replaceAll('https://github.com/bornfight/', '').replaceAll('.git', '')
}
