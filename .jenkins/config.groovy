import groovy.json.JsonSlurper

class config {
  static String cfgstr = '''

    github {
      repo = ""
      creds = ""
    }

    jenkins {
      folder = "foldername"
    }

'''

  static def cfg = new ConfigSlurper().parse("jenkins {  folder = 'foldername' }")
}
