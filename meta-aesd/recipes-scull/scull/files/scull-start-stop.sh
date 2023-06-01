#!/bin/sh

case "$1" in
    start)
	echo "loading scull"
	/usr/bin/scull_load scull
	;;
    stop)
	echo "removing scull"
	/usr/bin/scull_unload scull.ko
	;;
    *)
	echo "Usage: $0 {start|stop}"
	exit 1
esac

exit 0
