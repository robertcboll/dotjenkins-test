import groovy.json.JsonSlurper

class config {
  def cfg = {

    github = {
        repo = "robertcboll/dotjenkins-test"
        creds = "jenkins"
    }

    jenkins = {
      folder = "dotjenkins-test"

      views = {
      }

      jobs = {
      }
    }
  }
}
