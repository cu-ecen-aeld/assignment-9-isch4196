# Recipe created by recipetool
# This is the basis of a recipe and may need further editing in order to be fully functional.
# (Feel free to remove these comments when editing.)

# WARNING: the following LICENSE and LIC_FILES_CHKSUM values are best guesses - it is
# your responsibility to verify that the values are complete and correct.
#
# The following license files were not able to be identified and are
# represented as "Unknown" below, you will need to check them yourself:
#   LICENSE
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://LICENSE;md5=f098732a73b5f6f3430472f5b094ffdb"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignment-7-isch4196.git;protocol=ssh;branch=main \
	   file://scull-start-stop.sh \
	  "

# Modify these as desired
PV = "1.0+git${SRCPV}"
SRCREV = "9d1d6dcc651aebae5c3bf7657d702b2bff35b1ae"

S = "${WORKDIR}/git"

inherit module

EXTRA_OEMAKE:append:task-install = " -C ${STAGING_KERNEL_DIR} M=${S}/scull"
EXTRA_OEMAKE += "KERNELDIR=${STAGING_KERNEL_DIR}"

inherit update-rc.d
INITSCRIPT_PACKAGES = "${PN}"
INITSCRIPT_NAME:${PN} = "scull-start-stop.sh"

do_install () { 
	   install -d ${D}${bindir}
	   install -m 0755 ${S}/scull/scull_load ${D}${bindir}
	   install -m 0755 ${S}/scull/scull_unload ${D}${bindir}

	   install -d ${D}${sysconfdir}/init.d
	   install -m 0755 ${WORKDIR}/scull-start-stop.sh ${D}${sysconfdir}/init.d

	   install -d ${D}/lib/modules/${KERNEL_VERSION}/extra
	   install -m 0755 ${S}/scull/scull.ko ${D}/lib/modules/${KERNEL_VERSION}/extra
}

FILES:${PN} += " \
	       ${bindir}/scull_load \
	       ${bindir}/scull_unload \
	       ${sysconfdir}/init.d/scull-start-stop.sh \
	       "

# need to inhibit package strip else insmod complains about .ko file being stripped
INHIBIT_PACKAGE_STRIP = "1"
