/* variables */
def common = new Properties()
//common.load(streamFileFromWorkspace('dotjenkins/job-sync', '.jenkins/build.properties'))

def cfg = new ConfigSlurper().parse(readFileFromWorkspace('dotjenkins/job-sync', '.jenkins/build.properties'))

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
