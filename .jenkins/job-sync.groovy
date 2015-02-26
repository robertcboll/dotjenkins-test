/* variables */
def props = new Properties()
props.load(streamFileFromWorkspace('.jenkins/build.properties'))
def cfg = new ConfigSlurper().parse(props)

def git_branches  = [ 'master' ]


/* jobs */
def job_sync = "${cfg.jenkins_folder}/job-sync"

folder {
  name cfg.jenkins_folder
}

job {
  name job_sync
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

  steps {
    dsl {
      removeAction 'DELETE'
      external '.jenkins/*.groovy'
    }
  }
}
