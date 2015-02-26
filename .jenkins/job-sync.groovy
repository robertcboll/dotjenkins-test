/* variables */
def cfg = new Properties(); cfg.load(streamFileFromWorkspace('.jenkins/build.properties'))

def git_branches  = [ 'master' ]


/* jobs */
def job_sync_trigger = "${cfg.jenkins_folder}/job-sync-trigger"

folder {
  name cfg.jenkins_folder
}

job {
  name job_sync_trigger
  description '.jenkins job sync'

  scm {
    git {
      remote {
        github "${cfg.github_owner}/${cfg.github_repo}"
        
        credentials "${cfg.github_creds}"
      }
      
      git_branches.each { git_branch ->
        branch git_branch
      }
    }
  }

  triggers {
    scm '' // required for triggering
  }

  conditionalSteps {
    condition {
      shell 'git show --pretty="format:" --name-only | grep ".jenkins"'
    }

    dsl {
      removeAction 'DELETE'
      external '.jenkins/*.groovy'
    }
  }
}
