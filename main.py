import re
import urllib
import urllib2
from time import sleep
 
def do_http(url, data=None):
    if data:
        return urllib2.urlopen(urllib2.Request(url, data)).read()
    else:
        return urllib2.urlopen(urllib2.Request(url)).read()
 
if __name__ == '__main__':
    # edit these values!
    summoner_names = ['summoner1', 'summoner2']
    region = 'na'
    time_to_sleep = 60
    # NO MORE EDITING! :D
    base_url = 'http://%s.op.gg/' % region
    while True:
        for summoner_name in summoner_names:
            print "Checking %s." % summoner_name
            data = urllib.urlencode({'userName': summoner_name.replace(' ', '+'), 'force' : 'true'})
            response = do_http(base_url + 'summoner/ajax/spectator/', data)
            regex = re.compile("gameId=(\d+)\"")
            try:
                gameId = regex.search(response).groups()[0]
                response = do_http(base_url + 'summoner/ajax/requestRecording.json/gameId=%s' % gameId)
            except:
                pass
        print  "Sleeping for %s seconds." % time_to_sleep
        sleep(time_to_sleep)
