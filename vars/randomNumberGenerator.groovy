def call(String repoName) {
    Random random = new Random()
    number = random.nextInt()

    return number > 0 ? number : - number
}
