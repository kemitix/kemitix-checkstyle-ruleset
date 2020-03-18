#!/usr/bin/env bash

# Decrypts the signing key in .github/codesigning.asc.enc
# Imports that key
# Uses .github/settings.xml and the release profile to deploy

echo "deploy.sh: Starting..."

(
    cd .github

    echo "Retrieving GPG Private KEY"
    gpg --quiet \
        --batch \
        --yes \
        --decrypt \
        --passphrase="${GPG_PASSPHRASE}" \
        --output codesigning.asc \
        codesigning.asc.gpg

    echo "Loading signing key"
    gpg --batch \
        --fast-import codesigning.asc
)

echo "Releasing..."
mvn -pl ruleset tiles \
    --settings .github/settings.xml \
    -Dskip-Tests=true \
    -P release \
    -B \
    deploy

echo "deploy.sh: Done."
