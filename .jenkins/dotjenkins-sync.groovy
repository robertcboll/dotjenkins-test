import config

def json = config.json

job {
  name 'dotjenkins-sync'
  description '.jenkins job sync'

  scm {
    git {
      remote {
        github json.github.url

        credentials json.github.creds
      }

      json.jenkins.jobs.sync_trigger.branches.each { git_branch ->
        branch(git_branch)
      }
    }
  }

  triggers {
    scm '' // required for trigger
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
    }
  }

  publishers {
    publishCloneWorkspace("*")

    json.jenkins.jobs.sync_trigger.downstream.each { job_name -> 
      downstream(job_name)
    }
  }
}
