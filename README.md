#RecursiveHttpProxy

This http proxy acts as regular http proxy with socks upstreams, but also analyzes the reposnse location header.
If it sees big brother redirection in location header - it redoes the request thru next upstream socks.

Socks auth supported.

###Usage with auth:
````
java -jar RecursiveHttpProxy.jar [bindip:]bindport lawfilter.ertelecom[:info.rt.ru[:info.ertelecom]] proxyusername:proxypassword socks1:port1 socks2:port2 socks3:port3
````

###Usage without auth:
```
java -jar RecursiveHttpProxy.jar [bindip:]bindport lawfilter.ertelecom[:info.rt.ru[:info.ertelecom]] null socks1:port1 socks2:port2 socks3:port3
```

###Tested on
* Few Russian ISPs with DPI which do various HTTP response tampering with location header to their bullshit landing page
* HTTP torrent trackers announce properly thru this proxy
* Transparent redirection on gateway from port 80 to this proxy (works out of the box since the proxy parses Host header)

###Not tested
* HTTPS

Well it should be working since the code is based on other project, and it had HTTPS implemented already. But why do you need to route HTTPS into HTTP proxy? If you want to bypass some firewall better route everything on 443 port to some socks or tunnel

###Credits
To this guy:
https://github.com/zms351/JavaHttpProxy/

The project is based on his code.

Although their README is encrypted with some chinese cipher tho the code is clear, working and what I was searching for all day long. I needed some simple and working proxy implementation but all projects that exist either too oversmart and/or just cant do separate socks per connection due to connection caching and keepalive (which is really crucial here).

###Bugs
Fix yourself and do a pull request. I believe in you, you can do it!