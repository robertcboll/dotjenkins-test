import groovy.json.JsonSlurper
def json = new JsonSlurper().parseText(readFileFromWorkspace('.jenkins/config.json'))

def job_name      = "${json.jenkins.folder}/_job"
def job_desc      = 'a sample job'

job {
  name job_name
  description job_desc

  using json.jenkins.tmpl

  wrappers {
    //allocatePorts '49600'
  }

  environmentVariables {
    //env('key', 'value')
  }

  steps {
    shell 'echo "hello world"'
  }

  publishers {
    json.downstream."${job_name}".each { downstream_project -> 
      downstreamParameterized {
        trigger("${json.jenkins.folder}/${downstream_project}", condition = 'SUCCESS')
      }
    }
  }
}
