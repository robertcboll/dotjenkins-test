import config.*

job {
  name 'dotjenkins-sync'
  description '.jenkins job sync'

  scm {
    git {
      remote {
        github cfg.github.url

        credentials cfg.github.creds
      }

      cfg.jenkins.jobs.sync_trigger.branches.each { git_branch ->
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

    cfg.jenkins.jobs.sync_trigger.downstream.each { job_name -> 
      downstream(job_name)
    }
  }
}
