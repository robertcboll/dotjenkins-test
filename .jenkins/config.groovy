import groovy.json.JsonSlurper

class config {
  static def cfgstr = '''

    github {
      repo = ""
      creds = ""
    }

    jenkins {
      folder = "foldername"
    }

'''

  static def cfg = new ConfigSlurper().parse(cfgstr)
}
