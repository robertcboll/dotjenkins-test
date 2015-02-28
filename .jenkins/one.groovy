import groovy.json.JsonSlurper
def json = new JsonSlurper().parseText(readFileFromWorkspace('.jenkins/config.json'))

def me = json.jobs.one

def job_name = "one"
def job_desc = "a sample job"

job {
  name job_name
  description job_desc

  if (json.jenkins.labels != null) {
    label json.jenkins.labels
  }

  scm {
    git {
      remote {
        github json.git.url
        credentials json.git.creds
      }

      me.branches.each { git_branch ->
        branch git_branch
      }
    }
  }

  wrappers {
    timestamps()
    buildName '#${BUILD_NUMBER} ${GIT_BRANCH} ${GIT_REVISION,length=10}'
  }

  steps {
    //TODO fill in steps

    me.downstream.each { downstream_project -> 
      downstreamParameterized {
        trigger(downstream_project, condition = 'SUCCESS')
      }
    }
  }
}
