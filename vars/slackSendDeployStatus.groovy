def call() {
    echo currentBuild.result
    switch(currentBuild.result){
        case "SUCCESS":
            echo "success"
            break;
        case "FAILURE":
            echo "Failure"
            break;
        case "SKIP":
            echo "SKIP"
            currentBuild.result = "SUCCESS"
    }
}
