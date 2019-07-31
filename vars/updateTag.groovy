import com.bornfight.step.DeployedTagsTracker

def call(String sheetId, String credentials, String gitUrl, String env, String tag) {
    DeployedTagsTracker dtt = new DeployedTagsTracker()

    dtt.update(sheetId, credentials, gitUrl, env, tag)
}
