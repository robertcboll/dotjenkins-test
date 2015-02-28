import groovy.json.JsonSlurper

class config {
  static def cfg = {

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
