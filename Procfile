web: target/universal/stage/bin/socialflow -Dhttp.port=${PORT} -DapplyEvolutions.default=true -Dapplication.secret=${APPLICATION_SECRET}

dev: play -Dtwitter.access_token=${TWITTER_ACCESS_TOKEN} -Dtwitter.access_token_secret=${TWITTER_ACCESS_TOKEN_SECRET} -Dtwitter.consumer_key=${TWITTER_CONSUMER_KEY} -Dtwitter.consumer_secret=${TWITTER_CONSUMER_SECRET} run
