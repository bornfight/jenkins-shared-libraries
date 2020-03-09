package com.bornfight.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PullRequestBuildMatcher {
    public boolean isPR(String branchName){
        Pattern pattern = Pattern.compile("PR-[\\d]+");
        Matcher matcher = pattern.matcher(branchName);
        return matcher.matches();
    }
}
