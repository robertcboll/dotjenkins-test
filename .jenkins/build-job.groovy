def cfg = new Properties(); cfg.load(streamFileFromWorkspace('.jenkins/build.properties'))
def git_branches  = [ 'master' ]

/* jobs */
def build = "${cfg.jenkins_folder}/build"

folder {
  name cfg.jenkins_folder
}

job {
  name build
  description 'a simple build'

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

  steps {
    shell 'echo "hello worldddd"'
  }
}
