import groovy.json.JsonSlurper
def json = new JsonSlurper.parseText(readFileFromWorkspace('.jenkins/config.json'))

def me = json.jobs.sync

job {
  name 'sync'
  description '.jenkins sync'

  if (json.jenkins..labels != null) {
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

  triggers {
    scm ''
  }

  steps {
    conditionalSteps {
      condition {
        shell 'git show --pretty="format:" --name-only | grep ".jenkins"'
      }
      runner("Run")

      dsl {
        removeAction 'DELETE'
        external '.jenkins/*.groovy'
      }

      downstreamParameterized {
        me.downstream.each { downstream_project ->  
          trigger("${jenkins.folder}/${downstream_project}", condition = 'SUCCESS')
        }
      }
    }
  }
}
