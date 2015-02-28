import groovy.json.JsonSlurper

def json = new JsonSlurper().parseText(readFileFromWorkspace('.jenkins/jenkins.json'))

def cfg = {
  sample {
    foo = "bar"
  }
}
