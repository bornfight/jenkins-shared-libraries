def call(String repoName) {
    stage('Code Metrics') {
        when {
            branch 'master'
        }
        steps {
            checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'RelativeTargetDirectory', relativeTargetDir: 'code-metrics-observatory']], submoduleCfg: [], userRemoteConfigs: [[credentialsId: 'istudio', url: 'git@github.com:degordian/code-metrics-observatory.git']]])
            dir('code-metrics-observatory') {
                sh 'composer install'
                configFileProvider([configFile(fileId: 'c3d7339a-64c3-43ed-a9f0-51a2423f267c', targetLocation: '.env.test.local')]) {
                    sh "bin/console collect ${repoName} ../tests/_output/report.xml"
                }
            }
        }
    }
}