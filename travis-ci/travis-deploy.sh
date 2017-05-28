#!/usr/bin/env bash

if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
	openssl aes-256-cbc -K $encrypted_efec3258f55d_key -iv $encrypted_efec3258f55d_iv \
		-in travis-ci/codesigning.asc.enc -out travis-ci/codesigning.asc -d
	gpg --fast-import travis-ci/codesigning.asc
	./mvnw --projects plugin,ruleset --settings travis-settings.xml -Dskip-Tests=true -P release -B deploy
fi

