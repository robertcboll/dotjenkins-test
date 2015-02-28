import groovy.json.JsonSlurper
def json = new JsonSlurper().parseText(readFileFromWorkspace('.jenkins/config.json'))

def me = json.jenkins.jobs.base

job {
  name me.name
  description me.description

  if (me.labels != null) {
    me.labels
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
    //todo

    me.downstream.each { downstream_project -> 
      downstreamParameterized {
        trigger(downstream_project, condition = 'SUCCESS')
      }
    }
  }

  publishers {
    
  }
}
