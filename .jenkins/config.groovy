import groovy.json.JsonSlurper

class config {
  def json = new JsonSlurper().parseText(readFileFromWorkspace('.jenkins/jenkins.json'))
  def cfg = 
    {
      sample {
        foo = "bar"
      }
    }
}
