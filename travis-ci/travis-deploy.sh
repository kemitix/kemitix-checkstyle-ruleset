#!/usr/bin/env bash

## Only deploy from Travis-CI. Some CIs, like Shippable, lie by setting TRAVIS=true.
## Currently, Shippable, does not set TRAVIS_LANGUAGE, but Travis-CI does.
if [ "$TRAVIS_LANGUAGE" = "java" ] && [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
	openssl aes-256-cbc -K $encrypted_efec3258f55d_key -iv $encrypted_efec3258f55d_iv \
		-in travis-ci/codesigning.asc.enc -out travis-ci/codesigning.asc -d
	gpg --batch --fast-import travis-ci/codesigning.asc
	./mvnw --projects plugin,ruleset --settings travis-ci/travis-settings.xml \
		-Dskip-Tests=true -P release -B deploy
fi

