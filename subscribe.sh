curl -X POST \
  http://localhost:8082/jaws/subscription/morganstanley.com/predictions \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 52da8890-9414-18b3-5669-faf942293ea6'

curl -X POST \
  http://localhost:8082/jaws/subscription/wellsfargo.com/predictions \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 52da8890-9414-18b3-5669-faf942293ea6'

curl -X POST \
  http://localhost:8082/jaws/subscription/morganstanley.com/alerts \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 52da8890-9414-18b3-5669-faf942293ea6'

curl -X POST \
  http://localhost:8082/jaws/subscription/morganstanley.com/events \
  -H 'cache-control: no-cache' \
  -H 'postman-token: 52da8890-9414-18b3-5669-faf942293ea6'
