import groovy.json.JsonSlurper
def json = new JsonSlurper().parseText(readFileFromWorkspace('.jenkins/config.json'))

def job_name      = 'pipeline'
def job_desc      = 'pipeline'

view(type: BuildPipelineView) {
  name "${json.jenkins.folder}/${job_name}"
  description job_desc

  displayedBuilds 10

  selectedJob "${json.jenkins.folder}/sample"
}
