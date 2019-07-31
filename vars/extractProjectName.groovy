import com.bornfight.util.ProjectNameExtractor

def call(String url) {
    return ProjectNameExtractor.extractFromUrl(url)
}