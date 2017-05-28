#!/usr/bin/env bash

if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
	openssl aes-256-cbc -K $encrypted_e3200738ab3d_key -iv $encrypted_e3200738ab3d_iv \
		-in travis-ci/codesigning.asc.enc -out travis-ci/codesigning.asc -d
	gpg --fast-import travis-ci/codesigning.asc
	./mvnw --projects plugin,ruleset --settings travis-ci/travis-settings.xml \
		-Dskip-Tests=true -P release -B deploy
fi

