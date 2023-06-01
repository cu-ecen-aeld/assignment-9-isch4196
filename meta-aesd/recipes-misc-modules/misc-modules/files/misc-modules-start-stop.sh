#!/bin/sh

case "$1" in
    start)
	echo "loading misc-modules"
	modprobe hello
	/usr/bin/module_load faulty
	;;
    stop)
	echo "removing misc-modules"
	rmmod hello
	/usr/bin/module_unload faulty.ko
	;;
    *)
	echo "Usage: $0 {start|stop}"
	exit 1
esac

exit 0
