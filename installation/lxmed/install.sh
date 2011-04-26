#!/bin/bash

# user must be root
if [ "$(id -u)" != "0" ]; then
   echo "Installation must be run as root" 1>&2
   exit 1
fi

echo Installing application...
echo

# remove any existing files and folders
echo Removing any previous installed files and folders...
rm -r -v -f /opt/lxmed
rm -v /usr/share/applications/lxmed.desktop
rm -v /bin/lxmed

# copy new files
mkdir -v /opt/lxmed
cp -v content/lxmed /bin
chmod -v +x /bin/lxmed
cp -v content/LXMenuEditor.jar /opt/lxmed
cp -v content/uninstall.sh /opt/lxmed
chmod -v +x /opt/lxmed/uninstall.sh
cp -v content/lxmed.png /opt/lxmed
cp -v content/lxmed.desktop /usr/share/applications
echo
echo Installation sucessfully completed. Enter lxmed to run application.
echo
