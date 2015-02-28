import groovy.json.JsonSlurper

public def json = new JsonSlurper().parseText(readFileFromWorkspace('.jenkins/jenkins.json'))

def cfg = {
  sample {
    foo = "bar"
  }
}
