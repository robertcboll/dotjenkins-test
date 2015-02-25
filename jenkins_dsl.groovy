job {
  name "generated-test-build"

  scm {
    git {
      remote {
        url "https://github.com/robertcboll/dotjenkins-test"
      }
      branch "master"
    }
  }

  steps {
    shell "./build.sh"
  }
}
