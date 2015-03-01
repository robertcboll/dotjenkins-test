import groovy.json.JsonSlurper
def json = new JsonSlurper().parseText(readFileFromWorkspace('.jenkins/config.json'))

def job_name      = 'sample'
def job_desc      = 'a sample job'

job {
  name "${json.jenkins.folder}/${job_name}"
  description job_desc

  using json.jenkins.tmpl

  wrappers {
    //allocatePorts '49600'
  }

  environmentVariables {
    env('message', 'hello world')
  }

  steps {
    shell 'echo ${message}'
  }

  publishers {
    json.downstream."${job_name}".each { downstream_project -> 
      downstreamParameterized {
        trigger("${json.jenkins.folder}/${downstream_project}", condition = 'SUCCESS') {
          gitRevision(false)
        }
      }
    }
  }
}
