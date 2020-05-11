
#-------------------------------------------------------------------------------------------------------------
# Copyright (c) Microsoft Corporation. All rights reserved.
# Licensed under the MIT License. See https://go.microsoft.com/fwlink/?linkid=2090316 for license information.
#-------------------------------------------------------------------------------------------------------------
FROM ubuntu:18.04

# This Dockerfile adds a non-root user with sudo access. Use the "remoteUser"
# property in devcontainer.json to use it. On Linux, the container user's GID/UIDs
# will be updated to match your local UID/GID (when using the dockerFile property).
# See https://aka.ms/vscode-remote/containers/non-root-user for details.
ARG USERNAME=vscode
ARG USER_UID=1000
ARG USER_GID=$USER_UID

# Set to false to skip installing zsh and Oh My ZSH!
ARG INSTALL_ZSH="true"

# Location and expected SHA for common setup script - SHA generated on release
ARG COMMON_SCRIPT_SOURCE="https://raw.githubusercontent.com/microsoft/vscode-dev-containers/master/script-library/common-debian.sh"
ARG COMMON_SCRIPT_SHA="dev-mode"

# Avoid warnings by switching to noninteractive
ENV DEBIAN_FRONTEND=noninteractive

# Configure apt and install packages
RUN apt-get update \
   && apt-get -y install --no-install-recommends apt-utils dialog wget ca-certificates 2>&1 \
   #
   # Verify git, common tools / libs installed, add/modify non-root user, optionally install zsh
   && wget -q -O /tmp/common-setup.sh $COMMON_SCRIPT_SOURCE \
   && if [ "$COMMON_SCRIPT_SHA" != "dev-mode" ]; then echo "$COMMON_SCRIPT_SHA /tmp/common-setup.sh" | sha256sum -c - ; fi \
   && /bin/bash /tmp/common-setup.sh "$INSTALL_ZSH" "$USERNAME" "$USER_UID" "$USER_GID" \
   && rm /tmp/common-setup.sh \
   #
   # Clean up
   && apt-get autoremove -y \
   && apt-get clean -y \
   && rm -rf /var/lib/apt/lists/*

# Switch back to dialog for any ad-hoc use of apt-get
ENV DEBIAN_FRONTEND=dialog

# ** [Optional] Uncomment this section to install additional packages. **

ENV DEBIAN_FRONTEND=noninteractive
RUN apt-get update \
   && apt-get -y install --no-install-recommends zip dirmngr gpg-agent gnupg-agent socat openjdk-11-jdk software-properties-common \
   && curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add - \
   && add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" \
   && apt-get update \
   && apt-get install docker-ce-cli \
   && wget https://piccolo.link/sbt-1.3.10.tgz -O /opt/sbt.tgz \
   && tar xvzf /opt/sbt.tgz -C /opt \
   && rm /opt/sbt.tgz \
   && ln -s /opt/sbt/bin/sbt /usr/bin/sbt \
   && add-apt-repository ppa:deadsnakes/ppa \
   && apt-get -y install --no-install-recommends python3.7 python3-pip \
   && rm /usr/bin/python3 \
   && ln -s /usr/bin/python3.7 /usr/bin/python3 \
   && ln -s /usr/bin/python3 /usr/bin/python \
   && ln -s /usr/bin/pip3 /usr/bin/pip \
   && pip install setuptools \
   && pip install wheel \
   && pip install pypandoc \
   && pip install ipython \
   && pip install numpy \ 
   && pip install pyarrow \
   && pip install pandas  \
   && pip install pyspark \
   #
   # Clean up
   && apt-get autoremove -y \
   && apt-get clean -y \
   && rm -rf /var/lib/apt/lists/*
ENV DEBIAN_FRONTEND=dialog
ENV COURSIER_CACHE=/home/$USERNAME/

