#!/usr/bin/env bash

if [ "$TRAVIS_BRANCH" = 'master' ] && [ "$TRAVIS_PULL_REQUEST" == 'false' ]; then
	echo "Preparing to deploy to nexus..."
	openssl aes-256-cbc -K $encrypted_efec3258f55d_key -iv $encrypted_efec3258f55d_iv \
		-in travis-ci/codesigning.asc.enc -out travis-ci/codesigning.asc -d
	echo "Signing key decrypted"
	gpg --batch --fast-import travis-ci/codesigning.asc
	echo "Signing key imported"
	./mvnw --projects plugin,ruleset --settings travis-ci/travis-settings.xml \
		-Dskip-Tests=true -P release -B deploy
	echo "Deploy complete"
else
	echo "Not deploying"
	echo "  TRAVIS_BRANCH: $TRAVIS_BRANCH"
	echo "  TRAVIS_PULL_REQUEST: $TRAVIS_PULL_REQUEST"
fi

