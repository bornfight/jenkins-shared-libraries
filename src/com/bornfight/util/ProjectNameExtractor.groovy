package com.bornfight.util

class ProjectNameExtractor {

    private static final String GITHUB_ORGANIZATION_URL = 'https://github.com/bornfight/'

    static String extractFromUrl(String url){
        return url.replaceAll(GITHUB_ORGANIZATION_URL, '').replaceAll('.git', '')
    }
}
